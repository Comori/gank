package fred.angel.com.mgank.model;


public interface INetCallback<T> {

    void onSuccess(int pageNum, T data);

    void onFailure(int pageNum,String msg);

}
