package fred.angel.com.mgank.component.cache;

/**
 * Created by Comori on 2016/3/1.
 * Todo 本地数据管理工厂
 */
public class LocalDataFactory {

    public enum LocalDataType {
        CACHE,
        DB,
        DISK,
        SP
    }

    public static <T> IDataManager<T> getManager(LocalDataType type) throws IllegalArgumentException {
        IDataManager<T> dataManager = null;
        switch (type) {
            case CACHE:
                dataManager = new CacheFileManager<>();
                break;
            case DB:
                dataManager = new DBManager();
                break;
            case DISK:
                dataManager = new DiskManager<>();
                break;
            case SP:
                dataManager = new SPManager<>();
                break;
        }
        return dataManager;
    }
}
