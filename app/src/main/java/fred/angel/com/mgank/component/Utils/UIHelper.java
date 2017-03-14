package fred.angel.com.mgank.component.Utils;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;

import com.thefinestartist.finestwebview.FinestWebView;

import java.util.ArrayList;

import fred.angel.com.mgank.R;
import fred.angel.com.mgank.model.enity.Gank;
import fred.angel.com.mgank.view.PhotoGalleryActivity;

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

}
