package fred.angel.com.mgank.component.Utils;

import android.content.Context;
import android.graphics.Bitmap;

import com.facebook.cache.disk.DiskCacheConfig;
import com.facebook.common.internal.Supplier;
import com.facebook.common.util.ByteConstants;
import com.facebook.imagepipeline.cache.MemoryCacheParams;
import com.facebook.imagepipeline.core.ImagePipelineConfig;

/**
 * Fresco 照片配置
 *
 * @author chenqiang
 */
public class FrescoConfig {
    private static final int MAX_HEAP_SIZE = (int) Runtime.getRuntime().maxMemory();//分配的可用内存
    public static final int MAX_MEMORY_CACHE_SIZE = MAX_HEAP_SIZE / 4;//使用的缓存大小

    public static final int MAX_DISK_CACHE_VERYLOW_SIZE = 50 * ByteConstants.MB;//默认图极低磁盘空间缓存的最大值
    public static final int MAX_DISK_CACHE_LOW_SIZE = 100 * ByteConstants.MB;//默认图低磁盘空间缓存的最大值
    public static final int MAX_DISK_CACHE_SIZE = 200 * ByteConstants.MB;//默认图磁盘缓存的最大值


    private static ImagePipelineConfig sImagePipelineConfig;
    private static final String IMAGE_PIPELINE_CACHE_DIR = "imagepipeline_cache";//默认图所放路径的文件夹名

    private FrescoConfig() {
    }

    /**
     * 初始化配置，单例
     */
    public static ImagePipelineConfig getImagePipelineConfig(Context context) {
        if (sImagePipelineConfig == null) {
            sImagePipelineConfig = configureCaches(context);
        }
        return sImagePipelineConfig;
    }

    /**
     * 初始化配置
     */
    private static ImagePipelineConfig configureCaches(Context context) {
        //内存配置
        final MemoryCacheParams bitmapCacheParams = new MemoryCacheParams(
                FrescoConfig.MAX_MEMORY_CACHE_SIZE, // 内存缓存中总照片的最大大小,以字节为单位。
                Integer.MAX_VALUE,                     // 内存缓存中照片的最大数量。
                FrescoConfig.MAX_MEMORY_CACHE_SIZE, // 内存缓存中准备清除但尚未被删除的总照片的最大大小,以字节为单位。
                Integer.MAX_VALUE,                     // 内存缓存中准备清除的总照片的最大数量。
                Integer.MAX_VALUE);                    // 内存缓存中单个照片的最大大小。

        //修改内存照片缓存数量，空间策略（这个方式有点恶心）
        Supplier<MemoryCacheParams> mSupplierMemoryCacheParams = new Supplier<MemoryCacheParams>() {
            @Override
            public MemoryCacheParams get() {
                return bitmapCacheParams;
            }
        };


        //默认照片的磁盘配置
        DiskCacheConfig diskCacheConfig = DiskCacheConfig.newBuilder(context)
                .setBaseDirectoryPath(context.getApplicationContext().getExternalCacheDir())//缓存照片基路径
                .setBaseDirectoryName(IMAGE_PIPELINE_CACHE_DIR)//文件夹名
                .setMaxCacheSize(FrescoConfig.MAX_DISK_CACHE_SIZE)//默认缓存的最大大小。
                .setMaxCacheSizeOnLowDiskSpace(MAX_DISK_CACHE_LOW_SIZE)//缓存的最大大小,使用设备时低磁盘空间。
                .setMaxCacheSizeOnVeryLowDiskSpace(MAX_DISK_CACHE_VERYLOW_SIZE)//缓存的最大大小,当设备极低磁盘空间
                .build();

        ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(context)
                .setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)//内存缓存配置（一级缓存，已解码的照片）
                .setMainDiskCacheConfig(diskCacheConfig)//磁盘缓存配置（总，三级缓存）
                .setDownsampleEnabled(true) //优化卡顿
                .setBitmapsConfig(Bitmap.Config.RGB_565)
        ;

//        //缓存照片配置
//        ImagePipelineConfig.Builder configBuilder = ImagePipelineConfig.newBuilder(context)
//                .setBitmapMemoryCacheParamsSupplier(mSupplierMemoryCacheParams)//内存缓存配置（一级缓存，已解码的照片）
//                .setMainDiskCacheConfig(diskCacheConfig)//磁盘缓存配置（总，三级缓存）
//                .setDownsampleEnabled(true) //优化卡顿
//                ;
        return configBuilder.build();
    }

}
