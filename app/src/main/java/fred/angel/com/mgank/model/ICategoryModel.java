package fred.angel.com.mgank.model;

import java.util.List;

import fred.angel.com.mgank.model.enity.Category;

/**
 * Created by Comori on 2016/11/3.
 * Todo 分类数据接口
 */

public interface ICategoryModel {

    List<Category> getAll();

    void save(List<Category> categories);

    List<Category> getSelectCategories();
}
