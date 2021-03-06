package fred.angel.com.mgank.model.enity;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by Comori on 2016/9/27.
 * Todo
 */

public class Gank implements Parcelable {
//    {
//        _id: "57e8b595421aa95dd351b080",
//                createdAt: "2016-09-26T13:43:49.970Z",
//            desc: "MDCC 2016 Android 部分演讲稿合集。",
//            publishedAt: "2016-09-27T11:41:22.507Z",
//            source: "chrome",
//            type: "Android",
//            url: "https://github.com/MDCC2016/Android-Session-Slides",
//            used: true,
//            who: "wuzheng"
//    }

    private String _id;
    private String createdAt;
    private String publishedAt;
    private String desc;
    private String source;
    private String type;
    private String url;
    private boolean used;
    private String who;
    private List<String> images;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    public void setPublishedAt(String publishedAt) {
        this.publishedAt = publishedAt;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean isUsed() {
        return used;
    }

    public void setUsed(boolean used) {
        this.used = used;
    }

    public String getWho() {
        return who;
    }

    public void setWho(String who) {
        this.who = who;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this._id);
        dest.writeString(this.createdAt);
        dest.writeString(this.publishedAt);
        dest.writeString(this.desc);
        dest.writeString(this.source);
        dest.writeString(this.type);
        dest.writeString(this.url);
        dest.writeByte(this.used ? (byte) 1 : (byte) 0);
        dest.writeString(this.who);
        dest.writeStringList(this.images);
    }

    public Gank() {
    }

    protected Gank(Parcel in) {
        this._id = in.readString();
        this.createdAt = in.readString();
        this.publishedAt = in.readString();
        this.desc = in.readString();
        this.source = in.readString();
        this.type = in.readString();
        this.url = in.readString();
        this.used = in.readByte() != 0;
        this.who = in.readString();
        this.images = in.createStringArrayList();
    }

    public static final Parcelable.Creator<Gank> CREATOR = new Parcelable.Creator<Gank>() {
        @Override
        public Gank createFromParcel(Parcel source) {
            return new Gank(source);
        }

        @Override
        public Gank[] newArray(int size) {
            return new Gank[size];
        }
    };
}
