package com.rayboot.hanzitingxie;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.RemoteException;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.iflytek.speech.SpeechConstant;
import com.iflytek.speech.SpeechSynthesizer;
import com.iflytek.speech.SpeechUtility;
import com.iflytek.speech.SynthesizerListener;
import com.rayboot.hanzitingxie.util.ApkInstaller;

public class MainActivity extends Activity {
	String TAG = "MainActivity";
	// 语音合成对象
	private SpeechSynthesizer mTts;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		// 初始化合成对象
		mTts = new SpeechSynthesizer(this, null);
	}

	public void onVoice(View view) {

		// 没有可用的引擎
		if (SpeechUtility.getUtility(this).queryAvailableEngines() == null
				|| SpeechUtility.getUtility(this).queryAvailableEngines().length <= 0) {
			AlertDialog.Builder dialog = new AlertDialog.Builder(this);
			dialog.setMessage(getString(R.string.download_confirm_msg));
			dialog.setNegativeButton(R.string.dialog_cancel_button, null);
			dialog.setPositiveButton(getString(R.string.dialog_confirm_button),
					new DialogInterface.OnClickListener() {
						public void onClick(DialogInterface dialoginterface,
								int i) {
							String url = SpeechUtility.getUtility(
									MainActivity.this).getComponentUrl();
							String assetsApk = "SpeechService.apk";
							processInstall(MainActivity.this, url, assetsApk);
						}
					});
			dialog.show();
			return;
		}
		// 设置你申请的应用appid
		SpeechUtility.getUtility(MainActivity.this).setAppid("5244f7f4");

		mTts.setParameter(SpeechConstant.ENGINE_TYPE, "local");
		//  发音人。
		mTts.setParameter(SpeechSynthesizer.VOICE_NAME, "xiaoyan");
		// 语速（0~100）。
		mTts.setParameter(SpeechSynthesizer.SPEED, "20");
		// 音调（0~100）。
		mTts.setParameter(SpeechSynthesizer.PITCH, "50");
		//音量（0~100）。
		mTts.setParameter(SpeechSynthesizer.VOLUME, "50");
		mTts.startSpeaking("关关雎鸠在河之洲", mTtsListener);
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

}
