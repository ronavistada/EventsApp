package example.com.eventsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
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
    private Toolbar actionBar;
    private EventsFragment fragment;

    @Override
    //this is only called when Login is successful.
    //this should generate the list
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_activity);

        actionBar = findViewById(R.id.toolbar);
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

    //This event is triggered when the user press on an event. This will set up the fragment
    public void onEventClick(View view) {
        List<EventData> eventData = adapter.getEventlist();
        setUpFragment(eventData.get(recyclerView.getChildLayoutPosition(view)).getItemid());
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        actionBar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
                getSupportActionBar().setDisplayShowHomeEnabled(false);
                getSupportFragmentManager().beginTransaction().remove(fragment).commitNow();
            }
        });
        fragment = EventsFragment.newInstance();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.container, fragment)
                .commit();
    }

    //used by the onCreate to set the view
    public void generateDataList(List<EventData> eventList) {
        recyclerView = findViewById(R.id.eventsRecyclerView);
        adapter = new DataListAdapter(this, eventList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(Events.this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
        recyclerView.setAdapter(adapter);
    }

    //main method to create the fragment
    private void setUpFragment(int itemposition) {
        try {
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
        catch (Exception e){
            AlertFactory factory = new AlertFactory();
            factory.showAlert("An error occurred.",Events.this);
            Intent loginIntent = new Intent(Events.this,LoginPage.class);
            startActivity(loginIntent);

        }

    }

    //This is a seperate method to set up the list of speakers in the fragment
    private void setupSpeakers(final List<speakerIdentity> speakerids) {
        GetDataService service = RetrofitClient.getRetrofitInstance().create(GetDataService.class);
        for (int i = 0; i < speakerids.size(); i++) {
            Call<speakerDetails> call = service.getSpeaker(token.getToken(), speakerids.get(i).getSpeakerid());
            call.enqueue(new Callback<speakerDetails>() {
                @Override
                public void onResponse(Call<speakerDetails> call, Response<speakerDetails> response) {
                    addSpeakerDetails(response.body());
                }

                @Override
                public void onFailure(Call<speakerDetails> call, Throwable t) {
                    Toast.makeText(Events.this, "Oops! Something went wrong...", Toast.LENGTH_LONG).show();
                }
            });
        }
    }

    //this is called to set the view in the recycler view
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

    @Override
    //this is used to show the option menu
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.event_menu, menu);
        return true;
    }

    //when the user press on the logout option, it should bring them back to the Login screen
    public void onLogout(MenuItem item){
        token = null;
        try{
            Intent loginIntent = new Intent(Events.this,LoginPage.class);
            startActivity(loginIntent);
        }
        catch(Exception e) {
            Toast.makeText(Events.this, "Oops! Something went wrong...", Toast.LENGTH_LONG).show();
        }
    }

}
