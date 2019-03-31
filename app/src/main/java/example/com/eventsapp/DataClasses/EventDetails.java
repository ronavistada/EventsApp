package example.com.eventsapp.DataClasses;

import com.google.gson.annotations.SerializedName;

import java.util.Date;
import java.util.List;

public class EventDetails extends EventData {

    @SerializedName("event_description")
    private String eventSummary;
    @SerializedName("speakers")
    private List<speakerIdentity> speakerids;

    public EventDetails(int id, String title, String imageurl, Date startdate, Date enddate, String location,
                        Boolean isfeatured, String eventSummary, List<speakerIdentity> speakerids) {
        super(id, title, imageurl, startdate, enddate, location, isfeatured);
        this.eventSummary = eventSummary;
        this.speakerids = speakerids;
    }

    public List<speakerIdentity> getSpeakerids() {
        return speakerids;
    }

    public void setSpeakerids(List<speakerIdentity> speakerids) {
        this.speakerids = speakerids;
    }

    public String getEventSummary() {
        return eventSummary;
    }

    public void setEventSummary(String eventSummary) {
        this.eventSummary = eventSummary;
    }
}
