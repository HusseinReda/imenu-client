package com.sparta.imenu_client.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.google.android.gms.common.api.GoogleApiClient;
import com.sparta.imenu_client.R;
import com.sparta.imenu_client.model.User;
import com.sparta.imenu_client.service.LoginAuthService;
import com.sparta.imenu_client.service.SignUpService;

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
        passwordText = (EditText) findViewById(R.id.passwordLoginEditText);
        usernameText = (EditText) findViewById(R.id.usernameLoginEditText);
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
    public void signUpHandler(View view) {
        EditText fullName= (EditText)findViewById(R.id.fullNameEditText);
        EditText email= (EditText)findViewById(R.id.emailEditText);
        EditText password= (EditText)findViewById(R.id.passwordEditText);
        RadioGroup radioSexGroup = (RadioGroup) findViewById(R.id.radioGenderGroup);
        RadioButton gender = (RadioButton) findViewById(radioSexGroup.getCheckedRadioButtonId());
        User newUser = new User(fullName.getText().toString(),
                                email.getText().toString(),
                                password.getText().toString(),
                                gender.getText().toString());
        SignUpService signUpService=new SignUpService(this,newUser);
        signUpService.execute();
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
