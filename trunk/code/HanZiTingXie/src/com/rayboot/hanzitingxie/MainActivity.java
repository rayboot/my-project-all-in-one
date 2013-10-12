package com.rayboot.hanzitingxie;

import java.util.Random;

import org.holoeverywhere.ThemeManager;
import org.holoeverywhere.widget.Toast;

import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.RemoteException;
import android.support.v7.widget.PopupMenu;
import android.support.v7.widget.PopupMenu.OnMenuItemClickListener;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;

import com.iflytek.speech.SpeechConstant;
import com.iflytek.speech.SpeechSynthesizer;
import com.iflytek.speech.SpeechUtility;
import com.iflytek.speech.SynthesizerListener;
import com.rayboot.hanzitingxie.obj.SourceData;
import com.rayboot.hanzitingxie.util.ApkInstaller;
import com.rayboot.hanzitingxie.util.DataUtil;

public class MainActivity extends MyBaseActivity {
	String TAG = "MainActivity";
	// 语音合成对象
	private SpeechSynthesizer mTts;
	private SourceData curData;
	TextView tvBi;
	EditText[] ets = new EditText[4];
	TextView[] tvs = new TextView[4];
	LinearLayout[] lls = new LinearLayout[4];
	int curPlayType = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 初始化合成对象
		curPlayType = getIntent().getIntExtra("cur_play_type",
				MyApplication.PLAY_TYPE_CHUANGGUAN);
		mTts = new SpeechSynthesizer(this, null);

		tvBi = (TextView) findViewById(R.id.tvBi);
		ets[0] = (EditText) findViewById(R.id.et1);
		ets[1] = (EditText) findViewById(R.id.et2);
		ets[2] = (EditText) findViewById(R.id.et3);
		ets[3] = (EditText) findViewById(R.id.et4);
		tvs[0] = (TextView) findViewById(R.id.tv1);
		tvs[1] = (TextView) findViewById(R.id.tv2);
		tvs[2] = (TextView) findViewById(R.id.tv3);
		tvs[3] = (TextView) findViewById(R.id.tv4);
		lls[0] = (LinearLayout) findViewById(R.id.ll1);
		lls[1] = (LinearLayout) findViewById(R.id.ll2);
		lls[2] = (LinearLayout) findViewById(R.id.ll3);
		lls[3] = (LinearLayout) findViewById(R.id.ll4);
		for (EditText et : ets) {
			et.addTextChangedListener(watcher);
			et.setOnEditorActionListener(onEditorActionListener);
		}

		setHanzi();
		changeWenZiBi(0);
	}

	public void setHanzi() {
		if (MyApplication.PLAY_TYPE_CHUANGGUAN == curPlayType) {
			curData = SourceData.getChuangGuanRandomData();
			if (curData == null) {
				DialogsAlertDialogFragment dialog = new DialogsAlertDialogFragment();
				dialog.getBuilder(this).setMessage(
						"恭喜您已通关！可更新版本获得更多新词，或者尝试其他模式。");
				dialog.getBuilder(this).setTitle("提示");
				dialog.getBuilder(this).setNegativeButton("尝试其他模式",
						new OnClickListener() {

							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								MainActivity.this.finish();
							}
						});
				dialog.show(this);
				return;
			}
		} else if (MyApplication.PLAY_TYPE_WUJIN == curPlayType) {
			curData = SourceData.getRandomData();
		}

		int count = curData.title.length();
		for (int i = 0; i < lls.length; i++) {
			lls[i].setVisibility(i >= count ? View.GONE : View.VISIBLE);
			ets[i].setText("");
			ets[0].requestFocus();
		}

		String[] pinyinStrings = curData.pinyin.split(" ");
		for (int i = 0; i < pinyinStrings.length; i++) {
			tvs[i].setText(pinyinStrings[i]);
		}
	}

	private OnEditorActionListener onEditorActionListener = new OnEditorActionListener() {

		@Override
		public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
			// TODO Auto-generated method stub
			if (actionId == EditorInfo.IME_ACTION_DONE
					|| actionId == EditorInfo.IME_ACTION_UNSPECIFIED) {
				boolean isFinish = true;
				for (int i = 0; i < ets.length; i++) {
					if (TextUtils.isEmpty(ets[i].getText().toString())
							&& ets[i].isShown()) {
						isFinish = false;
					}
				}
				if (isFinish) {
					onFinish(findViewById(R.id.btnFinish));
				} else {
					Toast.makeText(MainActivity.this, "您还没有写完，不能提交。",
							Toast.LENGTH_SHORT).show();
				}
			}
			return false;
		}
	};

	private TextWatcher watcher = new TextWatcher() {

		@Override
		public void afterTextChanged(Editable s) {
			// TODO Auto-generated method stub
			if (TextUtils.isEmpty(s.toString())) {
				return;
			}
			int realnext = 0;
			for (int i = 0; i < ets.length; i++) {
				int next = i < curData.title.length() - 1 ? i + 1 : i;
				if (ets[i].hasFocus() && ets[next].isShown()) {
					realnext = next;
				}
			}
			ets[realnext].requestFocus();
		}

		@Override
		public void beforeTextChanged(CharSequence s, int start, int count,
				int after) {
			// TODO Auto-generated method stub
		}

		@Override
		public void onTextChanged(CharSequence s, int start, int before,
				int count) {
		}

	};

	public void onTip() {
		Intent intent = new Intent(this, TipActivity.class);
		intent.putExtra("data_answer", curData.title);
		intent.putExtra("data_tip", curData.url);
		startActivity(intent);
	}

	public void onZhengQue() {
		DialogsAlertDialogFragment dialog = new DialogsAlertDialogFragment();
		dialog.getBuilder(this).setMessage("显示一个正确的汉字会扣除20个文字币，是否确认？");
		dialog.getBuilder(this).setTitle("提示");
		dialog.getBuilder(this).setNegativeButton("再想想", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		dialog.getBuilder(this).setPositiveButton("好的", new OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				int count = DataUtil.getInfoFromShared(MainActivity.this,
						"wenzibi");
				if (count - 20 > 0) {
					changeWenZiBi(-20);

					boolean finishTip = false;
					for (int i = 0; i < ets.length; i++) {
						if (TextUtils.isEmpty(ets[i].getText().toString())
								&& ets[i].isShown()) {
							ets[i].setText(curData.title.subSequence(i, i + 1));
							finishTip = true;
							break;
						}
					}
					if (!finishTip) {
						int ram = new Random().nextInt(curData.title.length());
						ets[ram].setText(curData.title
								.subSequence(ram, ram + 1));

					}

				} else {
					dialog.dismiss();
					org.holoeverywhere.widget.Toast.makeText(MainActivity.this,
							"您的游戏币不足20个",
							org.holoeverywhere.widget.Toast.LENGTH_SHORT)
							.show();
				}

			}
		});
		dialog.show(this);
	}

	public void onFinish(View view) {
		if (getCurrentFocus() != null) {
			((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
					.hideSoftInputFromWindow(
							getCurrentFocus().getWindowToken(),
							InputMethodManager.HIDE_NOT_ALWAYS);
		}
		boolean isRight = true;
		for (int i = 0; i < curData.title.length(); i++) {
			if (curData.title.subSequence(i, i + 1).equals(
					ets[i].getText().toString())) {
				// ets[i].setBackgroundColor(getResources().getColor(
				// R.color.answer_right));
			} else {
				// ets[i].setBackgroundColor(getResources().getColor(
				// R.color.answer_wrong));
				isRight = false;
			}
		}

		String temp = "恭喜你，回答正确！\n正确答案：" + curData.title;
		if (isRight) {
			curData.right += 1;
			if (MyApplication.PLAY_TYPE_CHUANGGUAN == curPlayType) {
				curData.isRight = 1;
			}
			changeWenZiBi(5);
		} else {
			curData.wrong += 1;
			temp = "非常抱歉，回答错误。";
		}
		SourceData.updateItem(curData);

		DialogsAlertDialogFragment dialog = new DialogsAlertDialogFragment();
		dialog.getBuilder(this).setMessage(temp);
		dialog.getBuilder(this).setTitle("提示");
		if (isRight) {
			dialog.getBuilder(this).setNegativeButton("下一个",
					new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							setHanzi();
						}
					});
		} else {
			dialog.getBuilder(this).setNegativeButton("再看看",
					new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							dialog.dismiss();
						}
					});
			dialog.getBuilder(this).setPositiveButton("正确答案",
					new OnClickListener() {

						@Override
						public void onClick(DialogInterface dialog, int which) {
							showAnswer();
							dialog.dismiss();
						}
					});
		}
		dialog.show(this);
	}

	private void changeWenZiBi(int cnt) {
		int count = DataUtil.getInfoFromShared(this, "wenzibi");
		DataUtil.setInfoToShared(this, "wenzibi", count + cnt);
		tvBi.setText("文字币：" + DataUtil.getInfoFromShared(this, "wenzibi"));
	}

	private void showAnswer() {
		int count = DataUtil.getInfoFromShared(MainActivity.this, "wenzibi");
		if (count - 50 > 0) {
			for (int i = 0; i < ets.length; i++) {
				if (TextUtils.isEmpty(ets[i].getText().toString())
						&& ets[i].isShown()) {
					ets[i].setText(curData.title.subSequence(i, i + 1));
				}
			}
			Toast.makeText(MainActivity.this, "正确答案已经显示。", Toast.LENGTH_SHORT)
					.show();
			changeWenZiBi(-50);
		} else {
			Toast.makeText(MainActivity.this, "非常抱歉您的文字币不足50.",
					Toast.LENGTH_SHORT).show();
		}
	}

	public void onVoice(View view) {
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
									MainActivity.this).getComponentUrl();
							String assetsApk = "SpeechService.apk";
							processInstall(MainActivity.this, url, assetsApk);
						}
					});

			dialog.show(this);
			return;
		}
		// 设置你申请的应用appid
		SpeechUtility.getUtility(MainActivity.this).setAppid("5244f7f4");

		mTts.setParameter(SpeechConstant.ENGINE_TYPE, "local");
		// 发音人。
		mTts.setParameter(SpeechSynthesizer.VOICE_NAME, "xiaoyan");
		// 语速（0~100）。
		mTts.setParameter(SpeechSynthesizer.SPEED, DataUtil.YU_SU + "");
		// 音调（0~100）。
		mTts.setParameter(SpeechSynthesizer.PITCH, DataUtil.YIN_DIAO + "");
		// 音量（0~100）。
		mTts.setParameter(SpeechSynthesizer.VOLUME, DataUtil.YIN_LIANG + "");
		mTts.startSpeaking(curData.title, mTtsListener);
	}

	/**
	 * 合成回调监听。
	 */
	private SynthesizerListener mTtsListener = new SynthesizerListener.Stub() {
		@Override
		public void onBufferProgress(int progress) throws RemoteException {
			Log.d(TAG, "onBufferProgress :" + progress);
		}

		@Override
		public void onCompleted(int code) throws RemoteException {
			Log.d(TAG, "onCompleted code =" + code);
		}

		@Override
		public void onSpeakBegin() throws RemoteException {
			Log.d(TAG, "onSpeakBegin");
		}

		@Override
		public void onSpeakPaused() throws RemoteException {
			Log.d(TAG, "onSpeakPaused.");
		}

		@Override
		public void onSpeakProgress(int progress) throws RemoteException {
			Log.d(TAG, "onSpeakProgress :" + progress);
		}

		@Override
		public void onSpeakResumed() throws RemoteException {
			Log.d(TAG, "onSpeakResumed.");
		}
	};

	/**
	 * 如果服务组件没有安装，有两种安装方式。 1.直接打开语音服务组件下载页面，进行下载后安装。
	 * 2.把服务组件apk安装包放在assets中，为了避免被编译压缩，修改后缀名为mp3，然后copy到SDcard中进行安装。
	 */
	private void processInstall(Context context, String url, String assetsApk) {
		// 直接下载方式
		// ApkInstaller.openDownloadWeb(context, url);
		// 本地安装方式
		if (!ApkInstaller.installFromAssets(context, assetsApk)) {
			Toast.makeText(MainActivity.this, "安装失败", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mTts.stopSpeaking(mTtsListener);
		// 退出时释放连接
		mTts.destory();
	}

	public void onShowHelp(View view) {
		PopupMenu menu = new PopupMenu(this, view);
		menu.setOnMenuItemClickListener(onMenuItemClickListener);
		menu.inflate(R.menu.menu);
		menu.show();
	}

	private OnMenuItemClickListener onMenuItemClickListener = new OnMenuItemClickListener() {

		@Override
		public boolean onMenuItemClick(MenuItem item) {
			// TODO Auto-generated method stub
			switch (item.getItemId()) {
			case R.id.item_tip:
				onTip();
				break;
			case R.id.item_oneright:
				onZhengQue();
				break;
			case R.id.item_final:
				showAnswer();
				break;

			default:
				break;
			}
			return false;
		}
	};

}
