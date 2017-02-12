package fred.angel.com.mgank.component;

import android.support.annotation.LayoutRes;
import android.view.View;
import android.widget.TextView;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.component.widget.IToolbar;

/**
 * Created by chenqiang on 2016/11/3.
 * Todo
 */

public class BaseToolbarActivity extends BaseActivity {

    private View.OnClickListener toolbackListner = null;
    private TextView tvTitle;

    protected IToolbar iToolbar;

    @Override
    public void setContentView(@LayoutRes int layoutResID) {
        super.setContentView(layoutResID);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListener() {
        initToolBar();
    }

    private final void initToolBar() {
        iToolbar = getView(R.id.i_toolbar);
        if (iToolbar != null) {
            onInitToolBar(iToolbar);
        }
    }

    /**
     * 子类重写此方法设置toolbar中的属性
     *
     * @param iToolbar
     */
    protected void onInitToolBar(IToolbar iToolbar) {
        iToolbar.getBackImg().setOnClickListener(onclk);
    }

    //设置返回按钮的点击效果
    public void setToolbarBack(View.OnClickListener onclk) {
        toolbackListner = onclk;
    }

    private View.OnClickListener onclk = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (toolbackListner == null) {
                finish();
            } else {
                toolbackListner.onClick(v);
            }
        }
    };

}
