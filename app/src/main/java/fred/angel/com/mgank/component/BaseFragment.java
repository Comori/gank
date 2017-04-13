package fred.angel.com.mgank.component;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.View;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.component.Utils.Utils;
import fred.angel.com.mgank.component.widget.AVLoadingIndicatorView;

/**
 * Created by Comori on 2016/11/4.
 * Todo
 */

public abstract class BaseFragment extends Fragment {

    protected Context context;

    protected FragmentManager fragManager;
    private boolean created;

    private AVLoadingIndicatorView loadingView;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.context = context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragManager = getChildFragmentManager();
        created = false;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        created = true;
        loadingView = Utils.findView(view, R.id.loading);
    }

    @Override
    public void onResume() {
        super.onResume();
        // 判断当前fragment是否显示
        if (getUserVisibleHint()) {
            if (created) {
                onLoad();
                created = false;
            }
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        // 每次切换fragment时调用的方法
        if (isVisibleToUser) {
            if (created) {
                onLoad();
                created = false;
            }
        }
    }

    /**
     * 页面第一次加载时杂乱操作放在这里执行，非子线程
     */
    public abstract void onLoad();

    public abstract String getPageTitle();

    public <T extends View> T findView(int resId){
        return (T) getView().findViewById(resId);
    }

    public void showProgress(){
        if(loadingView != null) loadingView.setVisibility(View.VISIBLE);
    }
    public void hideProgress(){
        if(loadingView != null) loadingView.setVisibility(View.GONE);
    }

}
