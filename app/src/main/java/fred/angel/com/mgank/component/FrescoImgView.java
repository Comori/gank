package fred.angel.com.mgank.component;

import android.content.Context;
import android.graphics.drawable.Animatable;
import android.net.Uri;
import android.text.TextUtils;
import android.util.AttributeSet;

import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.drawee.backends.pipeline.PipelineDraweeControllerBuilder;
import com.facebook.drawee.controller.ControllerListener;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.view.SimpleDraweeView;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.ImageInfo;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imagepipeline.request.ImageRequestBuilder;

import java.io.Serializable;

/**
 * Created by chenqiang on 2016/3/1.
 * Todo 封装的Fresco照片类
 */
public class FrescoImgView extends SimpleDraweeView {

    private boolean isProgressiveRenderingEnabled;
    private LoadImageListener loadImageListener;


    public enum ImageType implements Serializable {
        NET,
        FILE,
        ASSETS,
        RES,
        CONTENT_PROVIDER
    }

    public FrescoImgView(Context context, GenericDraweeHierarchy hierarchy) {
        super(context, hierarchy);
    }

    public FrescoImgView(Context context) {
        super(context);
    }

    public FrescoImgView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FrescoImgView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    /**
     * @param type
     * @param path - 支持网络，本地，res，asset
     */
    public void setImage(ImageType type, String path) {

        if (TextUtils.isEmpty(path)) path = "";

        boolean isGif = path.endsWith(".gif");
//        boolean isGif = true;

        final Uri uri = Uri.parse(formatFrescoImg(type).concat(path));

        ImageRequest request = ImageRequestBuilder
                .newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true)
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(getController())
                .setAutoPlayAnimations(isGif)
                .setControllerListener(new ControllerListener<ImageInfo>() {
                    @Override
                    public void onSubmit(String id, Object callerContext) {
                    }
                    @Override
                    public void onFinalImageSet(String id, ImageInfo imageInfo, Animatable animatable) {
                        if(loadImageListener != null){
                            loadImageListener.onComplete(id,imageInfo);
                        }
                    }
                    @Override
                    public void onIntermediateImageSet(String id, ImageInfo imageInfo) {
                    }
                    @Override
                    public void onIntermediateImageFailed(String id, Throwable throwable) {
                    }
                    @Override
                    public void onFailure(String id, Throwable throwable) {
                    }
                    @Override
                    public void onRelease(String id) {
                        if(loadImageListener != null){
                            loadImageListener.onRealse();
                        }
                    }
                })
                .build();
        setController(controller);
    }


    /**
     * 直接设置Uri格式照片
     *
     * @param uri
     */
    public void setImage(Uri uri) {
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setUri(uri)
                .setOldController(getController())
                .setAutoPlayAnimations(false)
                .build();
        setController(controller);
    }

    public void setImage(ImageType type, String path, ResizeOptions options) {

        if (TextUtils.isEmpty(path)) path = "";

        boolean isGif = path.endsWith(".gif");

        Uri uri = Uri.parse(formatFrescoImg(type).concat(path));

        ImageRequestBuilder builder  = ImageRequestBuilder.newBuilderWithSource(uri)
                .setProgressiveRenderingEnabled(true);
        if(!isGif){
            builder.setResizeOptions(options);
        }
        ImageRequest request = builder.build();
        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setImageRequest(request)
                .setOldController(getController())
                .setAutoPlayAnimations(isGif)
                .build();
        setController(controller);
    }

    /**
     * 先展示缩略图，再展示原图
     *
     * @param lowUrl
     * @param url
     */
    public void setImage(String lowUrl, String url) {
        ImageType lowType;
        if(!lowUrl.startsWith("http")){//暂时只支持file和net
            lowType = ImageType.FILE;
        }else {
            lowType = ImageType.NET;
        }

        Uri lowResUri = Uri.parse(formatFrescoImg(lowType).concat(lowUrl));
        Uri highResUri = Uri.parse(formatFrescoImg(ImageType.NET).concat(url));

        ImageRequest request = ImageRequestBuilder.newBuilderWithSource(highResUri)
                .setProgressiveRenderingEnabled(true)
                .build();

        DraweeController controller = Fresco.newDraweeControllerBuilder()
                .setLowResImageRequest(ImageRequest.fromUri(lowResUri))
                .setImageRequest(request)
                .setOldController(getController())
                .build();
        setController(controller);
    }

    /**
     * 只是简单针对本地照片和网路照片
     * @param url
     */
    public void setImageAuto(String url) {
        if(TextUtils.isEmpty(url)){
            url = "";
        }
        ImageType type;
        if(url.startsWith("http")){
            type = ImageType.NET;
        }else {
            type = ImageType.FILE;
        }

        setImage(type,url);
    }

    /**
     * 自适应aspect
     * @param path
     */
    public void setImageAutoAspect(String path) {
        if(TextUtils.isEmpty(path)) path="";

        boolean isGif = path.endsWith(".gif");

        Uri uri = Uri.parse(formatFrescoImg(ImageType.NET).concat(path));

        if(!isGif) {
            ControllerListener<ImageInfo> listener = new ControllerListener<ImageInfo>() {
                @Override
                public void onSubmit(String s, Object o) {
                }

                @Override
                public void onFinalImageSet(String s, ImageInfo imageInfo, Animatable animatable) {
                    if (imageInfo == null) return;
                    float aspect = imageInfo.getWidth() * 1.0f / imageInfo.getHeight();
                    if(aspect < 0.5){
                        aspect = 0.5f;
                    }
                    setAspectRatio(aspect);
                }

                @Override
                public void onIntermediateImageSet(String s, ImageInfo imageInfo) {
                }

                @Override
                public void onIntermediateImageFailed(String s, Throwable throwable) {
                }

                @Override
                public void onFailure(String s, Throwable throwable) {
                }

                @Override
                public void onRelease(String s) {
                }
            };
            DraweeController controller = Fresco.newDraweeControllerBuilder()
                    .setControllerListener(listener)
                    .setUri(uri)
                    .build();
            setController(controller);
            return;
        }

        if(isProgressiveRenderingEnabled){
            DraweeController controller;
            ImageRequest request = ImageRequestBuilder
                    .newBuilderWithSource(uri)
                    .setProgressiveRenderingEnabled(true)
                    .build();
            if(isGif){
                controller  = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(getController())
                        .setAutoPlayAnimations(true)
                        .build();
            }else {
                controller  = Fresco.newDraweeControllerBuilder()
                        .setImageRequest(request)
                        .setOldController(getController())
                        .build();
            }
            setController(controller);
        }else {
            PipelineDraweeControllerBuilder builder = Fresco.newDraweeControllerBuilder()
                    .setUri(uri)
                    .setAutoPlayAnimations(isGif)
                    .setOldController(getController());
            DraweeController controller = builder.build();
            setController(controller);
        }
    }

    public void setLoadImageListener(LoadImageListener loadImageListener) {
        this.loadImageListener = loadImageListener;
    }

    public interface LoadImageListener{
        void onComplete(String url, ImageInfo imageInfo);
        void onRealse();
    }

    private String formatFrescoImg(FrescoImgView.ImageType type){
        StringBuffer sb = new StringBuffer();
        switch (type){
            case NET:
                break;
            case FILE:
                sb.append("file://");
                break;
            case ASSETS:
                sb.append("asset:///");
                break;
            case RES:
                sb.append("res:///");
                break;
            case CONTENT_PROVIDER:
                sb.append("content:///");
                break;
            default:
                break;
        }
        return sb.toString();
    }
}
