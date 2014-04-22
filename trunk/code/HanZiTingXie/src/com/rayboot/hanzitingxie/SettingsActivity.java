package com.rayboot.hanzitingxie;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.RemoteException;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import com.actionbarsherlock.view.MenuItem;
import com.iflytek.speech.SpeechConstant;
import com.iflytek.speech.SpeechSynthesizer;
import com.iflytek.speech.SpeechUtility;
import com.iflytek.speech.SynthesizerListener;
import com.rayboot.hanzitingxie.util.ApkInstaller;
import com.rayboot.hanzitingxie.util.DataUtil;

public class SettingsActivity extends MyBaseActivity
{
    SeekBar sb1;
    SeekBar sb2;
    SeekBar sb3;
    // 语音合成对象
    private SpeechSynthesizer mTts;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
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
                || SpeechUtility.getUtility(this).queryAvailableEngines().length
                <= 0)
        {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setMessage(getString(R.string.download_confirm_msg));
            dialogBuilder.setNegativeButton(R.string.dialog_cancel_button,
                    null);
            dialogBuilder.setPositiveButton(
                    getString(R.string.dialog_confirm_button),
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialoginterface,
                                int i)
                        {
                            String url = SpeechUtility.getUtility(
                                    SettingsActivity.this).getComponentUrl();
                            ApkInstaller.openDownloadWeb(SettingsActivity.this,
                                    url);
                        }
                    });

            dialogBuilder.create().show();
            return;
        }
    }

    @Override
    public void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();

        mTts = new SpeechSynthesizer(this, null);
    }

    private OnSeekBarChangeListener onSeekBarChangeListener =
            new OnSeekBarChangeListener()
            {

                @Override
                public void onStopTrackingTouch(SeekBar seekBar)
                {
                    // TODO Auto-generated method stub
                    if (seekBar.equals(sb1))
                    {
                        DataUtil.YU_SU = seekBar.getProgress();
                    }
                    else if (seekBar.equals(sb2))
                    {
                        DataUtil.YIN_DIAO = seekBar.getProgress();
                    }
                    else if (seekBar.equals(sb3))
                    {
                        DataUtil.YIN_LIANG = seekBar.getProgress();
                    }
                    // 设置你申请的应用appid
                    SpeechUtility.getUtility(SettingsActivity.this)
                            .setAppid("5244f7f4");

                    mTts.setParameter(SpeechConstant.ENGINE_TYPE, "local");
                    // 发音人。
                    mTts.setParameter(SpeechSynthesizer.VOICE_NAME, "xiaoyan");
                    // 语速（0~100）。
                    mTts.setParameter(SpeechSynthesizer.SPEED,
                            DataUtil.YU_SU + "");
                    // 音调（0~100）。
                    mTts.setParameter(SpeechSynthesizer.PITCH,
                            DataUtil.YIN_DIAO + "");
                    // 音量（0~100）。
                    mTts.setParameter(SpeechSynthesizer.VOLUME,
                            DataUtil.YIN_LIANG + "");
                    mTts.startSpeaking("汉字听写", mTtsListener);
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar)
                {
                    // TODO Auto-generated method stub

                }

                @Override
                public void onProgressChanged(SeekBar seekBar, int progress,
                        boolean fromUser)
                {
                    // TODO Auto-generated method stub

                }
            };

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener.Stub()
    {
        @Override
        public void onBufferProgress(int progress) throws RemoteException
        {
        }

        @Override
        public void onCompleted(int code) throws RemoteException
        {
        }

        @Override
        public void onSpeakBegin() throws RemoteException
        {
        }

        @Override
        public void onSpeakPaused() throws RemoteException
        {
        }

        @Override
        public void onSpeakProgress(int progress) throws RemoteException
        {
        }

        @Override
        public void onSpeakResumed() throws RemoteException
        {
        }
    };

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mTts.stopSpeaking(mTtsListener);
        // 退出时释放连接
        mTts.destory();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        switch (item.getItemId())
        {
        case android.R.id.home:
            finish();
            break;
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }
}
