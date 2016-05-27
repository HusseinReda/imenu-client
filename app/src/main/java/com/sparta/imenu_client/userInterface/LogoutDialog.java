package com.sparta.imenu_client.userInterface;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.activity.LoginActivity;

/**
 * Created by Hussein Abu Maash on 4/16/2016.
 */

public class LogoutDialog {
    public LogoutDialog(final AppCompatActivity callingActivity) {
        AlertDialog.Builder logoutDialogBuilder = new AlertDialog.Builder(callingActivity);
        logoutDialogBuilder.setMessage(R.string.dialog_logout);
        logoutDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {
                SharedPreferences currentUserPref = callingActivity.getApplicationContext()
                        .getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
                Editor editor = currentUserPref.edit();
                editor.clear();
                editor.commit();
                Intent loginIntent = new Intent(callingActivity, LoginActivity.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                callingActivity.getApplicationContext().startActivity(loginIntent);
                callingActivity.finish();
            }
        });
        logoutDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog logoutDialog = logoutDialogBuilder.create();
        logoutDialog.show();
    }
}
