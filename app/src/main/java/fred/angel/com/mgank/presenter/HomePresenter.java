package fred.angel.com.mgank.presenter;

import fred.angel.com.mgank.model.CategoryModel;
import fred.angel.com.mgank.model.ICategoryModel;
import fred.angel.com.mgank.view.IHomeView;

/**
 * Created by Comori on 2016/11/8.
 * Todo
 */

public class HomePresenter {

    private IHomeView homeView;
    private ICategoryModel categoryModel;

    public HomePresenter(IHomeView homeView) {
        this.homeView = homeView;
        categoryModel = new CategoryModel();
    }

    public void loadSelectCategories(){
        homeView.loadNavCategories(categoryModel.getSelectCategories());
    }
}
