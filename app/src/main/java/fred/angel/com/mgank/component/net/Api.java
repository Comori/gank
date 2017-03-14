package fred.angel.com.mgank.component.net;

import java.util.List;

import fred.angel.com.mgank.model.enity.Gank;
import fred.angel.com.mgank.model.enity.DateGank;
import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

/**
 * Created by Comori on 2016/9/29.
 * Todo
 */

public interface Api {

    /**
     * 获取历史日期
     * @return
     */
    @GET("api/day/history")
    Observable<String> getHistoryDate();

    /**
     * 获取分类数据
     * @param category 数据类型：  all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * @param pageNum 第几页：数字，大于0
     * @return
     */
    @GET("api/data/{category}/20/{pageNum}")
    Observable<List<Gank>> getCategoryData(@Path("category") String category, @Path("pageNum") int pageNum);

    /**
     * 随机数据
     * @param category 数据类型： all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * @return
     */
    @GET("api/random/data/{category}/20")
    Observable<List<Gank>> getRandomData(@Path("category") String category);

    /**
     * 每日数据： http://gank.io/api/day/年/月/日
     * @param date 必须是yy/MM/dd格式的
     * @return
     */
    @GET("api/day/{date}")
    Observable<DateGank> getDateData(@Path("date") String date);

    /**
     * 获取发过干货日期接口:
     * @return
     */
    @GET("api/day/history")
    Observable<List<String>> getAllDates();

    /**
     * 搜索接口额
     * @param category 数据类型： all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * @param keyword 搜索关键字
     * @param pageNum 页码
     * @return
     */
    @GET("api/search/query/{keyword}/category/{category}/count/20/page/{pageNum}")
    Observable<List<Gank>> queryGank(@Path("category") String category,@Path("keyword") String keyword,@Path("pageNum") int pageNum);

}
