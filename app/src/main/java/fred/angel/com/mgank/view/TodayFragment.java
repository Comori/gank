package fred.angel.com.mgank.view;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.adapter.TodayGankAdapter;
import fred.angel.com.mgank.component.BaseFragment;
import fred.angel.com.mgank.component.IRecyclerView;
import fred.angel.com.mgank.component.Utils.Constant;
import fred.angel.com.mgank.component.Utils.DisplayUtil;
import fred.angel.com.mgank.component.cache.LocalDataFactory;
import fred.angel.com.mgank.component.stickyrecyclerview.StickyRecyclerHeadersDecoration;
import fred.angel.com.mgank.model.enity.DateGank;
import fred.angel.com.mgank.presenter.TodayGankPresenter;

/**
 * Created by Comori on 2016/11/4.
 * Todo 今日数据fragment
 */

public class TodayFragment extends BaseFragment implements ITodayGankView{

    private SwipeRefreshLayout refreshLayout;
    private IRecyclerView recyclerView;

    private TodayGankPresenter todayGankPresenter;
    private TodayGankAdapter todayGankAdapter;

    private boolean first = true;

    final Calendar calendar = Calendar.getInstance();


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        todayGankPresenter = new TodayGankPresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(context).inflate(R.layout.fragment_today,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        refreshLayout = findView(R.id.swipeRefreshLayout);
        recyclerView = findView(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        todayGankAdapter = new TodayGankAdapter(context);
        recyclerView.setAdapter(todayGankAdapter);

        StickyRecyclerHeadersDecoration headersDecoration = new StickyRecyclerHeadersDecoration(todayGankAdapter);
        recyclerView.addItemDecoration(headersDecoration);

        final int pad_10 = DisplayUtil.dip2px(10);
        final int pad_1 = DisplayUtil.dip2px(0.5f);

        recyclerView.addItemDecoration(new RecyclerView.ItemDecoration() {
            @Override
            public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
                int position = ((RecyclerView.LayoutParams) view.getLayoutParams()).getViewLayoutPosition();
                if(position > 0){
                    if(!TextUtils.equals(todayGankAdapter.getItemData(position).getType(),
                            todayGankAdapter.getItemData(position-1).getType())){
                        outRect.top = pad_10;
                    }else {
                        outRect.top = pad_1;
                    }
                }
            }
        });

    }

    @Override
    public void onLoad() {

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                todayGankPresenter.loadTodayGank();
            }
        });

        todayGankPresenter.loadTodayGank();
    }

    @Override
    public String getPageTitle() {
        return "今日";
    }

    @Override
    public void showTodayGank(DateGank gank) {
        LocalDataFactory.getManager(LocalDataFactory.LocalDataType.SP)
                .put(Constant.LocalCacheKey.SP_INIT_DATE,true);
        LocalDataFactory.getManager(LocalDataFactory.LocalDataType.SP)
                .put(Constant.LocalCacheKey.SP_YEAR,calendar.get(Calendar.YEAR));
        LocalDataFactory.getManager(LocalDataFactory.LocalDataType.SP)
                .put(Constant.LocalCacheKey.SP_MONTH,calendar.get(Calendar.MONTH));
        LocalDataFactory.getManager(LocalDataFactory.LocalDataType.SP)
                .put(Constant.LocalCacheKey.SP_DAY,calendar.get(Calendar.DAY_OF_MONTH));

        todayGankAdapter.setData(gank);
    }

    @Override
    public void showError() {

    }

    @Override
    public void showEmpty() {
        if(LocalDataFactory.<Boolean>getManager(LocalDataFactory.LocalDataType.SP)
                .find(Constant.LocalCacheKey.SP_INIT_DATE)){
            calendar.set(Calendar.YEAR,LocalDataFactory.<Integer>getManager(LocalDataFactory.LocalDataType.SP)
                    .find(Constant.LocalCacheKey.SP_YEAR));
            calendar.set(Calendar.MONTH,LocalDataFactory.<Integer>getManager(LocalDataFactory.LocalDataType.SP)
                    .find(Constant.LocalCacheKey.SP_MONTH));
            calendar.set(Calendar.DAY_OF_MONTH,LocalDataFactory.<Integer>getManager(LocalDataFactory.LocalDataType.SP)
                    .find(Constant.LocalCacheKey.SP_DAY));
        }else {
            calendar.add(Calendar.DAY_OF_MONTH,-1);
        }
//
//        todayGankPresenter.loadTodayGank(calendar.get(Calendar.YEAR)+"",
//                (calendar.get(Calendar.MONTH)+1)+"",
//                calendar.get(Calendar.DAY_OF_MONTH)+"");
    }

    @Override
    public void showProgressView() {
    }

    @Override
    public void hideProgressView() {
        hideProgress();
        refreshLayout.setRefreshing(false);
    }
}
