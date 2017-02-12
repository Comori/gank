package fred.angel.com.mgank.model;

import fred.angel.com.mgank.model.enity.DateGank;

/**
 * Created by chenqiang on 2016/11/4.
 * Todo
 */

public interface OnLoadDateGankListener {

    void onSuccess(DateGank dateGank);
    void onFailure(String msg);
}
