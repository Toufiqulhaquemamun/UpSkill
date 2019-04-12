package com.elearningapp.tech5soft.upskill.Profile;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.elearningapp.tech5soft.upskill.R;
import org.json.JSONArray;
import org.json.JSONException;
import java.util.List;

public class ProfileRecyclerAdapter extends RecyclerView.Adapter<ProfileRecyclerAdapter.ViewHolder> {

    private Context mcntx;
    private List<GetUserInfoByIDResult> userdata;

    public ProfileRecyclerAdapter(Context mcntx, List<GetUserInfoByIDResult> userdata) {
        this.mcntx = mcntx;
        this.userdata = userdata;
    }

    @NonNull
    @Override
    public ProfileRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater= LayoutInflater.from(mcntx);
        View view = inflater.inflate(R.layout.layout_center_profile,null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProfileRecyclerAdapter.ViewHolder holder, int position) {

        holder.itemView.setTag(userdata.get(position));
        GetUserInfoByIDResult info = userdata.get(position);
        holder.userName.setText(info.getUserName());
        JSONArray array= (JSONArray) info.getUserProfilePic();
        Log.d("imageArray",array.toString());
        byte[] imagarr= new byte[array.length()];
        for(int i=0;i<array.length();i++)
        {
            try {
                imagarr[i] = (byte) array.getInt(i);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        Bitmap bmp = BitmapFactory.decodeByteArray(imagarr,0,imagarr.length);
        Glide.with(mcntx)
                .load(bmp)
                .into(holder.profileImage);
//        holder.profileImage.setImageBitmap(Bitmap.createScaledBitmap(bmp,200,200,false));
    }

    @Override
    public int getItemCount() {
        return userdata.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder
    {

        TextView userName;
        ImageView profileImage;
        public ViewHolder(View itemView) {
            super(itemView);

            userName=itemView.findViewById(R.id.profile_user_name_txt);
            //profileImage=itemView.findViewById(R.id.profile_image);
        }
    }
}
