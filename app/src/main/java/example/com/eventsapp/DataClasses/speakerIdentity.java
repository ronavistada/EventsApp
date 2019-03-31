package example.com.eventsapp.DataClasses;

import com.google.gson.annotations.SerializedName;

public class speakerIdentity{

    @SerializedName("id")
    private int speakerid;

    public speakerIdentity(int speakerid) {
        this.speakerid = speakerid;
    }

    public int getSpeakerid() {
        return speakerid;
    }

    public void setSpeakerid(int speakerid) {
        this.speakerid = speakerid;
    }
}
