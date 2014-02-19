package com.rayboot.pinyincrazy;

import org.holoeverywhere.widget.Toast;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;

import com.iflytek.speech.ErrorCode;
import com.iflytek.speech.ISpeechModule;
import com.iflytek.speech.InitListener;
import com.iflytek.speech.SpeechConstant;
import com.iflytek.speech.SpeechSynthesizer;
import com.iflytek.speech.SpeechUtility;
import com.iflytek.speech.SynthesizerListener;
import com.rayboot.pinyincrazy.obj.PinyinDataObj;
import com.rayboot.pinyincrazy.utils.ApkInstaller;
import com.rayboot.pinyincrazy.utils.DataUtil;
import com.rayboot.pinyincrazy.utils.Util;
import com.umeng.analytics.MobclickAgent;

import de.keyboardsurfer.android.widget.crouton.Crouton;

public class GameActivity extends MyBaseActivity {
	private PinyinDataObj pinyinData;
	@InjectView(R.id.tvKeyword)
	TextView tvKeyword;
	@InjectView(R.id.tvTone)
	TextView tvTone;
	@InjectView(R.id.tvTip)
	TextView tvTip;
	@InjectView(R.id.tvTitle)
	TextView tvTitle;
	@InjectView(R.id.llAddCoin)
	LinearLayout llAddCoin;
	@InjectView(R.id.tvCoinCount)
	TextView tvCoinCount;

	String[] toneArray = { " ", "―", "╱", "∨", "╲" };
	int rightCount = 0;
	private SpeechSynthesizer mTts = null;
	DialogsAlertDialogFragment dialog = new DialogsAlertDialogFragment();
	int curType = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Holo_Theme_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		ButterKnife.inject(this);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		curType = getIntent().getIntExtra("is_wujin", 0);
		if (curType == 0) {
			rightCount = PinyinDataObj.getAllRightDatas().size();
			getSupportActionBar().setSubtitle(
					"共" + PinyinDataObj.getAllDatas().size() + "");
			getSupportActionBar().setTitle("第" + (rightCount + 1) + "关");
		} else {
			getSupportActionBar().setTitle("无尽挑战");
		}
		setGameData();
		dialog.getBuilder(this).setTitle("提示");
		changeCoin(0);
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		if (mTts == null) {
			mTts = new SpeechSynthesizer(this, mTtsInitListener);
		}
		super.onResume();
		if (curType == 0) {
			getSupportActionBar().setTitle("第" + (rightCount + 1) + "关");
		} else {
			getSupportActionBar().setTitle("无尽挑战");
		}
	}

	public void setGameData() {
		if (curType == 0) {
			pinyinData = PinyinDataObj.getChuangGuanRandomData();
		} else {
			pinyinData = PinyinDataObj.getRandomData();
		}
		if (pinyinData == null) {
			Toast.makeText(this, "您已通关，先试试无尽模式吧", Toast.LENGTH_SHORT).show();
			finish();
			return;
		}
		tvTitle.setText(pinyinData.keychar);
		tvTip.setText(pinyinData.title);
		onClearClick(null);
	}

	public void onKeyClick(View view) {
		String keyString = ((TextView) view).getText().toString();
		String nowString = tvKeyword.getText().toString();
		tvKeyword.setText(nowString + keyString);
	}

	public void onClearClick(View view) {
		MobclickAgent.onEvent(this, "101");
		tvKeyword.setText("");
		tvTone.setText("");
		tvTone.setTag("0");
	}

	public void onOKClick(View view) {
		MobclickAgent.onEvent(this, "100");
		if (pinyinData.keypinyin.trim().equals(
				tvKeyword.getText().toString().trim())
				&& pinyinData.keytone == Integer.valueOf((String) tvTone
						.getTag())) {
			MobclickAgent.onEvent(this, "1000");
			changeCoin(3);
			// Crouton.makeText(this, "恭喜你答对了", Style.CONFIRM).show();
			Toast.makeText(this, "恭喜你答对了", Toast.LENGTH_SHORT).show();
			pinyinData.isRight = 1;
			pinyinData.save();
			rightCount++;
			getSupportActionBar().setTitle("第" + (rightCount + 1) + "关");
			setGameData();
			return;
		} else {
			MobclickAgent.onEvent(this, "1001");
			changeCoin(-2);
			// Crouton.makeText(this, "回答错误", Style.ALERT).show();
			Toast.makeText(this, "回答错误。", Toast.LENGTH_SHORT).show();
			pinyinData.wrong += 1;
			pinyinData.save();
		}
	}

	public void onToneClick(View view) {
		String toneString = ((TextView) view).getText().toString();
		if (toneString.equals("轻声")) {
			toneString = "";
		}
		tvTone.setText(toneString);
		tvTone.setTag(view.getTag());
	}

	public void onHelpClick(View view) {
		switch (Integer.valueOf((String) view.getTag())) {
		case 1:
			// 求助好友
			MobclickAgent.onEvent(this, "103");
			changeCoin(1);
			String shareInfo = "我在使用 #拼音达人# 问你个事，" + pinyinData.title + "的"
					+ pinyinData.keychar + "字怎么读，知道吗？";
			Util.shareSomethingText(this, "分享", shareInfo);
			break;
		case 2:
			// 显示答案
			MobclickAgent.onEvent(this, "102");
			if (!changeCoin(-50)) {
				Toast.makeText(this, "非常抱歉，您的金币不足。", Toast.LENGTH_LONG).show();
				return;
			}
			tvKeyword.setText(pinyinData.keypinyin);
			tvTone.setText(toneArray[pinyinData.keytone]);
			tvTone.setTag(pinyinData.keytone + "");
			break;
		case 3:
			// 听一听
			MobclickAgent.onEvent(this, "104");
			try {
				ApplicationInfo appInfo = this.getPackageManager()
						.getApplicationInfo(getPackageName(),
								PackageManager.GET_META_DATA);
				String channelName = appInfo.metaData
						.getString("UMENG_CHANNEL");
				if (!channelName.equals("mmw") && !changeCoin(-10)) {
					Toast.makeText(this, "非常抱歉，您的金币不足。", Toast.LENGTH_LONG)
							.show();
					return;
				}
			} catch (NameNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if (checkIsInstall() && mTts != null) {
				int code = mTts.startSpeaking(pinyinData.title, mTtsListener);
				if (code != 0) {
					// showTip("start speak error : " + code);
				} else {
					// showTip("start speak success.");
				}
			}
			break;
		case 4:
			// 跳过该题
			MobclickAgent.onEvent(this, "105");
			if (!changeCoin(-5)) {
				Toast.makeText(this, "非常抱歉，您的金币不足。", Toast.LENGTH_LONG).show();
				return;
			}
			setGameData();
			break;
		case 5:
			// 字面意思
			MobclickAgent.onEvent(this, "106");
			changeCoin(-1);
			Intent intent = new Intent(this, TipActivity.class);
			intent.putExtra("data_answer", pinyinData.title);
			intent.putExtra("data_tip", pinyinData.url);
			intent.putExtra("data_pinyin", pinyinData.pinyin);
			intent.putExtra("data_key_pinyin", pinyinData.keypinyin);
			intent.putExtra("data_key_char", pinyinData.keychar);
			this.startActivity(intent);
			break;
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// This uses the imported MenuItem from ActionBarSherlock
		switch (item.getItemId()) {
		case android.R.id.home:
			finish();
			break;
		default:
			break;
		}
		return true;
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (mTts != null) {
			mTts.stopSpeaking(mTtsListener);
			// 退出时释放连接
			mTts.destory();
		}
		Crouton.cancelAllCroutons();
	}

	/**
	 * 初期化监听。
	 */
	private InitListener mTtsInitListener = new InitListener() {

		@Override
		public void onInit(ISpeechModule arg0, int code) {
			if (code == ErrorCode.SUCCESS) {
				mTts.setParameter(SpeechSynthesizer.VOICE_NAME, "xiaoyan");
				mTts.setParameter(SpeechConstant.ENGINE_TYPE, "local");
				mTts.setParameter(SpeechSynthesizer.SPEED, "25");
				mTts.setParameter(SpeechSynthesizer.PITCH, "50");
				mTts.setParameter(SpeechConstant.PARAMS,
						"tts_audio_path=/sdcard/tts.pcm");
			}
		}
	};
	/**
	 * 合成回调监听。
	 */
	private SynthesizerListener mTtsListener = new SynthesizerListener.Stub() {
		@Override
		public void onBufferProgress(int progress) throws RemoteException {
		}

		@Override
		public void onCompleted(int code) throws RemoteException {
		}

		@Override
		public void onSpeakBegin() throws RemoteException {
		}

		@Override
		public void onSpeakPaused() throws RemoteException {
		}

		@Override
		public void onSpeakProgress(int progress) throws RemoteException {
		}

		@Override
		public void onSpeakResumed() throws RemoteException {
		}
	};

	public boolean checkIsInstall() {
		// 没有可用的引擎
		if (SpeechUtility.getUtility(this).queryAvailableEngines() == null
				|| SpeechUtility.getUtility(this).queryAvailableEngines().length <= 0) {
			DialogsAlertDialogFragment dialog = new DialogsAlertDialogFragment();
			dialog.getBuilder(this).setMessage(
					getString(R.string.download_confirm_msg));
			dialog.getBuilder(this).setNegativeButton(
					R.string.dialog_cancel_button, null);
			dialog.getBuilder(this).setPositiveButton(
					getString(R.string.dialog_confirm_button),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface,
								int i) {
							String url = SpeechUtility.getUtility(
									GameActivity.this).getComponentUrl();
							ApkInstaller
									.openDownloadWeb(GameActivity.this, url);
						}
					});

			dialog.show(this);
			return false;
		}
		return true;
	}

	public boolean changeCoin(int count) {
		boolean res = DataUtil.changeCoin(this, count);
		tvCoinCount.setText(" "
				+ DataUtil.getInfoFromShared(this, "pinyin_coin") + " 金币 ");
		return res;
	}
}
