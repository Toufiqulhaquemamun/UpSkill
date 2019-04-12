package com.elearningapp.tech5soft.upskill.Home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elearningapp.tech5soft.upskill.CourseDetails.CourseDetailsActivity;
import com.elearningapp.tech5soft.upskill.CourseDetails.CourseInfoActivity;
import com.elearningapp.tech5soft.upskill.CourseDetails.CourseInformationActivity;
import com.elearningapp.tech5soft.upskill.Models.CourseList;
import com.elearningapp.tech5soft.upskill.R;
import java.util.ArrayList;

public class HorizontalCourseAdapter extends RecyclerView.Adapter<HorizontalCourseAdapter.HorizontalViewHolder > {

    Context mcntxt;
    ArrayList<CourseList> arrayListCourse;

    public HorizontalCourseAdapter(Context mcntxt, ArrayList<CourseList> arrayList) {
        this.mcntxt = mcntxt;
        this.arrayListCourse = arrayList;
    }

    @NonNull
    @Override
    public HorizontalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_horizontal,parent,false);
        return new HorizontalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HorizontalViewHolder holder, int position) {

        holder.itemView.setTag(arrayListCourse.get(position));
        CourseList courseList = arrayListCourse.get(position);
        holder.txtViewTitle.setText(courseList.getCourseName());
        if(courseList.getCourseFlag()==1)
        {
            holder.txtEnrool.setVisibility(View.VISIBLE);
        }
        else {
            holder.txtEnrool.setVisibility(View.INVISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return arrayListCourse.size();
    }

    public class HorizontalViewHolder extends RecyclerView.ViewHolder {

        TextView txtViewTitle,txtEnrool;
        ImageView imageViewThumb;

        public HorizontalViewHolder(@NonNull View itemView) {
            super(itemView);
            txtViewTitle=itemView.findViewById(R.id.txt_titleHorizontal);
            txtEnrool=itemView.findViewById(R.id.txt_enrolled);
            imageViewThumb= itemView.findViewById(R.id.ivThumb);
            mcntxt=itemView.getContext();
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    CourseList courseList= (CourseList) view.getTag();
                     int courseFlag=courseList.getCourseFlag();
                     if(courseFlag==1)
                     {
                         Intent i = new Intent(mcntxt, CourseDetailsActivity.class);
                         i.putExtra("CourseName", courseList.getCourseName());
                         i.putExtra("CourseCode", courseList.getCourseCode());
                         i.putExtra("Wishflag", courseList.getWishFlag());
                         mcntxt.startActivity(i);

                     }
                     else {
                         Intent i = new Intent(mcntxt, CourseInfoActivity.class);
                         i.putExtra("CourseName",courseList.getCourseName());
                         i.putExtra("CourseCode",courseList.getCourseCode());
                         i.putExtra("Wishflag",courseList.getWishFlag());
                         i.putExtra("CourseFlag",courseList.getCourseFlag());
                         mcntxt.startActivity(i);
                        }

                }
            });
        }
    }
}
