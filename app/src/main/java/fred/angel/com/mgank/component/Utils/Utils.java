package fred.angel.com.mgank.component.Utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.view.View;

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

import fred.angel.com.mgank.IApplication;
import fred.angel.com.mgank.R;

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
}
