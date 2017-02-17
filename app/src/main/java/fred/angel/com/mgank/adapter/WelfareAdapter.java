package fred.angel.com.mgank.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;

import java.util.ArrayList;
import java.util.List;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.component.Utils.DisplayUtil;
import fred.angel.com.mgank.component.Utils.Utils;
import fred.angel.com.mgank.model.enity.Gank;
import fred.angel.com.mgank.model.enity.SizeModel;

/**
 * 福利照片
 */
public class WelfareAdapter extends RecyclerView.Adapter {

    private Context context;
    private List<Gank> ganks;
    private List<SizeModel> mSizeModels;
    int size = 0;

    public WelfareAdapter(Context context) {
        this.context = context;
        size = (int) ((DisplayUtil.getScreenWidth() - DisplayUtil.dip2px(4)) / 2f);
    }

    public WelfareAdapter(Context context, List<Gank> ganks) {
        this.context = context;
        this.ganks = ganks;
    }

    public void setData(List<Gank> data) {
        this.ganks = data;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PicHolder(LayoutInflater.from(context).inflate(R.layout.item_pic, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        final Gank gank = ganks.get(position);
        PicHolder picHolder = (PicHolder) holder;

        if (mSizeModels == null) {
            mSizeModels = new ArrayList<>();
        }
        SizeModel sizeModel = null;
        if (position < mSizeModels.size()) {
            sizeModel = mSizeModels.get(position);
        }
        if (sizeModel == null || !TextUtils.equals(gank.getUrl(), sizeModel.getUrl())) {
            sizeModel = new SizeModel();
            sizeModel.setUrl(gank.getUrl());
            mSizeModels.add(sizeModel);
        } else {
            setImageLayoutParams(picHolder.imageView,sizeModel.getWidth(),sizeModel.getHeight());
        }

        Glide.with(context).load(gank.getUrl()).asBitmap().
                fitCenter().override(size, BitmapImageViewTarget.SIZE_ORIGINAL).into(new DriverViewTarget(picHolder, sizeModel));
        picHolder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println(gank.getUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return ganks == null ? 0 : ganks.size();
    }

    class PicHolder extends RecyclerView.ViewHolder {

        ImageView imageView;

        public PicHolder(View itemView) {
            super(itemView);
            imageView = Utils.findView(itemView, R.id.pic);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        }
    }

    private class DriverViewTarget extends BitmapImageViewTarget {

        PicHolder holder;
        SizeModel sizeModel;

        public DriverViewTarget(PicHolder holder, SizeModel sizeModel) {
            super(holder.imageView);
            this.holder = holder;
            this.sizeModel = sizeModel;
        }

        @Override
        public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
            if (sizeModel == null || sizeModel.isNull()) {
                int viewWidth = size;
                float scale = resource.getWidth() / (viewWidth * 1.0f);
                int viewHeight = (int) (resource.getHeight() / scale);
                setImageLayoutParams(holder.imageView,viewWidth,viewHeight);
                sizeModel.setSize(viewWidth, viewHeight);
            }
            super.onResourceReady(resource, glideAnimation);
        }
    }

    public void addData(List<Gank> ganks) {
        int positionStart = this.ganks.size();
        this.ganks.addAll(ganks);
        notifyItemRangeInserted(positionStart, ganks.size());
    }

    private void setImageLayoutParams(View view, int width, int height) {
        ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
        layoutParams.width = width;
        layoutParams.height = height;
        view.setLayoutParams(layoutParams);
    }
}
