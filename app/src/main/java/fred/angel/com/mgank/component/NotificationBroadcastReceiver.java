package fred.angel.com.mgank.component;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.io.File;

import fred.angel.com.mgank.component.Utils.Constant;
import fred.angel.com.mgank.component.Utils.Utils;

/**
 * @author chenqiang
 */
public class NotificationBroadcastReceiver extends BroadcastReceiver {

    public static final String TYPE = "type";
    public static final String ACTION_CLICK = "notification_clicked";
    public static final String ACTION_CANCEL = "notification_cancelled";

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        int type = intent.getIntExtra(TYPE, -1);

        switch (type){
            case Constant.NotificationType.DOWNLOAD_APK:
                if(DownloadService.isDownloaded == null || !DownloadService.isDownloaded.get()) return;
                switch (action){
                    case ACTION_CLICK:
                        String apkPath = intent.getStringExtra(Constant.IntentKey.APK_PATH);
                        Utils.installApk(context,new File(apkPath));
                        break;
                    case ACTION_CANCEL:
                        break;
                }
                break;
        }
    }
}