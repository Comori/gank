package fred.angel.com.mgank.component.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.widget.ImageView;

import fred.angel.com.mgank.R;


/**
 * Created by chenqiang on 2016/12/27.
 */

public class AspectRatioImageView extends ImageView {

    private float aspect = 0f;

    public AspectRatioImageView(Context context) {
        this(context,null);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public AspectRatioImageView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.AspectRatioImageView);
        boolean touchable = array.getBoolean(R.styleable.AspectRatioImageView_touchable, true);
        aspect = array.getFloat(R.styleable.AspectRatioImageView_viewAspectRatio, 0f);

        array.recycle();
    }

    public void setAspectRatio(float aspectY) {
        if(this.aspect == aspectY) return;
        this.aspect = aspectY;
        requestLayout();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // For simple implementation, or internal size is always 0.
        // We depend on the container to specify the layout size of
        // our view. We can't really know what it is since we will be
        // adding and removing different arbitrary views and do not
        // want the layout to change as this happens.
        setMeasuredDimension(getDefaultSize(0, widthMeasureSpec), getDefaultSize(0, heightMeasureSpec));

        // Children are just made to fill our space.
        int childWidthSize = getMeasuredWidth();
        int childHeightSize = 0;
        if(aspect == 0f){
            childHeightSize = getMeasuredHeight();
        }else {
            childHeightSize = (int) (childWidthSize * 1f / aspect);
        }

        widthMeasureSpec = MeasureSpec.makeMeasureSpec(childWidthSize, MeasureSpec.EXACTLY);
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(childHeightSize, MeasureSpec.EXACTLY);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
