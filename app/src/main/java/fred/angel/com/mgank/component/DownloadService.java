package fred.angel.com.mgank.component;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.NotificationCompat;
import android.text.format.Formatter;
import android.util.Log;

import java.io.File;
import java.io.InputStream;
import java.util.concurrent.atomic.AtomicBoolean;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.component.Utils.Constant;
import fred.angel.com.mgank.component.Utils.FileUtils;
import fred.angel.com.mgank.component.Utils.Utils;
import fred.angel.com.mgank.component.net.ApiClient;
import fred.angel.com.mgank.component.net.DownloadProgressListener;
import fred.angel.com.mgank.model.enity.Download;
import okhttp3.ResponseBody;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

public class DownloadService extends IntentService {
    private static final String TAG = "DownloadService";

    private NotificationCompat.Builder notificationBuilder;
    private NotificationManager notificationManager;

    public static AtomicBoolean isDownloaded;
    File outputFile;

    public DownloadService() {
        super("DownloadService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        outputFile = new File(Environment.getExternalStoragePublicDirectory
                (Environment.DIRECTORY_DOWNLOADS), "file.apk");

        isDownloaded = new AtomicBoolean(false);

        notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

        notificationBuilder =new NotificationCompat.Builder(this);

        notificationBuilder.setSmallIcon(R.mipmap.icon_save_wallpaper)
                .setContentTitle("Download")
                .setContentText("Downloading File")
                .setAutoCancel(true);

        Intent intentClick = new Intent(this, NotificationBroadcastReceiver.class);
        intentClick.setAction(NotificationBroadcastReceiver.ACTION_CLICK);
        intentClick.putExtra(NotificationBroadcastReceiver.TYPE, Constant.NotificationType.DOWNLOAD_APK);
        intentClick.putExtra(Constant.IntentKey.APK_PATH, outputFile.getAbsolutePath());
        PendingIntent pendingIntentClick = PendingIntent.getBroadcast(this, 0, intentClick, PendingIntent.FLAG_ONE_SHOT);
        notificationBuilder.setContentIntent(pendingIntentClick);

        notificationManager.notify(Constant.NotificationType.DOWNLOAD_APK, notificationBuilder.build());

        download(intent.getStringExtra(Constant.IntentKey.DOWNLOAD_URL));
    }

    private void download(String apkUrl) {
        DownloadProgressListener listener = new DownloadProgressListener() {
            @Override
            public void update(long bytesRead, long contentLength, boolean done) {
                Download download = new Download();
                download.setTotalFileSize(contentLength);
                download.setCurrentFileSize(bytesRead);
                int progress = (int) ((bytesRead * 100) / contentLength);
                download.setProgress(progress);

                sendNotification(download);
            }
        };


        ApiClient.getDownloadApi(listener).download(apkUrl)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .map(new Func1<ResponseBody, InputStream>() {
                    @Override
                    public InputStream call(ResponseBody responseBody) {
                        return responseBody.byteStream();
                    }
                })
                .observeOn(Schedulers.computation())
                .doOnNext(new Action1<InputStream>() {
                    @Override
                    public void call(InputStream inputStream) {
                        FileUtils.writeFile(inputStream, outputFile);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<InputStream>() {
                    @Override
                    public void onCompleted() {
                        downloadCompleted();
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                        downloadCompleted();
                        Log.e(TAG, "onError: " + e.getMessage());
                    }
                    @Override
                    public void onNext(InputStream inputStream) {
                    }
                });
    }

    private void downloadCompleted() {
        Download download = new Download();
        download.setProgress(100);
        sendIntent(download);
        isDownloaded.set(true);
        notificationManager.cancel(Constant.NotificationType.DOWNLOAD_APK);
        notificationBuilder.setProgress(0, 0, false);
        notificationBuilder.setContentText("File Downloaded");
        notificationManager.notify(Constant.NotificationType.DOWNLOAD_APK, notificationBuilder.build());
        Utils.installApk(this,outputFile);
    }

    private void sendNotification(Download download) {

        sendIntent(download);
        notificationBuilder.setProgress(100, download.getProgress(), false);
        notificationBuilder.setContentText(
                Formatter.formatFileSize(this,download.getCurrentFileSize())+ "/" +
                        Formatter.formatFileSize(this,download.getTotalFileSize()));
        notificationManager.notify(Constant.NotificationType.DOWNLOAD_APK, notificationBuilder.build());
    }

    private void sendIntent(Download download) {
//        Intent intent = new Intent(MainActivity.MESSAGE_PROGRESS);
//        intent.putExtra("download", download);
//        LocalBroadcastManager.getInstance(DownloadService.this).sendBroadcast(intent);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        notificationManager.cancel(Constant.NotificationType.DOWNLOAD_APK);
        isDownloaded = null;
    }
}
