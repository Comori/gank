package fred.angel.com.mgank.component;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.TabLayout;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.TypedValue;

import java.lang.ref.WeakReference;

import fred.angel.com.mgank.R;

/**
 *
 * @author Comori
 */
public class ITabLayout extends TabLayout {

    private CharSequence[]texts;

    private int currentPosition;

    public ITabLayout(Context context) {
        this(context, null);
    }

    public ITabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }
    public ITabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.ITabLayout);

        TypedValue value = a.peekValue(R.styleable.ITabLayout_tab_texts);
        if(value != null){
            if(value.type == TypedValue.TYPE_REFERENCE){
                texts = a.getTextArray(R.styleable.ITabLayout_tab_texts);
            }else if(value.type == TypedValue.TYPE_STRING){
                texts = new CharSequence[1];
                texts[0] = a.getString(R.styleable.ITabLayout_tab_texts);
            }
        }

        a.recycle();

        addTabs(texts);
    }


    public void addTabs(int ...resIds){
        if(resIds == null) return;
        if(resIds.length > 0){
            for(int id : resIds){
                TabLayout.Tab tab = newTab();
                tab.setTag(id);
                tab.setText(id);
                addTab(tab);
            }
        }
    }

    public void addTabs(CharSequence...texts){
        if(texts == null) return;
        if(texts.length > 0){
            for(CharSequence s : texts){
                TabLayout.Tab tab = newTab();
                tab.setText(s);
                addTab(tab);
            }
        }
    }

    public void setTabs(CharSequence...texts){
        removeAllTabs();
        if(texts == null) return;
        if(texts.length > 0){
            for(CharSequence s : texts){
                TabLayout.Tab tab = newTab();
                tab.setText(s);
                addTab(tab);
            }
        }
    }

    public CharSequence[] getTexts(){
        return texts;
    }

    public void setupWithViewPager(ViewPager viewPager, boolean needPagerData) {
        PagerAdapter adapter = viewPager.getAdapter();
        if(adapter == null) {
            throw new IllegalArgumentException("ViewPager does not have a PagerAdapter set");
        } else {
            if(needPagerData){
                this.setTabsFromPagerAdapter(adapter);
            }
            viewPager.addOnPageChangeListener(new TabLayoutOnPageChangeListener(this));
            this.setOnTabSelectedListener(new ViewPagerSelectListener(viewPager));
        }
    }

    public void setupWithViewPager(ViewPager viewPager) {
        setupWithViewPager(viewPager,true);
    }

    public void setTabsFromPagerAdapter(PagerAdapter adapter) {
        this.removeAllTabs();
        int i = 0;

        for(int count = adapter.getCount(); i < count; ++i) {
            this.addTab(this.newTab().setText(adapter.getPageTitle(i)));
        }
    }

    private class ViewPagerSelectListener implements OnTabSelectedListener {

        private ViewPager mViewPager;

        public ViewPagerSelectListener(ViewPager mViewPager) {
            this.mViewPager = mViewPager;
        }

        @Override
        public void onTabSelected(Tab tab) {
            this.mViewPager.setCurrentItem(tab.getPosition());
            currentPosition = tab.getPosition();
        }
        @Override
        public void onTabUnselected(Tab tab) {
        }
        @Override
        public void onTabReselected(Tab tab) {
        }
    }

    public int getCurrentPosition(){
        return currentPosition;
    }

    private class TabLayoutOnPageChangeListener implements ViewPager.OnPageChangeListener{

        private final WeakReference<TabLayout> mTabLayoutRef;
        private int mScrollState;

        public TabLayoutOnPageChangeListener(TabLayout tabLayout) {
            this.mTabLayoutRef = new WeakReference(tabLayout);
        }

        public void onPageScrollStateChanged(int state) {
            this.mScrollState = state;
        }

        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            /**不注释掉，滑动会有颤抖效果**/
            TabLayout tabLayout = this.mTabLayoutRef.get();
            if(tabLayout != null) {
                tabLayout.setScrollPosition(position, positionOffset, this.mScrollState == 1);
            }

        }

        public void onPageSelected(int position) {
            TabLayout tabLayout = this.mTabLayoutRef.get();
            if(tabLayout != null && currentPosition != position && position < tabLayout.getTabCount()) {
                tabLayout.getTabAt(position).select();
            }

        }
    }

}
