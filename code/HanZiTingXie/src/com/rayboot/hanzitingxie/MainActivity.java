package com.rayboot.hanzitingxie;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteException;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.waps.AppConnect;
import cn.waps.UpdatePointsNotifier;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
import com.iflytek.speech.SpeechConstant;
import com.iflytek.speech.SpeechSynthesizer;
import com.iflytek.speech.SpeechUtility;
import com.iflytek.speech.SynthesizerListener;
import com.rayboot.hanzitingxie.obj.WordData;
import com.rayboot.hanzitingxie.util.ApkInstaller;
import com.rayboot.hanzitingxie.util.DataUtil;
import com.rayboot.hanzitingxie.util.ScreenShot;
import com.rayboot.hanzitingxie.util.Util;
import com.umeng.analytics.MobclickAgent;
import java.util.Random;

public class MainActivity extends MyBaseActivity
{
    String TAG = "MainActivity";
    // 语音合成对象
    private SpeechSynthesizer mTts;
    private WordData curData;
    TextView[] tvCi = new TextView[4];
    TextView[] tvPinyin = new TextView[4];
    LinearLayout[] lls = new LinearLayout[4];
    EditText etInput;
    TextView tvWenZiBi;
    TextView tvShortTip;
    int curPlayType = 0;
    int allCount = 0;
    Random random = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(android.R.color.transparent);
        LayoutInflater inflater = getLayoutInflater();
        View cusView = inflater.inflate(R.layout.view_wenzibi_customview, null);
        getSupportActionBar().setCustomView(cusView, new ActionBar.LayoutParams(
                Gravity.RIGHT | Gravity.CENTER_VERTICAL));
        tvWenZiBi = (TextView) cusView.findViewById(R.id.tvWenzibi);
        // 初始化合成对象
        curPlayType = getIntent().getIntExtra("cur_play_type",
                MyApplication.PLAY_TYPE_CHUANGGUAN);

        etInput = (EditText) findViewById(R.id.etInput);
        tvCi[0] = (TextView) findViewById(R.id.tvCi1);
        tvCi[1] = (TextView) findViewById(R.id.tvCi2);
        tvCi[2] = (TextView) findViewById(R.id.tvCi3);
        tvCi[3] = (TextView) findViewById(R.id.tvCi4);
        tvPinyin[0] = (TextView) findViewById(R.id.tvPinyin1);
        tvPinyin[1] = (TextView) findViewById(R.id.tvPinyin2);
        tvPinyin[2] = (TextView) findViewById(R.id.tvPinyin3);
        tvPinyin[3] = (TextView) findViewById(R.id.tvPinyin4);
        lls[0] = (LinearLayout) findViewById(R.id.ll1);
        lls[1] = (LinearLayout) findViewById(R.id.ll2);
        lls[2] = (LinearLayout) findViewById(R.id.ll3);
        lls[3] = (LinearLayout) findViewById(R.id.ll4);
        tvShortTip = (TextView) findViewById(R.id.tvShortTip);
        etInput.addTextChangedListener(watcher);

        setHanzi();
        changeWenZiBi(0);
        onCiClick(lls[0]);
    }

    private TextWatcher watcher = new TextWatcher()
    {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count,
                int after)
        {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before,
                int count)
        {

        }

        @Override public void afterTextChanged(Editable s)
        {
            for (int i = 0; i < tvCi.length; i++)
            {
                if (tvCi[i].isSelected())
                {
                    tvCi[i].setText(s.toString());
                    break;
                }
            }
        }
    };

    public void onCiClick(View view)
    {

        for (int i = 0; i < lls.length; i++)
        {
            if (lls[i].getVisibility() != View.VISIBLE)
            {
                continue;
            }
            if (lls[i].equals(view))
            {
                tvCi[i].setPressed(false);
                tvCi[i].setSelected(true);
                etInput.setText(tvCi[i].getText().toString());
                continue;
            }
            tvCi[i].setSelected(false);
            tvCi[i].setPressed(
                    TextUtils.isEmpty(tvCi[i].getText()) ? false : true);
        }
    }

    public void setHanzi()
    {
        if (MyApplication.PLAY_TYPE_CHUANGGUAN == curPlayType)
        {
            curData = WordData.getChuangGuanRandomData();
            if (curData == null)
            {
                MobclickAgent.onEvent(this, "10000");
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setMessage("恭喜您已通关！可更新版本获得更多新词，或者尝试其他模式。");
                dialog.setTitle("提示");
                dialog.setNegativeButton("尝试其他模式", new OnClickListener()
                {

                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        MainActivity.this.finish();
                    }
                });
                dialog.create().show();
                return;
            }
            if (allCount == 0)
            {
                allCount = WordData.getAllDatas().size();
            }
            getSupportActionBar().setTitle(
                    "第" + (WordData.getAllRightDatas().size() + 1) + "关");
            getSupportActionBar().setSubtitle("共" + allCount + "关");
        }
        else if (MyApplication.PLAY_TYPE_WUJIN == curPlayType)
        {
            curData = WordData.getRandomData();
        }

        int count = curData.title.length();
        for (int i = 0; i < lls.length; i++)
        {
            lls[i].setVisibility(i >= count ? View.GONE : View.VISIBLE);
            tvCi[i].setText("");
        }

        String[] pinyinStrings = curData.pinyin.split(" ");
        for (int i = 0; i < pinyinStrings.length; i++)
        {
            tvPinyin[i].setText(pinyinStrings[i].toLowerCase());
        }
        etInput.setText("");
        setTipContent();
        onCiClick(lls[0]);
    }

    private void setTipContent()
    {
        String tip = curData.tip;
        for (int i = 0; i < lls.length; i++)
        {
            if (lls[i].getVisibility() == View.VISIBLE)
            {
                tip = tip.replace(curData.title.substring(i, i + 1),
                        tvPinyin[i].getText().toString() + " ");
            }
        }

        tvShortTip.setText(tip);
    }

    public void onTip(View view)
    {
        MobclickAgent.onEvent(MainActivity.this, "102");
        Intent intent = new Intent(this, TipActivity.class);
        intent.putExtra("data_answer", curData.title);
        intent.putExtra("data_tip", curData.url);
        intent.putExtra("data_pinyin", curData.pinyin);
        startActivity(intent);
    }

    public void onZhengQue()
    {
        boolean isMore20 = DataUtil.g_wenzibi - 20 > 0;
        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle("提示");
        dialog.setNegativeButton("再想想", new OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                dialog.dismiss();
            }
        });
        if (isMore20)
        {
            dialog.setMessage("显示一个正确的汉字会扣除20个文字币，是否确认？");
            dialog.setPositiveButton("好的", new OnClickListener()
            {

                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    if (DataUtil.g_wenzibi - 20 > 0)
                    {
                        changeWenZiBi(-20);

                        boolean finishTip = false;
                        for (int i = 0; i < tvCi.length; i++)
                        {
                            if (TextUtils.isEmpty(tvCi[i].getText().toString())
                                    && tvCi[i].isShown())
                            {
                                tvCi[i].setText(
                                        curData.title.subSequence(i, i + 1));
                                if (tvCi[i].isSelected())
                                {
                                    etInput.setText(curData.title.subSequence(i,
                                            i + 1));
                                }
                                finishTip = true;
                                break;
                            }
                        }
                        if (!finishTip)
                        {
                            int ram = random.nextInt(curData.title.length());
                            tvCi[ram].setText(
                                    curData.title.subSequence(ram, ram + 1));
                            if (tvCi[ram].isSelected())
                            {
                                etInput.setText(curData.title.subSequence(ram,
                                        ram + 1));
                            }
                        }
                    }
                    else
                    {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "您的游戏币不足20个",
                                Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        else
        {
            dialog.setMessage("您的文字币不足20个，是否购买？");
            dialog.setPositiveButton("购买", new OnClickListener()
            {

                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    Intent intent = new Intent(MainActivity.this,
                            PayWenZiBiActivity.class);
                    MainActivity.this.startActivity(intent);
                }
            });
        }
        dialog.create().show();
    }

    public void onFinish(View view)
    {
        if (DataUtil.getInfoFromShared(MainActivity.this, "isShowAD") == 1)
        {
            if (random.nextInt(5) > 2)
            {
                AppConnect.getInstance(MainActivity.this)
                        .showPopAd(MainActivity.this);
            }
        }

        MobclickAgent.onEvent(MainActivity.this, "100");
        if (getCurrentFocus() != null)
        {
            ((InputMethodManager) getSystemService(
                    INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(
                    getCurrentFocus().getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }
        boolean isRight = true;
        for (int i = 0; i < curData.title.length(); i++)
        {

            if (!curData.title.subSequence(i, i + 1)
                    .equals(tvCi[i].getText().toString()))
            {
                isRight = false;
                break;
            }
        }

        String temp = "恭喜你，回答正确！\n正确答案：" + curData.title;
        if (isRight)
        {
            if (MyApplication.PLAY_TYPE_CHUANGGUAN == curPlayType)
            {
                curData.isRight = 1;
            }
        }
        else
        {
            curData.wrong += 1;
            temp = "非常抱歉，回答错误。";
        }
        WordData.updateItem(curData);

        AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setMessage(temp);
        dialog.setTitle("提示");
        if (isRight)
        {
            dialog.setNegativeButton("下一个", new OnClickListener()
            {

                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    changeWenZiBi(3);
                    setHanzi();
                }
            });
        }
        else
        {
            dialog.setNegativeButton("再看看", new OnClickListener()
            {

                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            });
            dialog.setPositiveButton("正确答案", new OnClickListener()
            {

                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    showAnswer();
                    dialog.dismiss();
                }
            });
        }
        dialog.create().show();
    }

    private void changeWenZiBi(int cnt)
    {
        if (cnt > 0)
        {
            AppConnect.getInstance(this)
                    .awardPoints(cnt, myUpdatePointsNotifier);
        }
        else if (cnt < 0)
        {
            AppConnect.getInstance(this)
                    .spendPoints(Math.abs(cnt), myUpdatePointsNotifier);
        }
        if (DataUtil.g_wenzibi == -100)
        {
            tvWenZiBi.setText("未知");
        }
        else
        {
            tvWenZiBi.setText("" + DataUtil.g_wenzibi);
        }
    }

    private void showAnswer()
    {
        if (DataUtil.g_wenzibi - 50 >= 0)
        {
            for (int i = 0; i < tvCi.length; i++)
            {
                if (tvCi[i].isShown())
                {
                    tvCi[i].setText(curData.title.subSequence(i, i + 1));
                    if (tvCi[i].isSelected())
                    {
                        etInput.setText(curData.title.subSequence(i, i + 1));
                    }
                }
            }
            Toast.makeText(MainActivity.this, "正确答案已经显示。", Toast.LENGTH_SHORT)
                    .show();
            changeWenZiBi(-50);
        }
        else
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("提示");
            dialog.setMessage("直接显示答案需要50个文字币，是否购买？");
            dialog.setNegativeButton("再想想", new OnClickListener()
            {

                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    dialog.dismiss();
                }
            });
            dialog.setPositiveButton("购买", new OnClickListener()
            {

                @Override
                public void onClick(DialogInterface dialog, int which)
                {
                    Intent intent = new Intent(MainActivity.this,
                            PayWenZiBiActivity.class);
                    MainActivity.this.startActivity(intent);
                }
            });
            dialog.create().show();
        }
    }

    public void onVoice(View view)
    {
        // 没有可用的引擎
        if (SpeechUtility.getUtility(this).queryAvailableEngines() == null
                || SpeechUtility.getUtility(this).queryAvailableEngines().length
                <= 0)
        {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setMessage(getString(R.string.download_confirm_msg));
            dialog.setNegativeButton(R.string.dialog_cancel_button, null);
            dialog.setPositiveButton(getString(R.string.dialog_confirm_button),
                    new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialoginterface,
                                int i)
                        {
                            String url =
                                    SpeechUtility.getUtility(MainActivity.this)
                                            .getComponentUrl();
                            String assetsApk = "SpeechService.apk";
                            processInstall(MainActivity.this, url, assetsApk);
                        }
                    }
            );

            dialog.create().show();
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
        MobclickAgent.onEvent(this, "101");
    }

    /**
     * 合成回调监听。
     */
    private SynthesizerListener mTtsListener = new SynthesizerListener.Stub()
    {
        @Override
        public void onBufferProgress(int progress) throws RemoteException
        {
            Log.d(TAG, "onBufferProgress :" + progress);
        }

        @Override
        public void onCompleted(int code) throws RemoteException
        {
            Log.d(TAG, "onCompleted code =" + code);
        }

        @Override
        public void onSpeakBegin() throws RemoteException
        {
            Log.d(TAG, "onSpeakBegin");
        }

        @Override
        public void onSpeakPaused() throws RemoteException
        {
            Log.d(TAG, "onSpeakPaused.");
        }

        @Override
        public void onSpeakProgress(int progress) throws RemoteException
        {
            Log.d(TAG, "onSpeakProgress :" + progress);
        }

        @Override
        public void onSpeakResumed() throws RemoteException
        {
            Log.d(TAG, "onSpeakResumed.");
        }
    };

    /**
     * 如果服务组件没有安装，有两种安装方式。 1.直接打开语音服务组件下载页面，进行下载后安装。
     * 2.把服务组件apk安装包放在assets中，为了避免被编译压缩，修改后缀名为mp3，然后copy到SDcard中进行安装。
     */
    private void processInstall(Context context, String url, String assetsApk)
    {
        // 直接下载方式
        ApkInstaller.openDownloadWeb(context, url);
        // 本地安装方式
        // if (!ApkInstaller.installFromAssets(context, assetsApk)) {
        // ApkInstaller.openDownloadWeb(context, url);
        // }
    }

    @Override
    public void onResume()
    {
        // TODO Auto-generated method stub
        super.onResume();
        mTts = new SpeechSynthesizer(this, null);
        AppConnect.getInstance(this).getPoints(myUpdatePointsNotifier);
        if (DataUtil.getInfoFromShared(MainActivity.this, "wenzibi") > 0)
        {
            AppConnect.getInstance(this)
                    .awardPoints(DataUtil.getInfoFromShared(MainActivity.this,
                            "wenzibi"), new UpdatePointsNotifier()
                    {

                        @Override
                        public void getUpdatePointsFailed(String arg0)
                        {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void getUpdatePoints(String arg0, int arg1)
                        {
                            // TODO Auto-generated method stub
                            DataUtil.setInfoToShared(MainActivity.this,
                                    "wenzibi", 0);
                            DataUtil.g_wenzibi = arg1;
                        }
                    });
        }
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        mTts.stopSpeaking(mTtsListener);
        // 退出时释放连接
        mTts.destory();
    }

    public void onJumpNext(View view)
    {
        if (DataUtil.getInfoFromShared(MainActivity.this, "isShowAD") == 1
                && random.nextInt(5) > 2)
        {
            AppConnect.getInstance(MainActivity.this)
                    .showPopAd(MainActivity.this);
        }
        MobclickAgent.onEvent(MainActivity.this, "106");
        setHanzi();
    }

    public void onShowOne(View view)
    {
        MobclickAgent.onEvent(MainActivity.this, "103");
        onZhengQue();
    }

    public void onShowAnswer(View view)
    {
        MobclickAgent.onEvent(MainActivity.this, "104");
        showAnswer();
    }

    public void onFriendHelp(View view)
    {

        MobclickAgent.onEvent(MainActivity.this, "105");
        String file = ScreenShot.shoot(MainActivity.this);
        if (TextUtils.isEmpty(file))
        {
            Util.shareSomethingText(MainActivity.this, "分享",
                    "我使用  #汉字听写#  谁能告诉我 " + curData.pinyin + " 这个词语怎么写？");
        }
        else
        {
            Util.shareSomethingTextPhoto(MainActivity.this, "分享",
                    "我使用  #汉字听写#  谁能告诉我 " + curData.pinyin + " 这个词语怎么写？", file);
        }
    }

    private UpdatePointsNotifier myUpdatePointsNotifier =
            new UpdatePointsNotifier()
            {

                @Override
                public void getUpdatePointsFailed(String arg0)
                {
                    // TODO Auto-generated method stub
                }

                @Override
                public void getUpdatePoints(String arg0, int arg1)
                {
                    // TODO Auto-generated method stub
                    DataUtil.g_wenzibi = arg1;
                    changeUIWenZiBiHandler.sendEmptyMessage(1);
                }
            };

    private Handler changeUIWenZiBiHandler = new Handler()
    {
        public void handleMessage(Message msg)
        {
            if (msg.what == 1)
            {
                tvWenZiBi.setText("" + DataUtil.g_wenzibi);
            }
        }
    };

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

    public void payWenzibi(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("文字币获取方式");
        builder.setItems(new String[] { "免费文字币", "购买文字币" },
                new OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        switch (which)
                        {
                        case 0:
                            AppConnect.getInstance(MainActivity.this)
                                    .showOffers(MainActivity.this);
                            break;
                        case 1:
                            Intent intent = new Intent(MainActivity.this,
                                    PayWenZiBiActivity.class);
                            MainActivity.this.startActivity(intent);
                            break;
                        }
                    }
                }
        );
        builder.setNegativeButton("取消", null);
        AlertDialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }
}
