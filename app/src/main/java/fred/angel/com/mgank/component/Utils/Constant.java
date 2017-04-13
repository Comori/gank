package fred.angel.com.mgank.component.Utils;

/**
 * Created by Comori on 2016/9/29.
 * Todo
 */

public class Constant {


    public static final String FILE_DIR = "mGank";
    public static final String FILE_NAME_CATEGORY = "categories";
    public static final String FIRTOKEN  = "39d48c990604f0e8b8567ab447efbfe9";


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
        String SP_YEAR = "sp_year";
        String SP_MONTH = "sp_month";
        String SP_DAY = "sp_day";
        String SP_INIT_DATE = "SP_INIT_DATE";
    }

    public interface IntentKey{
        String CATEGORY = "category";
        String CATEGORIES = "categories";
        String GANKS = "ganks";
        String CURRENT_POSITION = "CURRENT_POSITION";
        String DOWNLOAD_URL = "download_url";
        String APK_PATH = "apk_path";
        String DATE = "date";
        String MIND = "mind";
    }

    public interface RequestCode {
        int BASE = 0x101;

        int MANAGER_CATEGORY = BASE + 1;
    }

    public interface NotificationType{
        int DOWNLOAD_APK = 0x11;
    }
}
