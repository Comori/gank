package fred.angel.com.mgank.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.adapter.FragmentPagerAdapter;
import fred.angel.com.mgank.component.BaseFragment;
import fred.angel.com.mgank.component.ITabLayout;
import fred.angel.com.mgank.component.Utils.Constant;
import fred.angel.com.mgank.model.enity.Category;
import fred.angel.com.mgank.presenter.HomePresenter;

/**
 * Created by chenqiang on 2016/11/7.
 * Todo 首页
 */

public class HomeFragemnt extends BaseFragment implements IHomeView{

    private ViewPager viewPager;

    private ITabLayout navTablayout;
    private ImageView moreCategoryImg;

    private List<BaseFragment> fragments;
    private HomePresenter homePresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        homePresenter = new HomePresenter(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home,container,false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navTablayout = findView(R.id.category_tab_layout);
        moreCategoryImg = findView(R.id.more_category_ic);
        viewPager = findView(R.id.frag_vp_container);

        moreCategoryImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(context,ManagerCategoryActivity.class));
            }
        });
    }

    @Override
    public void onLoad() {
        homePresenter.loadSelectCategories();
    }

    @Override
    public void loadNavCategories(List<Category> categories) {
        if(fragments != null){
            fragments.clear();
        }
        fragments = new ArrayList<>();
        fragments.add(new TodayFragment());
        if(categories != null){
            for(Category category : categories){
                Bundle bundle = new Bundle();
                bundle.putString(Constant.IntentKey.CATEGORY,category.getName());
                CategoryDataFragment fragment = new CategoryDataFragment();
                fragment.setCategory(category.getName());
                fragment.setArguments(bundle);
                fragments.add(fragment);
            }
        }

        viewPager.setAdapter(new FragmentPagerAdapter(fragManager,fragments));
        navTablayout.setupWithViewPager(viewPager);
    }

    @Override
    public String getPageTitle() {
        return "首页";
    }
}
