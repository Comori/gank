package fred.angel.com.mgank.component.cache;

import android.text.TextUtils;

import java.io.File;

import fred.angel.com.mgank.component.Utils.FileUtils;
import fred.angel.com.mgank.component.Utils.Utils;

/**
 * Created by chenqiang
 * Todo 缓存文件管理器，文件目录在/sdcard/Android/data/包名/cache/mGank/目录下
 */
public class CacheFileManager<T> implements IDataManager<T> {

    private File rootDir;

    public CacheFileManager() {
        rootDir = new File(FileUtils.getCacheDirPath());
    }

    /**
     *
     * @param key key是文件名
     * @param t 需要保存的对象
     */
    @Override
    public void put(String key, T t) {
        if(TextUtils.isEmpty(key)) return;
        Utils.ObjectOutputStream(t,rootDir.getAbsolutePath()+ File.separator +key);
    }

    @Override
    public void delete(String key) {
        if(TextUtils.isEmpty(key)) return;
        File file = new File(rootDir.getAbsolutePath() + File.separator + key);
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
        return (T) Utils.ObjectInputStream(rootDir.getAbsolutePath() + File.separator + key);
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
