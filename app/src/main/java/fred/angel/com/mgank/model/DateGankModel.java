package fred.angel.com.mgank.model;

import fred.angel.com.mgank.component.net.ApiClient;
import fred.angel.com.mgank.component.net.NetSubscriber;
import fred.angel.com.mgank.model.enity.DateGank;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenqiang on 2016/11/4.
 * Todo
 */

public class DateGankModel implements IDateGankModel {

    @Override
    public void loadDateGank(String year, String month, String day,
                             final OnLoadDateGankListener loadDateGankListener) {
        ApiClient.getApi().getDateData(year, month, day)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetSubscriber<DateGank>(){
                    @Override
                    public void onNext(DateGank dateGank) {
                        super.onNext(dateGank);
                        if(loadDateGankListener != null){
                            loadDateGankListener.onSuccess(dateGank);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(loadDateGankListener != null){
                            loadDateGankListener.onFailure(e.getMessage());
                        }
                    }
                });
    }


}
