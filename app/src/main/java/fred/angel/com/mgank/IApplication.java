package fred.angel.com.mgank;

import android.app.Application;

import com.bumptech.glide.load.engine.cache.DiskCache;
import com.tencent.smtt.sdk.QbSdk;

/**
 * Created by chenqiang on 2016/11/1.
 * Todo
 */

public class IApplication extends Application {

    public static IApplication INSTACE;

    @Override
    public void onCreate() {
        super.onCreate();
        INSTACE = this;
//        Fresco.initialize(this, FrescoConfig.getImagePipelineConfig(this));
        QbSdk.initX5Environment(this,null);
        DiskCache
    }


}
