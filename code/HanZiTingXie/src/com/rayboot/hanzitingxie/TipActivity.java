package com.rayboot.hanzitingxie;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import cn.waps.AppConnect;
import com.actionbarsherlock.view.MenuItem;
import com.actionbarsherlock.view.Window;
import com.rayboot.hanzitingxie.util.DataUtil;
import java.util.Random;

public class TipActivity extends MyBaseActivity
{

    String title;
    String url;
    String pinyin;

    WebView mWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tip);
        getSupportActionBar().setDisplayShowTitleEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (DataUtil.getInfoFromShared(TipActivity.this, "isShowAD") == 1)
        {
            AppConnect.getInstance(TipActivity.this)
                    .showPopAd(TipActivity.this);
        }

        title = getIntent().getStringExtra("data_answer");
        url = getIntent().getStringExtra("data_tip");
        pinyin = getIntent().getStringExtra("data_pinyin");

        if (!url.contains("http"))
        {
            url = "http://wapbaike.baidu.com" + url;
        }

        mWebView = (WebView) findViewById(R.id.wvTip);
        mWebView.setVisibility(View.GONE);

        WebSettings webSettings = mWebView.getSettings();
        webSettings.setAllowFileAccess(true);// 设置允许访问文件数据
        webSettings.setJavaScriptEnabled(true);// 设置支持javascript脚本
        webSettings.setBuiltInZoomControls(true);// 设置支持缩放
        mWebView.loadUrl(url);
        mWebView.setWebViewClient(new WebViewClient()
        {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            {
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url)
            {
                StringBuffer jsString = new StringBuffer();
                String[] pinyinStrings = pinyin.split(" ");
                jsString.append(
                        "javascript:var searchbar = document.getElementById('toolbar-search');searchbar.parentNode.removeChild(searchbar);");
                jsString.append(
                        "javascript:var btnconfig = document.getElementById('btn-config');btnconfig.parentNode.removeChild(btnconfig);");
                jsString.append(
                        "javascript:var settingbar1 = document.getElementById('btn-catalog');settingbar1.parentNode.removeChild(settingbar1);");
                jsString.append(
                        "javascript:var settingbar2 = document.getElementById('gotop');settingbar2.parentNode.removeChild(settingbar2);");
                for (int i = 0; i < title.length(); i++)
                {
                    jsString.append(
                            "javascript:var orgHtml = document.getElementsByTagName('body')[0].innerHTML;document.getElementsByTagName('body')[0].innerHTML=orgHtml.replace(/\\");
                    jsString.append(title.substring(i, i + 1));
                    jsString.append("/g,' ");
                    jsString.append(pinyinStrings[i] + "');");
                }
                view.loadUrl(jsString.toString());
            }
        });
        mWebView.setWebChromeClient(new WebChromeClient()
        {

            @Override
            public void onProgressChanged(WebView view, int newProgress)
            {
                // TODO Auto-generated method stub
                super.onProgressChanged(view, newProgress);
                if (newProgress >= 100)
                {
                    //					Dialog popAdDialog = AppConnect.getInstance(
                    //							TipActivity.this).getPopAdDialog();
                    //					if (popAdDialog != null) {
                    //						popAdDialog.dismiss();
                    //					}
                    mWebView.setVisibility(View.VISIBLE);
                }
            }
        });
        // HtmlParser parser = new HtmlParser(mWebView, url, title, this);
        // parser.execute((Void) null);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event)
    {
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack())
        {
            mWebView.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // TODO Auto-generated method stub
        int ids = item.getItemId();
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
