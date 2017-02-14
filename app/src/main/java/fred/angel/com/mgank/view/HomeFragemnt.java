package fred.angel.com.mgank.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
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
import fred.angel.com.mgank.model.CategoryModel;
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
    private List<Category> srcCategories;
    private String selectCategoryName="今日";

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
                startActivityForResult(new Intent(context,ManagerCategoryActivity.class),Constant.RequestCode.MANAGER_CATEGORY);
            }
        });
    }

    @Override
    public void onLoad() {
        homePresenter.loadSelectCategories();
    }

    @Override
    public void loadNavCategories(List<Category> categories) {
        srcCategories = categories;
        if(fragments != null){
            fragments.clear();
            viewPager.clearOnPageChangeListeners();
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
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                selectCategoryName = navTablayout.getTabAt(position).getText().toString();
            }
            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
        FragmentPagerAdapter adapter = null;
        if(viewPager.getAdapter() != null){
            viewPager.clearOnPageChangeListeners();
            adapter = (FragmentPagerAdapter) viewPager.getAdapter();
            adapter.setFragments(fragments);
        }else {
            adapter = new FragmentPagerAdapter(fragManager,fragments,true);
            viewPager.setAdapter(adapter);
        }

        navTablayout.setupWithViewPager(viewPager);
        if (!TextUtils.equals("今日",selectCategoryName)) {
            int n = 0;
            for(int i=0;i < categories.size();i++){
                Category category = categories.get(i);
                if(TextUtils.equals(category.getName(),selectCategoryName)){
                    n = i;
                    break;
                }
            }
            if(n > 0) viewPager.setCurrentItem(n+1);
        }
    }

    @Override
    public String getPageTitle() {
        return "首页";
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode){
            case Constant.RequestCode.MANAGER_CATEGORY:
                if(data == null) return;
                List<Category> newCategories = (List<Category>) data.getSerializableExtra(Constant.IntentKey.CATEGORIES);
                if(newCategories != null ){
                    List<Category> selectCategories = CategoryModel.getSelectCategories(newCategories);
                    if(!selectCategories.equals(srcCategories)){
                        loadNavCategories(selectCategories);
                    }
                }
                break;
        }
    }
}
