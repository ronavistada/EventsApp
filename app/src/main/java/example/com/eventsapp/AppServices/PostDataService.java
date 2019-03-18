package example.com.eventsapp.AppServices;

import java.util.List;

import example.com.eventsapp.DataClasses.Authtoken;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface PostDataService {

    @FormUrlEncoded
    @POST("login?Username=test&Password=test")
    Call<Authtoken> getLogin(@Field("Username") String name, @Field("Password") String password);
}
