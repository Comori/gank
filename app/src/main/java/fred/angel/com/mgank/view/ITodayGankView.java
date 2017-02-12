package fred.angel.com.mgank.view;

import fred.angel.com.mgank.model.enity.DateGank;

/**
 * Created by chenqiang on 2016/11/4.
 * Todo
 */

public interface ITodayGankView extends IProgressView{

    void showTodayGank(DateGank gank);

    void showError();

    void showEmpty();

}
