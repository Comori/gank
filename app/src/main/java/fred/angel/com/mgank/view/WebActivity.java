package fred.angel.com.mgank.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.component.BaseToolbarActivity;
import fred.angel.com.mgank.component.widget.IToolbar;
import fred.angel.com.mgank.component.widget.IWebView;

/**
 * Created by chenqiang on 2016/11/4.
 * Todo
 */

public class WebActivity  extends BaseToolbarActivity{

    private IWebView webView;
    private String url;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_web);
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        url = intent.getStringExtra("web_url");
    }

    @Override
    public void initViews() {
        super.initViews();
        webView = getView(R.id.webview);
        webView.loadUrl(url);
    }

    @Override
    protected void onInitToolBar(IToolbar iToolbar) {
        super.onInitToolBar(iToolbar);
        iToolbar.setTitle("内容");
    }
}
