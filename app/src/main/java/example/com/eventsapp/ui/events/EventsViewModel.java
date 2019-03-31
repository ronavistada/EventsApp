package example.com.eventsapp.ui.events;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.widget.ImageView;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import example.com.eventsapp.AppServices.GetDataService;
import example.com.eventsapp.AppServices.RetrofitClient;
import example.com.eventsapp.DataClasses.Authtoken;
import example.com.eventsapp.DataClasses.EventDetails;
import example.com.eventsapp.R;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class EventsViewModel extends ViewModel {
    private int itemposition;
    private EventDetails eventChosen;



    public EventsViewModel(int itemposition, Authtoken token) {
        this.itemposition = itemposition;
        GetDataService service = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
        Call<EventDetails> call = service.getEvent(token.getToken(),itemposition);
        call.enqueue(new Callback<EventDetails>() {
            @Override
            public void onResponse(Call<EventDetails> call, Response<EventDetails> response) {
                eventChosen = response.body();
            }

            @Override
            public void onFailure(Call<EventDetails> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public int getItemposition() {
        return itemposition;
    }

    public void setItemposition(int itemposition) {
        this.itemposition = itemposition;
    }

    public EventDetails getEventChosen() {
        return eventChosen;
    }
}
