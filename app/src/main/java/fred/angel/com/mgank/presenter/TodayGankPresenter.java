package fred.angel.com.mgank.presenter;

import fred.angel.com.mgank.model.DateGankModel;
import fred.angel.com.mgank.model.IDateGankModel;
import fred.angel.com.mgank.model.OnLoadDateGankListener;
import fred.angel.com.mgank.model.enity.DateGank;
import fred.angel.com.mgank.view.ITodayGankView;

/**
 * Created by chenqiang on 2016/11/4.
 * Todo
 */

public class TodayGankPresenter implements OnLoadDateGankListener{

    private ITodayGankView todayGankView;
    private IDateGankModel dateGankModel;

    public TodayGankPresenter(ITodayGankView todayGankView) {
        this.todayGankView = todayGankView;
        dateGankModel = new DateGankModel();
    }

    public void loadTodayGank(String year,String month,String day){
        todayGankView.showProgressView();
        dateGankModel.loadDateGank(year,month,day,this);
    }

    @Override
    public void onSuccess(DateGank dateGank) {
        todayGankView.hideProgressView();
        if(dateGank == null || dateGank.getTotalSize() <= 0){
            todayGankView.showEmpty();
        }else {
            todayGankView.showTodayGank(dateGank);
        }
    }

    @Override
    public void onFailure(String msg) {
        todayGankView.hideProgressView();
        todayGankView.showError();
    }
}
