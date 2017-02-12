package fred.angel.com.mgank.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.component.Utils.DisplayUtil;
import fred.angel.com.mgank.component.Utils.Utils;
import fred.angel.com.mgank.model.enity.Gank;
import fred.angel.com.mgank.view.WebActivity;

/**
 * Created by chenqiang on 2016/11/8.
 * Todo
 */

public class CategoryGankAdapter extends RecyclerView.Adapter<GankHolder> {

    private Context context;
    private List<Gank> ganks;

    public CategoryGankAdapter(Context context) {
        this(context,null);
    }

    public CategoryGankAdapter(Context context, List<Gank> ganks) {
        this.context = context;
        this.ganks = ganks;
    }

    @Override
    public GankHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new GankHolder(LayoutInflater.from(context).inflate(R.layout.item_gank,null));
    }

    @Override
    public void onBindViewHolder(GankHolder holder, int position) {
        final Gank gank = ganks.get(position);
        holder.img.setImageAuto((gank.getImages()==null || gank.getImages().isEmpty())?"":gank.getImages().get(0)
                .concat("?imageView2/0/w/"+ DisplayUtil.dip2px(70)));
        holder.contentTv.setText(gank.getDesc());
        holder.authorTv.setText(gank.getWho());
        holder.timeTv.setText(gank.getPublishedAt());
        holder.tagImg.setImageResource(Utils.getTypeDrawableResId(gank.getType()));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.startActivity(new Intent(context, WebActivity.class).putExtra("web_url",gank.getUrl()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return ganks==null?0:ganks.size();
    }

    public void setData(List<Gank> data) {
        this.ganks = data;
        notifyDataSetChanged();
    }

    public void addData(List<Gank> ganks) {
        int positionStart = this.ganks.size();
        this.ganks.addAll(ganks);
        notifyItemRangeInserted(positionStart,ganks.size());
    }
}