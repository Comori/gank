package fred.angel.com.mgank.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.ImageViewTarget;

import java.util.List;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.component.Utils.Utils;
import fred.angel.com.mgank.component.widget.AspectRatioImageView;
import fred.angel.com.mgank.model.enity.Mind;

/**
 * @author chenqiang
 */
public class MindAdapter extends PagerAdapter{

    Context context;
    List<Mind> minds;
    LayoutInflater inflater;

    SparseArray<ViewHolder> holderSparseArray;

    public MindAdapter(Context context, List<Mind> minds) {
        this.context = context;
        this.minds = minds;
        inflater = LayoutInflater.from(context);
        holderSparseArray = new SparseArray<>();
    }

    @Override
    public int getCount() {
        return minds==null?0:minds.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public ViewHolder findHolder(int position){
        return holderSparseArray.get(position);
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        final ViewHolder holder = new ViewHolder(inflater.inflate(R.layout.item_find,null));
        Mind mind = minds.get(position);
        Glide.with(context).load(mind.getHp_img_url())
                .thumbnail(0.5f)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(new ImageViewTarget<GlideDrawable>(holder.img){
                    @Override
                    protected void setResource(GlideDrawable resource) {
                        float scale = resource.getIntrinsicWidth() * 1.0f / resource.getIntrinsicHeight();
                        holder.img.setAspectRatio(scale);
                        holder.img.setImageDrawable(resource);
                    }
                });
        holder.categoryTv.setText(mind.getHp_author().concat(" | ").concat(mind.getImage_authors()));
        holder.contentTv.setText(mind.getHp_content());
        holder.sourceTv.setText(mind.getText_authors());
        holder.timeTv.setText(Utils.formatMindTime(mind.getMaketime()));

        container.addView(holder.itemView, ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);

        holderSparseArray.put(position,holder);

        return holder.itemView;

    }

    public class ViewHolder{
        public AspectRatioImageView img;
        TextView categoryTv,sourceTv,contentTv,timeTv;
        View itemView;

        public ViewHolder(View view) {
            itemView = view;
            img = Utils.findView(view, R.id.img);
            categoryTv = Utils.findView(view, R.id.category_tv);
            sourceTv = Utils.findView(view, R.id.source_tv);
            contentTv = Utils.findView(view, R.id.content_tv);
            timeTv = Utils.findView(view, R.id.date_tv);

        }
    }
}
