package com.elearningapp.tech5soft.upskill.WishList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.elearningapp.tech5soft.upskill.CourseDetails.CourseInformationActivity;
import com.elearningapp.tech5soft.upskill.R;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

public class RecyclerViewAdapterWishlist extends RecyclerView.Adapter<RecyclerViewAdapterWishlist.ViewHolder> {

    private static final String TAG = "HorizontalCourseAdapter";
    private Context mcntxt;
    private ItemClickListener mClickListener;
    private List<WishlistCourseModel> wishlistCourse;
    private String CourseCOde,Corsename,userID,empID,orgID,appID;
    private Integer Wishflag;
    String BaseUrl="http://27.147.169.230/UpSkillService/UpSkillsService.svc/";

    public RecyclerViewAdapterWishlist(Context mcntxt, List<WishlistCourseModel> wishlistCourse) {
        this.mcntxt = mcntxt;
        this.wishlistCourse = wishlistCourse;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_course_lists, parent, false);
        RecyclerViewAdapterWishlist.ViewHolder viewHolder = new RecyclerViewAdapterWishlist.ViewHolder(view);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {
        holder.itemView.setTag(wishlistCourse.get(position));
        final WishlistCourseModel model = wishlistCourse.get(position);
        holder.circleImageView.setImageResource(R.drawable.market);
        String coursename=model.getCourseName();
        String newCourse=coursename.replaceAll("_"," ");
        holder.text_course_list_course_name.setText(newCourse);
        holder.image_fav.setImageResource(R.drawable.ic_favorite_black_24dp);
        holder.image_fav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CourseCOde=model.getCourseCode();
                Corsename=model.getCourseName();
                SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(mcntxt);
                userID = prefs.getString("UserID","");
                empID = prefs.getString("OrgEmpID","");
                orgID = prefs.getString("UserOrgID","");
                appID = prefs.getString("AppID","");


                String update_wish = BaseUrl+"Update_wishlist/"+CourseCOde+"/"+Corsename+"/"+0+"/"+empID+"/"+orgID;

                Ion.with(mcntxt)
                        .load("POST",update_wish)
                        .setBodyParameter("","")
                        .asString()
                        .setCallback(new FutureCallback<String>() {
                            @Override
                            public void onCompleted(Exception e, String result) {
                                Log.d("Result",result);
                                try {
                                    JSONObject obj =new JSONObject(result);
                                    String rslt=obj.getString("UpdateCourseWishListResult");
                                    Toast.makeText(mcntxt,"Course Removed",Toast.LENGTH_SHORT).show();

                                } catch (JSONException e1) {
                                    e1.printStackTrace();
                                }

                            }
                        });
            }
        });
    }



    @Override
    public int getItemCount() {
        return wishlistCourse.size();
    }


    //Holds the widget in memory for each individual entry
    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageView circleImageView;
        TextView text_course_list_course_name;
        //TextView text_course_list_course_teacher_name;
        RelativeLayout parent_layout_course_list;
        ImageView image_fav;

        public ViewHolder(View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.courseList_course_image);
            text_course_list_course_name = itemView.findViewById(R.id.courseList_course_name);
            image_fav = itemView.findViewById(R.id.image_fav_course);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    WishlistCourseModel model = (WishlistCourseModel)v.getTag();
//                    Intent i = new Intent(mcntxt, CourseInformationActivity.class);
//                    i.putExtra("CourseName",model.getCourseName().replaceAll("_"," "));
//                    i.putExtra("CourseCode",model.getCourseCode());
//                    i.putExtra("Wishflag",model.getWishFlag());
//                    mcntxt.startActivity(i);

                    if(mClickListener !=null){
                        int position = getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION)
                        {
                            mClickListener.onItemClick(v,position);
                        }
                    }


                }
            });
        }

    }
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

   public interface ItemClickListener {

        void onItemClick(View view, int position);
    }

}
