package fred.angel.com.mgank.component.widget;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.component.Utils.DisplayUtil;
import fred.angel.com.mgank.component.Utils.Utils;

/**
 * Created by chenqiang on 2016/5/19.
 * Todo 自定义的webview
 */
@SuppressLint("JavascriptInterface")
public class IWebView extends WebView {

    private boolean enableProgress; //是否显示网页加载进度
    private ProgressBar progressbar;
    private boolean enableGrab; //是否允许抓取内容


    public IWebView(Context context) {
        this(context,null);
    }

    public IWebView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public IWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        defaultSetting();
    }


    private void defaultSetting() {
        enableProgress = true;
        WebSettings settings = getSettings();
        settings.setDisplayZoomControls(false); //隐藏webview缩放按钮
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setSupportZoom(true);
        settings.setBuiltInZoomControls(true);
        settings.setJavaScriptEnabled(true);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        if(Utils.netWorkConnection()){
            settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        }else {
            settings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }

        settings.setRenderPriority(WebSettings.RenderPriority.HIGH);
        setWebChromeClient(new WebChromeClient());
        setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if(!TextUtils.isEmpty(url) && (url.startsWith("http")||url.startsWith("https"))){
                    view.loadUrl(url);// 使用当前WebView处理跳转
                    return true;//true表示此事件在此处被处理，不需要再广播
                }
                return false;
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                getSettings().setBlockNetworkImage(false);
                super.onPageFinished(view, url);
            }
        });

        if(enableProgress){
            progressbar = (ProgressBar) LayoutInflater.from(getContext()).inflate(R.layout.widget_progress,null);
            LayoutParams wbLp = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, DisplayUtil.dip2px(2));
            addView(progressbar,wbLp);
        }

        requestFocus();
        requestFocusFromTouch();
    }




    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
//        if(enableProgress && progressbar != null){
//            LayoutParams lp = (LayoutParams) progressbar.getLayoutParams();
//            lp.x = l;
//            lp.y = t;
//            progressbar.setLayoutParams(lp);
//        }
        super.onScrollChanged(l, t, oldl, oldt);
    }



    /**
     * 加载进度
     */
    private class WebChromeClient extends com.tencent.smtt.sdk.WebChromeClient {
        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            if(enableProgress && progressbar != null){
                if (newProgress == 100) {
                    progressbar.setVisibility(GONE);
                } else {
                    if (progressbar.getVisibility() == GONE)
                        progressbar.setVisibility(VISIBLE);
                    progressbar.setProgress(newProgress);
                }
            }
            if(enableGrab && newProgress == 100){
                enableGrab = false;
//                view.loadUrl("javascript:window.grab_obj.isShare(window.APP_isShare());");
//                view.loadUrl("javascript:window.grab_obj.readShareInfo(window.APP_shareConf(\"string\"));");
            }
            super.onProgressChanged(view, newProgress);
        }
    }


    public void onDestory(){
        stopLoading();
        clearView();
        freeMemory();
        destroy();
    }
}
