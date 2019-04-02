package example.com.eventsapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import example.com.eventsapp.AppServices.PostDataService;
import example.com.eventsapp.AppServices.RetrofitClient;
import example.com.eventsapp.DataClasses.Authtoken;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginPage extends AppCompatActivity {

    public static final String Extra_Data = "com.example.com.eventsapp.DATA";

    private EditText usernameInput;
    private EditText passwordInput;
    private TextView validateMessage;
    private Authtoken token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       usernameInput = findViewById(R.id.LoginUsername);
       passwordInput = findViewById(R.id.LoginPassword);
       validateMessage = findViewById(R.id.ValidateInputMessage);
       checkAuth();
    }

    public void checkAuth(){ //method to check if the user should enter the events page
        if(token != null){
            Intent loginIntent = new Intent(LoginPage.this,Events.class);
            loginIntent.putExtra(Extra_Data,token.getToken());
            startActivity(loginIntent);
        }
    }

    @Override
    protected void onResume() { //this method is here in case the users resumed the activity again
        super.onResume();
        checkAuth();
    }

    public void onLogin(View view){ //this method would validate the information entered by the user
        if(usernameInput.getText().toString().isEmpty()){
            validateMessage.setText("A username is required.");
            validateMessage.setVisibility(View.VISIBLE);
        }
        else if(passwordInput.getText().toString().isEmpty()){
            validateMessage.setText("A password is required.");
            validateMessage.setVisibility(View.VISIBLE);
        }
        else{
            validateMessage.setVisibility(View.INVISIBLE);
            authorizeEntry();
        }
    }

    private void authorizeEntry(){ //this method will request the needed token
        PostDataService service = RetrofitClient.getRetrofitInstance().create(PostDataService.class);
        Call<Authtoken> call = service.getLogin(usernameInput.getText().toString(),passwordInput.getText().toString());

        call.enqueue(new Callback<Authtoken>() {
            @Override
            public void onResponse(Call<Authtoken> call, Response<Authtoken> response) {
                token = response.body();
                checkAuth();
            }

            @Override
            public void onFailure(Call<Authtoken> call, Throwable t) {
                Toast.makeText(LoginPage.this,"Login failed!",Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBackPressed() { //method to prevent users to use the back button if there is no token
        if(token==null){
            Toast.makeText(LoginPage.this, "Action Not Allowed", Toast.LENGTH_LONG).show();
        }
        else {
            super.onBackPressed();
        }

    }
}
