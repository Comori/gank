package fred.angel.com.mgank.model;

import fred.angel.com.mgank.model.enity.DateGank;

/**
 * Created by Comori on 2016/11/4.
 * Todo
 */

public interface IDateGankModel {

    void loadDateGank(String date,INetCallback<DateGank> loadDateGankListener);

    void loadHistoryDate(INetCallback<String> netCallback);

}
