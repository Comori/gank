package fred.angel.com.mgank.component.Utils;

import android.content.Context;
import android.view.Gravity;

import com.thefinestartist.finestwebview.FinestWebView;

import fred.angel.com.mgank.R;

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

}
