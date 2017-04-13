package fred.angel.com.mgank.component.net;

import java.util.List;

import fred.angel.com.mgank.model.enity.Mind;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface MindApi {
    //    http://v3.wufazhuce.com:8000/api/hp/bymonth/2016-08-01%2000:00:00?channel=wdj&version=4.0.2&uuid=ffffffff-a90e-706a-63f7-ccf973aae5ee&platform=android

    @GET("api/hp/bymonth/{date}%2000:00:00?channel=wdj&version=4.0.2&platform=android")
    Observable<List<Mind>> getMindList(@Path("date") String date, @Query("uuid") String uuid);
}

