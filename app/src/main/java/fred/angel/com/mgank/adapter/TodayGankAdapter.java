package fred.angel.com.mgank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.component.Utils.Constant;
import fred.angel.com.mgank.component.Utils.DisplayUtil;
import fred.angel.com.mgank.component.Utils.ImageLoader;
import fred.angel.com.mgank.component.Utils.UIHelper;
import fred.angel.com.mgank.component.Utils.Utils;
import fred.angel.com.mgank.component.stickyrecyclerview.StickyRecyclerHeadersAdapter;
import fred.angel.com.mgank.component.widget.AspectRatioImageView;
import fred.angel.com.mgank.model.enity.DateGank;
import fred.angel.com.mgank.model.enity.Gank;

/**
 * Created by Comori on 2016/11/4.
 * Todo
 */

public class TodayGankAdapter extends RecyclerView.Adapter implements StickyRecyclerHeadersAdapter{

    private static final int TYPE_WELFARE_PHOTO = 0x123;
    private DateGank dateGank;

    private Context context;

    public TodayGankAdapter(Context context) {
        this(context,null);
    }

    public TodayGankAdapter(Context context, DateGank dateGank) {
        this.context = context;
        this.dateGank = dateGank;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_WELFARE_PHOTO){
            View view = LayoutInflater.from(context).inflate(R.layout.item_welfare_photo,null);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            return new RecyclerView.ViewHolder(view) {
                @Override
                public String toString() {
                    return super.toString();
                }
            };
        }
        return new GankHolder(LayoutInflater.from(context).inflate(R.layout.item_gank,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        final Gank gank = dateGank.getItem(position);
        if(getItemViewType(position) == TYPE_WELFARE_PHOTO){
            final AspectRatioImageView img = (AspectRatioImageView) holder.itemView;
//            ImageLoader.displayImage(context,img,(gank.getUrl()==null)?"":gank.getUrl());
            Glide.with(context).load((gank.getUrl()==null)?"":gank.getUrl())
                    .thumbnail(0.5f)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .into(new ImageViewTarget<GlideDrawable>(img){
                        @Override
                        protected void setResource(GlideDrawable resource) {
                            float scale = resource.getIntrinsicWidth() * 1.0f / resource.getIntrinsicHeight();
                            img.setAspectRatio(scale);
                            img.setImageDrawable(resource);
                        }
                    });
        }else {
            GankHolder itemHolder = (GankHolder) holder;
            int pResId = 0;
            if(TextUtils.equals(gank.getType(), Constant.Category.REST_VIDEO)){
                pResId = R.drawable.ic_video_70dp;
            }else {
                pResId = R.drawable.ic_description_70dp;
            }
            ImageLoader.displayImage(context,itemHolder.img,(gank.getImages()==null || gank.getImages().isEmpty())?"":gank.getImages().get(0)
                    .concat("?imageView2/0/w/"+ DisplayUtil.dip2px(140)),pResId);
            itemHolder.contentTv.setText(gank.getDesc());
            itemHolder.authorTv.setText(gank.getWho());
            itemHolder.timeTv.setText(Utils.parseDate(gank.getPublishedAt()));
            itemHolder.tagImg.setImageResource(Utils.getTypeDrawableResId(gank.getType()));
            itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    context.startActivity(new Intent(context, WebActivity.class).putExtra("web_url",gank.getUrl()));
                    UIHelper.openWeb(context,gank.getUrl());
                }
            });

        }
    }

    @Override
    public long getHeaderId(int position) {
        return dateGank==null?0:dateGank.getItem(position).getType().hashCode();
    }

    @Override
    public RecyclerView.ViewHolder onCreateHeaderViewHolder(ViewGroup parent) {
        return new SectionViewHolder(context);
    }

    @Override
    public void onBindHeaderViewHolder(RecyclerView.ViewHolder holder, int position) {
        SectionViewHolder sectionViewHolder = (SectionViewHolder) holder;
        sectionViewHolder.tv_name.setText(dateGank.getItem(position).getType());
        switch (dateGank.getItem(position).getType()){
            case "Android":
                sectionViewHolder.img.setImageResource(R.drawable.ic_android);
                sectionViewHolder.tv_name.setTextColor(context.getResources().getColor(R.color.android_green));
                break;
            case "iOS":
                sectionViewHolder.img.setImageResource(R.drawable.ic_iphone);
                sectionViewHolder.tv_name.setTextColor(context.getResources().getColor(R.color.text_black));
                break;
            case "休息视频":
                sectionViewHolder.img.setImageResource(R.drawable.ic_video);
                sectionViewHolder.tv_name.setTextColor(context.getResources().getColor(R.color.grey));
                break;
            case "瞎推荐":
                sectionViewHolder.img.setImageResource(R.drawable.ic_widgets);
                sectionViewHolder.tv_name.setTextColor(context.getResources().getColor(R.color.grey));
                break;
            case "福利":
                sectionViewHolder.img.setImageResource(R.drawable.ic_widgets);
                sectionViewHolder.tv_name.setTextColor(context.getResources().getColor(R.color.grey));
                sectionViewHolder.tv_name.setText("每日一图");
                break;
            case "拓展资源":
                sectionViewHolder.img.setImageResource(R.drawable.ic_widgets);
                sectionViewHolder.tv_name.setTextColor(context.getResources().getColor(R.color.grey));
                break;
            case "前端":
                sectionViewHolder.img.setImageResource(R.drawable.ic_web);
                sectionViewHolder.tv_name.setTextColor(context.getResources().getColor(R.color.grey));
                break;
            case "App":
                sectionViewHolder.img.setImageResource(R.drawable.ic_widgets);
                sectionViewHolder.tv_name.setTextColor(context.getResources().getColor(R.color.grey));
                break;
        }
    }

    @Override
    public int getItemCount() {
        return dateGank==null?0:dateGank.getTotalSize();
    }

    @Override
    public int getItemViewType(int position) {
        if(TextUtils.equals(Constant.Category.WELFARE,getItemData(position).getType())){
            return TYPE_WELFARE_PHOTO;
        }
        return super.getItemViewType(position);
    }

    public void setData(DateGank data){
        this.dateGank = data;
        notifyDataSetChanged();
    }

    public Gank getItemData(int position){
        return dateGank.getItem(position);
    }

}
