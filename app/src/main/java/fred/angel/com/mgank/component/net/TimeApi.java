package fred.angel.com.mgank.component.net;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import rx.Observable;

/**
 * @author chenqiang
 */
public interface TimeApi {

    @GET("time")
    Observable<ResponseBody> getRealTime();

}
