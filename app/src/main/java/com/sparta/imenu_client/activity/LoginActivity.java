package com.sparta.imenu_client.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.service.LoginAuthService;

public class LoginActivity extends AppCompatActivity {
    boolean usernameTextFlag;
    boolean passwordTextFlag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        usernameTextFlag= false;
        passwordTextFlag= false;
        final EditText passwordText = (EditText) findViewById(R.id.passwordText) ;
        final EditText usernameText = (EditText) findViewById(R.id.usernameText);
        passwordText.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!passwordTextFlag){
                    passwordTextFlag=true;
                    hasFocus =false;
                }
            }
        });
        usernameText.setOnFocusChangeListener(new View.OnFocusChangeListener(){
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (!usernameTextFlag){
                    usernameTextFlag=true;
                    hasFocus=false;
                }
            }
        });
    }
    public void loginHandler(View view){
        LoginAuthService service = new LoginAuthService();
        service.execute();
    }
}
