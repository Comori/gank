package fred.angel.com.mgank.component.cache;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import fred.angel.com.mgank.IApplication;

/**
 * Created by Comori on 2016/3/1.
 * Todo SharedPreferences管理器
 */
public class SPManager<T> implements IDataManager<T> {

    private SharedPreferences sp;

    public SPManager() {
        this.sp = PreferenceManager.getDefaultSharedPreferences(IApplication.INSTACE);
    }

    @Override
    public void put(String key, T t) {
        if (t == null) return;
        SharedPreferences.Editor editor = sp.edit();
        switch (t.getClass().getSimpleName()) {
            case "String":
                editor.putString(key, (String) t);
                break;
            case "Integer":
                editor.putInt(key, (Integer) t);
                break;
            case "Boolean":
                editor.putBoolean(key, (Boolean) t);
                break;
            case "Float":
                editor.putFloat(key, (Float) t);
                break;
            case "Long":
                editor.putLong(key, (Long) t);
                break;
        }
        editor.apply();
    }

    @Override
    public void delete(String key) {
        sp.edit().remove(key).apply();
    }

    @Override
    public void update(String key, T t) {
        put(key, t);
    }

    @Override
    public T find(String key) {
        Object o = sp.getAll().get(key);
        if (o == null) return null;
        switch (o.getClass().getSimpleName()) {
            case "String":
                return (T) sp.getString(key, "");
            case "Integer":
                return (T) (Integer) sp.getInt(key, 0);
            case "Boolean":
                return (T) (Boolean) sp.getBoolean(key, false);
            case "Float":
                return (T) (Float) sp.getFloat(key, 0f);
            case "Long":
                return (T) (Long) sp.getLong(key, 0L);
        }
        return null;
    }

    @Override
    public T find(String key, Class<T> cls) {
        return null;
    }

    @Override
    public void clear() {
        sp.edit().clear().apply();
    }
}
