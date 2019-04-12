package com.elearningapp.tech5soft.upskill.Home;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elearningapp.tech5soft.upskill.Models.CourseCatagory;
import com.elearningapp.tech5soft.upskill.Models.CourseList;
import com.elearningapp.tech5soft.upskill.R;

import java.util.ArrayList;


public class VerticalCatagoryAdapter extends RecyclerView.Adapter<VerticalCatagoryAdapter.VerticalViewHolder> {

    private static final String TAG = "VerticalCatagoryAdapter";
    Context context;
    ArrayList<CourseCatagory> arrayList;

    public VerticalCatagoryAdapter(Context context, ArrayList<CourseCatagory> arrayList) {
        this.context = context;
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public VerticalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view =LayoutInflater.from(parent.getContext()).inflate(R.layout.item_vertical,parent,false);
        return new VerticalViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VerticalViewHolder holder, int position) {

        CourseCatagory catagoryModel= arrayList.get(position);
        String title= catagoryModel.getCategoryName();
        ArrayList<CourseList> singleItem= catagoryModel.getCourseList();
        holder.txtViewTitle.setText(title);
        HorizontalCourseAdapter horizontalcourseAdapter = new HorizontalCourseAdapter(context,singleItem);

        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.HORIZONTAL,false));
        holder.recyclerView.setAdapter(horizontalcourseAdapter);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Toast.makeText(context,catagoryModel.getCategoryName(),Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }


    public class VerticalViewHolder extends RecyclerView.ViewHolder {

        RecyclerView recyclerView;
        TextView txtViewTitle;
        Button btnMore;
        public VerticalViewHolder(@NonNull View itemView) {
            super(itemView);

            recyclerView = itemView.findViewById(R.id.recyclerView_item);
            //btnMore=itemView.findViewById(R.id.btnMore);
            txtViewTitle=itemView.findViewById(R.id.txtTitle);
        }

    }

}
