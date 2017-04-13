package fred.angel.com.mgank.view;

import java.util.List;

import fred.angel.com.mgank.model.enity.Gank;

/**
 * Created by Comori on 2016/11/8.
 * Todo
 */

public interface ICategoryGankView extends IProgressView{

    void showCategoryGanks(int pageNum,List<Gank> ganks);

    void showEmpty(int pageNum);

    void showError(int pageNum,String msg);

}
