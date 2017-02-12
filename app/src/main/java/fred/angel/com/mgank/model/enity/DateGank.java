package fred.angel.com.mgank.model.enity;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by chenqiang on 2016/11/3.
 * Todo 今日数据
 */

public class DateGank extends BaseModel {

    @SerializedName("Android")
    private List<Gank> androidGanks;

    @SerializedName("iOS")
    private List<Gank> iosGanks;

    @SerializedName("休息视频")
    private List<Gank> restGanks;

    @SerializedName("瞎推荐")
    private List<Gank> recommendGanks;

    @SerializedName("福利")
    private List<Gank> welfareGanks;

    @SerializedName("拓展资源")
    private List<Gank> expandGanks;

    @SerializedName("前端")
    private List<Gank> webGanks;

    @SerializedName("App")
    private List<Gank> appGanks;

    public List<Gank> getAndroidGanks() {
        return androidGanks;
    }

    public void setAndroidGanks(List<Gank> androidGanks) {
        this.androidGanks = androidGanks;
    }

    public List<Gank> getIosGanks() {
        return iosGanks;
    }

    public void setIosGanks(List<Gank> iosGanks) {
        this.iosGanks = iosGanks;
    }

    public List<Gank> getRestGanks() {
        return restGanks;
    }

    public void setRestGanks(List<Gank> restGanks) {
        this.restGanks = restGanks;
    }

    public List<Gank> getRecommendGanks() {
        return recommendGanks;
    }

    public void setRecommendGanks(List<Gank> recommendGanks) {
        this.recommendGanks = recommendGanks;
    }

    public List<Gank> getWelfareGanks() {
        return welfareGanks;
    }

    public void setWelfareGanks(List<Gank> welfareGanks) {
        this.welfareGanks = welfareGanks;
    }

    public List<Gank> getExpandGanks() {
        return expandGanks;
    }

    public void setExpandGanks(List<Gank> expandGanks) {
        this.expandGanks = expandGanks;
    }

    public List<Gank> getWebGanks() {
        return webGanks;
    }

    public void setWebGanks(List<Gank> webGanks) {
        this.webGanks = webGanks;
    }

    public List<Gank> getAppGanks() {
        return appGanks;
    }

    public void setAppGanks(List<Gank> appGanks) {
        this.appGanks = appGanks;
    }

    public int getAndroidGankSize(){
        return androidGanks==null?0:androidGanks.size();
    }
    public int getIosGankSize(){
        return iosGanks==null?0:iosGanks.size();
    }
    public int getRestGankSize(){
        return restGanks==null?0:restGanks.size();
    }
    public int getWebGankSize(){
        return webGanks==null?0:webGanks.size();
    }
    public int getWelfareGankSize(){
        return welfareGanks==null?0:welfareGanks.size();
    }
    public int getExpandGankSize(){
        return expandGanks==null?0:expandGanks.size();
    }
    public int getAppGankSize(){
        return appGanks==null?0:appGanks.size();
    }
    public int getRecommendGankSize(){
        return recommendGanks==null?0:recommendGanks.size();
    }


    public int getTotalSize(){
        int count = 0;
        count += recommendGanks==null?0:recommendGanks.size();
        count += androidGanks==null?0:androidGanks.size();
        count += iosGanks==null?0:iosGanks.size();
        count += webGanks==null?0:webGanks.size();
        count += appGanks==null?0:appGanks.size();
        count += expandGanks==null?0:expandGanks.size();
        count += welfareGanks==null?0:welfareGanks.size();
        count += restGanks==null?0:restGanks.size();

        return count;
    }

    public Gank getItem(int position){
        int count = getRecommendGankSize();
        if(position >=0 && position < count){
            return recommendGanks.get(position);
        }

        if(position >= count && position < (count+getAndroidGankSize())){
            return androidGanks.get(position-count);
        }

        count += getAndroidGankSize();
        if(position >= count && position < (count+getIosGankSize())){
            return iosGanks.get(position-count);
        }

        count += getIosGankSize();
        if(position >= count && position < (count+getWebGankSize())){
            return webGanks.get(position-count);
        }

        count += getWebGankSize();
        if(position >= count && position < (count+getAppGankSize())){
            return appGanks.get(position-count);
        }

        count += getAppGankSize();
        if(position >= count && position < (count+getExpandGankSize())){
            return expandGanks.get(position-count);
        }

        count += getExpandGankSize();
        if(position >= count && position < (count+getWelfareGankSize())){
            return welfareGanks.get(position-count);
        }

        count += getWelfareGankSize();
        if(position >= count && position < (count+getRestGankSize())){
            return restGanks.get(position-count);
        }

        return null;
    }
}
