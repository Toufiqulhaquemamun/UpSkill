package com.elearningapp.tech5soft.upskill.Utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.util.Log;
import android.view.MenuItem;

import com.elearningapp.tech5soft.upskill.DashBoard.DashBoardActivity;
import com.elearningapp.tech5soft.upskill.Home.HomeActivity;
import com.elearningapp.tech5soft.upskill.Profile.ProfileActivity;
import com.elearningapp.tech5soft.upskill.R;
import com.elearningapp.tech5soft.upskill.Search.SearchActivity;
import com.elearningapp.tech5soft.upskill.WishList.WishListActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;


/*

As BottomNavigationView will be used many activities we will use the package to organize staffs
 */

public class BottomNavigationViewHelper {

    private static final String TAG = "BottomNavigationViewHel";
    
    public static void setupBottomNavigationView(BottomNavigationViewEx bottomNavigationViewEx)
    {
        Log.d(TAG, "setupBottomNavigationView: Setting up Bottom Navigation view");
        bottomNavigationViewEx.enableAnimation(false);
        bottomNavigationViewEx.enableItemShiftingMode(false);
        bottomNavigationViewEx.enableShiftingMode(false);
        bottomNavigationViewEx.setTextVisibility(true);
    }

    public static void enableNavigation(final Context context, BottomNavigationViewEx view) //Context was declared final to use it in override method
    {
        view.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                switch (item.getItemId()){

                    case R.id.menu_home:
                        Intent intent_Home = new Intent(context, HomeActivity.class);
                        context.startActivity(intent_Home);
                        ((Activity)context).finish();
                        break;


                    case R.id.menu_search:
                        Intent intent_Search = new Intent(context, SearchActivity.class);
                        context.startActivity(intent_Search);
                        ((Activity)context).finish();
                        break;

                    case R.id.menu_dashboard:
                        Intent intent_Dashboard = new Intent(context, DashBoardActivity.class);
                        context.startActivity(intent_Dashboard);
                        ((Activity)context).finish();
                        break;

                    case R.id.menu_wish_list:
                        Intent intent_Wishlist = new Intent(context, WishListActivity.class);
                        context.startActivity(intent_Wishlist);
                        ((Activity)context).finish();
                        break;

                    case R.id.menu_profile:
                        Intent intent_Profile = new Intent(context, ProfileActivity.class);
                        context.startActivity(intent_Profile);
                        ((Activity)context).finish();
                        break;
                }

                return false;
            }
        });
    }

}
