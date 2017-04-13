package fred.angel.com.mgank.component;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

/**
 * Created by Comori on 2016/9/29.
 * Todo
 */

public abstract class BaseActivity extends AppCompatActivity {

    protected boolean isTranslucentStatus;

    public abstract void initViews();

    public abstract void initListener();

    public void initIntent(Intent intent){};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        if (!BuildConfig.DEBUG) {
//        }
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
//        //状态栏沉浸效果 sdk>=19
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus(true);

        }
        initIntent(getIntent());
    }

    public void setTranslucentStatus(boolean on) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) return;
        isTranslucentStatus = on;
        Window win = getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
        initViews();
        initListener();
    }

    public boolean isTranslucentStatus() {
        return isTranslucentStatus;
    }

    public <T extends View> T getView(int id){
        return (T) findViewById(id);
    }
}
