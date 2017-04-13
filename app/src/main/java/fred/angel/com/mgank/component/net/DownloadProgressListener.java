package fred.angel.com.mgank.component.net;


public interface DownloadProgressListener {
    void update(long bytesRead, long contentLength, boolean done);
}