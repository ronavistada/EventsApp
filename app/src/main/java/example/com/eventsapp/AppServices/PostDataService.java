package example.com.eventsapp.AppServices;

import example.com.eventsapp.DataClasses.Authtoken;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

//class to get token
public interface PostDataService {

    @FormUrlEncoded
    @POST("login")
    Call<Authtoken> getLogin(@Field("Username") String name, @Field("Password") String password);
}
