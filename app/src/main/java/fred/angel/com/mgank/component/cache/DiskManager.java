package fred.angel.com.mgank.component.cache;

import android.text.TextUtils;

import java.io.File;

import fred.angel.com.mgank.component.Utils.FileUtils;
import fred.angel.com.mgank.component.Utils.Utils;

/**
 * Created by Comori on 2016/3/1.
 * Todo
 */
public class DiskManager<T> implements IDataManager<T> {
    private File rootDir;

    public DiskManager() {
        rootDir = FileUtils.getDiskDir();
    }

    /**
     *
     * @param key key是文件名
     * @param t 需要保存的对象
     */
    @Override
    public void put(String key, T t) {
        if(TextUtils.isEmpty(key)) return;
        Utils.ObjectOutputStream(t,rootDir.getAbsolutePath()+key);
    }

    @Override
    public void delete(String key) {
        if(TextUtils.isEmpty(key)) return;
        File file = new File(rootDir.getAbsolutePath() + key);
        if(!file.exists()) return;
        file.delete();
    }

    @Override
    public void update(String key, T t) {
        delete(key);
        put(key,t);
    }

    @Override
    public T find(String key) {
        return (T) Utils.ObjectInputStream(rootDir.getAbsolutePath() + key);
    }

    @Override
    public T find(String key, Class<T> cls) {
        return null;
    }

    @Override
    public void clear() {
        FileUtils.deleteFile(rootDir);
    }
}
