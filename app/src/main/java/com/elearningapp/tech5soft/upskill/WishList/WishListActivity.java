package com.elearningapp.tech5soft.upskill.WishList;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.elearningapp.tech5soft.upskill.CourseDetails.CourseInformationActivity;
import com.elearningapp.tech5soft.upskill.LoginActivity.LoginActivity;
import com.elearningapp.tech5soft.upskill.Profile.ProfileActivity;
import com.elearningapp.tech5soft.upskill.R;
import com.elearningapp.tech5soft.upskill.Utils.BottomNavigationViewHelper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class WishListActivity extends AppCompatActivity implements RecyclerViewAdapterWishlist.ItemClickListener {
    private static final String TAG = "WishListActivity";
    private static final int ACTIVITY_NUM = 3;
    private Context mCOntext = WishListActivity.this;
    private String Baseurl="http://27.147.169.230/UpSkillService/UpSkillsService.svc/";
    public String OrgId,EmpId,CourseId;
    public List<WishlistCourseModel> wishdata;
    RecyclerView recyclerView;
    RecyclerViewAdapterWishlist adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wishlist);
        recyclerView= findViewById(R.id.recyclerView_wishlist);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        getSupportActionBar().setTitle("WishList");
        Log.d(TAG, "onCreate: starting. WishListActivity");
        setupBottomNavigationView();
        wishdata=new ArrayList<>();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        OrgId = prefs.getString("UserOrgID","");
        EmpId = prefs.getString("OrgEmpID","");

        getWishList(OrgId,EmpId);
    }
    public void getWishList(String OrgId ,String EmpId)
    {
        String check_url=Baseurl+"Get_CourseWishList/"+EmpId+"/"+OrgId;
        Ion.with(WishListActivity.this)
                .load("GET",check_url)
                .setBodyParameter("","")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Log.d("Wishlist",result);
                        try {
                            Gson gson = new Gson();
                            JSONObject jsonObject = new JSONObject(result);
                            JSONArray jsonArray = jsonObject.getJSONArray("GetCourseWishListResult");

                            for(int i=0; i<jsonArray.length();i++)
                            {

                                WishlistCourseModel model = new WishlistCourseModel();
                                JSONObject course= jsonArray.getJSONObject(i);
                                model.setCourseCode(course.getString("CourseCode"));
                                model.setCourseName(course.getString("CourseName"));
                                model.setERROR(course.get("ERROR"));
                                model.setEmpID(course.getInt("EmpID"));
                                model.setOrgID(course.getInt("OrgID"));
                                model.setPKID(course.getInt("PKID"));
                                model.setWishFlag(course.getInt("WishFlag"));
                                wishdata.add(model);
                            }
                             adapter= new RecyclerViewAdapterWishlist(WishListActivity.this,wishdata);
                            recyclerView.setAdapter(adapter);
                            adapter.setClickListener(WishListActivity.this);

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                });

    }

    //BottomNavigationView Setup
    private void setupBottomNavigationView()
    {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView");
        BottomNavigationViewEx bottomNavigationViewEx  = findViewById(R.id.bottomNavViewBar);
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx);
        BottomNavigationViewHelper.enableNavigation(mCOntext, bottomNavigationViewEx);

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


    @Override
    public void onItemClick(View view, int position) {
        WishlistCourseModel model = wishdata.get(position);

        Intent i = new Intent(WishListActivity.this, CourseInformationActivity.class);
        i.putExtra("CourseName",model.getCourseName().replaceAll("_"," "));
        i.putExtra("CourseCode",model.getCourseCode());
        i.putExtra("Wishflag",model.getWishFlag());
        startActivity(i);
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        WishListActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
