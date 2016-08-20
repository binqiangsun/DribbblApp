package com.dribbb.sun.dribbblapp.activity;

import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.dribbb.sun.dribbblapp.R;
import com.dribbb.sun.dribbblapp.base.BaseActivity;
import com.dribbb.sun.dribbblapp.databinding.WebLayoutBinding;

/**
 * Created by sunbinqiang on 7/27/16.
 */

public class WebActivity extends BaseActivity<WebLayoutBinding> {

    @Override
    protected int getLayoutId() {
        return R.layout.web_layout;
    }

    @Override
    protected void initViews() {
        String url = getIntent().getStringExtra("url");
        if(url.contains("auth")){
            Uri uri = Uri.parse(url);
            url = uri.buildUpon().appendQueryParameter("client_id", "4bb696924129982493144fe6c11560052410112e681aa8c931226a437965521d")
                    .appendQueryParameter("redirect_uri", "drib://user")
                    .appendQueryParameter("scope", "public")
                    .appendQueryParameter("state", "dribbblelogin")
                    .build().toString();
            Log.e("web", "url:"+url);
        }
        mBinding.web.setLayerType(View.LAYER_TYPE_SOFTWARE, null);
        mBinding.web.loadUrl(url);
        mBinding.web.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                if(url.toLowerCase().startsWith("drib://")){
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    WebActivity.this.startActivity(intent);
                    WebActivity.this.finish();
                    return false;
                }else {
                    view.loadUrl(url);
                    return super.shouldOverrideUrlLoading(view, url);
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                String webViewTitle = null;

                if (mBinding.web != null) {
                    webViewTitle = mBinding.web.getTitle();
                }
                if (!TextUtils.isEmpty(webViewTitle)) {
                    setTitle(webViewTitle);
                }
            }

            @Override
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                super.onReceivedError(view, errorCode, description, failingUrl);
            }
        });
        mBinding.web.setWebChromeClient(new WebChromeClient());
    }

    public class WebChromeClient extends android.webkit.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if (newProgress == 100) {
                mBinding.progressBar.setVisibility(View.GONE);
            } else {
                if (mBinding.progressBar.getVisibility() == View.GONE)
                    mBinding.progressBar.setVisibility(View.VISIBLE);
                mBinding.progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public void onReceivedTitle(WebView view, String title) {
            super.onReceivedTitle(view, title);
        }

    }

}
