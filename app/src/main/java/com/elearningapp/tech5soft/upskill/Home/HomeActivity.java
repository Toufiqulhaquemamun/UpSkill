package com.elearningapp.tech5soft.upskill.Home;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.elearningapp.tech5soft.upskill.Models.CourseCatagory;
import com.elearningapp.tech5soft.upskill.Models.CourseList;
import com.elearningapp.tech5soft.upskill.Models.MyCourselistModel;
import com.elearningapp.tech5soft.upskill.R;
import com.elearningapp.tech5soft.upskill.Utils.BottomNavigationViewHelper;
import com.elearningapp.tech5soft.upskill.connection;
import com.google.gson.Gson;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {

    private static final String TAG = "HomeActivity"; //Organizing Log
    //variables
    private static final int ACTIVITY_NUM = 0;
    private Context mContext = HomeActivity.this;
    RecyclerView verticalRecyclerView;
    VerticalCatagoryAdapter adapter;
    connection db=new connection();
    ArrayList<CourseCatagory> arrayListcatagory;
    Map<CourseCatagory,CourseList> map;
    private String Base_url = "http://27.147.169.230/UpSkillService/UpSkillsService.svc/";
    private String EmpId,OrgId,Cat_id,Cat_name,crnt_cat_name="";
    private ProgressBar bar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Log.d(TAG, "onCreate: starting.");
        getSupportActionBar().hide();
        bar=findViewById(R.id.progress_bar);
        connection db=new connection();
        //arrayListcourse = new ArrayList<>();
        arrayListcatagory=new ArrayList<>();
        verticalRecyclerView = findViewById(R.id.recyclerview_main);
        verticalRecyclerView.setHasFixedSize(true);
        verticalRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        adapter = new VerticalCatagoryAdapter(this, arrayListcatagory);
        verticalRecyclerView.setAdapter(adapter);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        EmpId = prefs.getString("OrgEmpID","");
        OrgId=prefs.getString("UserOrgID","");
        bar.setVisibility(View.VISIBLE);
        initRecyclerViewcouseList();
        //setCatagory();
        setupBottomNavigationView();
    }

    private void setCatagory() {
        for(int i=0;i<=5;i++)
        {
            CourseCatagory catagoryModel = new CourseCatagory();
            catagoryModel.setCategoryName("Catagory"+i);
            ArrayList<CourseList> arrayCourselist = new ArrayList<>();
            for(int j=0;j<=5;j++)
            {
                CourseList courseListModel = new CourseList();
                courseListModel.setCategoryID("Description"+j);
                courseListModel.setCourseName(i+"Name"+j);
                arrayCourselist.add(courseListModel);
            }
            catagoryModel.setCourseList(arrayCourselist);
            arrayListcatagory.add(catagoryModel);
        }
        adapter.notifyDataSetChanged();
    }


    private void initRecyclerViewcouseList() {

        db.insert();
        String url=Base_url+"GetCNCCourseDefByorg/"+OrgId+"/"+EmpId;
        Log.d("CourseDef",url);
        Ion.with(getApplicationContext())
                .load("GET",url)
                .setBodyParameter("","")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onCompleted(Exception e, String result) {
                        try {
                            JSONObject obj =new JSONObject(result);

                            JSONArray jsonArray = obj.getJSONArray("GetCNCCourseDefByorgResult");
                            if(obj.isNull("GetCNCCourseDefByorgResult"))
                            {
                                Toast.makeText(getApplicationContext(),"No Course Found",Toast.LENGTH_SHORT).show();
                            }
                            else if (!obj.equals(null)) {
                                String cata="";
                                Log.d("Resul3", jsonArray.toString());
                                Log.d("Resul3", String.valueOf(jsonArray.length()));
                                for (int i = 0; i < jsonArray.length(); i++) {
                                    CourseCatagory catagoryModel = new CourseCatagory();
                                    JSONObject course = jsonArray.getJSONObject(i);
                                    catagoryModel.setCategoryName(course.getString("CategoryName"));
                                    catagoryModel.setCategoryID(course.getInt("CategoryID"));
                                    Log.d("Catagorymain",course.getString("CategoryName"));
                                    ArrayList<CourseList> arrayCourselist = new ArrayList<>();
                                    arrayCourselist.clear();
                                    for (int j=0;j<jsonArray.length();j++)
                                    {
                                        CourseList courselist = new CourseList();
                                        JSONObject cat1 = jsonArray.getJSONObject(j);
                                        Log.d("--cheking--",cat1.getString("CategoryName")+"  "+course.getString("CategoryName"));
                                        if(course.getString("CategoryName").equalsIgnoreCase(cat1.getString("CategoryName"))){
                                            courselist.setCourseName(cat1.getString("CourseName"));
                                            courselist.setCourseCode(cat1.getString("CourseCode"));
                                            courselist.setWishFlag(cat1.getInt("WishFlag"));
                                            courselist.setCourseFlag(cat1.getInt("CourseFlag"));
                                            Log.d("------inside if-----",cat1.getString("CourseName"));
                                            arrayCourselist.add(courselist);
                                            Log.d("--list sixze--", String.valueOf(arrayCourselist.size()));
                                        }
                                        catagoryModel.setCourseList(arrayCourselist);
                                        insertUniqueItem(catagoryModel);
                                    }
                                }
                                adapter.notifyDataSetChanged();
                                bar.setVisibility(View.INVISIBLE);
                            }
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });

    }

//    public void insertUniqueCourse(CourseList course) {
//        if(!contains(course)) {
//            arr.add(course);
//        }
//    }
//
//    private boolean contains(CourseList item) {
//        for(CourseList i : arrayListcourse) {
//            if(i.getCourseName().equals(item.getCourseName())) {
//                return true;
//            }
//        }
//        return false;
//    }


    public void insertUniqueItem(CourseCatagory cat) {
        if(!contains(cat)) {
            arrayListcatagory.add(cat);
        }
    }

    private boolean contains(CourseCatagory item) {
        for(CourseCatagory i : arrayListcatagory) {
            if(i.getCategoryName().equals(item.getCategoryName())) {
                return true;
            }
        }
        return false;
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
                        HomeActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }




}
