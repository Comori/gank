package fred.angel.com.mgank.component.widget.indicator;

import android.animation.Animator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseIndicatorController {

    private WeakReference<View> mTargetRef;

    protected List<Animator> animators = new ArrayList<Animator>(32);


    public void setTarget(View target){
        this.mTargetRef= new WeakReference<View>(target);
    }

    public int getWidth(){
        View mTarget = mTargetRef.get();
        return mTarget == null ? 10 : mTarget.getWidth();
    }

    public int getHeight(){
        View mTarget = mTargetRef.get();
        return mTarget == null ? 10 : mTarget.getHeight();
    }

    public void postInvalidate(){

        View mTarget = mTargetRef.get();
        if (null != mTarget) {
            mTarget.postInvalidate();
        }
    }

    /**
     * draw indicator what ever
     * you want to draw
     * @param canvas
     * @param paint
     */
    public abstract void draw(Canvas canvas, Paint paint);

    /**
     * create animation or animations
     * ,and add to your indicator.
     */
    public abstract void createAnimation();

    public void stopAnimation() {
        int size = animators.size();
        for (int i = 0; i < size; i++) {
            Animator animator = animators.get(i);
            if (null != animator) {
                animator.cancel();
            }
        }
        animators.clear();
    }


    public final void performCreateAnimation() {
        if (animators.size() < 1) {
            createAnimation();
        }

        int size = animators.size();
        for (int i = 0; i < size; i++) {
            Animator animator = animators.get(i);
            if (null != animator && !animator.isStarted()) {
                animator.start();
            }
        }

    }
}
