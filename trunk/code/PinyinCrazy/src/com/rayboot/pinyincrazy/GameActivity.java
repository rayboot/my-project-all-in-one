package com.rayboot.pinyincrazy;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;
import android.os.RemoteException;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.ButterKnife;
import butterknife.InjectView;
import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.view.MenuItem;
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

public class GameActivity extends MyBaseActivity
{
    private PinyinDataObj pinyinData;
    @InjectView(R.id.tvChar1) TextView tvChar1;
    @InjectView(R.id.tvChar2) TextView tvChar2;
    @InjectView(R.id.tvChar3) TextView tvChar3;
    @InjectView(R.id.tvChar4) TextView tvChar4;

    @InjectView(R.id.tvTitle) TextView tvTitle;
    @InjectView(R.id.tvAnswer) TextView tvAnswer;
    TextView tvWenzibi;

    int rightCount = 0;
    private SpeechSynthesizer mTts = null;
    int curType = 0;
    String[] ATone = { "a", "ā", "á", "ǎ", "à" };
    String[] OTone = { "o", "ō", "ó", "ǒ", "ò" };
    String[] ETone = { "e", "ē", "é", "ě", "è" };
    String[] ITone = { "i", "ī", "í", "ǐ", "ì" };
    String[] UTone = { "u", "ū", "ú", "ǔ", "ù" };
    String[] VTone = { "ü", "ǖ", "ǘ", "ǚ", "ǜ" };
    TextView[] tvCharArray;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        ButterKnife.inject(this);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setLogo(android.R.color.transparent);
        LayoutInflater inflater = getLayoutInflater();
        View cusView = inflater.inflate(R.layout.view_wenzibi_customview, null);
        tvWenzibi = (TextView) cusView.findViewById(R.id.tvWenzibi);
        getSupportActionBar().setCustomView(cusView, new ActionBar.LayoutParams(
                Gravity.RIGHT | Gravity.CENTER_VERTICAL));
        curType = getIntent().getIntExtra("is_wujin", 0);
        if (curType == 0)
        {
            rightCount = PinyinDataObj.getAllRightDatas().size();
            getSupportActionBar().setSubtitle(
                    "共" + PinyinDataObj.getAllDatas().size() + "");
            getSupportActionBar().setTitle("第" + (rightCount + 1) + "关");
        }
        else
        {
            getSupportActionBar().setTitle("无尽挑战");
        }
        tvCharArray = new TextView[4];
        tvCharArray[0] = tvChar1;
        tvCharArray[1] = tvChar2;
        tvCharArray[2] = tvChar3;
        tvCharArray[3] = tvChar4;
        setGameData();
        tvTitle.setSelected(true);
        changeCoin(0);
    }

    @Override
    public void onResume()
    {
        // TODO Auto-generated method stub
        if (mTts == null)
        {
            mTts = new SpeechSynthesizer(this, mTtsInitListener);
        }
        super.onResume();
        if (curType == 0)
        {
            getSupportActionBar().setTitle("第" + (rightCount + 1) + "关");
        }
        else
        {
            getSupportActionBar().setTitle("无尽挑战");
        }
    }

    public void setGameData()
    {
        if (curType == 0)
        {
            pinyinData = PinyinDataObj.getChuangGuanRandomData();
        }
        else
        {
            pinyinData = PinyinDataObj.getRandomData();
        }
        if (pinyinData == null)
        {
            Toast.makeText(this, "您已通关，先试试无尽模式吧", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }
        tvTitle.setText(pinyinData.keychar);
        tvCharArray[1].setVisibility(View.GONE);
        tvCharArray[2].setVisibility(View.GONE);
        tvCharArray[3].setVisibility(View.GONE);
        for (int i = 0; i < pinyinData.title.trim().length(); i++)
        {
            String charText = pinyinData.title.substring(i, i + 1);
            tvCharArray[i].setText(charText);
            tvCharArray[i].setSelected(charText.equals(pinyinData.keychar));
            tvCharArray[i].setVisibility(View.VISIBLE);
        }
        onClearClick(null);
    }

    public void onKeyClick(View view)
    {
        String keyString = ((TextView) view).getText().toString();
        String nowString = tvAnswer.getText().toString();
        String tagString = (String) tvAnswer.getTag(R.string.tag_answer_word);
        tvAnswer.setTag(R.string.tag_answer_word, tagString + keyString);
        tvAnswer.setText(nowString + keyString);
    }

    public void onClearClick(View view)
    {
        MobclickAgent.onEvent(this, "101");
        tvAnswer.setText("");
        tvAnswer.setTag(R.string.tag_answer_word, "");
        tvAnswer.setTag(R.string.tag_answer_tone, "0");
    }

    public void onOKClick(View view)
    {
        MobclickAgent.onEvent(this, "100");
        String tagAswer =
                ((String) tvAnswer.getTag(R.string.tag_answer_word)).trim();
        String tagTone = (String) tvAnswer.getTag(R.string.tag_answer_tone);
        if (pinyinData.keypinyin.trim().equals(tagAswer)
                && pinyinData.keytone == Integer.valueOf(tagTone))
        {
            MobclickAgent.onEvent(this, "1000");
            changeCoin(3);
            Toast.makeText(this, "恭喜你答对了", Toast.LENGTH_SHORT).show();
            pinyinData.isRight = 1;
            pinyinData.save();
            rightCount++;
            getSupportActionBar().setTitle("第" + (rightCount + 1) + "关");
            setGameData();
            return;
        }
        else
        {
            MobclickAgent.onEvent(this, "1001");
            changeCoin(-2);
            Toast.makeText(this, "回答错误。", Toast.LENGTH_SHORT).show();
            pinyinData.wrong += 1;
            pinyinData.save();
        }
    }

    public void onToneClick(View view)
    {
        tvAnswer.setTag(R.string.tag_answer_tone, view.getTag());
        String toneString = ((TextView) view).getText().toString();
        if (toneString.equals("轻声"))
        {
            toneString = "";
        }
        int tone = Integer.valueOf((String) view.getTag());
        String answer = tvAnswer.getText().toString().trim();
        tvAnswer.setText(makeTone(answer, tone));
    }

    private String makeTone(String word, int tone)
    {
        for (int i = 0; i < ATone.length; i++)
        {
            if (word.contains(ATone[i]))
            {
                word = word.replace(ATone[i], ATone[tone]);
            }
        }
        for (int i = 0; i < OTone.length; i++)
        {
            if (word.contains(OTone[i]))
            {
                word = word.replace(OTone[i], OTone[tone]);
            }
        }
        for (int i = 0; i < ETone.length; i++)
        {
            if (word.contains(ETone[i]))
            {
                word = word.replace(ETone[i], ETone[tone]);
            }
        }
        for (int i = 0; i < ITone.length; i++)
        {
            if (word.contains(ITone[i]))
            {
                word = word.replace(ITone[i], ITone[tone]);
            }
        }
        for (int i = 0; i < UTone.length; i++)
        {
            if (word.contains(UTone[i]))
            {
                word = word.replace(UTone[i], UTone[tone]);
            }
        }
        for (int i = 0; i < VTone.length; i++)
        {
            if (word.contains(VTone[i]))
            {
                word = word.replace(VTone[i], VTone[tone]);
            }
        }
        return word;
    }

    public void onHelpClick(View view)
    {
        switch (Integer.valueOf((String) view.getTag()))
        {
        case 1:
            // 求助好友
            MobclickAgent.onEvent(this, "103");
            changeCoin(1);
            String shareInfo = "我在使用 #拼音达人# 问你个事，"
                    + pinyinData.title
                    + "的"
                    + pinyinData.keychar
                    + "字怎么读，知道吗？";
            Util.shareSomethingText(this, "分享", shareInfo);
            break;
        case 2:
            // 显示答案
            MobclickAgent.onEvent(this, "102");
            if (!changeCoin(-50))
            {
                Toast.makeText(this, "非常抱歉，您的金币不足。", Toast.LENGTH_LONG).show();
                return;
            }
            tvAnswer.setText(
                    makeTone(pinyinData.keypinyin, pinyinData.keytone));
            tvAnswer.setTag(R.string.tag_answer_word, pinyinData.keypinyin);
            tvAnswer.setTag(R.string.tag_answer_tone, pinyinData.keytone + "");
            break;
        case 3:
            // 听一听
            MobclickAgent.onEvent(this, "104");
            try
            {
                ApplicationInfo appInfo = this.getPackageManager()
                        .getApplicationInfo(getPackageName(),
                                PackageManager.GET_META_DATA);
                String channelName =
                        appInfo.metaData.getString("UMENG_CHANNEL");
                if (!channelName.equals("mmw") && !changeCoin(-10))
                {
                    Toast.makeText(this, "非常抱歉，您的金币不足。", Toast.LENGTH_LONG)
                            .show();
                    return;
                }
            }
            catch (NameNotFoundException e)
            {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            if (checkIsInstall() && mTts != null)
            {
                int code = mTts.startSpeaking(pinyinData.title, mTtsListener);
                if (code != 0)
                {
                    // showTip("start speak error : " + code);
                }
                else
                {
                    // showTip("start speak success.");
                }
            }
            break;
        case 4:
            // 跳过该题
            MobclickAgent.onEvent(this, "105");
            if (!changeCoin(-5))
            {
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
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // This uses the imported MenuItem from ActionBarSherlock
        switch (item.getItemId())
        {
        case android.R.id.home:
            finish();
            break;
        default:
            break;
        }
        return true;
    }

    @Override
    protected void onDestroy()
    {
        super.onDestroy();
        if (mTts != null)
        {
            mTts.stopSpeaking(mTtsListener);
            // 退出时释放连接
            mTts.destory();
        }
    }

    /**
     * 初期化监听。
     */
    private InitListener mTtsInitListener = new InitListener()
    {

        @Override
        public void onInit(ISpeechModule arg0, int code)
        {
            if (code == ErrorCode.SUCCESS)
            {
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

    public boolean checkIsInstall()
    {
        // 没有可用的引擎
        if (SpeechUtility.getUtility(this).queryAvailableEngines() == null
                || SpeechUtility.getUtility(this).queryAvailableEngines().length
                <= 0)
        {

            AlertDialog.Builder alert = new AlertDialog.Builder(this);
            alert.setMessage(getString(R.string.download_confirm_msg));
            alert.setNegativeButton(R.string.dialog_cancel_button, null);
            alert.setPositiveButton(R.string.dialog_confirm_button,
                    new DialogInterface.OnClickListener()
                    {
                        @Override public void onClick(DialogInterface dialog,
                                int which)
                        {
                            String url =
                                    SpeechUtility.getUtility(GameActivity.this)
                                            .getComponentUrl();
                            ApkInstaller.openDownloadWeb(GameActivity.this,
                                    url);
                        }
                    }
            );
            alert.create().show();

            return false;
        }
        return true;
    }

    public boolean changeCoin(int count)
    {
        boolean res = DataUtil.changeCoin(this, count);
        tvWenzibi.setText(
                " " + DataUtil.getInfoFromShared(this, "pinyin_coin"));
        return res;
    }

    public void payWenzibi(View view)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("文字币获取方式");
        builder.setItems(new String[] { "免费文字币", "购买文字币" },
                new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        switch (which)
                        {
                        case 0:
                            //AppConnect.getInstance(MainActivity.this)
                            //        .showOffers(MainActivity.this);
                            break;
                        case 1:
                            //Intent intent = new Intent(MainActivity.this,
                            //        PayWenZiBiActivity.class);
                            //MainActivity.this.startActivity(intent);
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
