package com.elearningapp.tech5soft.upskill.Search;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.EditText;
import android.widget.TextView;

import com.elearningapp.tech5soft.upskill.R;
import com.elearningapp.tech5soft.upskill.Utils.BottomNavigationViewHelper;
import com.elearningapp.tech5soft.upskill.WishList.WishListActivity;
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private static final String TAG = "SearchActivity";
    private static final int ACTIVITY_NUM = 1;
    private Context mCOntext = SearchActivity.this;
    RecyclerAdapterSearch adapter;


    private SearchView searchView;
   // private Toolbar toolbar;

    List<Courses> courseList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().setTitle("Search");
        Log.d(TAG, "onCreate: starting. SearchActivity");



//        toolbar = findViewById(R.id.profileToolbar);
//        setSupportActionBar(toolbar);
//        if (getSupportActionBar() != null) {
//            getSupportActionBar().setTitle("Main Page");
//        }
//        toolbar.setSubtitle("Test Subtitle");
//        toolbar.inflateMenu(R.menu.searchfile);

        initImageBitmaps();
        setupBottomNavigationView();
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


    private void initImageBitmaps(){
        Log.d(TAG, "initImageBitmaps: preparing bitmaps.");


        courseList.add(new Courses("Mobile Application Development", R.drawable.a));
        courseList.add(new Courses("Learn Python ",R.drawable.b));
        courseList.add(new Courses("Learn C# ",R.drawable.c));
        courseList.add(new Courses("Master Java",R.drawable.d));
        courseList.add(new Courses("Become Marketing Expert",R.drawable.e));
        courseList.add(new Courses("Improve Soft Skills",R.drawable.f));




        initRecyclerView();

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchfile, menu);
        final  MenuItem myActionMenuItem = menu.findItem(R.id.search_filter);
        searchView =(SearchView) myActionMenuItem.getActionView();
        changeSearchViewTextColor(searchView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ((EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text)).setTextColor(getColor(R.color.colorWhite));
        }
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                if(!searchView.isIconified())
                {
                    searchView.setIconified(true);
                }
                myActionMenuItem.collapseActionView();
                return  false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                final List<Courses> filterModeList =    filter(courseList,newText);
                adapter.setFilter(filterModeList);
                return true;
            }
        });


        return true;
    }

    private void initRecyclerView(){
        Log.d(TAG, "initRecyclerView: init recyclerview.");
        RecyclerView recyclerView = findViewById(R.id.recyclerView_search);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new RecyclerAdapterSearch(this, courseList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        Context context = recyclerView.getContext();
        LayoutAnimationController controller =null;
        controller = AnimationUtils.loadLayoutAnimation(context,R.anim.layout_from_side);

        recyclerView.setLayoutAnimation(controller);
        recyclerView.getAdapter().notifyDataSetChanged();
        recyclerView.scheduleLayoutAnimation();
    }

    private List<Courses>  filter(List<Courses> course, String query)
    {
        query= query.toLowerCase();
        final List<Courses> filterModeList = new ArrayList<>();
        for (Courses model:course)
        {
            final String text = model.getCourseName().toLowerCase();
            if(text.startsWith(query))
            {
                filterModeList.add(model);
            }
        }
        return  filterModeList;
    }


    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {

        super.onStop();
    }

    @Override
    public void onResume() {
        super.onResume();
    }


    @Override
    public void onPause() {
        super.onPause();
    }


    //for changing the text color of searchview
    private void changeSearchViewTextColor(View view) {
        if (view != null) {
            if (view instanceof TextView) {
                ((TextView) view).setTextColor(Color.WHITE);
                return;
            } else if (view instanceof ViewGroup) {
                ViewGroup viewGroup = (ViewGroup) view;
                for (int i = 0; i < viewGroup.getChildCount(); i++) {
                    changeSearchViewTextColor(viewGroup.getChildAt(i));
                }
            }
        }
    }

    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setTitle("Exit")
                .setMessage("Are you sure you want to exit?")
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        SearchActivity.this.finish();
                    }
                })
                .setNegativeButton("No", null)
                .show();
    }
}
