package fred.angel.com.mgank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.component.Utils.UIHelper;
import fred.angel.com.mgank.component.Utils.Utils;
import fred.angel.com.mgank.model.enity.Gank;

/**
 * @author chenqiang
 */
public class SearchResultAdapter extends RecyclerView.Adapter {

    Context context;
    List<Gank> ganks;

    public SearchResultAdapter(Context context, List<Gank> ganks) {
        this.context = context;
        this.ganks = ganks;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemHolder(LayoutInflater.from(context).inflate(R.layout.item_search,parent,false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ItemHolder itemHolder = (ItemHolder) holder;
        final Gank gank = ganks.get(position);
        itemHolder.timeTv.setText(Utils.parseDate(gank.getPublishedAt()));
        itemHolder.categoryImg.setImageResource(Utils.getTypeDrawableResId(gank.getType()));
        itemHolder.titleTv.setText(gank.getDesc());
        itemHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UIHelper.openWeb(context,gank.getUrl());
            }
        });
    }

    @Override
    public int getItemCount() {
        return ganks==null?0:ganks.size();
    }

    class ItemHolder extends RecyclerView.ViewHolder{

        TextView titleTv,timeTv;
        ImageView categoryImg;

        public ItemHolder(View itemView) {
            super(itemView);
            timeTv = Utils.findView(itemView, R.id.time_tv);
            titleTv = Utils.findView(itemView, R.id.title_tv);
            categoryImg = Utils.findView(itemView, R.id.category_img);
        }
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
