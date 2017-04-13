package fred.angel.com.mgank.component.widget.indicator;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

public class LineScaleIndicator extends BaseIndicatorController {

    public static final float SCALE = 1.0f;

    float[] scaleYFloats = new float[]{SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,};

    private static final RectF tempRect = new RectF();
    private static final long[] delays = new long[]{100, 200, 300, 400, 500};

    @Override
    public void draw(Canvas canvas, Paint paint) {
        float translateX = getWidth() / 11;
        float translateY = getHeight() / 2;
        for (int i = 0; i < 5; i++) {
            canvas.save();
            canvas.translate((2 + i * 2) * translateX - translateX / 2, translateY);
            canvas.scale(SCALE, scaleYFloats[i]);
            tempRect.set(-translateX / 2, -getHeight() / 2.5f, translateX / 2, getHeight() / 2.5f);
            canvas.drawRoundRect(tempRect, 5, 5, paint);
            canvas.restore();
        }
    }

    @Override
    public void createAnimation() {
        animators.clear();
        for (int i = 0; i < 5; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.4f, 1);
            scaleAnim.setDuration(1000);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);
            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleYFloats[index] = (Float) animation.getAnimatedValue();
//                    Log.i("AAAAAAAXXX", "onAnimationUpdate() called with " + "animation = [" + animation + "]");
                    postInvalidate();
                }
            });
            animators.add(scaleAnim);
        }
    }

    @Override
    public void stopAnimation() {
        for (Animator animator :
                animators) {
            if (null != animator) {
                animator.cancel();
            }
        }
        animators.clear();
    }

}
