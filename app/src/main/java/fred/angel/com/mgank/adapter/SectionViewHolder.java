package fred.angel.com.mgank.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fred.angel.com.mgank.R;


/**
 * Created by chenqiang
 * Todo
 */
public class SectionViewHolder extends RecyclerView.ViewHolder{
    public View itemView;
    public TextView tv_name;
    public ImageView img;

    public SectionViewHolder(Context context){
        this(LayoutInflater.from(context).inflate(R.layout.section_layout,null));
    }

    public SectionViewHolder(View itemView) {
        super(itemView);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        tv_name = (TextView) itemView.findViewById(R.id.name_tv);
        img = (ImageView) itemView.findViewById(R.id.img);
    }
}
