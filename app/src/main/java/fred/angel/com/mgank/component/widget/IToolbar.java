package fred.angel.com.mgank.component.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.component.BaseActivity;
import fred.angel.com.mgank.component.Utils.DisplayUtil;
import fred.angel.com.mgank.component.Utils.Utils;

/**
 * Created by Comori
 * Todo 自定义顶部top toolbar
 */
public class IToolbar extends FrameLayout {

    private Context context;

    private View contentView;
    private LinearLayout contentLayout;
    private ImageView backImg;
    private TextView titleTv;
    private View rightView; //可配置右边的view

    private Drawable toolbarBgDrawable;

    public IToolbar(Context context) {
        this(context, null);
    }

    public IToolbar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public IToolbar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
        TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.IToolbar);
        int resId = array.getResourceId(R.styleable.IToolbar_rightView,0);
        toolbarBgDrawable = array.getDrawable(R.styleable.IToolbar_toolbarBackground);
        if(resId > 0){
            rightView = LayoutInflater.from(context).inflate(resId,null);
        }
        array.recycle();

        defaultInit();
    }

    private void defaultInit() {
        LayoutInflater.from(context).inflate(R.layout.toolbar_normal, this, true);
        contentView = findViewById(R.id.id_toolbar);
        contentLayout = (LinearLayout) findViewById(R.id.content);
        backImg = (ImageView) findViewById(R.id.back_ic);
        titleTv = (TextView) findViewById(R.id.tile_tv);
        if(rightView != null){
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            lp.gravity = Gravity.CENTER_VERTICAL;
            contentLayout.addView(rightView,lp);
        }
        if(toolbarBgDrawable != null){
            contentView.setBackgroundDrawable(toolbarBgDrawable);
        }
    }

    public void setToolbarBgDrawable(Drawable toolbarBgDrawable) {
        this.toolbarBgDrawable = toolbarBgDrawable;
        if(contentView != null){
            contentView.setBackgroundDrawable(toolbarBgDrawable);
        }
    }

    public void setRightView(View view){
        if(view != null){
            if(this.rightView != null){
                contentLayout.removeView(rightView);
            }
            this.rightView = view;
            LayoutParams lp = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.MATCH_PARENT);
            lp.gravity = Gravity.CENTER_VERTICAL;
            contentLayout.addView(rightView,lp);
        }
    }

    public void setRightView(int resId){
        if(resId > 0){
            View view = LayoutInflater.from(context).inflate(resId,null);
            setRightView(view);
        }
    }


    public void setTitle(String text){
        titleTv.setText(text);
    }
    public void setTitle(int text){
        titleTv.setText(text);
    }

    public TextView getTitleTv() {
        return titleTv;
    }

    public ImageView getBackImg() {
        return backImg;
    }

    public View getRightView() {
        return rightView;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        if(Utils.needAddStatusBaeHeight()){
            int h = DisplayUtil.dip2px(48);
            if(context instanceof BaseActivity){
                if(((BaseActivity) context).isTranslucentStatus()){
                    h += DisplayUtil.getStatusHeight( context);
                    setMeasuredDimension(DisplayUtil.getScreenWidth(),h);
                }
            }
        }
    }
}
