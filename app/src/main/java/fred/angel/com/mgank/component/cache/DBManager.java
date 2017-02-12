package fred.angel.com.mgank.component.cache;


import java.io.Serializable;

/**
 * Created by chenqiang
 * Todo 数据库管理器
 */
public class DBManager<T extends Serializable> implements IDataManager<T> {

    @Override
    public void put(String key, T t) {

    }

    @Override
    public void delete(String key) {

    }

    @Override
    public void update(String key, T t) {

    }

    @Override
    public T find(String key) {
        return null;
    }

    @Override
    public T find(String key, Class<T> cls) {
        return null;
    }

    @Override
    public void clear() {

    }
}
