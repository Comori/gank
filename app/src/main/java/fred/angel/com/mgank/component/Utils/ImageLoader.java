package fred.angel.com.mgank.component.Utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.GlideDrawableImageViewTarget;

/**
 * Created by Comori on 2016/12/27.
 */

public class ImageLoader {

   public static void displayImage(Context context, ImageView imageView, String url, int placeholderResId, ImageView.ScaleType placeholderScaleType){
       if(placeholderScaleType == ImageView.ScaleType.CENTER_CROP){
           Glide.with(context).load(url)
                   .asBitmap()
                   .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                   .placeholder(placeholderResId)
                   .centerCrop()
                   .into(imageView);
       }else {
           Glide.with(context).load(url)
                   .asBitmap()
                   .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                   .placeholder(placeholderResId)
                   .centerCrop()
                   .dontAnimate()
                   .into(new PlaceholderImageTagrget(imageView,placeholderScaleType));
       }

   }

   public static void displayImage(Context context, ImageView imageView, String url, int placeholderResId){
       Glide.with(context).load(url)
               .asBitmap()
               .diskCacheStrategy(DiskCacheStrategy.SOURCE)
               .placeholder(placeholderResId)
               .centerCrop()
               .into(imageView);
   }

   public static void displayImage(Context context, ImageView imageView, String url, int placeholderResId, int width, int height, ImageView.ScaleType placeholderScaleType){
       if(placeholderScaleType == ImageView.ScaleType.CENTER_CROP){
           Glide.with(context).load(url)
                   .asBitmap()
                   .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                   .placeholder(placeholderResId)
                   .override(width,height)
                   .centerCrop()
                   .into(imageView);
       }else {
           Glide.with(context).load(url)
                   .asBitmap()
                   .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                   .placeholder(placeholderResId)
                   .override(width,height)
                   .centerCrop()
                   .dontAnimate()
                   .into(new PlaceholderImageTagrget(imageView,placeholderScaleType));
       }
   }

   public static void displayImage(Context context, ImageView imageView, String url, int placeholderResId, int width, int height){
       Glide.with(context).load(url)
               .asBitmap()
               .diskCacheStrategy(DiskCacheStrategy.SOURCE)
               .placeholder(placeholderResId)
               .override(width,height)
               .centerCrop()
               .into(imageView);
   }

   public static void displayImage(Context context, ImageView imageView, String url, int width, int height){
       Glide.with(context).load(url)
               .asBitmap()
               .diskCacheStrategy(DiskCacheStrategy.SOURCE)
               .override(width,height)
               .centerCrop()
               .into(imageView);
   }

   public static void displayImage(Context context, ImageView imageView, String url){
       Glide.with(context).load(url)
               .diskCacheStrategy(DiskCacheStrategy.SOURCE)
               .centerCrop()
               .into(new GlideDrawableImageViewTarget(imageView, 1));
   }
   public static void displayImage(Context context, ImageView imageView, int srcResId){
       Glide.with(context).load(srcResId)
               .diskCacheStrategy(DiskCacheStrategy.SOURCE)
               .into(new GlideDrawableImageViewTarget(imageView, 1));
   }

    static class PlaceholderImageTagrget extends BitmapImageViewTarget{
        ImageView.ScaleType scaleType;

        public PlaceholderImageTagrget(ImageView view, ImageView.ScaleType scaleType) {
            super(view);
            this.scaleType = scaleType;
        }

        @Override
        public void onLoadStarted(Drawable placeholder) {
            view.setScaleType(scaleType);
            view.setImageDrawable(placeholder);
        }

        @Override
        public void onLoadCleared(Drawable placeholder) {
            view.setScaleType(scaleType);
            view.setImageDrawable(placeholder);
        }
    }
}
