package com.sparta.imenu_client.activity;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.text.format.DateFormat;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.sparta.imenu_client.R;
import com.sparta.imenu_client.model.User;
import com.sparta.imenu_client.model.UserSpec;
import com.sparta.imenu_client.service.Auxiliary;
import com.sparta.imenu_client.service.GetUserByEmailService;
import com.sparta.imenu_client.service.UpdateUserService;
import com.sparta.imenu_client.userInterface.IMenuAnimation;
import com.sparta.imenu_client.userInterface.LogoutDialog;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Locale;

public class ProfileActivity extends AppCompatActivity {

    private ImageView userImage;
    private EditText userFullName;
    private TextView userDob;
    private Date userNewDate;
    private SimpleDateFormat dateFormatter;
    private DatePickerDialog userDobDialog;
    private Calendar newDate;
    private User currentUser;
    private User newUser;
    private RadioButton maleRadioButton;
    private RadioButton femaleRadioButton;
    private AppCompatSpinner userCountriesSpinner;
    private LinearLayout userPasswordData;
    private TextView userPasswordStart;
    private EditText userCity;
    private LinearLayout userPreferenceStart;
    private LinearLayout userPreferencesDataLayout;
    private TextView addNewPreferenceStart;
    private LinearLayout userAddNewPreferencesData;
    private Button addPreferenceButton;
    private TextView userPreferencesData;
    private EditText addNewPreferenceEditText;
    private TextView userNewPreferences;
    private Button removePreferenceButton;
    private static int RESULT_LOAD_IMG = 1;
    private TextView addRemovePreferenceStart;
    private LinearLayout userAddRemovePreferencesDataLayout;
    private TextView userDobChangeIt;
    private LinearLayout userRestrictionsDataLayout;
    private LinearLayout userRestrictionStart;
    private TextView userRestrictionsData;
    private LinearLayout userAddRemoveRestrictionsDataLayout;
    private TextView addRemoveRestrictionStart;
    private AppCompatSpinner addNewRestrictionSpinner;
    private TextView userNewRestrictions;
    private Button addRestrictionButton;
    private Button removeRestrictionButton;
    private LinearLayout userHealthIssuesDataLayout;
    private LinearLayout userHealthIssueStart;
    private TextView userHealthIssuesData;
    private LinearLayout userAddRemoveHealthIssuesDataLayout;
    private TextView addRemoveHealthIssueStart;
    private AppCompatSpinner addNewHealthIssueSpinner;
    private TextView userNewHealthIssues;
    private Button addHealthIssueButton;
    private Button removeHealthIssueButton;
    private EditText userOldPassword;
    private EditText userNewPassword;
    private EditText userNewPassword2;
    private Button userUpdatePasswordButton;
    private Button removeUserImageButton;
    private ImageView userHealthIssueStartImg;
    private ImageView userPreferenceStartImg;
    private ImageView userRestrictionStartImg;

    public ProfileActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.profile_toolbar);
        setSupportActionBar(toolbar);
        setDateTimeField();

        Intent intent = getIntent();
        currentUser = (User) intent.getSerializableExtra("currentUser");

        newUser = new User();
        newUser.copyUser(currentUser);

        // Image
        removeUserImageButton = (Button) findViewById(R.id.remove_user_image_button);
        if(currentUser.getImage()==null || (currentUser.getImage())[0]=='a')
            removeUserImageButton.setVisibility(View.GONE);

        image();

        // FullName
        userFullName = (EditText) findViewById(R.id.user_full_name);
        userFullName.setText(currentUser.getName());
        userFullName.setSelection(userFullName.getText().length());

        // Password
        password();

        // DateOfBirth
        dateOfBirth();

        // Gender
        gender();

        // Country
        setUpCountriesSpinner();

        // City
        userCity = (EditText) findViewById(R.id.user_city);
        if(currentUser.getCity()!=null) {
            userCity.setText(currentUser.getCity());
            userCity.setSelection(userCity.getText().length());
        }

        // Preferences
        userPreferenceStartImg = (ImageView) findViewById(R.id.user_preferences_start_img);
        preferences();

        // Restrictions
        userRestrictionStartImg = (ImageView) findViewById(R.id.user_restrictions_start_img);
        restrictions();

        // HealthIssues
        userHealthIssueStartImg = (ImageView) findViewById(R.id.user_health_issues_start_img);
        healthIssues();
    }

    private void image() {
        userImage = (ImageView) findViewById(R.id.user_image);
        if(currentUser.getImage()!=null && (currentUser.getImage())[0]!='a'){
            Bitmap bmp = BitmapFactory.decodeByteArray(currentUser.getImage(), 0, currentUser.getImage().length);
            userImage.setImageBitmap(bmp);
        }
        else{
            userImage.setImageResource(R.drawable.add_profile_picture);
        }
    }

    public void loadImageFromGallery(View view) {
        // Create intent to Open Image applications like Gallery, Google Photos
        Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        // Start the Intent
        startActivityForResult(galleryIntent, RESULT_LOAD_IMG);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            // When an Image is picked
            if (requestCode == RESULT_LOAD_IMG && resultCode == RESULT_OK
                    && null != data) {

                // Get the Image from data
                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                // Get the cursor
                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);

                // Move to first row
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                // Set the Image in ImageView after decoding the String
                userImage.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));
                removeUserImageButton.setVisibility(View.VISIBLE);

                Bitmap bitmap = ((BitmapDrawable) userImage.getDrawable()).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                byte[] byteArray = stream.toByteArray();

                newUser.setImage(byteArray);

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }

    }

    private void gender() {
        maleRadioButton = (RadioButton) findViewById(R.id.user_radio_male);
        femaleRadioButton = (RadioButton) findViewById(R.id.user_radio_female);
        if(currentUser.getGender().equals("Male"))
            maleRadioButton.setChecked(true);
        else
            femaleRadioButton.setChecked(true);
    }

    private void dateOfBirth() {
        userDob = (TextView) findViewById(R.id.user_dob);
        if(currentUser.getDateOfBirth()==null) {
            newDate = Calendar.getInstance();
            newDate.set(newDate.get(Calendar.YEAR), newDate.get(Calendar.MONTH), newDate.get(Calendar.DAY_OF_MONTH));
            userDob.setText(dateFormatter.format(newDate.getTime()));
        }
        else{
            userDob.setText(dateFormatter.format(currentUser.getDateOfBirth()));
        }
        userDobChangeIt = (TextView) findViewById(R.id.user_dob_change_it);
        userDobChangeIt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userDobDialog.show();
            }
        });
    }

    private void password() {
        userPasswordData = (LinearLayout) findViewById(R.id.user_password_data);
        userPasswordData.setVisibility(View.GONE);

        userPasswordStart = (TextView) findViewById(R.id.user_password_start);
        userPasswordStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userPasswordData.isShown()) {
                    IMenuAnimation.slide_up(ProfileActivity.this, userPasswordData);
                    userPasswordData.setVisibility(View.GONE);
                } else {
                    userPasswordData.setVisibility(View.VISIBLE);
                    IMenuAnimation.slide_down(ProfileActivity.this, userPasswordData);
                }

            }
        });

        userUpdatePasswordButton = (Button) findViewById(R.id.user_update_password_button);
        userOldPassword = (EditText) findViewById(R.id.user_old_password);
        userNewPassword = (EditText) findViewById(R.id.user_new_password);
        userNewPassword2 = (EditText) findViewById(R.id.user_new_password_2);
        userUpdatePasswordButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userOldPassword.getText()!=null && userOldPassword.getText().length()!=0
                        && userOldPassword.getText().toString().equals(currentUser.getPassword())){

                    if(userNewPassword.getText()!=null && userNewPassword.getText().length()!=0){
                        if(userNewPassword.getText().toString().equals(userNewPassword2.getText().toString())) {
                            User newUserPassword = new User();
                            newUserPassword.copyUser(currentUser);
                            newUserPassword.setPassword(userNewPassword2.getText().toString());
                            UpdateUserService updateUserService = new UpdateUserService(ProfileActivity.this,newUserPassword);
                            updateUserService.execute();
                        }
                        else
                            Toast.makeText(ProfileActivity.this,"New passwords don't match",Toast.LENGTH_SHORT).show();
                    }
                    else
                        Toast.makeText(ProfileActivity.this,"Empty new password",Toast.LENGTH_SHORT).show();
                }
                else
                    Toast.makeText(ProfileActivity.this,"Wrong old password",Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void preferences() {

        userPreferencesDataLayout = (LinearLayout) findViewById(R.id.user_preferences_data_layout);
        userPreferencesDataLayout.setVisibility(View.GONE);

        userPreferenceStart = (LinearLayout) findViewById(R.id.user_preferences_start);
        userPreferenceStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userPreferencesDataLayout.getVisibility()==View.VISIBLE) {
                    IMenuAnimation.slide_up(ProfileActivity.this, userPreferencesDataLayout);
                    userPreferencesDataLayout.setVisibility(View.GONE);
                    userPreferenceStartImg.setImageResource(R.drawable.plus_icon);
                } else if (userPreferencesDataLayout.getVisibility()==View.GONE){
                    userPreferencesDataLayout.setVisibility(View.VISIBLE);
                    IMenuAnimation.slide_down(ProfileActivity.this, userPreferencesDataLayout);
                    userPreferenceStartImg.setImageResource(R.drawable.minus_icon);
                }
            }
        });

        userPreferencesData = (TextView) findViewById(R.id.user_preferences_data);
        String tempPreferences="";
        for(int i=0;i<currentUser.getPreferences().size();i++){
            tempPreferences+="- "+currentUser.getPreferences().get(i);
            tempPreferences+="\n";
        }
        userPreferencesData.setText(tempPreferences);


        userAddRemovePreferencesDataLayout = (LinearLayout) findViewById(R.id.user_add_remove_preference_data);
        userAddRemovePreferencesDataLayout.setVisibility(View.GONE);

        addRemovePreferenceStart= (TextView) findViewById(R.id.user_add_remove_preference_start);
        addRemovePreferenceStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userAddRemovePreferencesDataLayout.isShown()) {
                    IMenuAnimation.slide_up(ProfileActivity.this, userAddRemovePreferencesDataLayout);
                    userAddRemovePreferencesDataLayout.setVisibility(View.GONE);
                } else {
                    userAddRemovePreferencesDataLayout.setVisibility(View.VISIBLE);
                    IMenuAnimation.slide_down(ProfileActivity.this, userAddRemovePreferencesDataLayout);
                }
            }
        });

        addNewPreferenceEditText = (EditText) findViewById(R.id.user_add_new_preference_edit_text);
        userNewPreferences = (TextView) findViewById(R.id.user_new_preferences);
        userNewPreferences.setMovementMethod(ScrollingMovementMethod.getInstance());
        addPreferenceButton = (Button) findViewById(R.id.user_add_new_preference_button);
        addPreferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String preferenceText = addNewPreferenceEditText.getText().toString();
                if(preferenceText==null || preferenceText.length()==0)
                    Toast.makeText(ProfileActivity.this,"No preference written",Toast.LENGTH_SHORT).show();
                else{
                    if(newUser.getPreferences().indexOf(preferenceText)!=-1)
                        Toast.makeText(ProfileActivity.this,"Preference already exists",Toast.LENGTH_SHORT).show();
                    else{
                        newUser.getPreferences().add(preferenceText);
                        userNewPreferences.setText(userNewPreferences.getText().toString()+"\n+ "+preferenceText);
                        addNewPreferenceEditText.setText("");
                    }
                }
            }
        });

        removePreferenceButton = (Button) findViewById(R.id.user_remove_new_preference_button);
        removePreferenceButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String preferenceText = addNewPreferenceEditText.getText().toString();
                if(preferenceText==null || preferenceText.length()==0)
                    Toast.makeText(ProfileActivity.this,"No preference written",Toast.LENGTH_SHORT).show();
                else{
                    if(newUser.getPreferences().indexOf(preferenceText)==-1)
                        Toast.makeText(ProfileActivity.this,"Preference already doesn't exist",Toast.LENGTH_SHORT).show();
                    else{
                        newUser.getPreferences().remove(preferenceText);
                        userNewPreferences.setText(userNewPreferences.getText().toString()+"\n- "+preferenceText);
                        addNewPreferenceEditText.setText("");
                    }
                }
            }
        });
    }

    private void restrictions() {
        userRestrictionsDataLayout = (LinearLayout) findViewById(R.id.user_restrictions_data_layout);
        userRestrictionsDataLayout.setVisibility(View.GONE);

        userRestrictionStart = (LinearLayout) findViewById(R.id.user_restrictions_start);
        userRestrictionStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userRestrictionsDataLayout.getVisibility()==View.VISIBLE) {
                    IMenuAnimation.slide_up(ProfileActivity.this, userRestrictionsDataLayout);
                    userRestrictionsDataLayout.setVisibility(View.GONE);
                    userRestrictionStartImg.setImageResource(R.drawable.plus_icon);
                } else if (userRestrictionsDataLayout.getVisibility()==View.GONE){
                    userRestrictionsDataLayout.setVisibility(View.VISIBLE);
                    IMenuAnimation.slide_down(ProfileActivity.this, userRestrictionsDataLayout);
                    userRestrictionStartImg.setImageResource(R.drawable.minus_icon);
                }
            }
        });

        userRestrictionsData = (TextView) findViewById(R.id.user_restrictions_data);
        String tempRestrictions="";
        for(int i=0;i<currentUser.getRestrictions().size();i++){
            tempRestrictions+="- "+currentUser.getRestrictions().get(i).getName();
            tempRestrictions+="\n";
        }
        userRestrictionsData.setText(tempRestrictions);


        userAddRemoveRestrictionsDataLayout = (LinearLayout) findViewById(R.id.user_add_remove_restriction_data);
        userAddRemoveRestrictionsDataLayout.setVisibility(View.GONE);

        addRemoveRestrictionStart= (TextView) findViewById(R.id.user_add_remove_restriction_start);
        addRemoveRestrictionStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userAddRemoveRestrictionsDataLayout.isShown()) {
                    IMenuAnimation.slide_up(ProfileActivity.this, userAddRemoveRestrictionsDataLayout);
                    userAddRemoveRestrictionsDataLayout.setVisibility(View.GONE);
                } else {
                    userAddRemoveRestrictionsDataLayout.setVisibility(View.VISIBLE);
                    IMenuAnimation.slide_down(ProfileActivity.this, userAddRemoveRestrictionsDataLayout);
                }
            }
        });


        addNewRestrictionSpinner = (AppCompatSpinner) findViewById(R.id.user_add_new_restriction_spinner);
        ArrayList<String> restrictionsNames = new ArrayList<>();
        Log.i("profile act","restrictions size : "+Auxiliary.restrictions.size());
        for(int i=0;i< Auxiliary.restrictions.size();i++)
            restrictionsNames.add(Auxiliary.restrictions.get(i).getName());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, restrictionsNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addNewRestrictionSpinner.setAdapter(dataAdapter);

        userNewRestrictions = (TextView) findViewById(R.id.user_new_restrictions);
        addRestrictionButton = (Button) findViewById(R.id.user_add_new_restriction_button);
        addRestrictionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String restrictionText = (String) addNewRestrictionSpinner.getSelectedItem().toString();
                UserSpec userSpec = new UserSpec();
                if(newUser.getRestrictions().indexOf(Auxiliary.getRestrictionByName(restrictionText))!=-1)
                    Toast.makeText(ProfileActivity.this,"Restriction already exists",Toast.LENGTH_SHORT).show();
                else{
                    newUser.getRestrictions().add(Auxiliary.getRestrictionByName(restrictionText));
                    userNewRestrictions.setText(userNewRestrictions.getText().toString() + "\n+ " + restrictionText);
                }

            }
        });

        removeRestrictionButton = (Button) findViewById(R.id.user_remove_new_restriction_button);
        removeRestrictionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String restrictionText = (String) addNewRestrictionSpinner.getSelectedItem().toString();
                if(! (newUser.findRestrictionByName(restrictionText)) )
                    Toast.makeText(ProfileActivity.this,"Restriction already doesn't exist",Toast.LENGTH_SHORT).show();
                else{
                    newUser.removeRestrictionByName(restrictionText);
                    userNewRestrictions.setText(userNewRestrictions.getText().toString() +"\n- "+restrictionText);
                }

            }
        });
    }

    private void healthIssues() {
        userHealthIssuesDataLayout = (LinearLayout) findViewById(R.id.user_health_issues_data_layout);
        userHealthIssuesDataLayout.setVisibility(View.GONE);

        userHealthIssueStart = (LinearLayout) findViewById(R.id.user_health_issues_start);
        userHealthIssueStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userHealthIssuesDataLayout.getVisibility()==View.VISIBLE) {
                    IMenuAnimation.slide_up(ProfileActivity.this, userHealthIssuesDataLayout);
                    userHealthIssuesDataLayout.setVisibility(View.GONE);
                    userHealthIssueStartImg.setImageResource(R.drawable.plus_icon);
                } else if (userHealthIssuesDataLayout.getVisibility()==View.GONE){
                    userHealthIssuesDataLayout.setVisibility(View.VISIBLE);
                    IMenuAnimation.slide_down(ProfileActivity.this, userHealthIssuesDataLayout);
                    userHealthIssueStartImg.setImageResource(R.drawable.minus_icon);
                }
            }
        });

        userHealthIssuesData = (TextView) findViewById(R.id.user_health_issues_data);
        String tempHealthIssues="";
        for(int i=0;i<currentUser.getHealthIssues().size();i++){
            tempHealthIssues+="- "+currentUser.getHealthIssues().get(i).getName();
            tempHealthIssues+="\n";
        }
        userHealthIssuesData.setText(tempHealthIssues);


        userAddRemoveHealthIssuesDataLayout = (LinearLayout) findViewById(R.id.user_add_remove_health_issue_data);
        userAddRemoveHealthIssuesDataLayout.setVisibility(View.GONE);

        addRemoveHealthIssueStart= (TextView) findViewById(R.id.user_add_remove_health_issue_start);
        addRemoveHealthIssueStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userAddRemoveHealthIssuesDataLayout.isShown()) {
                    IMenuAnimation.slide_up(ProfileActivity.this, userAddRemoveHealthIssuesDataLayout);
                    userAddRemoveHealthIssuesDataLayout.setVisibility(View.GONE);
                } else {
                    userAddRemoveHealthIssuesDataLayout.setVisibility(View.VISIBLE);
                    IMenuAnimation.slide_down(ProfileActivity.this, userAddRemoveHealthIssuesDataLayout);
                }
            }
        });

        addNewHealthIssueSpinner = (AppCompatSpinner) findViewById(R.id.user_add_new_health_issue_spinner);
        ArrayList<String> healthIssuesNames = new ArrayList<>();
        Log.i("profile act","healthissues size : "+Auxiliary.healthIssues.size());
        for(int i=0;i< Auxiliary.healthIssues.size();i++)
            healthIssuesNames.add(Auxiliary.healthIssues.get(i).getName());

        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, healthIssuesNames);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        addNewHealthIssueSpinner.setAdapter(dataAdapter);

        userNewHealthIssues = (TextView) findViewById(R.id.user_new_health_issues);
        addHealthIssueButton = (Button) findViewById(R.id.user_add_new_health_issue_button);
        addHealthIssueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String healthIssueText = (String) addNewHealthIssueSpinner.getSelectedItem().toString();
                UserSpec userSpec = new UserSpec();
                if(newUser.getHealthIssues().indexOf(Auxiliary.getHealthIssueByName(healthIssueText))!=-1)
                    Toast.makeText(ProfileActivity.this,"Health issue already exists",Toast.LENGTH_SHORT).show();
                else{
                    newUser.getHealthIssues().add(Auxiliary.getHealthIssueByName(healthIssueText));
                    userNewHealthIssues.setText(userNewHealthIssues.getText() + "\n+ " + healthIssueText);
                }

            }
        });

        removeHealthIssueButton = (Button) findViewById(R.id.user_remove_new_health_issue_button);
        removeHealthIssueButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String healthIssueText = (String) addNewHealthIssueSpinner.getSelectedItem().toString();
                if(! (newUser.findHealthIssueByName(healthIssueText)) )
                    Toast.makeText(ProfileActivity.this,"Health issue already doesn't exist",Toast.LENGTH_SHORT).show();
                else{
                    Log.i("profile act","before remove : "+newUser.getHealthIssues().size());
                    newUser.removeHealthIssueByName(healthIssueText);
                    Log.i("profile act", "after remove : " + newUser.getHealthIssues().size());
                    userNewHealthIssues.setText(userNewHealthIssues.getText()+"\n- "+healthIssueText);
                }

            }
        });
    }

    private void setUpCountriesSpinner() {
        userCountriesSpinner = (AppCompatSpinner) findViewById(R.id.user_countries_spinner);
        Locale[] locales = Locale.getAvailableLocales();
        ArrayList<String> countries = new ArrayList<String>();
        for (Locale locale : locales) {
            String country = locale.getDisplayCountry();
            if (country.trim().length()>0 && !countries.contains(country)) {
                countries.add(country);
            }
        }
        Collections.sort(countries);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, countries);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        userCountriesSpinner.setAdapter(dataAdapter);
        if(currentUser.getCountry()!=null) {
            int spinnerPosition = dataAdapter.getPosition(currentUser.getCountry());
            userCountriesSpinner.setSelection(spinnerPosition);
        }
    }

    private void setDateTimeField() {
        Calendar newCalendar = Calendar.getInstance();
        dateFormatter = new SimpleDateFormat("dd-MM-yyyy");
        userDobDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                newDate = Calendar.getInstance();
                newDate.set(year, monthOfYear, dayOfMonth);
                userDob.setText(dateFormatter.format(newDate.getTime()));
            }

        },newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
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
                GetUserByEmailService getUserByEmailService = new GetUserByEmailService(this,true);
                getUserByEmailService.execute();
                return true;

            case R.id.action_logout:
                LogoutDialog logoutDialog = new LogoutDialog(this);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void saveChanges(View v){
        AlertDialog.Builder saveChangesDialogBuilder = new AlertDialog.Builder(this);
        saveChangesDialogBuilder.setMessage(R.id.save_changes_dialog);
        saveChangesDialogBuilder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

                newUser.setName(userFullName.getText().toString());

                RadioGroup radioSexGroup = (RadioGroup) findViewById(R.id.user_gender_group);
                RadioButton gender = (RadioButton) findViewById(radioSexGroup.getCheckedRadioButtonId());
                newUser.setGender(gender.getText().toString());

                int day = userDobDialog.getDatePicker().getDayOfMonth();
                int month = userDobDialog.getDatePicker().getMonth();
                int year =  userDobDialog.getDatePicker().getYear();
                Calendar calendar = Calendar.getInstance();
                calendar.set(year, month, day);
                newUser.setDateOfBirth(calendar.getTime());

                newUser.setCountry(userCountriesSpinner.getSelectedItem().toString());
                if(userCity.getText()!=null && userCity.getText().toString().length()!=0)
                    newUser.setCity(userCity.getText().toString());

                Log.i("profile act","before service : "+newUser.getHealthIssues().size());
                UpdateUserService updateUserService = new UpdateUserService(ProfileActivity.this,newUser);
                updateUserService.execute();
            }
        });
        saveChangesDialogBuilder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });
        AlertDialog logoutDialog = saveChangesDialogBuilder.create();
        logoutDialog.show();
    }

    public void removeUserImage (View v){
        userImage.setImageResource(R.drawable.add_profile_picture);
        byte[] temp = {'a'};
        newUser.setImage(temp);
        removeUserImageButton.setVisibility(View.GONE);
    }
}
