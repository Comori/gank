package fred.angel.com.mgank.component;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

/**
 * 自定义通用列表<br/>
 * 管理列表滚动<br/>
 * @author chenqiang
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
		addOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
				super.onScrollStateChanged(recyclerView, newState);
			}

			@Override
			public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
				if(iRecyclerViewListener != null){
					final LinearLayoutManager layoutManager = (LinearLayoutManager) getLayoutManager();
					if (dy > 0 && layoutManager != null // dy>0表示向下滑动
							&& layoutManager.findLastVisibleItemPosition() >= layoutManager.getItemCount() - 1) { // 剩下1个item自动加载
						iRecyclerViewListener.loadMore();
					}
				}

				super.onScrolled(recyclerView, dx, dy);
			}

		});
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

//	/**
//	 * 设置顶部空隙
//	 * @param topSpace
//	 */
//	public void setTopSpace(int topSpace) {
//		this.topSpace = topSpace;
//		Adapter adapter = getAdapter();
//		if (adapter != null && adapter instanceof BaseRecyclerViewAdapter) {
//			((BaseRecyclerViewAdapter) adapter).setTopSpace(topSpace);
//		}
//	}

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

//		if (adapter != null && adapter instanceof BaseRecyclerViewAdapter) {
//			((BaseRecyclerViewAdapter) adapter).setTopSpace(topSpace);
//		}
//
//		if (adapter instanceof BaseRecyclerViewAdapter) {
//			this.adapter = (BaseRecyclerViewAdapter) adapter;
//		}
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
//	public PageRecyclerLayout getRefreshLayout() {
//		if (multiStateLayout != null && multiStateLayout instanceof PageRecyclerLayout) {
//			return (PageRecyclerLayout) multiStateLayout;
//		} else {
//			return null;
//		}
//	}
//
//	void setMultiStateLayout(MultiStateLayout refreshLayout) {
//		this.multiStateLayout = refreshLayout;
//	}

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
}
