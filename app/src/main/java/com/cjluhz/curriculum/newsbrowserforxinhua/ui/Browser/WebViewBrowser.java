package com.cjluhz.curriculum.newsbrowserforxinhua.ui.Browser;

import android.annotation.SuppressLint;

import android.os.Bundle;

import android.view.MenuItem;

import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.cjluhz.curriculum.newsbrowserforxinhua.R;

public class WebViewBrowser extends AppCompatActivity {
    private String href;
    private String Title;
    private WebView webView;

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.webview);

        Bundle getBundle = this.getIntent().getExtras();
        if (getBundle != null) {
            Title = getBundle.getString("title");
            href = getBundle.getString("newsLink");
        }

        getSupportActionBar().setTitle(Title);
        getSupportActionBar().setSubtitle(getBundle.getString("subtitle"));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        webView = (WebView) findViewById(R.id.webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());

        webView.loadUrl(href);

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:   //返回键的id
                this.finish();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
