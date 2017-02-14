package fred.angel.com.mgank;

import android.app.Application;

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
    }


}
