package example.com.eventsapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

public class AlertFactory {

    public void showAlert(String message, Context context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(message)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
