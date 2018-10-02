package vishwadeepreddy.user;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hbb20.CountryCodePicker;

import java.util.Calendar;

public class CreateAccountActivity extends AppCompatActivity {

    private EditText firstname;
    private EditText lastname;
    private EditText email;
    private EditText password;
    private EditText city;
    private Button createaccount;
    private DatabaseReference mdatabaserefernce;
    private FirebaseDatabase mdatabse;
    private FirebaseAuth mAuth;
    private ProgressDialog mprogessdialog;

    private EditText date;
    private DatePickerDialog.OnDateSetListener datePickerListener;

    private CountryCodePicker cpp;
    private CountryCodePicker cpp1;
    private EditText phone;

    private Calendar mycalendar;

    private TextInputLayout firstnameWrapper;
    private TextInputLayout lastnameWrapper;
    private TextInputLayout emailWrapper;
    private TextInputLayout passwordWrapper;
    private TextInputLayout dobWrapper;
    private TextInputLayout cityWrapper;
    private TextInputLayout countryWrapper;
    private TextInputLayout phoneWrapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        hideKeyboard();

        mdatabse = FirebaseDatabase.getInstance();
        mdatabaserefernce = mdatabse.getReference().child("Users");
        mAuth = FirebaseAuth.getInstance();

        mprogessdialog = new ProgressDialog(CreateAccountActivity.this);

        firstname = (EditText) findViewById(R.id.firstnameid);
        lastname = (EditText) findViewById(R.id.lastnameid);
        email = (EditText) findViewById(R.id.emailid);
        password = (EditText) findViewById(R.id.passwordid);
        city = (EditText) findViewById(R.id.cityid);
        createaccount = (Button) findViewById(R.id.createaccountid);


        firstnameWrapper = (TextInputLayout) findViewById(R.id.firstnameidWrapper);
        lastnameWrapper = (TextInputLayout) findViewById(R.id.lastnameidWrapper);
        emailWrapper = (TextInputLayout) findViewById(R.id.emailidWrapper);
        passwordWrapper = (TextInputLayout) findViewById(R.id.passwordWrapper);
        dobWrapper = (TextInputLayout) findViewById(R.id.dobWrapper);
        cityWrapper = (TextInputLayout) findViewById(R.id.cityidWrapper);
        countryWrapper = (TextInputLayout) findViewById(R.id.countryidWrapper);
        phoneWrapper = (TextInputLayout) findViewById(R.id.phoneidWrapper);

        /*
        firstnameWrapper.setHint("First Name");
        lastnameWrapper.setHint("Last Name");
        emailWrapper.setHint("Email");
        passwordWrapper.setHint("Password");
        dobWrapper.setHint("Date of Birth");
        cityWrapper.setHint("City");
        countryWrapper.setHint("Country");
        phoneWrapper.setHint("Phone Number");
        */

        cpp = (CountryCodePicker) findViewById(R.id.countryid);
        cpp1 = (CountryCodePicker) findViewById(R.id.codeid);
        phone = (EditText) findViewById(R.id.phoneid);

        date = (EditText) findViewById(R.id.dob);

        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mycalendar = Calendar.getInstance();
                int day = mycalendar.get(Calendar.DAY_OF_MONTH);
                int month = mycalendar.get(Calendar.MONTH);
                int year = mycalendar.get(Calendar.YEAR);

                DatePickerDialog dialog = new DatePickerDialog(CreateAccountActivity.this,
                        android.R.style.Theme_Holo_Light_Dialog_MinWidth,
                        datePickerListener, day, month, year);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialog.show();


                //setting minimum day,month & year to start inside the dialog

                mycalendar.add(Calendar.YEAR, -58);
                mycalendar.add(Calendar.MONTH, -5);
                mycalendar.add(Calendar.DAY_OF_MONTH, -36);

                long mindate = mycalendar.getTime().getTime();

                dialog.getDatePicker().setMinDate(mindate);


            }
        });


        datePickerListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

                int selectedmonth = month + 1;

                String myformat = " "
                        + dayOfMonth + "/" + (selectedmonth < 10 ? ("0" + selectedmonth) : (selectedmonth)) + "/" + year;
                date.setText(myformat);

            }
        };

        //attach phone number to edittext to ccp1
        cpp1.registerCarrierNumberEditText(phone);


        createaccount.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {

                final String name = firstname.getText().toString().trim();
                final String last_name = lastname.getText().toString().trim();
                final String em = email.getText().toString().trim();
                final String pwd = password.getText().toString().trim();
                final String dob = date.getText().toString().trim();
                final String cities = city.getText().toString().trim();
                final String country_selected = cpp.getSelectedCountryName().toString().trim();
                final String phone_number = cpp1.getFullNumberWithPlus().toString().trim();

                hideKeyboard();

                                    /*firstnameWrapper.setErrorEnabled(false);
                    lastnameWrapper.setErrorEnabled(false);
                    emailWrapper.setErrorEnabled(false);
                    passwordWrapper.setErrorEnabled(false);
                    dobWrapper.setErrorEnabled(false);
                    cityWrapper.setErrorEnabled(false);
                    phoneWrapper.setErrorEnabled(false);
                    */
                if(TextUtils.isEmpty(name)){
                    firstnameWrapper.setError("Enter Firstname");
                }else{firstnameWrapper.setErrorEnabled(false);}
                if(TextUtils.isEmpty(last_name)){
                    lastnameWrapper.setError("Enter Lastname");
                }else{lastnameWrapper.setErrorEnabled(false);}
                if(TextUtils.isEmpty(em)){
                    emailWrapper.setError("Enter Email");
                }else{emailWrapper.setErrorEnabled(false);}
                if(TextUtils.isEmpty(pwd)){
                    passwordWrapper.setError("Enter Password");
                }else{passwordWrapper.setErrorEnabled(false);}
                if(TextUtils.isEmpty(dob)){
                    dobWrapper.setError("Enter Date of Birth");
                }else{dobWrapper.setErrorEnabled(false);}
                if(TextUtils.isEmpty(cities)){
                    cityWrapper.setError("Enter City");
                }else{cityWrapper.setErrorEnabled(false);}
                if(TextUtils.isEmpty(phone_number)){
                    phoneWrapper.setError("Enter Phone Number");
                }else{phoneWrapper.setErrorEnabled(false);}
                /*if(TextUtils.isEmpty(country_selected)){
                    cpp.setError("Country is not entered");
                    phone.requestFocus();
                }*/
                if(!TextUtils.isEmpty(name) && !TextUtils.isEmpty(last_name) && !TextUtils.isEmpty(em)
                        && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(dob) && !TextUtils.isEmpty(cities)
                        && !TextUtils.isEmpty(country_selected) && !TextUtils.isEmpty(phone_number)) {

                    createNewAccount();

                }
            }

            public void countryvalidate(){
                boolean count = true;

            }
        });

        /*private void isValidate(String firstname){

        }

       /* public static boolean isEmailValid(String email){
            Pattern pattern = Patterns.EMAIL_ADDRESS;
            Matcher matcher = pattern.matcher(email);
            return matcher.matches();
        }*/
    }


    private void hideKeyboard() {
        View view = getCurrentFocus();
        if (view != null) {
            ((InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE)).
                    hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        }
    }

    private void createNewAccount() {

        final String name = firstname.getText().toString().trim();
        final String last_name = lastname.getText().toString().trim();
        final String em = email.getText().toString().trim();
        final String pwd = password.getText().toString().trim();
        final String dob = date.getText().toString().trim();
        final String cities = city.getText().toString().trim();
        final String country_selected = cpp.getSelectedCountryName().toString().trim();
        final String phone_number = cpp1.getFullNumberWithPlus().toString().trim();

        /*firstnameWrapper.setErrorEnabled(false);
        lastnameWrapper.setErrorEnabled(false);
        emailWrapper.setErrorEnabled(false);
        passwordWrapper.setErrorEnabled(false);
        dobWrapper.setErrorEnabled(false);
        cityWrapper.setErrorEnabled(false);
        phoneWrapper.setErrorEnabled(false);*/

        if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(last_name) && !TextUtils.isEmpty(em)
                && !TextUtils.isEmpty(pwd) && !TextUtils.isEmpty(dob) && !TextUtils.isEmpty(cities)
                && !TextUtils.isEmpty(country_selected) && !TextUtils.isEmpty(phone_number)) {


            mprogessdialog.setMessage("Creating Accounting....");
            mprogessdialog.show();

            mAuth.createUserWithEmailAndPassword(em, pwd).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                @Override
                public void onSuccess(AuthResult authResult) {
                    if (authResult != null) {

                        User user = new User();

                        user.setFirst_name(name);
                        user.setLast_name(last_name);
                        user.setEmail(em);
                        user.setPassword(pwd);
                        user.setDob(dob);
                        user.setCity(cities);
                        user.setCountry(country_selected);
                        user.setMobile_number(phone_number);

                        String first = user.getFirst_name();
                        Log.d("user.com: ", first);
                        String userid = mAuth.getCurrentUser().getUid();
                        DatabaseReference currentUserDb = mdatabaserefernce.child(userid);
                        currentUserDb.child("First Name").setValue(name);
                        currentUserDb.child("Last Name").setValue(last_name);
                        currentUserDb.child("Email").setValue(em);
                        currentUserDb.child("Date of Birth").setValue(dob);
                        currentUserDb.child("City").setValue(cities);
                        currentUserDb.child("Country").setValue(country_selected);
                        currentUserDb.child("Phone Number").setValue(phone_number);

                        mprogessdialog.dismiss();
                        Toast.makeText(CreateAccountActivity.this, "Account Created", Toast.LENGTH_LONG).show();

                        Intent intent = new Intent(CreateAccountActivity.this, PostListActivity.class);
                        startActivity(intent);

                    }
                }
            });
        }
        /*else if (password.getText().toString().length() > 8) {
            passwordWrapper.setError("Password should be atleast 8 characters");
        }
        else if(!cpp1.isValidFullNumber()) {
            phone.setError("Check Your Phone Number");
            phone.requestFocus();
        }*/
    }

}
