package fred.angel.com.mgank;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

/**
 * Created by Comori on 2016/11/1.
 * Todo
 */

public class IApplication extends Application {

    public static IApplication INSTACE;

    @Override
    public void onCreate() {
        super.onCreate();
        if(isMineApp(this)){
            INSTACE = this;
        }
    }

    /**
     * 是否是本应用程序进程
     *
     * @param context
     * @return
     */
    public boolean isMineApp(Context context) {
        int pid = android.os.Process.myPid();
        ActivityManager activityManager = ((ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE));
        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {
            if (appProcess.pid == pid && appProcess.processName.equalsIgnoreCase(context.getPackageName())) {
                return true;
            }
        }
        return false;
    }

}
