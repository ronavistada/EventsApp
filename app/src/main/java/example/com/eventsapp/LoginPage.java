package example.com.eventsapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

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

    public void checkAuth(){
        if(token != null){
            Intent loginIntent = new Intent(LoginPage.this,Events.class);
            loginIntent.putExtra(Extra_Data,token.getToken());
            startActivity(loginIntent);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        checkAuth();
    }

    public void onLogin(View view){
        if(usernameInput.getText().toString().isEmpty()){
            Log.i("Main","Username Imput: "+usernameInput.getText().toString());
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

    private void authorizeEntry(){
        PostDataService service = RetrofitClient.getRetrofitInstance().create(PostDataService.class);
        Call<Authtoken> call = service.getLogin(usernameInput.getText().toString(),passwordInput.getText().toString());

        call.enqueue(new Callback<Authtoken>() {
            @Override
            public void onResponse(Call<Authtoken> call, Response<Authtoken> response) {
                Log.i("LoginPage","Got a response");
                token = response.body();
                checkAuth();
            }

            @Override
            public void onFailure(Call<Authtoken> call, Throwable t) {
                Log.i("LoginPage","Login failed!");
                Toast.makeText(LoginPage.this,"Login failed!",Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }


}
