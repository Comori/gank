package fred.angel.com.mgank.component.net;

import android.content.Context;
import android.widget.Toast;

import java.io.IOException;

import fred.angel.com.mgank.IApplication;
import fred.angel.com.mgank.component.Utils.Utils;
import retrofit2.adapter.rxjava.HttpException;
import rx.Subscriber;

/**
 * Created by chenqiang on 2016/11/1.
 * Todo 对网络请求做统一处理
 */

public class NetSubscriber<T> extends Subscriber<T> {

    @Override
    public void onStart() {
        //请求开始之前，检查是否有网络。无网络直接抛出异常
        //另外，在你无法确定当前代码运行在什么线程的时候，不要将UI的相关操作放在这里。
        if (!Utils.netWorkConnection()) {
            this.onError(new ApiException(ApiException.ApiErrorEnum.NETWORK_ERROR));
            return;
        }
    }

    @Override
    public void onCompleted() {
    }

    @Override
    public void onError(Throwable e) {
        handleCommonError(e);
    }

    @Override
    public void onNext(T t) {
    }

    public static void handleCommonError(Throwable e) {
        Context context = IApplication.INSTACE.getApplicationContext();
        if (e instanceof HttpException) {
            Toast.makeText(context, "服务暂不可用", Toast.LENGTH_SHORT).show();
        } else if (e instanceof IOException) {
            Toast.makeText(context, "连接失败", Toast.LENGTH_SHORT).show();
        } else if (e instanceof ApiException) {
            //ApiException处理
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(context, "未知错误", Toast.LENGTH_SHORT).show();
        }
    }
}
