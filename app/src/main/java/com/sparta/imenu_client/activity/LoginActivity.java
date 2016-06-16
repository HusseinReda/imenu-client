package com.sparta.imenu_client.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.common.api.GoogleApiClient;
import com.sparta.imenu_client.R;
import com.sparta.imenu_client.model.User;
import com.sparta.imenu_client.model.UserRequest;
import com.sparta.imenu_client.service.AddRestaurant;
import com.sparta.imenu_client.service.GetAllUserSpecService;
import com.sparta.imenu_client.service.LoginAuthService;
import com.sparta.imenu_client.service.SignUpService;
import com.sparta.imenu_client.userInterface.IMenuAnimation;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class LoginActivity extends AppCompatActivity {
    final String TAG ="LoginActivity";
    boolean emailTextFlag;
    boolean passwordTextFlag;
    EditText passwordText;
    EditText emailText;
    RelativeLayout signUpLayout;
    TextView newAccountTextView;
    LinearLayout contentLoginLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences currentUserPref = getApplicationContext().getSharedPreferences("CurrentUser",MODE_PRIVATE);
        if(currentUserPref.getString("email",null)!=null){
            Intent homeIntent = new Intent(this, HomeActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(homeIntent);
            finish();
        }
        setContentView(R.layout.activity_login);

        contentLoginLayout = (LinearLayout) findViewById(R.id.content_login_layout);
        contentLoginLayout.setVisibility(View.GONE);
        IMenuAnimation.fade_in(this,contentLoginLayout);
        contentLoginLayout.setVisibility(View.VISIBLE);

        emailTextFlag = false;
        passwordTextFlag = false;
        passwordText = (EditText) findViewById(R.id.passwordLoginEditText);
        emailText = (EditText) findViewById(R.id.emailLoginEditText);

        // sign up toggle view
        newAccountTextView = (TextView) findViewById(R.id.newAccountTextView);
        signUpLayout = (RelativeLayout) findViewById(R.id.signUp_layout);
        signUpLayout.setVisibility(View.INVISIBLE);

        newAccountTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (signUpLayout.getVisibility()==View.VISIBLE) {
                    IMenuAnimation.slide_up(LoginActivity.this, signUpLayout);
                    signUpLayout.setVisibility(View.INVISIBLE);
                } else if(signUpLayout.getVisibility()==View.INVISIBLE) {
                    signUpLayout.setVisibility(View.VISIBLE);
                    IMenuAnimation.slide_down(LoginActivity.this, signUpLayout);
                }
            }
        });
    }

    public void loginHandler(View view) {
        if(getUsername().equals("admin")&&getPassword().equals("admin")){
            SharedPreferences currentUserPref = getApplicationContext().getSharedPreferences("CurrentUser", Context.MODE_PRIVATE);
            Intent homeIntent = new Intent(this, AdminActivity.class);
            homeIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            getApplicationContext().startActivity(homeIntent);
            finish();
        }
        else {
            LoginAuthService service = new LoginAuthService(this, new UserRequest(getUsername(), getPassword()));
            service.execute();
        }
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
                gender.getText().toString(),
                1);

        Calendar newDate = Calendar.getInstance();
        newDate.set(newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH));
        newUser.setJoinDate(newDate.getTime());
        byte[] temp={'a'};
        newUser.setImage(temp);
        SignUpService signUpService=new SignUpService(this,newUser);
        signUpService.execute();
    }
    public String getUsername(){
        return emailText.getText().toString() ;
    }
    public String getPassword(){
        return passwordText.getText().toString();
    }
    @Override
    public void onStart() {
        super.onStart();
    }

}
