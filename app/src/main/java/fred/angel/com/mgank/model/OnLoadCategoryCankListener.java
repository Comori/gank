package fred.angel.com.mgank.model;

import java.util.List;

import fred.angel.com.mgank.model.enity.Gank;

/**
 * Created by chenqiang on 2016/11/8.
 * Todo
 */

public interface OnLoadCategoryCankListener {

    void onSucess(int pageNum, List<Gank> ganks);

    void onFailure(int pageNum,String msg);

}
