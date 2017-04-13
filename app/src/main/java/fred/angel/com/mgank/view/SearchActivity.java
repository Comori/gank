package fred.angel.com.mgank.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.TextView;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.adapter.SearchResultAdapter;
import fred.angel.com.mgank.component.BaseToolbarActivity;
import fred.angel.com.mgank.component.IRecyclerView;
import fred.angel.com.mgank.component.Utils.Constant;
import fred.angel.com.mgank.component.widget.AVLoadingIndicatorView;
import fred.angel.com.mgank.component.widget.DividerItemDecoration;
import fred.angel.com.mgank.component.widget.IToolbar;
import fred.angel.com.mgank.model.enity.Gank;
import fred.angel.com.mgank.presenter.SearchContract;
import fred.angel.com.mgank.presenter.SearchPresenter;

/**
 * @author chenqiang
 */
public class SearchActivity extends BaseToolbarActivity implements SearchContract.View, IRecyclerView.IRecyclerViewListener {

    EditText mSearchEdt;
    AppCompatSpinner mCategorySpinner;
    SwipeRefreshLayout refreshLayout;
    IRecyclerView recyclerView;

    String mCategory;

    List<String> mCategories;

    SearchContract.Presenter presenter;
    AVLoadingIndicatorView loadingIndicatorView;
    int pageNum = 1;
    private String searchWord;
    SearchResultAdapter adapter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_search);

        initDisplay();
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        mCategory = intent.getStringExtra(Constant.IntentKey.CATEGORY);
        mCategories = Arrays.asList(getResources().getStringArray(R.array.category_names));
        presenter = new SearchPresenter(this);
    }

    @Override
    public void initViews() {
        super.initViews();
        refreshLayout = getView(R.id.swipeRefreshLayout);
        recyclerView = getView(R.id.recyclerview);
        loadingIndicatorView = getView(R.id.loading);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this));
        adapter = new SearchResultAdapter(this,null);
        recyclerView.setAdapter(adapter);
    }

    @Override
    protected void onInitToolBar(IToolbar iToolbar) {
        super.onInitToolBar(iToolbar);
        mSearchEdt = (EditText) iToolbar.getMiddleView();
        mCategorySpinner = (AppCompatSpinner) iToolbar.getRightView();
    }

    @Override
    public void initListener() {
        super.initListener();
        recyclerView.setIRecyclerViewListener(this);
        mCategorySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(!mCategory.equalsIgnoreCase(mCategories.get(position))){
                    mCategory = mCategories.get(position).toLowerCase(Locale.US);
                    presenter.search(mCategory,searchWord,pageNum);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        mSearchEdt.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId == EditorInfo.IME_ACTION_SEARCH){
                    searchWord = mSearchEdt.getText().toString();
                    if(!TextUtils.isEmpty(searchWord)){
                        pageNum = 1;
                        presenter.search(mCategory,searchWord,pageNum);
                    }
                }
                return false;
            }
        });
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                pageNum = 1;
                presenter.search(mCategory,searchWord,pageNum);
            }
        });
        
    }

    private void initDisplay() {
        mCategorySpinner.setSelection(mCategories.indexOf(mCategory));
    }

    @Override
    public void showProgressView() {
        if(pageNum == 1 && !refreshLayout.isRefreshing()){
            loadingIndicatorView.setVisibility(View.VISIBLE);
        }else {
            refreshLayout.setRefreshing(true);
        }
    }

    @Override
    public void hideProgressView() {
        refreshLayout.setRefreshing(false);
        loadingIndicatorView.setVisibility(View.GONE);
    }

    @Override
    public void showSearchResult(List<Gank> gankList) {
        if(gankList == null || gankList.isEmpty()){
            if(pageNum == 1){// showEmpty

            }
        }else {
            if(pageNum == 1){
                adapter.setData(gankList);
            }else {
                adapter.addData(gankList);
            }
        }
    }

    @Override
    public void onEmpty() {

    }

    @Override
    public void loadMore() {
        presenter.search(mCategory,searchWord,++pageNum);
    }
}
