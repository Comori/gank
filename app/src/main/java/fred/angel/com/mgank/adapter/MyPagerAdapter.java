package fred.angel.com.mgank.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

import fred.angel.com.mgank.component.BaseFragment;


/**
 * viewpager+fragment的共用adapter <br>
 * 默认不会自动销毁过期的页面
 * 
 * @author Comori
 * 
 */
public class MyPagerAdapter extends FragmentStatePagerAdapter {
	private List<BaseFragment> fragments;
	private boolean destoryPager;

	public MyPagerAdapter(FragmentManager fragmentManager, List<BaseFragment> fragments) {
		this(fragmentManager, fragments, false);
	}

	public MyPagerAdapter(FragmentManager fragmentManager, List<BaseFragment> fragments, boolean destoryPager) {
		super(fragmentManager);
		this.fragments = fragments;
		this.destoryPager = destoryPager;
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public BaseFragment getItem(int arg0) {
		return fragments.get(arg0);
	}

	@Override
	public CharSequence getPageTitle(int position) {
		return fragments.get(position).getPageTitle();
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// 去掉super.destroyItem方法，防止页面被销毁
		if (destoryPager) {
			super.destroyItem(container, position, object);
		}
	}
}
