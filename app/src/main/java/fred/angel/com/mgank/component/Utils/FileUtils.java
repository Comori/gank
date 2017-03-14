package fred.angel.com.mgank.component.Utils;

import android.os.Environment;
import android.text.TextUtils;

import java.io.File;

import fred.angel.com.mgank.IApplication;

/**
 * Created by Comori on 2016/11/3.
 * Todo
 */

public class FileUtils {

    /**
     * 判断手机是否有SD卡。
     *
     * @return 有SD卡返回true，没有返回false。
     */
    private static boolean hasSDCard() {
        return Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState());
    }

    /**
     * 返回sd卡目录
     *
     * @return
     */
    public static String getSDPath() {
        if (hasSDCard()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        } else {
            return null;
        }
    }

    /**
     * 获取私有的磁盘文件路径
     *
     * @return
     */
    public static String getFileCachePath(String fileName) {
        return IApplication.INSTACE.getExternalFilesDir(null).getAbsolutePath() + File.separator +
                Constant.FILE_DIR + File.separator + fileName;
    }

    /**
     * 获取磁盘文件夹
     *
     * @return
     */
    public static File getDiskDir() {
        String sdcard = getSDPath();
        if (!TextUtils.isEmpty(sdcard)) {
            String path = sdcard + File.separator + Constant.FILE_DIR;
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            return file;
        }
        return null;
    }

    /**
     * 获取私有的缓存文件夹路径
     *
     * @return
     */
    public static String getCacheDirPath() {
        String path = "";
        File cacheFile = IApplication.INSTACE.getExternalCacheDir();
        if (cacheFile == null) {
            cacheFile = IApplication.INSTACE.getCacheDir();
        }
        path = cacheFile.getAbsolutePath() + File.separator + Constant.FILE_DIR;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    /**
     * 将SD卡文件删除
     *
     * @param file
     */
    public static void deleteFile(File file) {
        if (file.exists()) {
            if (file.isFile()) {
                file.delete();
            }
            // 如果它是一个目录
            else if (file.isDirectory()) {
                // 声明目录下所有的文件 files[];
                File files[] = file.listFiles();
                for (File file1 : files) { // 遍历目录下所有的文件
                    deleteFile(file1); // 把每个文件 用这个方法进行迭代
                }
            }
            file.delete();
        }
    }
}
