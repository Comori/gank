package fred.angel.com.mgank.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.component.FrescoImgView;
import fred.angel.com.mgank.component.Utils.Utils;

/**
 * Created by chenqiang on 2016/11/8.
 * Todo
 */
public class GankHolder extends RecyclerView.ViewHolder{

    FrescoImgView img;
    TextView contentTv,authorTv,timeTv;
    ImageView tagImg;

    public GankHolder(View itemView) {
        super(itemView);
        itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        img = Utils.findView(itemView, R.id.img);
        contentTv = Utils.findView(itemView,R.id.content_tv);
        authorTv = Utils.findView(itemView,R.id.creator_name_tv);
        timeTv = Utils.findView(itemView,R.id.create_time_tv);
        tagImg = Utils.findView(itemView,R.id.tag_img);
    }

}
