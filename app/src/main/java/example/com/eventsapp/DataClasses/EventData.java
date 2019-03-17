package example.com.eventsapp.DataClasses;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class EventData {

    @SerializedName("id")
    private int itemid;
    @SerializedName("title")
    private String itemtitle;
    @SerializedName("image_url")
    private String urlimage;
    @SerializedName("start_date_time")
    private Date startdatetime;
    @SerializedName("end_date_time")
    private Date enddatetime;
    @SerializedName("location")
    private String itemlocation;
    @SerializedName("featured")
    private Boolean itemfeatured;

    public EventData(int id, String title, String imageurl, Date startdate, Date enddate, String location, Boolean isfeatured){
        this.itemid = id;
        this.itemtitle = title;
        this.urlimage = imageurl;
        this.startdatetime = startdate;
        this.enddatetime = enddate;
        this.itemlocation = location;
        this.itemfeatured = isfeatured;
    }

    public int getItemid() {
        return itemid;
    }

    public void setItemid(int itemid) {
        this.itemid = itemid;
    }

    public String getItemtitle() {
        return itemtitle;
    }

    public void setItemtitle(String itemtitle) {
        this.itemtitle = itemtitle;
    }

    public String getUrlimage() {
        return urlimage;
    }

    public void setUrlimage(String urlimage) {
        this.urlimage = urlimage;
    }

    public Date getStartdatetime() {
        return startdatetime;
    }

    public void setStartdatetime(Date startdatetime) {
        this.startdatetime = startdatetime;
    }

    public Date getEnddatetime() {
        return enddatetime;
    }

    public void setEnddatetime(Date enddatetime) {
        this.enddatetime = enddatetime;
    }

    public String getItemlocation() {
        return itemlocation;
    }

    public void setItemlocation(String itemlocation) {
        this.itemlocation = itemlocation;
    }

    public Boolean getItemfeatured() {
        return itemfeatured;
    }

    public void setItemfeatured(Boolean itemfeatured) {
        this.itemfeatured = itemfeatured;
    }
}
