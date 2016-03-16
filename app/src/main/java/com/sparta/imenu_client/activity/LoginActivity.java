package com.sparta.imenu_client.activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.sparta.imenu_client.R;
import com.sparta.imenu_client.service.LoginAuthService;

public class LoginActivity extends AppCompatActivity {
    final String TAG ="LoginActivity";
    boolean usernameTextFlag;
    boolean passwordTextFlag;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;
    public EditText passwordText;
    public EditText usernameText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameTextFlag = false;
        passwordTextFlag = false;
        passwordText = (EditText) findViewById(R.id.passwordText);
        usernameText = (EditText) findViewById(R.id.usernameText);
        usernameText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    public void loginHandler(View view) {
        LoginAuthService service = new LoginAuthService(this);
        service.execute();
    }
    public String getUsername(){
       return usernameText.getText().toString() ;
    }
    public String getPassword(){
        return passwordText.getText().toString();
    }
    @Override
    public void onStart() {
        super.onStart();
    }

}
