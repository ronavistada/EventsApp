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

public class LoginPage extends AppCompatActivity {

    private EditText usernameInput;
    private EditText passwordInput;
    private TextView validateMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       usernameInput = findViewById(R.id.LoginUsername);
       passwordInput = findViewById(R.id.LoginPassword);
       validateMessage = findViewById(R.id.ValidateInputMessage);


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
        Intent loginIntent = new Intent(this,Events.class);
        startActivity(loginIntent);
    }


}
