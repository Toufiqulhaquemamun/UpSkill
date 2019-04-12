package com.elearningapp.tech5soft.upskill.CourseDetails;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.elearningapp.tech5soft.upskill.Models.VideoModel;
import com.elearningapp.tech5soft.upskill.R;

import java.util.ArrayList;
import java.util.HashMap;

public class CourseVideoListRecyclerAdapter extends RecyclerView.Adapter<CourseVideoListRecyclerAdapter.MyViewHolder>  {


    private static final String TAG = "CourseVideoListRecycler";
    private Context mcntx;
    private ArrayList<VideoModel> video;
    private onItemClickListener mListener;

    public CourseVideoListRecyclerAdapter(Context mcntx, ArrayList<VideoModel> video) {
        this.mcntx = mcntx;
        this.video = video;


    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_course_video_list,parent,false);
        MyViewHolder myViewHolder = new MyViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull  MyViewHolder holder, int position) {
        holder.itemView.setTag(video.get(position));
         VideoModel mode = video.get(position);
         //int flag= mode.getCompleteflag();
        holder.text_video_file_name.setText(mode.getLectureTitle());
        holder.text_video_index.setText(String.valueOf(position+1));
        if(mode.getCompleteflag()==1)
        {
            holder.text_video_file_duration.setVisibility(View.VISIBLE);
        }
        else {holder.text_video_file_duration.setVisibility(View.INVISIBLE);}


    }

    @Override
    public int getItemCount() {
        return video.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder
    {
        TextView text_video_index;
        TextView text_video_file_name;
        TextView text_video_file_duration;
        ProgressBar progressBar;

        public MyViewHolder(View itemView)
        {
            super(itemView);
            text_video_index = itemView.findViewById(R.id.text_video_file_index);
            text_video_file_name = itemView.findViewById(R.id.text_video_file_name);
            text_video_file_duration = itemView.findViewById(R.id.text_video_file_duration);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(mListener !=null){
                        int position = getAdapterPosition();
                        if(position !=RecyclerView.NO_POSITION)
                        {
                            mListener.onItemClick(v,position);
                        }
                    }
                }
            });
        }
    }
    void setClickListener(onItemClickListener itemClickListener) {
        this.mListener = itemClickListener;
    }

    public interface onItemClickListener {

        void onItemClick(View view, int position);
    }


}
