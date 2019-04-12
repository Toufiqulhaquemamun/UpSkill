package com.elearningapp.tech5soft.upskill.Profile;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.bumptech.glide.Glide;
import com.elearningapp.tech5soft.upskill.LoginActivity.LoginActivity;
import com.elearningapp.tech5soft.upskill.R;
import com.elearningapp.tech5soft.upskill.RequisitionActivity;
import com.elearningapp.tech5soft.upskill.Utils.BottomNavigationViewHelper;
import com.google.gson.JsonArray;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ProfileActivity extends AppCompatActivity {
    private static final String TAG = "ProfileActivity";
    private static final int ACTIVITY_NUM = 4;
    private Context mContext = ProfileActivity.this;
    private ImageView image_logout,image_profile;
    private TextView text_user_name,text_Requisition;
    private String userID,userPropic, userSessionID,useripAddress,userAppid;
    public List<GetUserInfoByIDResult> userdata;
    public String Base_url="http://27.147.169.230/AdminaService/AdminaService.svc/";
    int position ;
    RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_profile);
        image_profile = findViewById(R.id.newprofile_image);
        image_logout = findViewById(R.id.image_profile_logout);
        text_user_name= findViewById(R.id.profile_user_name_txt);
        text_Requisition=findViewById(R.id.txt_requisition);
        userdata = new ArrayList<>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        userID = prefs.getString("UserID","");
        String u_name=prefs.getString("UserName","");
        text_user_name.setText(u_name);
        userSessionID=prefs.getString("SessionId","");
        getSupportActionBar().hide();
        Log.d(TAG, "onCreate: starting. ProfileActivity");
        setupBottomNavigationView();
        userdata();
           onClick();
    }
    public void userdata()
    {
        String User_url = Base_url+"Get_UserInfoByID/"+userID+"/"+5+"/"+5+"/"+userID;
        Log.d("USer url",User_url);
        Ion.with(getApplicationContext())
                .load("GET",User_url)
                .setBodyParameter("UserID",userID)
                .asString().setCallback(new FutureCallback<String>() {
            @Override
            public void onCompleted(Exception e, String result) {
                String rslt="";
                Log.d("rsl", "result is: " + result);
                try {
                    JSONObject obj = new JSONObject(result);
                    rslt=obj.getString("GetUserInfoByIDResult");
                    JSONObject dataobj = new JSONObject(rslt);

                    userdata.add(new GetUserInfoByIDResult(
                                      dataobj.getString("ActStatus"),
                                    dataobj.get("ActiveFlagInactivUser"),
                                    dataobj.get("ActiveFlagMultiLogin"),
                                    dataobj.getInt("AppID"),
                                    dataobj.get("AppNM"),
                                    dataobj.get("ClassificationId"),
                                    dataobj.get("ERROR"),
                                    dataobj.get("FailedLoginAttempt"),
                                    dataobj.get("FirstLoginFlag"),
                                    dataobj.getInt("OrgEmpID"),
                                    dataobj.getString("Password"),
                                    dataobj.getString("RecStatus"),
                                    dataobj.getString("StatusNM"),
                                    dataobj.getString("UserCellNo"),
                                    dataobj.getString("UserDOB"),
                                    dataobj.get("UserDigSig"),
                                    dataobj.getString("UserEmail"),
                                    dataobj.getString("UserID"),
                                    dataobj.getInt("UserIDLockWrnAtmp"),
                                    dataobj.getString("UserIPAdd"),
                                    dataobj.getString("UserNID"),
                                    dataobj.get("UserNIDImg"),
                                    dataobj.getString("UserName"),
                                    dataobj.getInt("UserOrgID"),
                                    dataobj.getString("UserOrgNM"),
                                    dataobj.get("UserProfilePic"),
                                    dataobj.getString("UserShName"),
                                    dataobj.getString("UserStatus"),
                                    dataobj.get("UserThumb"),
                                    dataobj.getInt("UserTypID"),
                                    dataobj.getString("UserTypIDVal"),
                                    dataobj.getString("UserTypNM")
                           ));
                    String username = dataobj.getString("UserName");

                    useripAddress =dataobj.getString("UserIPAdd");
                    userAppid=dataobj.getString("AppID");
                    String Orgid = dataobj.getString("UserOrgID");
                    String Empid = dataobj.getString("OrgEmpID");
                    JSONArray array= dataobj.getJSONArray("UserProfilePic");
                    Log.d("Array",array.toString());

                    byte[] imagarr= new byte[array.length()];
                    for(int i=0;i<array.length();i++)
                    {
                        imagarr[i] = (byte) array.getInt(i);
                    }
                    Bitmap bmp = BitmapFactory.decodeByteArray(imagarr,0,imagarr.length);
                    Glide.with(getApplicationContext())
                            .load(bmp)
                            .into(image_profile);
                    //image_profile.setImageBitmap(bmp);
                    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    prefs.edit().putString("UserName", username).apply();
                    prefs.edit().putString("UserIPAdd",useripAddress).apply();
                    prefs.edit().putString("AppID",userAppid).apply();
                    prefs.edit().putString("UserOrgID",Orgid).apply();
                    prefs.edit().putString("OrgEmpID",Empid).apply();

//                    ProfileRecyclerAdapter adapter = new ProfileRecyclerAdapter(ProfileActivity.this,userdata);
//                    recyclerView.setAdapter(adapter);
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }
        });
    }


    public void onClick()
    {
        image_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Loggedout();
                Intent myIntent = new Intent(ProfileActivity.this, LoginActivity.class);
                finish();
                startActivity(myIntent);
                overridePendingTransition(0,0);
                myIntent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);

            }
        });

        text_Requisition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent myintent= new Intent(ProfileActivity.this, RequisitionActivity.class);
                startActivity(myintent);
            }
        });

    }
    public void Loggedout()
    {
        String logout_url=Base_url+"Logout_user/"+userID+"/"+userSessionID+"/"+"''"+"/"+"''";
        Ion.with(getApplicationContext())
                .load(logout_url)
                .setBodyParameter("UserID",userID)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Log.d("Logout result",result);
                    }
                });
    }

    //BottomNavigationView Setup
    private void setupBottomNavigationView()
    {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx  = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mContext, bottomNavigationViewEx);

        Menu menu = bottomNavigationViewEx.getMenu();
        MenuItem menuItem = menu.getItem(ACTIVITY_NUM);
        menuItem.setChecked(true);

        overridePendingTransition(0,0);

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

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        ProfileActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
