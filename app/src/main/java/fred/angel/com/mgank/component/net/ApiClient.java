package fred.angel.com.mgank.component.net;

import java.util.concurrent.TimeUnit;

import fred.angel.com.mgank.BuildConfig;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;

/**
 * Created by Comori on 2016/9/27.
 * Todo 请求客户端
 */

public class ApiClient {

    private static Api api;


    private ApiClient(){
    }

    public static Api getApi(){
        if(api == null){
            synchronized (ApiClient.class){
                if(api == null){
                    HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
                    loggingInterceptor.setLevel(BuildConfig.DEBUG? HttpLoggingInterceptor.Level.BODY:
                            HttpLoggingInterceptor.Level.NONE);
                    OkHttpClient client = new OkHttpClient.Builder()
                            .retryOnConnectionFailure(true)
                            .connectTimeout(30, TimeUnit.SECONDS)
                            .readTimeout(5,TimeUnit.MINUTES)
                            .writeTimeout(5,TimeUnit.MINUTES)
                            .addInterceptor(loggingInterceptor)
//                            .addInterceptor(new Interceptor() {
//                                @Override
//                                public Response intercept(Chain chain) throws IOException {
//                                    Request request = chain.request();
//
//                                    CacheControl.Builder rcacheBuilder = new CacheControl.Builder();
//                                    rcacheBuilder.maxAge(0, TimeUnit.SECONDS);
//                                    rcacheBuilder.maxStale(365,TimeUnit.DAYS);
//                                    CacheControl rcacheControl = rcacheBuilder.build();
//
//                                    Response response = chain.proceed(request.newBuilder()
//                                            .cacheControl(rcacheControl)
//                                            .build());
//
//                                    CacheControl cacheControl = new CacheControl.Builder()
//                                            .maxAge(30, TimeUnit.DAYS)
//                                            .build();
//                                    return response.newBuilder()
//                                            .header("Cache-Control", cacheControl.toString() )
//                                            .build();
//                                }
//                            })
//                            .cache(new Cache(new File(IApplication.INSTACE.getCacheDir(), "responses"),100 * ByteConstants.MB))
                            .build();
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl("http://gank.io/")
                            .client(client)
                            .addConverterFactory(CustomGsonConvertFactory.create())
                            .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                            .build();
                    api = retrofit.create(Api.class);
                }
            }
        }
        return api;
    }

    public static DownloadApi getDownloadApi(DownloadProgressListener listener){
        DownloadProgressInterceptor interceptor = new DownloadProgressInterceptor(listener);

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG? HttpLoggingInterceptor.Level.BODY:
                HttpLoggingInterceptor.Level.NONE);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(5,TimeUnit.MINUTES)
                .writeTimeout(5,TimeUnit.MINUTES)
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://gank.io/")
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(DownloadApi.class);
    }

    public static MindApi getMindApi(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG? HttpLoggingInterceptor.Level.BODY:
                HttpLoggingInterceptor.Level.NONE);

        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(5,TimeUnit.MINUTES)
                .writeTimeout(5,TimeUnit.MINUTES)
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://v3.wufazhuce.com:8000/")
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .addConverterFactory(MindGsonConvertFactory.create())
                .build();
        return retrofit.create(MindApi.class);
    }
    public static TimeApi getTimeApi(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(BuildConfig.DEBUG? HttpLoggingInterceptor.Level.BODY:
                HttpLoggingInterceptor.Level.NONE);

        OkHttpClient client = new OkHttpClient.Builder()
                .retryOnConnectionFailure(true)
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(5,TimeUnit.MINUTES)
                .writeTimeout(5,TimeUnit.MINUTES)
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.time.3023.com/")
                .client(client)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();
        return retrofit.create(TimeApi.class);
    }
}
