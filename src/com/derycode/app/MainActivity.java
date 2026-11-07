package com.derycode.app;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.CookieManager;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class MainActivity extends Activity {

    private static final String HOME = "https://derycode.online/";
    private WebView web;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        web = new WebView(this);
        web.setBackgroundColor(Color.parseColor("#070B14"));
        setContentView(web);

        WebSettings s = web.getSettings();
        s.setJavaScriptEnabled(true);
        s.setDomStorageEnabled(true);
        s.setDatabaseEnabled(true);
        s.setLoadWithOverviewMode(true);
        s.setUseWideViewPort(true);
        s.setSupportZoom(false);
        s.setBuiltInZoomControls(false);
        s.setMediaPlaybackRequiresUserGesture(false);
        s.setMixedContentMode(WebSettings.MIXED_CONTENT_NEVER_ALLOW);
        CookieManager.getInstance().setAcceptCookie(true);

        web.setWebChromeClient(new WebChromeClient());
        web.setWebViewClient(new WebViewClient() {

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest req) {
                return handleUrl(req.getUrl().toString());
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                view.setVisibility(View.VISIBLE);
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest req, WebResourceError err) {
                if (req != null && req.isForMainFrame()) {
                    view.loadUrl("file:///android_asset/offline.html");
                }
            }
        });

        // let people download the other DeryCode apps straight from the site
        web.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String ua, String cd, String mime, long len) {
                try {
                    DownloadManager.Request r = new DownloadManager.Request(Uri.parse(url));
                    r.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                    r.setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                            android.webkit.URLUtil.guessFileName(url, cd, mime));
                    DownloadManager dm = (DownloadManager) getSystemService(Context.DOWNLOAD_SERVICE);
                    dm.enqueue(r);
                    Toast.makeText(MainActivity.this, "Downloading…", Toast.LENGTH_SHORT).show();
                } catch (Exception e) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
                }
            }
        });

        web.loadUrl(HOME);
    }

    /** Keep DeryCode pages inside the app; send phone, WhatsApp, mail and other sites outside. */
    private boolean handleUrl(String url) {
        if (url == null) return false;

        if (url.startsWith("tel:") || url.startsWith("mailto:") || url.startsWith("sms:")
                || url.startsWith("whatsapp:") || url.startsWith("intent:")) {
            try { startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))); } catch (Exception ignored) {}
            return true;
        }

        if (url.contains("wa.me") || url.contains("api.whatsapp.com")) {
            try { startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))); } catch (Exception ignored) {}
            return true;
        }

        Uri u = Uri.parse(url);
        String host = u.getHost() == null ? "" : u.getHost();
        boolean ours = host.endsWith("derycode.online")
                || host.endsWith("deryaccount.vercel.app")
                || host.endsWith("sageco-evergreen.vercel.app")
                || host.endsWith("asiimwe3.github.io");

        if (ours) return false;

        try { startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url))); } catch (Exception ignored) {}
        return true;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && web != null && web.canGoBack()) {
            web.goBack();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onSaveInstanceState(Bundle out) {
        super.onSaveInstanceState(out);
        if (web != null) web.saveState(out);
    }
}
