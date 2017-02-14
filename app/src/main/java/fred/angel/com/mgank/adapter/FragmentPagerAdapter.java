package fred.angel.com.mgank.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.ViewGroup;

import java.util.List;

import fred.angel.com.mgank.component.BaseFragment;


/**
 * viewpager+fragment的共用adapter <br>
 * 默认不会自动销毁过期的页面
 * 
 * @author chenqiang
 * 
 */
public class FragmentPagerAdapter extends android.support.v4.app.FragmentPagerAdapter {
	private List<BaseFragment> fragments;
	private boolean destoryPager;

	public FragmentPagerAdapter(FragmentManager fragmentManager, List<BaseFragment> fragments) {
		this(fragmentManager, fragments, false);
	}

	public FragmentPagerAdapter(FragmentManager fragmentManager, List<BaseFragment> fragments, boolean destoryPager) {
		super(fragmentManager);
		this.fragments = fragments;
		this.destoryPager = destoryPager;
	}

	@Override
	public int getCount() {
		return fragments.size();
	}

	@Override
	public Fragment getItem(int arg0) {
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

	public void setFragments(List<BaseFragment> fragments){
		this.fragments = fragments;
		notifyDataSetChanged();
	}
}
