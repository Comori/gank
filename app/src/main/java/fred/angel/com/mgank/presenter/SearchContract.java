package fred.angel.com.mgank.presenter;

import java.util.List;

import fred.angel.com.mgank.model.enity.Gank;
import fred.angel.com.mgank.view.IProgressView;

/**
 * @author chenqiang
 */
public class SearchContract {

    public interface View extends IProgressView{

        void showSearchResult(List<Gank> gankList);

    }

    public interface Presenter{
        void search(String category,String keyword,int pageNum);
    }

}
