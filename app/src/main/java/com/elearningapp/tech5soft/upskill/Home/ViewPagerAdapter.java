package com.elearningapp.tech5soft.upskill.Home;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.elearningapp.tech5soft.upskill.CourseDetails.CourseDetailsActivity;
import com.elearningapp.tech5soft.upskill.R;

public class ViewPagerAdapter  extends PagerAdapter {

    private Context context;
    private LayoutInflater layoutInflater;
    private Integer[] imageSources = {R.drawable.material, R.drawable.strategy, R.drawable.market,R.drawable.softskill,R.drawable.web};
    private String[] names = {"Material Design for Android Developers","Business Strategy for Making More Profit", "Marketing Plans to Follow","Soft skill for Businessman","Web Development"};
    private String[] price = {"Roman Nurik","Alex Hunter","AMore Murrfy","Tim Gasner","Nick Forbes"};

    public ViewPagerAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return imageSources.length;
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == object;
    }


    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, final int position) {

        layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        final View view = layoutInflater.inflate(R.layout.layout_custom_imageslider_home, null);
        ImageView imageView = view.findViewById(R.id.imageView);
        imageView.setImageResource(imageSources[position]);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(position == 0)
                {
                    Toast.makeText(context, names[position]+" selected", Toast.LENGTH_SHORT).show();
                    Intent myIntent = new Intent(view.getContext(), CourseDetailsActivity.class);
                    myIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK| Intent.FLAG_ACTIVITY_MULTIPLE_TASK);
                    view.getContext().startActivity(myIntent);


                }
                else if (position == 1)
                {
                    Toast.makeText(context, names[position]+" selected", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(context, names[position]+" selected", Toast.LENGTH_SHORT).show();
                }
            }
        });

        TextView textView_name = view.findViewById(R.id.textView_name);
        textView_name.setText(names[position]);
        TextView textView_price = view.findViewById(R.id.textView_price);
        textView_price.setText(price[position]);



        ViewPager viewPager = (ViewPager) container;
        viewPager.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager viewPager = (ViewPager) container;
        View view = (View) object;
        viewPager.removeView(view);

    }
}
