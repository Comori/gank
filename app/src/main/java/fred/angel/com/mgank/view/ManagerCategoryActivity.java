package fred.angel.com.mgank.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CompoundButton;

import java.util.Collections;
import java.util.List;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.adapter.CategoryAdapter;
import fred.angel.com.mgank.component.BaseToolbarActivity;
import fred.angel.com.mgank.component.itemtouch.DefaultItemTouchHelpCallback;
import fred.angel.com.mgank.component.itemtouch.DefaultItemTouchHelper;
import fred.angel.com.mgank.component.widget.DividerItemDecoration;
import fred.angel.com.mgank.component.widget.IToolbar;
import fred.angel.com.mgank.model.enity.Category;
import fred.angel.com.mgank.presenter.CategoryPresenter;

/**
 * Created by chenqiang on 2016/11/3.
 * Todo 管理分类
 */

public class ManagerCategoryActivity extends BaseToolbarActivity implements ICategoryView{

    private CategoryPresenter categoryPresenter;
    private RecyclerView recyclerView;
    private CategoryAdapter categoryAdapter;

    private List<Category> categories;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_category);
    }

    @Override
    public void initViews() {
        super.initViews();
        categoryPresenter = new CategoryPresenter(this);
        recyclerView = getView(R.id.recyclerview);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        // 必须要设置一个布局管理器
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(this));

        categoryAdapter = new CategoryAdapter(null);
        categoryAdapter.setOnCheckedChangeListener(onCheckedChangeListener);
        recyclerView.setAdapter(categoryAdapter);

        categoryPresenter.loadCategories();
    }

    @Override
    public void initListener() {
        super.initListener();
        // 把ItemTouchHelper和itemTouchHelper绑定
        DefaultItemTouchHelper itemTouchHelper = new DefaultItemTouchHelper(onItemTouchCallbackListener);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        categoryAdapter.setItemTouchHelper(itemTouchHelper);

        itemTouchHelper.setDragEnable(true);
    }

    @Override
    protected void onInitToolBar(IToolbar iToolbar) {
        super.onInitToolBar(iToolbar);
        iToolbar.setTitle("首页展示");
    }

    @Override
    public List<Category> getCategories() {
        return categories;
    }

    @Override
    public void setCategories(List<Category> categories) {
        this.categories = categories;
        categoryAdapter.setData(categories);
    }

    private DefaultItemTouchHelpCallback.OnItemTouchCallbackListener onItemTouchCallbackListener = new DefaultItemTouchHelpCallback.OnItemTouchCallbackListener() {
        @Override
        public void onSwiped(int adapterPosition) {
            if (categories != null) {
                categories.remove(adapterPosition);
                categoryAdapter.notifyItemRemoved(adapterPosition);
            }
        }
        @Override
        public boolean onMove(int srcPosition, int targetPosition) {
            if (categories != null) {
                // 更换数据源中的数据Item的位置
                Collections.swap(categories, srcPosition, targetPosition);

                // 更新UI中的Item的位置，主要是给用户看到交互效果
                categoryAdapter.notifyItemMoved(srcPosition, targetPosition);
                return true;
            }
            return false;
        }
    };

    private CategoryAdapter.OnCheckedChangeListener onCheckedChangeListener = new CategoryAdapter.OnCheckedChangeListener() {
        @Override
        public void onItemCheckedChange(CompoundButton view, int position, boolean checked) {
            categories.get(position).setChecked(checked);
        }
    };
}
