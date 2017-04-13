package fred.angel.com.mgank.component.Utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import fred.angel.com.mgank.BuildConfig;
import fred.angel.com.mgank.IApplication;
import fred.angel.com.mgank.R;
import fred.angel.com.mgank.model.enity.UpdateInfo;
import im.fir.sdk.FIR;
import im.fir.sdk.VersionCheckCallback;

/**
 * Created by Comori on 2016/11/1.
 * Todo
 */

public class Utils {


    /**
     * 判断有无网络 true有网 否则返回false
     *
     * @return
     */
    public static boolean netWorkConnection() {
        try {
            ConnectivityManager connectivity = (ConnectivityManager) IApplication.INSTACE.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (null == connectivity) {
                return false;
            }

            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (null == info || !info.isAvailable()) {
                return false;
            } else {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }


    // 序列化对象
    public static void ObjectOutputStream(Object object, String savePath) {
        java.io.ObjectOutputStream out = null;
        try {
            File file = new File(savePath);
            File parentFIle = file.getParentFile();
            if (!parentFIle.exists()) {
                parentFIle.mkdirs();
            }
            if (!file.isFile()) {
                file.createNewFile();
            }
            out = new java.io.ObjectOutputStream(new FileOutputStream(file));
            out.writeObject(object);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                    out = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // 反列化对象
    public static Object ObjectInputStream(String savePath) {
        Object object = null;
        java.io.ObjectInputStream in = null;
        try {
            in = new java.io.ObjectInputStream(new FileInputStream(savePath));
            object = in.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return object;
    }
    // 反列化对象
    public static Object ObjectInputStream(InputStream inputStream) {
        Object object = null;
        java.io.ObjectInputStream in = null;
        try {
            in = new java.io.ObjectInputStream(inputStream);
            object = in.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                    in = null;
                }
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return object;
    }

    public static boolean needAddStatusBaeHeight(){
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static <T extends View> T findView(View view, int resId){
        return (T) view.findViewById(resId);
    }

    public static int getTypeDrawableResId(String type){
        switch (type){
            case "Android":
                return R.drawable.ic_android;
            case "iOS":
                return R.drawable.ic_iphone;
            case "休息视频":
                return R.drawable.ic_video;
            case "瞎推荐":
                return R.drawable.ic_widgets;
            case "福利":
                return R.drawable.ic_widgets;
            case "拓展资源":
                return R.drawable.ic_widgets;
            case "前端":
                return R.drawable.ic_web;
            case "App":
                return R.drawable.ic_widgets;
        }
        return 1;
    }

    /**
     * 格式化时间
     * @return
     */
    public static String parseDate(String dateStr){
        SimpleDateFormat srcFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'");
        Date srcDate = null;
        try {
            srcDate = srcFormat.parse(dateStr);
            Calendar now = Calendar.getInstance();
            int ny = now.get(Calendar.YEAR);
            Calendar old = Calendar.getInstance();
            old.setTimeInMillis(srcDate.getTime());
            int oy = old.get(Calendar.YEAR);
            Date date = old.getTime();
            if(ny == oy){//今年只显示月日
                SimpleDateFormat mdFormat = new SimpleDateFormat("MM-dd HH:mm");
                return mdFormat.format(date);
            }else{
                SimpleDateFormat ymdFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
                return ymdFormat.format(date);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     *
     * @param dateStr
     * @return
     */
    public static String formateDate(String dateStr){
        SimpleDateFormat mdFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = mdFormat.parse(dateStr);
            return new SimpleDateFormat("yyyy/MM/dd").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    /**
     *
     * @param dateStr
     * @return
     */
    public static String formateDateMonth(String dateStr){
        SimpleDateFormat mdFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = mdFormat.parse(dateStr);
            return new SimpleDateFormat("MM-dd").format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return dateStr;
    }

    public static void checkUpdate(final CheckUpdateCallback callback){
        FIR.checkForUpdateInFIR(Constant.FIRTOKEN, new VersionCheckCallback() {
            @Override
            public void onSuccess(String versionJson) {
                Gson gson = new Gson();
                UpdateInfo updateInfo = gson.fromJson(versionJson, UpdateInfo.class);
                if(updateInfo != null){
                    float build = Float.valueOf(updateInfo.getBuild());
                    if(build > BuildConfig.VERSION_CODE){
                        if(callback != null){
                            callback.onComplete(true,updateInfo);
                        }
                    }
                }

            }
            @Override
            public void onFail(Exception exception) {
                Log.e("check_update",exception.getMessage());
            }
            @Override
            public void onStart() {
            }
            @Override
            public void onFinish() {
            }
        });
    }

    public static String getUUID(Context ctx){
        final TelephonyManager tm = (TelephonyManager) ctx.getSystemService(Context.TELEPHONY_SERVICE);

        final String tmDevice, tmSerial, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(ctx.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32) | tmSerial.hashCode());

        return deviceUuid.toString();
    }

    public static String getNowDate() {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(date);
    }

    public static String formatMindTime(String dateStr) { //2016-05-10 08:22:12
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
            sdf = new SimpleDateFormat("MMMM dd");
            return sdf.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.toString();
    }
    public static String format2MindTime(String dateStr) { //2016-05-10 08:22:12
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date date = null;
        try {
            date = sdf.parse(dateStr);
            sdf = new SimpleDateFormat("yyyy/MM/dd");
            return sdf.format(date);

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date.toString();
    }

    public static String format2Time(long times) { //2016-05-10
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        return sdf.format(new Date(times));
    }

    public interface CheckUpdateCallback{
        void onComplete(boolean needUpdate,UpdateInfo updateInfo);
    }

    //打开APK程序代码

    public static void installApk(Context activity, File file) {
        // TODO Auto-generated method stub
        Intent intent = new Intent();
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setAction(android.content.Intent.ACTION_VIEW);
        intent.setDataAndType(Uri.fromFile(file),
                "application/vnd.android.package-archive");
        activity.startActivity(intent);
    }

    public static boolean isRecyclerViewTop(RecyclerView recyclerView) {
        if (recyclerView != null) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                int firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
                View childAt = recyclerView.getChildAt(0);
                if (childAt == null || (firstVisibleItemPosition <= 0 && childAt != null && layoutManager.getDecoratedTop(childAt) == 0)) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean isRecyclerViewBottom(RecyclerView recyclerView) {
        if (recyclerView != null) {
            RecyclerView.LayoutManager layoutManager = recyclerView.getLayoutManager();
            if (layoutManager instanceof LinearLayoutManager) {
                int lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
                View childAt = recyclerView.getChildAt(recyclerView.getChildCount()-1);
                int rvHeight = recyclerView.getHeight();
                if (childAt == null || (lastVisibleItemPosition >= (layoutManager.getItemCount()-1) && childAt != null && layoutManager.getDecoratedBottom(childAt) == rvHeight)) {
                    return true;
                }
            }
        }
        return false;
    }
}
