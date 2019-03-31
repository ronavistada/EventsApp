package example.com.eventsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.picasso.OkHttp3Downloader;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import example.com.eventsapp.AppServices.DataListAdapter;
import example.com.eventsapp.AppServices.GetDataService;
import example.com.eventsapp.AppServices.RetrofitClient;
import example.com.eventsapp.AppServices.SpeakerListAdapter;
import example.com.eventsapp.DataClasses.Authtoken;
import example.com.eventsapp.DataClasses.EventData;
import example.com.eventsapp.DataClasses.EventDetails;
import example.com.eventsapp.DataClasses.speakerDetails;
import example.com.eventsapp.DataClasses.speakerIdentity;
import example.com.eventsapp.ui.events.EventsFragment;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Events extends AppCompatActivity {

    private DataListAdapter adapter;
    private SpeakerListAdapter adapter_frag;
    private RecyclerView recyclerView;
    private RecyclerView recyclerView_frag;
    private Authtoken token;
    private List<speakerDetails> speakerdetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_activity);
        Toolbar actionBar = findViewById(R.id.toolbar);
        setSupportActionBar(actionBar);
        speakerdetails = new ArrayList<>();

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
                Toast.makeText(Events.this, "Oops! Something went wrong...", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }

    public void onEventClick(View view) {
        List<EventData> eventData = adapter.getEventlist();
        setUpFragment(eventData.get(recyclerView.getChildLayoutPosition(view)).getItemid());
        //Bundle bundle = new Bundle();
        //bundle.putString("edttext", "From Activity");
        // set Fragmentclass Arguments
        //EventsFragment fragobj = new EventsFragment();
        //fragobj.setArguments(bundle);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, EventsFragment.newInstance())
                .commit();
    }

    public void generateDataList(List<EventData> eventList) {
        recyclerView = findViewById(R.id.eventsRecyclerView);
        adapter = new DataListAdapter(this, eventList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Events.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    private void setUpFragment(int itemposition) {
        GetDataService service = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
        Call<EventDetails> call = service.getEvent(token.getToken(), itemposition);
        call.enqueue(new Callback<EventDetails>() {
            @Override
            public void onResponse(Call<EventDetails> call, Response<EventDetails> response) {
                ImageView imageView = findViewById(R.id.eventViewFrag);

                Picasso.Builder builder = new Picasso.Builder(Events.this);
                builder.downloader(new OkHttp3Downloader(Events.this));
                builder.build().load(response.body().getUrlimage())
                        .placeholder(R.drawable.ic_launcher_background)
                        .error(R.drawable.ic_launcher_background)
                        .into(imageView);

                TextView title = findViewById(R.id.eventTitleFrag);
                TextView summary = findViewById(R.id.eventSummaryFrag);
                TextView startdate = findViewById(R.id.eventDateFrag);
                TextView location = findViewById(R.id.eventLocationFrag);
                LinearLayout linear = findViewById(R.id.linearLayoutFrag);
                title.setText(response.body().getItemtitle());
                summary.setText(response.body().getEventSummary());
                startdate.setText(response.body().getStartdatetime().toString());
                location.setText(response.body().getItemlocation());
                setupSpeakers(response.body().getSpeakerids());
                recyclerView_frag = findViewById(R.id.eventSpeakerFrag);
                recyclerView_frag.setNestedScrollingEnabled(false);
            }

            @Override
            public void onFailure(Call<EventDetails> call, Throwable t) {
                Toast.makeText(Events.this, "Oops! Something went wrong...", Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });

    }

    private void setupSpeakers(final List<speakerIdentity> speakerids) {
        GetDataService service = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
        for (int i = 0; i < speakerids.size(); i++) {
            Call<speakerDetails> call = service.getSpeaker(token.getToken(), speakerids.get(i).getSpeakerid());
            call.enqueue(new Callback<speakerDetails>() {
                @Override
                public void onResponse(Call<speakerDetails> call, Response<speakerDetails> response) {
                    addSpeakerDetails(response.body());
                    //Log.d("SetupSpeakers","speaker size: "+speakerdetails.size());
                }

                @Override
                public void onFailure(Call<speakerDetails> call, Throwable t) {
                    Toast.makeText(Events.this, "Oops! Something went wrong...", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    private void addSpeakerDetails(speakerDetails speakerDetails){
        speakerdetails.add(speakerDetails);
        Log.d("addSpeakerDetails","added a new detail");
        recyclerView_frag = findViewById(R.id.eventSpeakerFrag);
        adapter_frag = new SpeakerListAdapter(Events.this,speakerdetails);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Events.this);
        recyclerView_frag.setLayoutManager(layoutManager);
        recyclerView_frag.addItemDecoration(new DividerItemDecoration(recyclerView_frag.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView_frag.setAdapter(adapter_frag);
    }
}
