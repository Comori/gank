package fred.angel.com.mgank.component;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ViewGroup;

import fred.angel.com.mgank.component.Utils.Utils;

/**
 * 自定义通用列表<br/>
 * 管理列表滚动<br/>
 * @author Comori
 */
public class IRecyclerView extends RecyclerView {

	/** 顶部空隙 **/
	private int topSpace;
	/** 缓慢滚动时是否暂停加载数据 **/
	private boolean pauseOnDragging = true;
	/** 快速滑动时是否暂停加载数据 **/
	private boolean pauseOnSettling = true;
	IRecyclerViewListener iRecyclerViewListener;

	private final AdapterDataObserver observer = new AdapterDataObserver() {
		@Override
		public void onChanged() {
			checkIfEmpty();
		}

		@Override
		public void onItemRangeInserted(int positionStart, int itemCount) {
			checkIfEmpty();
		}

		@Override
		public void onItemRangeRemoved(int positionStart, int itemCount) {
			checkIfEmpty();
		}

		@Override
		public void onItemRangeChanged(int positionStart, int itemCount) {
			checkIfEmpty();
		}

		@Override
		public void onItemRangeMoved(int fromPosition, int toPosition, int itemCount) {
			checkIfEmpty();
		}

	};
	private boolean needIntercept;
	private ViewGroup viewGroup;

	public IRecyclerView(Context context) {
		this(context, null);
	}

	public IRecyclerView(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	public IRecyclerView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		init();
	}

	private void init() {
//		addOnScrollListener(new OnScrollListener() {
//
//			@Override
//			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
//				super.onScrollStateChanged(recyclerView, newState);
//			}
//
//			@Override
//			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//				if(iRecyclerViewListener != null){
//					if(getLayoutManager() instanceof LinearLayoutManager){
//						final LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
//						if (dy > 0 && layoutManager != null // dy>0表示向下滑动
//								&& layoutManager.findLastVisibleItemPosition() >= layoutManager.getItemCount() - 1) { // 剩下1个item自动加载
//							iRecyclerViewListener.loadMore();
//						}
//					}else {
//						final StaggeredGridLayoutManager layoutManager = (StaggeredGridLayoutManager) getLayoutManager();
//						int[] lastPosition = layoutManager.findFirstVisibleItemPositions(null);
//						if (dy > 0 && layoutManager != null // dy>0表示向下滑动
//								&& layoutManager.findLastVisibleItemPositions() >= layoutManager.getItemCount() - 1) { // 剩下1个item自动加载
//							iRecyclerViewListener.loadMore();
//						}
//					}
//				}
//
//				super.onScrolled(recyclerView, dx, dy);
//			}
//
//		});
	}

	/**
	 * 设置滚动时是否可加载照片
	 * @param pauseOnDragging 手指拖动时是否可加载照片
	 * @param pauseOnSettling  手指拖动结束，列表自动滚动时是否可加载照片（应该是指这个过程吧）
	 */
	public void setOnScrollListener(boolean pauseOnDragging, boolean pauseOnSettling) {
		this.pauseOnDragging = pauseOnDragging;
		this.pauseOnSettling = pauseOnSettling;
	}


	@Override
	public void setAdapter(Adapter adapter) {
		final Adapter oldAdapter = getAdapter();
		if (oldAdapter != null) {
			oldAdapter.unregisterAdapterDataObserver(observer);
		}
		super.setAdapter(adapter);
		if (adapter != null) {
			adapter.registerAdapterDataObserver(observer);
		}

	}
//
	public void checkIfEmpty() {
//		if (iRecyclerViewListener != null && adapter != null) {
//			if (adapter.getItemCount() == 0) {
//				iRecyclerViewListener.onEmpty();
//			} else {
//			}
//		}
	}
//

	public interface IRecyclerViewListener{

		void onEmpty();

		void loadMore();

	}

	public IRecyclerViewListener getiRecyclerViewListener() {
		return iRecyclerViewListener;
	}

	public void setIRecyclerViewListener(IRecyclerViewListener iRecyclerViewListener) {
		this.iRecyclerViewListener = iRecyclerViewListener;
	}


	public enum LAYOUT_MANAGER_TYPE {
		LINEAR,
		GRID,
		STAGGERED_GRID
	}

	private LAYOUT_MANAGER_TYPE layoutManagerType;

	/**
	 * 最后一个的位置
	 */
	private int[] lastPositions;

	/**
	 * 最后一个可见的item的位置
	 */
	private int lastVisibleItemPosition =-1;

	/**
	 * 第一个可见的item的位置
	 */
	private int firstVisibleItemPosition = -1;

	int direction = 0;


	@Override
	public void onScrolled(int dx, int dy) {
		super.onScrolled(dx, dy);
		direction = dy;
		Log.e("SC","dy: "+dy);
		RecyclerView.LayoutManager layoutManager = getLayoutManager();
		if (layoutManagerType == null) {
			if (layoutManager instanceof LinearLayoutManager) {
				layoutManagerType = LAYOUT_MANAGER_TYPE.LINEAR;
			} else if (layoutManager instanceof StaggeredGridLayoutManager) {
				layoutManagerType = LAYOUT_MANAGER_TYPE.STAGGERED_GRID;
			} else {
				throw new RuntimeException(
						"Unsupported LayoutManager used. Valid ones are LinearLayoutManager, GridLayoutManager and StaggeredGridLayoutManager");
			}
		}

		switch (layoutManagerType) {
			case LINEAR:
				lastVisibleItemPosition = ((LinearLayoutManager) layoutManager).findLastVisibleItemPosition();
				firstVisibleItemPosition = ((LinearLayoutManager) layoutManager).findFirstVisibleItemPosition();
				break;
			case GRID:
				lastVisibleItemPosition = ((GridLayoutManager) layoutManager).findLastVisibleItemPosition();
				firstVisibleItemPosition = ((GridLayoutManager) layoutManager).findFirstVisibleItemPosition();
				break;
			case STAGGERED_GRID:
				StaggeredGridLayoutManager staggeredGridLayoutManager = (StaggeredGridLayoutManager) layoutManager;
				if (lastPositions == null) {
					lastPositions = new int[staggeredGridLayoutManager.getSpanCount()];
				}
				staggeredGridLayoutManager.findLastVisibleItemPositions(lastPositions);
				lastVisibleItemPosition = findMax(lastPositions);
				firstVisibleItemPosition = findMin(lastPositions);
				break;
		}
	}


	@Override
	public void onScrollStateChanged(int state) {
		super.onScrollStateChanged(state);
		if(state == RecyclerView.SCROLL_STATE_IDLE){
			RecyclerView.LayoutManager layoutManager = getLayoutManager();
			int visibleItemCount = layoutManager.getChildCount();
			int totalItemCount = layoutManager.getItemCount();
			if (visibleItemCount > 0 && state == RecyclerView.SCROLL_STATE_IDLE && lastVisibleItemPosition == totalItemCount - 1 && iRecyclerViewListener != null) {
				iRecyclerViewListener.loadMore();
			}

			if(needIntercept ){
//				if(visibleItemCount > 0 && ((direction >= 0 && lastVisibleItemPosition == totalItemCount - 1) || (direction <= 0 || firstVisibleItemPosition ==0))){
//					viewGroup.requestDisallowInterceptTouchEvent(false);
//				}else viewGroup.requestDisallowInterceptTouchEvent(true);
			}

		}
	}


	public void needIntercept(ViewGroup viewGroup, boolean need){
		this.needIntercept = need;
		this.viewGroup = viewGroup;
	}
	float downY = 0;
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(needIntercept){
			switch (ev.getAction()){
				case MotionEvent.ACTION_DOWN:
					downY = ev.getY();
					viewGroup.requestDisallowInterceptTouchEvent(true);
					break;
				case MotionEvent.ACTION_MOVE:
						float dy = ev.getY() - downY;
						if(getLayoutManager().getChildCount() > 0 && (dy > 0 && Utils.isRecyclerViewTop(this)) || (dy < 0 && Utils.isRecyclerViewBottom(this))){
							viewGroup.requestDisallowInterceptTouchEvent(false);
						}else viewGroup.requestDisallowInterceptTouchEvent(true);
					break;
				case MotionEvent.ACTION_UP:
				case MotionEvent.ACTION_CANCEL:
//					viewGroup.requestDisallowInterceptTouchEvent(true);
					break;
			}
		}

		return super.dispatchTouchEvent(ev);
	}




	private int findMax(int[] lastPositions) {
		int max = lastPositions[0];
		for (int value : lastPositions) {
			if (value > max) {
				max = value;
			}
		}
		return max;
	}

	private int findMin(int[] lastPositions) {
		int min = lastPositions[0];
		for (int value : lastPositions) {
			if (value < min) {
				min = value;
			}
		}
		return min;
	}

}
