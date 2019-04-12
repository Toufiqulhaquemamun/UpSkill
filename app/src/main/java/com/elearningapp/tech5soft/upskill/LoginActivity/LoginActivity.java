package com.elearningapp.tech5soft.upskill.LoginActivity;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.elearningapp.tech5soft.upskill.DashBoard.DashBoardActivity;
import com.elearningapp.tech5soft.upskill.Home.HomeActivity;
import com.elearningapp.tech5soft.upskill.Profile.ProfileActivity;
import com.elearningapp.tech5soft.upskill.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    private EditText edit_user, edit_pass;
    private Button btn_login;
    private ImageView imageViewlogo;
    private String user_id,pass,sessionId;
    public String Base_url="http://27.147.169.230/AdminaService/AdminaService.svc/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ProfileActivity prop=new ProfileActivity();
        if (!isConnected(LoginActivity.this)) buildDialog(LoginActivity.this).show();
        edit_user = findViewById(R.id.editText_username);
        edit_pass = findViewById(R.id.editText_password);
        btn_login=findViewById(R.id.button_signIn);
        imageViewlogo=findViewById(R.id.image_app_logo);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
//                Intent intent = new Intent(LoginActivity.this, ProfileActivity.class);
//                startActivity(intent);
            }
        });

    }

    private void userLogin() {

        user_id = edit_user.getText().toString();
        pass = edit_pass.getText().toString();
        sessionId=user_id+"session";
        String url= Base_url+"Verify_User_And_Password_For_login/"+user_id+"/"+pass+"/"+sessionId+"/"+"''"+"/"+"''";
        Log.d("URL ",url);
        if (user_id.isEmpty()) {
            edit_user.setError("Id Required");
            edit_user.requestFocus();
            return;
        }
        if (pass.isEmpty()) {
            edit_pass.setError("Password Required");
                edit_pass.requestFocus();
            return;
        }

        Ion.with(getApplicationContext()).
                load(url).
                setBodyParameter("UserID", user_id).
                setBodyParameter("Password", pass).
                asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                String rslt="";
                Log.d("rsl", "result is: " + result);

                try {
                    JSONObject json = new JSONObject(result);
                    rslt = json.getString("VerifyUserAndPasswordForLoginResult");
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }
                if (rslt.equals("1")) try {

                    JSONObject json = new JSONObject(result);
                    Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
                    //progressDialog.dismiss();
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                        prefs.edit().putString("UserID", user_id).apply();
                        prefs.edit().putString("SessionId",sessionId).apply();
                } catch (JSONException e1) {
                    e1.printStackTrace();
                    //progressDialog.dismiss();
                }
                else if (rslt.equals("2")){
                    Toast.makeText(LoginActivity.this, "User Already Logged in", Toast.LENGTH_LONG).show();
                }
                else if (rslt.equals("3")){
                    Toast.makeText(LoginActivity.this, "User id is inactive! Pls Contact with system administrator.", Toast.LENGTH_LONG).show();
                }
                else if (rslt.equals("4")){
                    Toast.makeText(LoginActivity.this, "Invalid login credentials.", Toast.LENGTH_LONG).show();
                }
                else if (rslt.equals("5")){
                    Toast.makeText(LoginActivity.this, "User id locked!", Toast.LENGTH_LONG).show();
                }
                else if (rslt.equals("6")){
                    Toast.makeText(LoginActivity.this, "You have no role for this application", Toast.LENGTH_LONG).show();
                }
                else if (rslt.equals("7")){
                    Toast.makeText(LoginActivity.this, "Wrong UserId or Password", Toast.LENGTH_LONG).show();
                }
                else {
                    //progressDialog.dismiss();
                    Toast.makeText(LoginActivity.this, "Invalid login credentials.", Toast.LENGTH_LONG).show();
                }
            }
        });

    }
    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {

        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
    }


     //To check the internet connectivity of the handset
    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            return (mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting());
        } else
            return false;
    }

    //To show the error dialog if there is no internet connection
    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok",new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog,int which) {

                finish();
            }
        });

        return builder;
    }


}
