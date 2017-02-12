package fred.angel.com.mgank.adapter;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.model.enity.Category;

/**
 * Created by chenqiang on 2016/11/3.
 * Todo
 */

public class CategoryAdapter extends RecyclerView.Adapter {

    /**
     * Item是否被选中监听
     */
    private OnCheckedChangeListener mOnCheckedChangeListener;

    /**
     * Item拖拽滑动帮助
     */
    private ItemTouchHelper itemTouchHelper;

    private List<Category> categories;

    public CategoryAdapter(List<Category> categories) {
        this.categories = categories;
    }

    public void setItemTouchHelper(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener mOnCheckedChangeListener) {
        this.mOnCheckedChangeListener = mOnCheckedChangeListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new CategoryViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_category,null));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CategoryViewHolder categoryViewHolder = (CategoryViewHolder) holder;
        Category category = categories.get(position);
        categoryViewHolder.mTvName.setText(category.getName());
        categoryViewHolder.mCbCheck.setChecked(category.isChecked());
    }

    @Override
    public int getItemCount() {
        return categories==null?0:categories.size();
    }

    public void setData(List<Category> data) {
        this.categories = data;
        notifyDataSetChanged();
    }

    public interface OnCheckedChangeListener {
        void onItemCheckedChange(CompoundButton view, int position, boolean checked);
    }

    class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener, View.OnTouchListener {

        TextView mTvName;
        /**
         * 触摸就可以拖拽
         */
        ImageView mIvTouch;
        /**
         * 是否选中
         */
        SwitchCompat mCbCheck;

        public CategoryViewHolder(View itemView) {
            super(itemView);
            itemView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT));
            itemView.setOnClickListener(this);
            mTvName = (TextView) itemView.findViewById(R.id.name_tv);
            mIvTouch = (ImageView) itemView.findViewById(R.id.drag_handle_ic);
            mCbCheck = (SwitchCompat) itemView.findViewById(R.id.switchCompat);
            mCbCheck.setOnClickListener(this);
            mIvTouch.setOnTouchListener(this);
        }

        @Override
        public void onClick(View view) {
             if (view == mCbCheck && mOnCheckedChangeListener != null) {
                mOnCheckedChangeListener.onItemCheckedChange(mCbCheck, getAdapterPosition(), mCbCheck.isChecked());
            }
        }

        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if (view == mIvTouch)
                itemTouchHelper.startDrag(this);
            return false;
        }
    }
}
