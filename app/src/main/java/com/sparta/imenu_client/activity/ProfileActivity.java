package com.sparta.imenu_client.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.service.PreferenceService;

public class ProfileActivity extends AppCompatActivity {

    String username;
    AppCompatActivity context;
    EditText preference;
    public ProfileActivity(String userName, AppCompatActivity context) {
        this.username = userName;
        this.context=context;
    }

    public ProfileActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        preference = (EditText)findViewById(R.id.preference_editText);
        Intent myIntent = getIntent();
        username=myIntent.getStringExtra("username");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.template_menu, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.action_profile:
                Intent profileIntent = new Intent(context, ProfileActivity.class);
                context.getApplicationContext().startActivity(profileIntent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void addPreferenceHandler(View view) {
        Log.i("profile page","Add button clicked");
        String preference_input = preference.getText().toString();
        PreferenceService preferenceService=new PreferenceService(username,preference_input,"add",this);
        preferenceService.execute();
    }
    public void removePreferenceHandler(View view) {
        Log.i("profile page","remove button clicked");
        String preference_input = preference.getText().toString();
        PreferenceService preferenceService=new PreferenceService(username,preference_input,"remove",this);
        preferenceService.execute();
    }

}
