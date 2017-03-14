package fred.angel.com.mgank.component.widget.indicator;

import android.animation.ValueAnimator;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;


public class BallClipRotateMultipleIndicator extends BaseIndicatorController {

    float scaleFloat = 1, degrees;
    RectF tempRect = new RectF();
    float[] bStartAngles = new float[]{135, -45};
    float[] sStartAngles = new float[]{225, 45};


    @Override
    public void draw(Canvas canvas, Paint paint) {
        paint.setStrokeWidth(3);
        paint.setStyle(Paint.Style.STROKE);

        float circleSpacing = 12;
        float x = getWidth() / 2;
        float y = getHeight() / 2;

        canvas.save();

        canvas.translate(x, y);
        canvas.scale(scaleFloat, scaleFloat);
        canvas.rotate(degrees);

        //draw two big arc

        for (int i = 0; i < 2; i++) {
            tempRect.set(-x + circleSpacing, -y + circleSpacing, x - circleSpacing, y - circleSpacing);
            canvas.drawArc(tempRect, bStartAngles[i], 90, false, paint);
        }

        canvas.restore();
        canvas.translate(x, y);
        canvas.scale(scaleFloat, scaleFloat);
        canvas.rotate(-degrees);
        //draw two small arc
        for (int i = 0; i < 2; i++) {
            tempRect.set(-x / 1.8f + circleSpacing, -y / 1.8f + circleSpacing, x / 1.8f - circleSpacing, y / 1.8f - circleSpacing);
            canvas.drawArc(tempRect, sStartAngles[i], 90, false, paint);
        }
    }

    @Override
    public void createAnimation() {
        animators.clear();
        ValueAnimator scaleAnim = ValueAnimator.ofFloat(1, 0.6f, 1);
        scaleAnim.setDuration(1000);
        scaleAnim.setRepeatCount(-1);
        scaleAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                scaleFloat = (Float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animators.add(scaleAnim);

        ValueAnimator rotateAnim = ValueAnimator.ofFloat(0, 180, 360);
        rotateAnim.setDuration(1000);
        rotateAnim.setRepeatCount(-1);
        rotateAnim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                degrees = (Float) animation.getAnimatedValue();
                postInvalidate();
            }
        });
        animators.add(rotateAnim);
    }

}
