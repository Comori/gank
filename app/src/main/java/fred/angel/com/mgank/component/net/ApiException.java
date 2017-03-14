package fred.angel.com.mgank.component.net;

/**
 * Created by Comori on 2016/11/1.
 * Todo
 */

public class ApiException extends RuntimeException {

    public enum  ApiErrorEnum{

        SERVER_ERROR("服务器错误"),
        PARSE_ERROR("解析错误"),
        NETWORK_ERROR("網絡連接错误");

        String msg;
        ApiErrorEnum(String msg){
            this.msg = msg;
        }
    }

    ApiErrorEnum apiErrorEnum;

    public ApiException(ApiErrorEnum apiErrorEnum) {
        super(apiErrorEnum.msg);
        this.apiErrorEnum = apiErrorEnum;
    }
}
