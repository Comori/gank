package fred.angel.com.mgank.view;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.adapter.TodayGankAdapter;
import fred.angel.com.mgank.component.BaseFragment;
import fred.angel.com.mgank.component.IRecyclerView;
import fred.angel.com.mgank.component.Utils.Constant;
import fred.angel.com.mgank.component.Utils.DisplayUtil;
import fred.angel.com.mgank.component.Utils.Utils;
import fred.angel.com.mgank.component.stickyrecyclerview.StickyRecyclerHeadersDecoration;
import fred.angel.com.mgank.model.DateGankModel;
import fred.angel.com.mgank.model.INetCallback;
import fred.angel.com.mgank.model.enity.DateGank;

/**
 * Created by Comori on 2016/11/4.
 * Todo 今日数据fragment
 */

public class DayFragment extends BaseFragment {

    private IRecyclerView recyclerView;

    private TodayGankAdapter todayGankAdapter;


    DateGankModel dateGankModel;
    private String pageTitle;
    private ViewPager viewPager;

    public static DayFragment newInstance(String date){
        DayFragment dayFragment = new DayFragment();
        Bundle bundle = new Bundle();
        bundle.putString(Constant.IntentKey.DATE,date);
        dayFragment.setArguments(bundle);
        return dayFragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dateGankModel = new DateGankModel();
        if(getArguments() != null){
            pageTitle = getArguments().getString(Constant.IntentKey.DATE);
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(context).inflate(R.layout.fragment_day,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = findView(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        todayGankAdapter = new TodayGankAdapter(context);
        recyclerView.setAdapter(todayGankAdapter);

        recyclerView.needIntercept(viewPager,true);

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
        showProgress();
        dateGankModel.loadDateGank(Utils.formateDate(pageTitle), new INetCallback<DateGank>() {

            @Override
            public void onSuccess(int pageNum, DateGank data) {
                hideProgressView();
                showTodayGank(data);
            }

            @Override
            public void onFailure(int pageNum, String msg) {
                hideProgressView();
            }
        });


    }

    public DayFragment setPageTitle(String pageTitle){
        this.pageTitle = pageTitle;
        return this;
    }
    public DayFragment setViewPager(ViewPager viewPager){
        this.viewPager = viewPager;
        return this;
    }

    @Override
    public String getPageTitle() {
        return Utils.formateDateMonth(pageTitle);
    }

    public void showTodayGank(DateGank gank) {

        todayGankAdapter.setData(gank);
    }

    public void showError() {

    }

    public void showEmpty() {
//
//        todayGankPresenter.loadTodayGank(calendar.get(Calendar.YEAR)+"",
//                (calendar.get(Calendar.MONTH)+1)+"",
//                calendar.get(Calendar.DAY_OF_MONTH)+"");
    }

    public void showProgressView() {
    }

    public void hideProgressView() {
        hideProgress();
    }
}
