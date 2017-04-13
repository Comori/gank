package fred.angel.com.mgank.presenter;

import java.util.List;

import fred.angel.com.mgank.model.CategoryGankModel;
import fred.angel.com.mgank.model.ICategoryGankModel;
import fred.angel.com.mgank.model.INetCallback;
import fred.angel.com.mgank.model.enity.Gank;
import fred.angel.com.mgank.view.ICategoryGankView;

/**
 * Created by Comori on 2016/11/8.
 * Todo
 */

public class CategoryGankPresenter implements INetCallback<List<Gank>> {

    private ICategoryGankView categoryGankView;
    private ICategoryGankModel categoryGankModel;

    public CategoryGankPresenter(ICategoryGankView categoryGankView) {
        this.categoryGankView = categoryGankView;
        categoryGankModel = new CategoryGankModel();
    }

    public void loadGanks(int pageNum,String category){
        categoryGankModel.loadCategoryGank(category,pageNum,this);
    }

    @Override
    public void onSuccess(int pageNum, List<Gank> ganks) {
        categoryGankView.hideProgressView();
        if(ganks == null || ganks.isEmpty()){
            categoryGankView.showEmpty(pageNum);
        }else {
            categoryGankView.showCategoryGanks(pageNum,ganks);
        }
    }

    @Override
    public void onFailure(int pageNum, String msg) {
        categoryGankView.hideProgressView();
        categoryGankView.showError(pageNum,msg);
    }
}
