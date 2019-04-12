package com.elearningapp.tech5soft.upskill.CourseDetails;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elearningapp.tech5soft.upskill.R;
import com.elearningapp.tech5soft.upskill.RequisitionActivity;
import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class CourseInfoActivity extends AppCompatActivity {
    private TextView text_course_name,txt_wishlist,txt_requisition;
    private TextView text_course_details;
    private TextView text_course_teacher_name;
    private CircleImageView image_instructor;
    private ImageView image_Course,wish_icon;
    private String courseCode,corseName,WishFlag,userID,empID,orgID,appID="";
    private Integer Wishflag,CourseFlag;
    String BaseUrl="http://27.147.169.230/UpSkillService/UpSkillsService.svc/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_info);

        initialize();
    }

    public void initialize()
    {
        getSupportActionBar().setTitle("About this Course");
        corseName=getIntent().getExtras().getString("CourseName");
        CourseFlag=getIntent().getExtras().getInt("CourseFlag");
        Wishflag=getIntent().getExtras().getInt("Wishflag");
        text_course_name = findViewById(R.id.text_courseinfo_name);
        text_course_details = findViewById(R.id.text_course_detailsinfo);
        text_course_teacher_name = findViewById(R.id.text_instructor_nameinfo);
        image_instructor = findViewById(R.id.image_instructorinfo);
        txt_requisition=findViewById(R.id.text_req);
        image_Course=findViewById(R.id.courseImageinfo);
        wish_icon=findViewById(R.id.wishimageicon);
        if(Wishflag==1)
        {
            wish_icon.setImageResource(R.drawable.ic_favorite_black_24dp);
        }
        else {
            wish_icon.setImageResource(R.drawable.ic_fav);
        }

        text_course_name.setText(corseName);
        text_course_details.setText("In this course, you'll learn how to apply the material design principles that define Android's visual language to your apps. We'll start by walking you through Android design fundamentals, then we'll show you how to apply this knowledge to transform design elements of sample apps. By the end of the course, you'll understand how to create and use material design elements, surfaces, transitions and graphics in your app, across multiple form factors.");
        image_instructor.setImageResource(R.drawable.teacher);
        text_course_teacher_name.setText("Roman Nurik");

        txt_requisition.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i =new Intent(CourseInfoActivity.this, RequisitionActivity.class);
                startActivity(i);
            }
        });

        wish_icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                CourseCOde=courselistmodel.getCourseCode();
//                Corsename=courselistmodel.getCourseName();
//                Wishflag=courselistmodel.getCourseFlag();
                String newCorsename = corseName;
                final String output = newCorsename.replaceAll("\\s+","_");
                courseCode=getIntent().getExtras().getString("CourseCode");
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                userID = prefs.getString("UserID","");
                empID = prefs.getString("OrgEmpID","");
                orgID = prefs.getString("UserOrgID","");
                appID = prefs.getString("AppID","");

                String check_url = BaseUrl+"checkcurrentwishlist/"+courseCode+"/"+empID+"/"+orgID;

                Ion.with(getApplicationContext())
                        .load("GET",check_url)
                        .setBodyParameter("","")
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {
                                Log.d("Result",result);
                                try {
                                    JSONObject obj =new JSONObject(result);


                                    if(obj.isNull("checkcurrentwishlistResult"))
                                    {
                                        addwishlist(courseCode, output, 1, empID, orgID, userID, appID);
                                        Log.d("if block","compiled");
                                    }
                                    else if (!obj.equals(null))
                                    {
                                        String rslt=obj.getString("checkcurrentwishlistResult");
                                        JSONObject dataobj = new JSONObject(rslt);
                                        Log.d("new",dataobj.toString());
                                        String crnt_Coursecode = dataobj.getString("CourseCode");
                                        String crnt_CorseName = dataobj.getString("CourseName");
                                        String crnt_flag=dataobj.getString("WishFlag");

                                        if (crnt_CorseName.equals(output) && crnt_Coursecode.equals(courseCode) &&crnt_flag.equals("1")) {
                                            Toast.makeText(getApplicationContext(), "Already added", Toast.LENGTH_SHORT).show();
                                        } else {
                                            updateflag(courseCode, output,1, empID, orgID);
                                        }
                                    }
                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

                            }
                        });


            }
        });

    }

    private void addwishlist(String CourseCOde, String output, Integer Wishflag, String empID, String orgID, String userID, String appID) {



        String wish_url = BaseUrl+"Add_CourseWishListFromApps/"+CourseCOde+"/"+output+"/"+1+"/"+empID+"/"+orgID+"/"+userID+"/"+appID;
        Log.d("Wish URL ",wish_url);

        JsonObject json = new JsonObject();
        Ion.with(getApplicationContext())
                .load("POST",wish_url)
                .setLogging("MyLogsadd",Log.DEBUG)
                .setBodyParameter("CourseCode",CourseCOde)
                .setBodyParameter("CourseName",output)
                .setBodyParameter("WishFlag","1")
                .setBodyParameter("EmpID",empID)
                .setBodyParameter("OrgID",orgID)
                .setBodyParameter("UserID",userID)
                .setBodyParameter("AppID",appID)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Log.d("Result",result);
                        Toast.makeText(getApplicationContext(),"Added to Wishlist",Toast.LENGTH_SHORT).show();
                        wish_icon.setImageResource(R.drawable.ic_favorite_black_24dp);


                    }
                });
    }

    private void updateflag(String courseCOde, String output, int i, String empID, String orgID) {
        String update_wish = BaseUrl+"Update_wishlist/"+courseCOde+"/"+output+"/"+1+"/"+empID+"/"+orgID;
        Log.d("update flag ",update_wish);
        Ion.with(getApplicationContext())
                .load("POST",update_wish.trim())
                .setLogging("MyLogsup",Log.DEBUG)
                .setBodyParameter("CourseCode",courseCOde)
                .setBodyParameter("CourseName",output)
                .setBodyParameter("WishFlag","1")
                .setBodyParameter("EmpID",empID)
                .setBodyParameter("OrgID",orgID)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Log.d("Result",result);
                        try {
                            JSONObject obj =new JSONObject(result);
                            String rslt=obj.getString("UpdateCourseWishListResult");
                            Toast.makeText(getApplicationContext(),"Updated to Wishlist",Toast.LENGTH_SHORT).show();
                            wish_icon.setImageResource(R.drawable.ic_favorite_black_24dp);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }

                    }
                });
    }
}
