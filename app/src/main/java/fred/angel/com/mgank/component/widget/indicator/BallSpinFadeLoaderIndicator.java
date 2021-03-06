package fred.angel.com.mgank.component.widget.indicator;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PointF;


/**
 * Created by Jack on 2015/10/20.
 */
public class BallSpinFadeLoaderIndicator extends BaseIndicatorController {

    public static final float SCALE = 1.0f;

    public static final int ALPHA = 255;

    float[] scaleFloats = new float[]{SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE,
            SCALE};

    int[] alphas = new int[]{ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA,
            ALPHA};


    private PointF tempPint = new PointF();
    @Override
    public void draw(Canvas canvas, Paint paint) {
        float radius = getWidth() / 10;
        for (int i = 0; i < 8; i++) {
            canvas.save();
            PointF point = circleAt(getWidth(), getHeight(), getWidth() / 2 - radius, i * (Math.PI / 4), tempPint);
            canvas.translate(point.x, point.y);
            canvas.scale(scaleFloats[i], scaleFloats[i]);
            paint.setAlpha(alphas[i]);
            canvas.drawCircle(0, 0, radius, paint);
            canvas.restore();
        }
    }

    /**
     * 圆O的圆心为(a,b),半径为R,点A与到X轴的为角α.
     * 则点A的坐标为(a+R*cosα,b+R*sinα)
     *
     * @param width
     * @param height
     * @param radius
     * @param angle
     * @param tempPint
     * @return
     */
    PointF circleAt(int width, int height, float radius, double angle, PointF tempPint) {
        float x = (float) (width / 2 + radius * (Math.cos(angle)));
        float y = (float) (height / 2 + radius * (Math.sin(angle)));
        tempPint.set(x, y);
        return tempPint;
    }

    @Override
    public void createAnimation() {
        animators.clear();
        int[] delays = {0, 120, 240, 360, 480, 600, 720, 780, 840};
        for (int i = 0; i < 8; i++) {
            final int index = i;
            ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.4f, 1);
            scaleAnim.setDuration(1000);
            scaleAnim.setRepeatCount(-1);
            scaleAnim.setStartDelay(delays[i]);
            scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    scaleFloats[index] = (Float) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            animators.add(scaleAnim);

            ValueAnimator alphaAnim = ValueAnimator.ofInt(255, 77, 255);
            alphaAnim.setDuration(1000);
            alphaAnim.setRepeatCount(-1);
            alphaAnim.setStartDelay(delays[i]);
            alphaAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                @Override
                public void onAnimationUpdate(ValueAnimator animation) {
                    alphas[index] = (Integer) animation.getAnimatedValue();
                    postInvalidate();
                }
            });
            animators.add(scaleAnim);
        }
    }

}
