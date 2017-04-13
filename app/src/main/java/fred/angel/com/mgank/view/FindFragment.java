package fred.angel.com.mgank.view;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.zhy.m.permission.MPermissions;
import com.zhy.m.permission.PermissionGrant;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.List;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.adapter.MindAdapter;
import fred.angel.com.mgank.component.BaseFragment;
import fred.angel.com.mgank.component.Utils.BlurKit;
import fred.angel.com.mgank.component.Utils.DisplayUtil;
import fred.angel.com.mgank.component.Utils.Utils;
import fred.angel.com.mgank.component.Utils.ViewSwitchUtils;
import fred.angel.com.mgank.component.net.ApiClient;
import fred.angel.com.mgank.component.net.NetSubscriber;
import fred.angel.com.mgank.component.widget.MyTransformation;
import fred.angel.com.mgank.model.enity.Mind;
import okhttp3.ResponseBody;
import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * @author chenqiang
 */
public class FindFragment extends BaseFragment {

    private final static String[] PERMISSIONS = {Manifest.permission.READ_PHONE_STATE};
    private final static int REQUEST_GET_PHONE_CODE = 1;

    ViewPager viewPager;
    MindAdapter mindAdapter;
    ImageView bgImg;
    BlurRunnable blurRunnable;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_find,container,false);
        if(Utils.needAddStatusBaeHeight()){
            view.findViewById(R.id.id_toolbar).setPadding(0, DisplayUtil.getStatusHeight(context),0,0);
        }
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = Utils.findView(view,R.id.viewPager);
        bgImg = Utils.findView(view,R.id.bg_img);
        Utils.findView(view,R.id.root).setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return viewPager.dispatchTouchEvent(motionEvent);
            }
        });

        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }
            @Override
            public void onPageSelected(int position) {
                handleBlur(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }
        });

        bgImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(context,null,2017,4,11);
                datePickerDialog.show();
            }
        });
    }

    private void handleBlur(int position) {
        if (blurRunnable == null) {
            blurRunnable = new BlurRunnable(position);
        } else {
            bgImg.removeCallbacks(blurRunnable);
            blurRunnable.setPosition(position);
        }
        bgImg.postDelayed(blurRunnable, 500);
    }

    @Override
    public void onLoad() {
        int pagerWidth= (int) (getResources().getDisplayMetrics().widthPixels*4.0f/5.0f - DisplayUtil.dip2px(25));
        ViewGroup.LayoutParams lp=viewPager.getLayoutParams();
        if (lp==null){
            lp=new ViewGroup.LayoutParams(pagerWidth, ViewGroup.LayoutParams.MATCH_PARENT);
        }else {
            lp.width=pagerWidth;
        }
        viewPager.setLayoutParams(lp);
        viewPager.setOffscreenPageLimit(3);
        viewPager.setPageMargin(-DisplayUtil.dip2px(10f));
        viewPager.setPageTransformer(true,new MyTransformation());

        if(ContextCompat.checkSelfPermission(context,Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            MPermissions.requestPermissions(this,REQUEST_GET_PHONE_CODE,PERMISSIONS);
        }else {
            loadData();
        }

    }

    private class BlurRunnable implements Runnable{

        int position;

        public BlurRunnable(int position) {
            this.position = position;
        }

        @Override
        public void run() {
            SoftReference<Bitmap> softReference = BlurKit.getInstance(context).blur(mindAdapter.findHolder(position).img,25);
            if(softReference.get() != null){
                ViewSwitchUtils.startSwitchBackgroundAnim(bgImg,softReference.get());
            }
        }

        public void setPosition(int position) {
            this.position = position;
        }
    }

    @Override
    public void onPause() {
        if(blurRunnable != null){
            bgImg.removeCallbacks(blurRunnable);
        }
        super.onPause();
    }

    @Override
    public String getPageTitle() {
        return "";
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        MPermissions.onRequestPermissionsResult(this,requestCode,permissions,grantResults);
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @PermissionGrant(REQUEST_GET_PHONE_CODE)
    public void loadData(){
        ApiClient.getTimeApi().getRealTime()
                .subscribeOn(Schedulers.io())
                .flatMap(new Func1<ResponseBody, Observable<List<Mind>>>() {
                    @Override
                    public Observable<List<Mind>> call(ResponseBody responseBody) {
                        JSONObject jsonObject = null;
                        try {
                            jsonObject = new JSONObject(responseBody.string());
                            long times = jsonObject.optLong("stime");
                            return ApiClient.getMindApi().getMindList(Utils.format2Time(times*1000),Utils.getUUID(context));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        return null;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new NetSubscriber<List<Mind>>(){
                    @Override
                    public void onStart() {
                        super.onStart();
                        showProgress();
                    }

                    @Override
                    public void onCompleted() {
                        super.onCompleted();
                        hideProgress();
                    }

                    @Override
                    public void onNext(List<Mind> minds) {
                        super.onNext(minds);
                        mindAdapter = new MindAdapter(context,minds);
                        viewPager.setAdapter(mindAdapter);
                        handleBlur(0);
                    }
                });
    }
}
