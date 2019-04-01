package example.com.eventsapp.AppServices;

import java.util.List;

import example.com.eventsapp.DataClasses.EventData;
import example.com.eventsapp.DataClasses.EventDetails;
import example.com.eventsapp.DataClasses.speakerDetails;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Path;

public interface GetDataService {


    @GET("events")
    Call<List<EventData>> getAllEvents(@Header("authorization") String token);

    @GET("events/{id}")
    Call<EventDetails> getEvent(@Header("authorization") String token, @Path("id") int id);

    @GET("speakers/{id}")
    Call<speakerDetails> getSpeaker(@Header("authorization") String token, @Path("id") int id);


}
