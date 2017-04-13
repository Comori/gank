package fred.angel.com.mgank.component.Utils;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.model.enity.Gank;
import fred.angel.com.mgank.view.PhotoGalleryActivity;
import fred.angel.com.mgank.view.SearchActivity;

public class UIHelper {

    public static void openWeb(Context context,String url){
        new FinestWebView.Builder(context).theme(R.style.FinestWebViewTheme)
                .titleDefault("内容")
                .showUrl(true)
                .webViewUseWideViewPort(true)
                .webViewLoadWithOverviewMode(true)
                .statusBarColorRes(R.color.colorPrimaryDark)
                .toolbarColorRes(R.color.colorPrimary)
                .titleColorRes(R.color.finestWhite)
                .urlColorRes(R.color.bluePrimaryLight)
                .iconDefaultColorRes(R.color.finestWhite)
                .progressBarColorRes(R.color.finestWhite)
                .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                .stringResCopiedToClipboard(R.string.copied_to_clipboard)
                .showSwipeRefreshLayout(true)
                .swipeRefreshColorRes(R.color.colorPrimaryDark)
                .menuSelector(R.drawable.selector_light_theme)
                .menuTextGravity(Gravity.CENTER)
                .menuTextPaddingRightRes(R.dimen.defaultMenuTextPaddingLeft)
                .gradientDivider(true)
                .setCustomAnimations(R.anim.slide_right_in, R.anim.hold, R.anim.hold,
                        R.anim.slide_right_out)
                .show(url);
    }

    public static void goPhotoGallery(Context context, int currentPosition, ArrayList<Gank> ganks){
        Intent intent = new Intent(context, PhotoGalleryActivity.class);
        intent.putParcelableArrayListExtra(Constant.IntentKey.GANKS, ganks);
        intent.putExtra(Constant.IntentKey.CURRENT_POSITION,currentPosition);
        context.startActivity(intent);
//        context.overridePendingTransition(R.anim.act_alpha_exit,R.anim.act_alpha_enter);
    }

    /**
     * 展示有提示语和确定、取消按钮的dialog，仿IOS 重载showCenterDialog，添加了点击去掉的回调
     *
     * @param context
     * @param message
     * @param positive       确定按钮上显示的文字
     * @param negative       取消按钮上显示的文字
     * @param sureListener   点击确定后的监听器，不需要去关闭dialog，会自动关闭dialog
     * @param cancelListener 点击取消之后的回调，不需要去关闭dialog，会自动关闭dialog
     * @param cancelabel
     * @return
     */
    public static Dialog showCenterDialogWithCancleCallback(final Context context, CharSequence message,
                                                            String positive, boolean positiveEnable, String negative, boolean negativeEnable,
                                                            final View.OnClickListener sureListener, final View.OnClickListener cancelListener, boolean cancelabel) {
        final Dialog dialog = new Dialog(context, R.style.Dialog_Center);
        dialog.setCanceledOnTouchOutside(cancelabel);
        dialog.setCancelable(cancelabel);
        View inflate = LayoutInflater.from(context).inflate(R.layout.widget_center_dialog, null);
        TextView dialog_center_content = (TextView) inflate.findViewById(R.id.dialog_center_content);
        TextView dialog_center_cancel = (TextView) inflate.findViewById(R.id.dialog_center_cancel);
        TextView dialog_center_sure = (TextView) inflate.findViewById(R.id.dialog_center_sure);
        dialog_center_content.setGravity(Gravity.LEFT);
        if (TextUtils.isEmpty(message)) {
            dialog_center_content.setVisibility(View.GONE);
            inflate.findViewById(R.id.dialog_split).setVisibility(View.GONE);
        } else {
            dialog_center_content.setText(message);
        }
        if (!TextUtils.isEmpty(positive)) {
            dialog_center_sure.setText(positive);
        }
        if (!TextUtils.isEmpty(negative)) {
            dialog_center_cancel.setText(negative);
        }
        if (!positiveEnable) {
            dialog_center_sure.setVisibility(View.GONE);
        }
        if (!negativeEnable) {
            dialog_center_cancel.setVisibility(View.GONE);
        }

        dialog_center_cancel.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {// 先关闭dialog再执行任务
                    dialog.dismiss();
                }
                if (cancelListener != null) {
                    cancelListener.onClick(v);
                }
            }
        });
        dialog_center_sure.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (dialog.isShowing()) {
                    dialog.dismiss();
                }
                if (sureListener != null) {
                    sureListener.onClick(v);
                }
            }
        });
//        if(cancelListener == null){
//            dialog_center_cancel.setVisibility(View.GONE);
//        }
//        if(sureListener == null){
//            dialog_center_sure.setVisibility(View.GONE);
//        }
        dialog.setContentView(inflate);
        dialog.show();
        return dialog;
    }

    public static void goSearch(Context context,String category){
        if(TextUtils.isEmpty(category) || TextUtils.equals(category,"今日")){
            category = "all";
        }
        Intent intent = new Intent(context, SearchActivity.class);
        intent.putExtra(Constant.IntentKey.CATEGORY,category);
        context.startActivity(intent);
    }

}
