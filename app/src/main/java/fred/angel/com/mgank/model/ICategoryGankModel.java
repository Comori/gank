package fred.angel.com.mgank.model;

import java.util.List;

import fred.angel.com.mgank.model.enity.Gank;

/**
 * Created by Comori on 2016/11/8.
 * Todo
 */

public interface ICategoryGankModel {

    void loadCategoryGank(String category,int pageNum,INetCallback<List<Gank>> listener);

}
