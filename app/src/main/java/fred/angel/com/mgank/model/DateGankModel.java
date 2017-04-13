package fred.angel.com.mgank.model;

import fred.angel.com.mgank.component.net.ApiClient;
import fred.angel.com.mgank.component.net.NetSubscriber;
import fred.angel.com.mgank.model.enity.DateGank;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by Comori on 2016/11/4.
 * Todo
 */

public class DateGankModel implements IDateGankModel {

    @Override
    public void loadHistoryDate(final INetCallback<String> listener) {
        ApiClient.getApi().getHistoryDate()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetSubscriber<String>(){
                    @Override
                    public void onNext(String s) {
                        super.onNext(s);
                        if(listener != null){
                            listener.onSuccess(0,s);
                        }
                    }
                });
    }

    @Override
    public void loadDateGank(String date,
                             final INetCallback<DateGank> loadDateGankListener) {
        ApiClient.getApi().getDateData(date)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetSubscriber<DateGank>(){
                    @Override
                    public void onNext(DateGank dateGank) {
                        super.onNext(dateGank);
                        if(loadDateGankListener != null){
                            loadDateGankListener.onSuccess(0,dateGank);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(loadDateGankListener != null){
                            loadDateGankListener.onFailure(0,e.getMessage());
                        }
                    }
                });
    }



}
