package fred.angel.com.mgank.component.net;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;
import rx.Observable;

public interface DownloadApi {

    @Streaming
    @GET
    Observable<ResponseBody> download(@Url String url);
}
