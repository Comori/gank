package fred.angel.com.mgank.model;

import java.util.List;

import fred.angel.com.mgank.component.net.ApiClient;
import fred.angel.com.mgank.component.net.NetSubscriber;
import fred.angel.com.mgank.model.enity.Gank;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * Created by chenqiang on 2016/11/8.
 * Todo
 */

public class CategoryGankModel implements ICategoryGankModel{

    @Override
    public void loadCategoryGank(String category, final int pageNum, final OnLoadCategoryCankListener listener) {
        ApiClient.getApi().getCategoryData(category,pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetSubscriber<List<Gank>>(){
                    @Override
                    public void onNext(List<Gank> ganks) {
                        super.onNext(ganks);
                        if(listener != null){
                            listener.onSucess(pageNum,ganks);
                        }
                    }
                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        if(listener != null){
                            listener.onFailure(pageNum,e.getMessage());
                        }
                    }
                });

    }
}
