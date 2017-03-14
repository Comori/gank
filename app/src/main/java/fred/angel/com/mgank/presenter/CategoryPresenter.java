package fred.angel.com.mgank.presenter;

import java.util.List;

import fred.angel.com.mgank.model.CategoryModel;
import fred.angel.com.mgank.model.ICategoryModel;
import fred.angel.com.mgank.model.enity.Category;
import fred.angel.com.mgank.view.ICategoryView;

/**
 * Created by Comori on 2016/11/3.
 * Todo
 */

public class CategoryPresenter {

    private ICategoryModel categoryModel;
    private ICategoryView categoryView;

    public CategoryPresenter(ICategoryView categoryView) {
        this.categoryView = categoryView;
        categoryModel = new CategoryModel();
    }

    public List<Category> loadCategories(){
        List<Category> categories = categoryModel.getAll();
        categoryView.setCategories(categories);
        return categories;
    }

    public void saveCategories(List<Category> categories){
        categoryModel.save(categories);
    }
}
