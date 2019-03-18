package example.com.eventsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;
import android.widget.Toolbar;

import java.util.List;

import example.com.eventsapp.AppServices.DataListAdapter;
import example.com.eventsapp.AppServices.GetDataService;
import example.com.eventsapp.AppServices.RetrofitClient;
import example.com.eventsapp.DataClasses.Authtoken;
import example.com.eventsapp.DataClasses.EventData;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Events extends AppCompatActivity {

    private Authtoken token;
    private DataListAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_activity);
        Toolbar actionBar = findViewById(R.id.toolbar);

        Intent intent = getIntent();
        token = new Authtoken(intent.getStringExtra(LoginPage.Extra_Data));
        GetDataService service = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
        Call<List<EventData>> call = service.getAllEvents(token.getToken());
        call.enqueue(new Callback<List<EventData>>() {
            @Override
            public void onResponse(Call<List<EventData>> call, Response<List<EventData>> response) {
                generateDataList(response.body());
            }

            @Override
            public void onFailure(Call<List<EventData>> call, Throwable t) {
                Toast.makeText(Events.this,"Oops! Something went wrong...",Toast.LENGTH_LONG);
                t.printStackTrace();

            }
        });
//        if (savedInstanceState == null) {

//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container, EventsFragment.newInstance())
//                    .commitNow();
 //       }
    }

    private void generateDataList(List<EventData> eventList){
        recyclerView = findViewById(R.id.eventsRecyclerView);
        adapter = new DataListAdapter(this,eventList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Events.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }
}
