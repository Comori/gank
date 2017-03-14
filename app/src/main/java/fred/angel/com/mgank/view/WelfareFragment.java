package fred.angel.com.mgank.view;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.adapter.WelfareAdapter;
import fred.angel.com.mgank.component.BaseFragment;
import fred.angel.com.mgank.component.IRecyclerView;
import fred.angel.com.mgank.component.Utils.Constant;
import fred.angel.com.mgank.component.Utils.DisplayUtil;
import fred.angel.com.mgank.model.enity.Gank;
import fred.angel.com.mgank.presenter.CategoryGankPresenter;

/**
 * Created by Comori on 2016/11/8.
 * Todo
 */

public class WelfareFragment extends BaseFragment implements ICategoryGankView{

    private String category;

    private SwipeRefreshLayout refreshLayout;
    private IRecyclerView recyclerView;

    private WelfareAdapter gankAdapter;

    private CategoryGankPresenter gankPresenter;

    private int pageNum = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(getArguments() != null){
            category = getArguments().getString(Constant.IntentKey.CATEGORY);
        }
        gankPresenter = new CategoryGankPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_today,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout = findView(R.id.swipeRefreshLayout);
        recyclerView = findView(R.id.recyclerview);
        StaggeredGridLayoutManager layoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(layoutManager);

        gankAdapter = new WelfareAdapter(context);
        recyclerView.setAdapter(gankAdapter);
        final int dp_2 = DisplayUtil.dip2px(2);
        final int dp_1 = DisplayUtil.dip2px(1);
        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                outRect.right = dp_1;
                outRect.left = dp_1;
                outRect.bottom = dp_2;
            }
        });

        recyclerView.setIRecyclerViewListener(new IRecyclerView.IRecyclerViewListener() {
            @Override
            public void onEmpty() {
            }
            @Override
            public void loadMore() {
                pageNum++;
                gankPresenter.loadGanks(pageNum,category);
            }
        });

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                gankPresenter.loadGanks(pageNum,category);
            }
        });
    }

    public WelfareFragment setCategory(String category) {
        this.category = category;
        return this;
    }

    @Override
    public void onLoad() {
        gankPresenter.loadGanks(pageNum,category);
    }

    @Override
    public String getPageTitle() {
        return category;
    }

    @Override
    public void showCategoryGanks(int pageNum,List<Gank> ganks) {
        if(pageNum > 1){
            gankAdapter.addData(ganks);
        }else {
            gankAdapter.setData(ganks);
        }
    }

    @Override
    public void showEmpty(int pageNum) {

    }

    @Override
    public void showError(int pageNum,String msg) {

    }

    @Override
    public void showProgressView() {
        refreshLayout.setRefreshing(true);
    }

    @Override
    public void hideProgressView() {
        refreshLayout.setRefreshing(false);
        hideProgress();
    }
}
