package fred.angel.com.mgank.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import fred.angel.com.mgank.component.widget.photoview.PhotoView;
import fred.angel.com.mgank.model.enity.Gank;

public class PhotoGalleryAdapter extends PagerAdapter {

    private Context context;
    private List<Gank> ganks;
    OnItemClickListener listener;
    int currentPosition = 0;

    public PhotoGalleryAdapter(Context context, List<Gank> ganks) {
        this.context = context;
        this.ganks = ganks;
    }

    @Override
    public int getCount() {
        return ganks==null?0:ganks.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, final int position) {
        PhotoView photoView = new PhotoView(context);
        photoView.enable();
        final Gank gank = ganks.get(position);
        Glide.with(context).load(gank.getUrl())
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .fitCenter()
                .into(photoView);
        if(listener != null){
            photoView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.OnItemClick(position,gank);
                }
            });
        }
        container.addView(photoView, ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        return photoView;
    }


    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        if (object != null) {
            container.removeView((View) object);
        }
    }

//    @Override
//    public void setPrimaryItem(ViewGroup container, int position, Object object) {
//        if(mCurrentView == null ){
//            mCurrentView = (PhotoView) object;
//            mCurrentView.animaFrom(((WelfareAdapter.PicHolder)adapter.findViewHolderForAdapterPosition(position)).imageView.getInfo());
//
//
//        }else {
//            mCurrentView = (PhotoView) object;
//        }
//    }

    public void setListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnItemClickListener{
        void OnItemClick(int position,Gank gank);
    }
}
