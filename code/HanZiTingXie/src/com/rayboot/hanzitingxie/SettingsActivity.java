package com.rayboot.hanzitingxie;

import org.holoeverywhere.widget.SeekBar;
import org.holoeverywhere.widget.SeekBar.OnSeekBarChangeListener;
import org.holoeverywhere.widget.Toast;

import android.content.DialogInterface;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.MenuItem;
import android.view.View;

import com.iflytek.speech.SpeechConstant;
import com.iflytek.speech.SpeechSynthesizer;
import com.iflytek.speech.SpeechUtility;
import com.iflytek.speech.SynthesizerListener;
import com.rayboot.hanzitingxie.obj.WordData;
import com.rayboot.hanzitingxie.util.ApkInstaller;
import com.rayboot.hanzitingxie.util.DataUtil;

public class SettingsActivity extends MyBaseActivity {
	SeekBar sb1;
	SeekBar sb2;
	SeekBar sb3;
	// 语音合成对象
	private SpeechSynthesizer mTts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		setTheme(R.style.Holo_Theme_Light_DarkActionBar);
		super.onCreate(savedInstanceState);
		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
		setContentView(R.layout.activity_settings);
		sb1 = (SeekBar) findViewById(R.id.seekBar1);
		sb2 = (SeekBar) findViewById(R.id.seekBar2);
		sb3 = (SeekBar) findViewById(R.id.seekBar3);
		sb1.setOnSeekBarChangeListener(onSeekBarChangeListener);
		sb2.setOnSeekBarChangeListener(onSeekBarChangeListener);
		sb3.setOnSeekBarChangeListener(onSeekBarChangeListener);
		sb1.setProgress(DataUtil.YU_SU);
		sb2.setProgress(DataUtil.YIN_DIAO);
		sb3.setProgress(DataUtil.YIN_LIANG);

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
									SettingsActivity.this).getComponentUrl();
							String assetsApk = "SpeechService.apk";
//							if (!ApkInstaller.installFromAssets(
//									SettingsActivity.this, assetsApk)) {
//								Toast.makeText(SettingsActivity.this, "安装失败",
//										Toast.LENGTH_SHORT).show();
//							}
							ApkInstaller.openDownloadWeb(SettingsActivity.this, url);
						}
					});

			dialog.show(this);
			return;
		}
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();

		mTts = new SpeechSynthesizer(this, null);
	}

	private OnSeekBarChangeListener onSeekBarChangeListener = new OnSeekBarChangeListener() {

		@Override
		public void onStopTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub
			if (seekBar.equals(sb1)) {
				DataUtil.YU_SU = seekBar.getProgress();
			} else if (seekBar.equals(sb2)) {
				DataUtil.YIN_DIAO = seekBar.getProgress();
			} else if (seekBar.equals(sb3)) {
				DataUtil.YIN_LIANG = seekBar.getProgress();
			}
			// 设置你申请的应用appid
			SpeechUtility.getUtility(SettingsActivity.this)
					.setAppid("5244f7f4");

			mTts.setParameter(SpeechConstant.ENGINE_TYPE, "local");
			// 发音人。
			mTts.setParameter(SpeechSynthesizer.VOICE_NAME, "xiaoyan");
			// 语速（0~100）。
			mTts.setParameter(SpeechSynthesizer.SPEED, DataUtil.YU_SU + "");
			// 音调（0~100）。
			mTts.setParameter(SpeechSynthesizer.PITCH, DataUtil.YIN_DIAO + "");
			// 音量（0~100）。
			mTts.setParameter(SpeechSynthesizer.VOLUME, DataUtil.YIN_LIANG + "");
			mTts.startSpeaking("汉字听写", mTtsListener);
		}

		@Override
		public void onStartTrackingTouch(SeekBar seekBar) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProgressChanged(SeekBar seekBar, int progress,
				boolean fromUser) {
			// TODO Auto-generated method stub

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

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mTts.stopSpeaking(mTtsListener);
		// 退出时释放连接
		mTts.destory();
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

	public void onExport(View view) {
		if (WordData.getChuangGuanRandomData() != null) {
			Toast.makeText(this, "您还未通关，不能导入自定义词语", Toast.LENGTH_SHORT).show();
			return;
		}
		int wenzibi = DataUtil.getInfoFromShared(this, "wenzibi");
		if (wenzibi < 200) {
			Toast.makeText(this, "文字币至少需要200，才能导入自定义词语", Toast.LENGTH_SHORT)
					.show();
			return;
		}
	}
}
