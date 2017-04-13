package fred.angel.com.mgank.presenter;

import java.util.List;

import fred.angel.com.mgank.component.net.ApiClient;
import fred.angel.com.mgank.component.net.NetSubscriber;
import fred.angel.com.mgank.model.enity.Gank;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author chenqiang
 */
public class SearchPresenter implements SearchContract.Presenter {

    SearchContract.View view;


    public SearchPresenter(SearchContract.View view) {
        this.view = view;
    }

    @Override
    public void search(String category, String keyword, int pageNum) {
        ApiClient.getApi().queryGank(category,keyword,pageNum)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetSubscriber<List<Gank>>(){

                    @Override
                    public void onStart() {
                        super.onStart();
                        view.showProgressView();
                    }

                    @Override
                    public void onNext(List<Gank> ganks) {
                        super.onNext(ganks);
                        view.hideProgressView();
                        view.showSearchResult(ganks);
                    }

                    @Override
                    public void onError(Throwable e) {
                        super.onError(e);
                        view.hideProgressView();
                    }
                });
    }
}
