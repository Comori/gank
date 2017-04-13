package fred.angel.com.mgank.presenter;

import org.json.JSONArray;
import org.json.JSONException;

import fred.angel.com.mgank.component.Utils.Utils;
import fred.angel.com.mgank.model.DateGankModel;
import fred.angel.com.mgank.model.IDateGankModel;
import fred.angel.com.mgank.model.INetCallback;
import fred.angel.com.mgank.model.enity.DateGank;
import fred.angel.com.mgank.view.ITodayGankView;

/**
 * Created by Comori on 2016/11/4.
 * Todo
 */

public class TodayGankPresenter implements INetCallback{

    private ITodayGankView todayGankView;
    private IDateGankModel dateGankModel;
    private String date;

    public TodayGankPresenter(ITodayGankView todayGankView) {
        this.todayGankView = todayGankView;
        dateGankModel = new DateGankModel();
    }

    public void loadTodayGank(){
        todayGankView.showProgressView();
        dateGankModel.loadHistoryDate(this);
    }


    @Override
    public void onSuccess(int pageNum, Object data) {
        if(data == null) return;
        if(data instanceof DateGank){
            DateGank dateGank = (DateGank) data;
            todayGankView.hideProgressView();
            if(dateGank == null || dateGank.getTotalSize() <= 0){
                todayGankView.showEmpty();
            }else {
                todayGankView.showTodayGank(dateGank);
            }
        }else if(data instanceof String){

            String dates = (String) data;
            try {
                JSONArray array = new JSONArray(dates);
                date = Utils.formateDate(array.opt(0).toString());

            } catch (JSONException e) {
                e.printStackTrace();
            }


            dateGankModel.loadDateGank(date,this);
        }
    }

    @Override
    public void onFailure(int pageNum, String msg) {
        todayGankView.hideProgressView();
        todayGankView.showError();
    }
}
