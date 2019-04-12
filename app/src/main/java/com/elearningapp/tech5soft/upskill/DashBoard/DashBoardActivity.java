package com.elearningapp.tech5soft.upskill.DashBoard;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.elearningapp.tech5soft.upskill.CourseDetails.CourseDetailsActivity;
import com.elearningapp.tech5soft.upskill.CourseDetails.CourseVideoListRecyclerAdapter;
import com.elearningapp.tech5soft.upskill.Models.MyCourselistModel;
import com.elearningapp.tech5soft.upskill.Models.VideoModel;
import com.elearningapp.tech5soft.upskill.R;
import com.elearningapp.tech5soft.upskill.Utils.BottomNavigationViewHelper;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DashBoardActivity extends AppCompatActivity {
    private static final String TAG = "DashBoardActivity";
    private static final int ACTIVITY_NUM = 2;
    private Context mCOntext = DashBoardActivity.this;
    public List<MyCourselistModel> CourseList;
    private ArrayList<VideoModel> videoDetail;
    private String Base_url="http://27.147.169.230/UpSkillService/UpSkillsService.svc/";
    private String EmpId,OrgId;
    private int completePercent;
    RecyclerView recyclerView;
    private TextView txtpercentage;
    float count=0;
    float total_lec=0;
    int percent;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_dashboard);
        recyclerView= findViewById(R.id.recyclerView_dashboard);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        EmpId = prefs.getString("OrgEmpID","");
        OrgId=prefs.getString("UserOrgID","");
        completePercent=prefs.getInt("Completepercent",0);


        CourseList = new ArrayList<>();
        videoDetail = new ArrayList<>();

        getSupportActionBar().setTitle("DashBoard");
        Log.d(TAG, "onCreate: starting. DashBoardActivity");

        initImageBitmaps();
        setupBottomNavigationView();

    }



    private void initImageBitmaps(){
        String url=Base_url+"GetMyCourseList/"+EmpId+"/"+OrgId;
        Ion.with(getApplicationContext())
                .load("GET",url)
                .setBodyParameter("","")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Log.d("Result",result);
                        try {
                            JSONObject obj = new JSONObject(result);
                            if(obj.isNull("GetMyCourseListResult"))
                            {
                                Log.d("if block","compiled");
                                Toast.makeText(mCOntext, "No Course Found", Toast.LENGTH_SHORT).show();
                            }
                            else if(!obj.equals(null))
                                {
                                    JSONArray jsonArray = obj.getJSONArray("GetMyCourseListResult");
                                    Log.d("Resul3",jsonArray.toString());
                                    for(int i=0;i<jsonArray.length();i++)
                                    {
                                        MyCourselistModel courselist =new MyCourselistModel();
                                        JSONObject course= jsonArray.getJSONObject(i);
                                        courselist.setCourseCode(course.getString("CourseCode"));
                                        //courselist.setCourseFlag(course.getInt("CourseFlag"));
                                        courselist.setCourseName(course.getString("CourseName"));
                                        //courselist.setERROR(course.get("ERROR"));
                                       // courselist.setEmpID(course.getInt("EmpID"));
                                        courselist.setOrgID(course.getString("OrgID"));
                                        //courselist.setPKID(course.getInt("PKID"));
                                        String courseName= course.getString("CourseName");
                                        String courseCode=course.getString("CourseCode");

                                        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                                        prefs.edit().putString("CourseName", courseName).apply();
                                        Log.d("Cousename",course.getString("CourseName"));
                                        percentcal(courseCode);
                                        CourseList.add(courselist);
                                    }
                                    RecyclerViewDashBoardAdapter adapter = new RecyclerViewDashBoardAdapter(DashBoardActivity.this,CourseList);
                                    recyclerView.setAdapter(adapter);
                                }

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
    }
    public void percentcal(String courseCode)
    {
        String url=Base_url+"Get_CNCContentByCTid/"+courseCode+"/"+OrgId+"/"+EmpId;
        Log.d("Url",url);
        Ion.with(getApplicationContext())
                .load("GET",url)
                .setBodyParameter("CourseCode","")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
//                        Log.d("Result",result);
                        try {
                            JSONObject obj =  new JSONObject(result);
                            JSONArray array= obj.getJSONArray("GetCNCContentByCTidResult");
                            if(obj.isNull("GetCNCContentByCTidResult"))
                            {
                                Toast.makeText(getApplicationContext(),"No Course Found",Toast.LENGTH_SHORT).show();
                            }
                            else if (!obj.equals(null)){
                                for(int i=0;i<array.length();i++)
                                {
                                    VideoModel model =new VideoModel();
                                    JSONObject videos = array.getJSONObject(i);
                                    //model.setUrl("https://sample-videos.com/video123/mp4/720/big_buck_bunny_720p_20mb.mp4");
                                    model.setActStatus(videos.getString("ActStatus"));
                                    model.setCategoryID(videos.getInt("CategoryID"));
                                    model.setContentImg(videos.get("ContentImg"));
                                    model.setContentTyp(videos.getInt("ContentTyp"));
                                    model.setContentTypNM(videos.getString("ContentTypNM"));
                                    model.setCourseCode(videos.getString("CourseCode"));
                                    model.setCourseCurriculum(videos.get("CourseCurriculum"));
                                    model.setCourseDateTime(videos.getString("CourseDateTime"));
                                    model.setCourseNM(videos.getString("CourseNM"));
                                    model.seteRROR(videos.get("ERROR"));
                                    model.setEmpID(videos.getInt("EmpID"));
                                    model.setFileExtension(videos.getString("FileExtension"));
                                    model.setFileName(videos.getString("FileName"));
                                    model.setFileSize(videos.getInt("FileSize"));
                                    model.setLectureID(videos.getInt("LectureID"));
                                    model.setLectureNM(videos.getString("LectureNM"));
                                    model.setLectureTitle(videos.getString("LectureTitle"));
                                    model.setOverView(videos.getString("OverView"));
                                    model.setOverView(videos.getString("PKID"));
                                    model.setOverView(videos.getString("RecStatus"));
                                    model.setOverView(videos.getString("TopicCode"));
                                    model.setOverView(videos.getString("TopicNM"));
                                    model.setOverView(videos.getString("VideoFile"));
                                    model.setCompleteflag(videos.getInt("completeflag"));

                                    int complete_flag=videos.getInt("completeflag");
                                    if(complete_flag==1)
                                    {
                                        count++;
                                        Log.d("Complete Course", String.valueOf(count));
                                    }
                                    videoDetail.add(model);

                                }
                                total_lec=array.length();
                                float per=(count/total_lec);
                                percent= (int) (per*100);
//                                Log.d("Total Course", String.valueOf(total_lec));
//                                Log.d("Per", String.valueOf(percent));


                            }



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
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        DashBoardActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
