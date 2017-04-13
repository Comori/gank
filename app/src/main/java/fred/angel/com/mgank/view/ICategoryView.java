package fred.angel.com.mgank.view;

import java.util.List;

import fred.angel.com.mgank.model.enity.Category;

/**
 * Created by Comori on 2016/11/3.
 * Todo
 */

public interface ICategoryView {

    List<Category> getCategories();

    void setCategories(List<Category> categories);

}
