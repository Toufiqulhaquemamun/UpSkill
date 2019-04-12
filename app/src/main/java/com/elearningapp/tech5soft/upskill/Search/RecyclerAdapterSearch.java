package com.elearningapp.tech5soft.upskill.Search;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elearningapp.tech5soft.upskill.R;

import java.util.ArrayList;
import java.util.List;

public class RecyclerAdapterSearch extends RecyclerView.Adapter<RecyclerAdapterSearch.ViewHolder> {

    private static final String TAG = "HorizontalCourseAdapter";
    private Context context;

    private List<Courses> coursesList;

    public RecyclerAdapterSearch(Context context, List<Courses> coursesList) {
        this.context = context;
        this.coursesList = coursesList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_course_lists, parent, false);
        RecyclerAdapterSearch.ViewHolder viewHolder = new RecyclerAdapterSearch.ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        holder.circleImageView.setImageResource(coursesList.get(position).getCoursePhoto());
        holder.text_course_list_course_name.setText(coursesList.get(position).getCourseName());
    }


    @Override
    public int getItemCount() {
        return coursesList.size();
    }

    public void setFilter(List<Courses> courseslist)
    {
        coursesList = new ArrayList<>();
        coursesList.addAll(courseslist);
        notifyDataSetChanged();
    }

    //Holds the widget in memory for each individual entry
    public class ViewHolder extends RecyclerView.ViewHolder{

        ImageView circleImageView;
        TextView text_course_list_course_name;
        //TextView text_course_list_course_teacher_name;
        RelativeLayout parent_layout_course_list;
        ImageView image_fav;

        public ViewHolder(View itemView) {
            super(itemView);

            circleImageView = itemView.findViewById(R.id.courseList_course_image);
            text_course_list_course_name = itemView.findViewById(R.id.courseList_course_name);
            //text_course_list_course_teacher_name = itemView.findViewById(R.id.courseList_course_teacher_name);
            parent_layout_course_list = itemView.findViewById(R.id.course_list_wishList_layout);
            image_fav = itemView.findViewById(R.id.image_fav_course);
        }
    }
}
