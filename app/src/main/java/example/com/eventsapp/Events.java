package example.com.eventsapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import example.com.eventsapp.ui.events.EventsFragment;

public class Events extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_activity);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, EventsFragment.newInstance())
                    .commitNow();
        }
    }
}