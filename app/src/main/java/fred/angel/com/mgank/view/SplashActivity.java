package fred.angel.com.mgank.view;

import android.animation.Animator;
import android.content.Intent;
import android.os.Bundle;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.component.BaseActivity;
import fred.angel.com.mgank.model.SvgCompletedCallBack;
import fred.angel.com.mgank.model.SvgView;

/**
 * Created by chenqiang on 2016/9/28.
 * Todo
 */

public class SplashActivity extends BaseActivity {

    private SvgView svgView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.aty_splash);
    }

    @Override
    public void initViews() {
        svgView = getView(R.id.svg);
        svgView.setSvgResource(R.raw.gank);
        svgView.postDelayed(new Runnable() {
            @Override
            public void run() {
                svgView.startAnimation();
            }
        },500);

    }

    @Override
    public void initListener() {
        svgView.setmCallback(new SvgCompletedCallBack() {
            @Override
            public void onSvgCompleted() {
                animateFinish();
            }
        });
    }

    private void animateFinish() {
        svgView.animate()
                .translationY(-100)
                .alpha(0)
                .setDuration(1500)
                .setListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {
                    }
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        startActivity(new Intent(SplashActivity.this,MainActivity.class));
                        finish();
                    }
                    @Override
                    public void onAnimationCancel(Animator animation) {
                    }
                    @Override
                    public void onAnimationRepeat(Animator animation) {
                    }
                })
                .start();
    }
}
