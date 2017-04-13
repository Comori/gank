package fred.angel.com.mgank.view;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

import java.util.List;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.adapter.PhotoGalleryAdapter;
import fred.angel.com.mgank.component.BaseActivity;
import fred.angel.com.mgank.component.Utils.Constant;
import fred.angel.com.mgank.model.enity.Gank;

public class PhotoGalleryActivity extends BaseActivity implements PhotoGalleryAdapter.OnItemClickListener {

    private ViewPager viewPager;
    private ImageView downLoadImg;
    private PhotoGalleryAdapter galleryAdapter;
    List<Gank> ganks;
    int currentPosition = 0;
    boolean tapPhoto;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_photo_gallery);
        onLoad();
    }

    @Override
    public void initIntent(Intent intent) {
        super.initIntent(intent);
        ganks = intent.getParcelableArrayListExtra(Constant.IntentKey.GANKS);
        currentPosition = intent.getIntExtra(Constant.IntentKey.CURRENT_POSITION,0);
    }

    @Override
    public void initViews() {
        viewPager = getView(R.id.vp_content);
        downLoadImg = getView(R.id.download_img);
    }

    @Override
    public void initListener() {
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });
    }

    private void onLoad() {
        galleryAdapter = new PhotoGalleryAdapter(this,ganks);
        galleryAdapter.setListener(this);
        viewPager.setAdapter(galleryAdapter);
        viewPager.setCurrentItem(currentPosition);
    }

    @Override
    public void OnItemClick(int position, Gank gank) {
        downLoadImg.setVisibility(tapPhoto?View.GONE:View.VISIBLE);
        tapPhoto = !tapPhoto;
    }
}
