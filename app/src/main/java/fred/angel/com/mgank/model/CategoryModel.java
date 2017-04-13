package fred.angel.com.mgank.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import fred.angel.com.mgank.IApplication;
import fred.angel.com.mgank.component.Utils.Constant;
import fred.angel.com.mgank.component.Utils.FileUtils;
import fred.angel.com.mgank.component.Utils.Utils;
import fred.angel.com.mgank.model.enity.Category;

/**
 * Created by Comori on 2016/11/3.
 * Todo 分类数据的实体类
 */

public class CategoryModel implements ICategoryModel {

    @Override
    public List<Category> getAll() {
        List<Category> categories = (List<Category>) Utils.ObjectInputStream(FileUtils.getFileCachePath(Constant.FILE_NAME_CATEGORY));
        if(categories == null){
            try {
                categories = (List<Category>) Utils.ObjectInputStream(
                        IApplication.INSTACE.getResources().getAssets().open("default_categories"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return categories;
    }

    @Override
    public void save(List<Category> categories) {
        if(categories == null) return;
        Collections.sort(categories, new Comparator<Category>() {
            @Override
            public int compare(Category lhs, Category rhs) {
                int l = lhs.isChecked() ? 1:0;
                int r = rhs.isChecked() ? 1:0;
                return r-l;
            }
        });
        Utils.ObjectOutputStream(categories, FileUtils.getFileCachePath(Constant.FILE_NAME_CATEGORY));
    }

    @Override
    public List<Category> getSelectCategories() {

        List<Category> allCategories = getAll();
        List<Category> selectCategories = new ArrayList<>();
        for(Category category : allCategories){
            if(category.isChecked()){
                selectCategories.add(category);
            }
        }
        return selectCategories;
    }


    public static List<Category> getSelectCategories(List<Category> allCategories) {
        List<Category> selectCategories = new ArrayList<>();
        for(Category category : allCategories){
            if(category.isChecked()){
                selectCategories.add(category);
            }
        }
        return selectCategories;
    }
}
