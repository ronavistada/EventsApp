package example.com.eventsapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toolbar;

import example.com.eventsapp.DataClasses.Authtoken;
import example.com.eventsapp.ui.events.EventsFragment;

public class Events extends AppCompatActivity {

    private Authtoken token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.events_activity);
        Toolbar actionBar = findViewById(R.id.toolbar);

        Intent intent = getIntent();
        token = new Authtoken(intent.getStringExtra(LoginPage.Extra_Data));
//        if (savedInstanceState == null) {
//            getSupportFragmentManager().beginTransaction()
//                    .replace(R.id.container, EventsFragment.newInstance())
//                    .commitNow();
//        }
    }
}
