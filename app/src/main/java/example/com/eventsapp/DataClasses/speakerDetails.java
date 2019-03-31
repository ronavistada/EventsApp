package example.com.eventsapp.DataClasses;

import com.google.gson.annotations.SerializedName;

public class speakerDetails extends speakerIdentity {

    @SerializedName("first_name")
    private String speakerfirstname;

    @SerializedName("last_name")
    private String speakerlastname;

    @SerializedName("bio")
    private String speakerbio;

    @SerializedName("image_url")
    private String speakerimageurl;

    public speakerDetails(int speakerid, String speakerfirstname, String speakerlastname, String speakerbio, String speakerimageurl) {
        super(speakerid);
        this.speakerfirstname = speakerfirstname;
        this.speakerlastname = speakerlastname;
        this.speakerbio = speakerbio;
        this.speakerimageurl = speakerimageurl;
    }

    public String getSpeakerfirstname() {
        return speakerfirstname;
    }

    public void setSpeakerfirstname(String speakerfirstname) {
        this.speakerfirstname = speakerfirstname;
    }

    public String getSpeakerlastname() {
        return speakerlastname;
    }

    public void setSpeakerlastname(String speakerlastname) {
        this.speakerlastname = speakerlastname;
    }

    public String getSpeakerbio() {
        return speakerbio;
    }

    public void setSpeakerbio(String speakerbio) {
        this.speakerbio = speakerbio;
    }

    public String getSpeakerimageurl() {
        return speakerimageurl;
    }

    public void setSpeakerimageurl(String speakerimageurl) {
        this.speakerimageurl = speakerimageurl;
    }
}
