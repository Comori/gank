package fred.angel.com.mgank.component.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ItemDecoration;
import android.view.View;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.component.Utils.DisplayUtil;


/**
 * list的recyclerview的分割线，支持横向和竖向的
 * 
 * @author Comori
 *
 */
public class DividerItemDecoration extends ItemDecoration {
	/**
	 * RecyclerView的布局方向，默认先赋值 为纵向布局 RecyclerView 布局可横向，也可纵向 横向和纵向对应的分割想画法不一样
	 */
	private int mOrientation = LinearLayoutManager.VERTICAL;

	/**
	 * item之间分割线的size，默认为1
	 */
	private int mItemSize = 1;

	/**
	 * 绘制item分割线的画笔，和设置其属性 来绘制个性分割线
	 */
	private Paint mPaint;
	/**
	 * 分割线左边缘或者下边缘
	 */
	private int paddingStart;
	/**
	 * 分割线右边缘或者上边缘
	 */
	private int paddingEnd;
	/**
	 * 顶部或左边少绘制的分割线数量
	 */
	private int unDrawStartLineCount = 0;
	/**
	 * 底部或右边少绘制的分割线数量
	 */
	private int unDrawEndLineCount = 0;

	public DividerItemDecoration(Context context) {
		this(context, LinearLayoutManager.VERTICAL);
	}

	/**
	 * 构造方法传入布局方向，不可不传
	 * 
	 * @param context
	 * @param orientation
	 */
	public DividerItemDecoration(Context context, int orientation) {
		this.mOrientation = orientation;
		if (orientation != LinearLayoutManager.VERTICAL && orientation != LinearLayoutManager.HORIZONTAL) {
			throw new IllegalArgumentException("请传入正确的参数");
		}
		final Resources resources = context.getResources();
		mItemSize = DisplayUtil.dip2px(0.5f);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(resources.getColor(R.color.line_color));
		/* 设置填充 */
		mPaint.setStyle(Paint.Style.FILL);
	}

	/**
	 * 设置分割线与左边缘（垂直列表）或下边缘（水平列表）的距离
	 * @param paddingStart
	 */
	public void setPaddingStart(int paddingStart) {
		this.paddingStart = paddingStart;
	}

	public void setLineColor(int color){
		mPaint.setColor(color);
	}

	public void setItemSize(int size){
		if(size > 0)
		mItemSize = size;
	}
	/**
	 * 设置分割线与右边缘（垂直列表）或上边缘（水平列表）的距离
	 * @param paddingEnd
	 */
	public void setPaddingEnd(int paddingEnd) {
		this.paddingEnd = paddingEnd;
	}

	/**
	 * 设置顶部（垂直列表）或左边（水平列表）不画的的分割线数量
	 * @param unDrawStartLineCount
	 */
	public void setUnDrawStartLineCount(int unDrawStartLineCount) {
		this.unDrawStartLineCount = unDrawStartLineCount;
	}

	/**
	 * 设置底部（垂直列表）或右边（水平列表）不画的的分割线数量
	 * @param unDrawEndLineCount
	 */
	public void setUnDrawEndLineCount(int unDrawEndLineCount) {
		this.unDrawEndLineCount = unDrawEndLineCount;
	}

	@Override
	public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
		if (mOrientation == LinearLayoutManager.VERTICAL) {
			drawVertical(c, parent);
		} else {
			drawHorizontal(c, parent);
		}
	}

	/**
	 * 绘制纵向item分割线
	 * 
	 * @param canvas
	 * @param parent
	 */
	private void drawVertical(Canvas canvas, RecyclerView parent) {
		final int left = parent.getPaddingLeft() + paddingStart;
		final int right = parent.getMeasuredWidth() - parent.getPaddingRight() - paddingEnd;
		final int childSize = parent.getChildCount() - unDrawEndLineCount;
		for (int i = unDrawStartLineCount; i < childSize; i++) {
			final View child = parent.getChildAt(i);
			RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
			int top = child.getBottom() + layoutParams.bottomMargin;
			int bottom = top + mItemSize;
			canvas.drawRect(left, top, right, bottom, mPaint);
		}
	}

	/**
	 * 绘制横向item分割线
	 * 
	 * @param canvas
	 * @param parent
	 */
	private void drawHorizontal(Canvas canvas, RecyclerView parent) {
		final int top = parent.getPaddingTop() + paddingStart;
		final int bottom = parent.getMeasuredHeight() - parent.getPaddingBottom() - paddingEnd;
		final int childSize = parent.getChildCount() - unDrawEndLineCount;
		for (int i = unDrawStartLineCount; i < childSize; i++) {
			final View child = parent.getChildAt(i);
			RecyclerView.LayoutParams layoutParams = (RecyclerView.LayoutParams) child.getLayoutParams();
			final int left = child.getRight() + layoutParams.rightMargin;
			final int right = left + mItemSize;
			canvas.drawRect(left, top, right, bottom, mPaint);
		}
	}

	/**
	 * 设置item分割线的size
	 * 
	 * @param outRect
	 * @param view
	 * @param parent
	 * @param state
	 */
	@Override
	public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
		if (mOrientation == LinearLayoutManager.VERTICAL) {
			outRect.set(0, 0, 0, mItemSize);
		} else {
			outRect.set(0, 0, mItemSize, 0);
		}
	}

}
