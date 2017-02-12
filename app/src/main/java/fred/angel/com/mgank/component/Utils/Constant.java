package fred.angel.com.mgank.component.Utils;

/**
 * Created by chenqiang on 2016/9/29.
 * Todo
 */

public class Constant {


    public static final String FILE_DIR = "mGank";
    public static final String FILE_NAME_CATEGORY = "categories";


//    all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App

    public interface Category{
        String ALL = "all";
        String ANDROID = "Android";
        String IOS = "iOS";
        String REST_VIDEO = "休息视频";
        String WELFARE = "福利";
        String EXPAND = "拓展资源";
        String WEB = "前端";
        String APP = "App";
        String RECOMMEND = "瞎推荐";
    }


    /**
     * 加载类型
     */
    public enum LoadType{
        FIRSTLOAD,
        REFRESH,
        LOAD_MORE
    }

    /**
     * 本地缓存的key
     */
    public interface LocalCacheKey{

        String SP_KEYBOARD_HEIGHT = "sp_keyboard_height";
    }

    public interface IntentKey{
        String CATEGORY = "category";
    }
}
