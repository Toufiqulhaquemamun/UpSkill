package com.elearningapp.tech5soft.upskill.DashBoard;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.elearningapp.tech5soft.upskill.CourseDetails.CourseDetailsActivity;
import com.elearningapp.tech5soft.upskill.CourseDetails.CourseInformationActivity;
import com.elearningapp.tech5soft.upskill.CourseDetails.CourseVideoListRecyclerAdapter;
import com.elearningapp.tech5soft.upskill.Models.MyCourselistModel;
import com.elearningapp.tech5soft.upskill.Models.VideoModel;
import com.elearningapp.tech5soft.upskill.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewDashBoardAdapter extends RecyclerView.Adapter<RecyclerViewDashBoardAdapter.ViewHolder> {

    private Context mcntx;
    private List<MyCourselistModel> courseList;
   private ArrayList<VideoModel> videoDetail = new ArrayList<>();
    private String Base_url="http://27.147.169.230/UpSkillService/UpSkillsService.svc/";
    private String EmpId,OrgId;
    float count=0;
    float total_lec=0;
    int percent;
    



    public RecyclerViewDashBoardAdapter(Context mcntx, List<MyCourselistModel> courseList) {
        this.mcntx = mcntx;
        this.courseList = courseList;
        this.videoDetail=videoDetail;

    }

    @NonNull
    @Override
    public RecyclerViewDashBoardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater Inflater = LayoutInflater.from(mcntx);
        View view = Inflater.inflate(R.layout.dashboard_course_layout,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerViewDashBoardAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(courseList.get(position));
        MyCourselistModel courselistmodel = courseList.get(position);

        holder.courseImage.setImageResource(R.drawable.market);
        holder.coursename.setText(courselistmodel.getCourseName());
        String courseCode=courselistmodel.getCourseCode();
        //coursePercent(courseCode);

        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mcntx);
        EmpId = prefs.getString("OrgEmpID","");
        OrgId=prefs.getString("UserOrgID","");


        String url=Base_url+"Get_CNCContentByCTid/"+courseCode+"/"+OrgId+"/"+EmpId;
        Log.d("Url",url);
        Ion.with(mcntx)
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
                                Toast.makeText(mcntx,"No Course Found",Toast.LENGTH_SHORT).show();
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
                                holder.coursePercent.setText(String.valueOf(percent));


                            }



                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });




    }

    @Override
    public int getItemCount() {
        return courseList.size();
    }




    public class ViewHolder extends RecyclerView.ViewHolder
    {
        TextView coursename,coursePercent;
        ImageView courseImage;
        public ViewHolder(View itemview)
        {
            super(itemview);

            courseImage=itemview.findViewById(R.id.courseList_course_image);
            coursename=itemview.findViewById(R.id.courseList_course_name);
            coursePercent=itemview.findViewById(R.id.text_course_complete_rate);


            mcntx=itemView.getContext();
            itemview.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MyCourselistModel model = (MyCourselistModel)v.getTag();
                    Intent i = new Intent(mcntx,CourseDetailsActivity.class);
                    i.putExtra("CourseCode",model.getCourseCode());
                    mcntx.startActivity(i);
                }
            });
        }
    }


}
