package example.com.eventsapp.AppServices;

import java.util.List;

import example.com.eventsapp.DataClasses.EventData;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Path;

public interface GetDataService {


    @GET("events")
    Call<List<EventData>> getAllEvents(@Header("authorization") String token);

    @Headers("authorization: {token}")
    @GET("events/{id}")
    Call<EventData> getEvent(@Path("token") String token,@Path("id") int id);
}
