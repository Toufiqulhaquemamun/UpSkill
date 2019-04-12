package com.elearningapp.tech5soft.upskill.CourseDetails;

import android.Manifest;
import android.app.NotificationManager;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Handler;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;


import com.elearningapp.tech5soft.upskill.Models.VideoModel;
import com.elearningapp.tech5soft.upskill.R;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.Player;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.source.ConcatenatingMediaSource;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.source.hls.HlsMediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSource;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class CourseDetailsActivity extends AppCompatActivity implements CourseVideoListRecyclerAdapter.onItemClickListener   {


    private PlayerView playerView;
    private SimpleExoPlayer player;
    private DefaultTrackSelector trackSelector;
    private Handler mainHandler;
    RecyclerView recyclerView;
    CourseVideoListRecyclerAdapter adapter;
    private BandwidthMeter bandwidthMeter;
    public ArrayList<VideoModel> videoDetail ;
    ProgressBar progressBar;
    String coursecode,EmpId,OrgId;
    Timeline.Window window;
    private boolean playWhenReady = true;
    private int currentWindow;
    private long playbackPosition ;
    private NotificationManager notificationManager;
    private static final String TAG = CourseDetailsActivity.class.getSimpleName();
    int total_video;
    private TextView text_lecture;
    private TextView text_more;
    int positn;
    String base_url="http://27.147.169.230/UpSkillService/UpSkillsService.svc/";





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_details);
        getSupportActionBar().hide();
        coursecode=getIntent().getExtras().getString("CourseCode");
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        EmpId = prefs.getString("OrgEmpID","");
        OrgId=prefs.getString("UserOrgID","");
        text_lecture = findViewById(R.id.text_lectures);
        text_more = findViewById(R.id.text_more);
        progressBar=findViewById(R.id.proressvideo);
        playerView = findViewById(R.id.player_view_course);
        recyclerView=findViewById(R.id.courseVideoRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.setHasFixedSize(true);
        videoDetail =new ArrayList<>();
        progressBar.setVisibility(View.INVISIBLE);
        createPlayList();
        mainHandler=new Handler();
        bandwidthMeter = new DefaultBandwidthMeter();
        details();
    }

    public void details()
    {
        text_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent myIntent = new Intent(CourseDetailsActivity.this, MoreActivity.class);
                startActivity(myIntent);
            }
        });
    }
    public void createPlayList()
    {
        progressBar.setVisibility(View.VISIBLE);

        String url=base_url+"Get_CNCContentByCTid/"+coursecode+"/"+OrgId+"/"+EmpId;
        Log.d("Url",url);
        Ion.with(getApplicationContext())
                .load("GET",url)
                .setBodyParameter("CourseCode","")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Log.d("Result",result);
                        try {
                            JSONObject obj =  new JSONObject(result);
                            JSONArray array= obj.getJSONArray("GetCNCContentByCTidResult");
                            if(obj.isNull("GetCNCContentByCTidResult"))
                            {
                                Toast.makeText(getApplicationContext(),"No Course Found",Toast.LENGTH_SHORT).show();
                            }
                            else if (!obj.equals(null)){
                                videoDetail.clear();
                                for(int i=0;i<array.length();i++)
                                {

                                    VideoModel model =new VideoModel();
                                    JSONObject videos = array.getJSONObject(i);
                                    model.setUrl("https://jahinportfolio.000webhostapp.com/img/Accounting during the Year.mp4");
                                    model.setUrl("https://jahinportfolio.000webhostapp.com/img/introduction to accounting.mp4");
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
                                    videoDetail.add(model);
                                    }
                            adapter = new CourseVideoListRecyclerAdapter(CourseDetailsActivity.this,videoDetail);
                            recyclerView.setAdapter(adapter);
                            adapter.setClickListener(CourseDetailsActivity.this);

                            }
                            adapter.notifyDataSetChanged();
                            progressBar.setVisibility(View.INVISIBLE);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });

    }

    private MediaSource builMediaSource(Uri uri)
    {
        DefaultDataSourceFactory datasourcefactory= new DefaultDataSourceFactory(this,Util.getUserAgent(this,"UpSkill"));
        ExtractorMediaSource mediaSource =new ExtractorMediaSource.Factory(datasourcefactory).createMediaSource(uri);
        return  mediaSource;
    }



//    private void previous() {
//        Timeline timeline = player.getCurrentTimeline();
//        if (timeline.isEmpty() || player.isPlayingAd()) {
//            return;
//        }
//        int windowIndex = player.getCurrentWindowIndex();
//        timeline.getWindow(windowIndex, window);
//        int previousWindowIndex = player.getPreviousWindowIndex();
//        if (previousWindowIndex != C.INDEX_UNSET
//                && (player.getCurrentPosition() <= MAX_POSITION_FOR_SEEK_TO_PREVIOUS
//                || (window.isDynamic && !window.isSeekable))) {
//            player.seekTo(previousWindowIndex, C.TIME_UNSET);
//        } else {
//            player.seekTo(0);
//        }
//    }

    public void intPlayer()
    {
        playerView.requestFocus();
        TrackSelection.Factory videoTrackselection = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackselection);
        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getApplicationContext()), trackSelector, new DefaultLoadControl());
        playerView.setPlayer(player);

    }
    @Override
    protected void onStart() {
        super.onStart();
        if(player==null)
        {
            intPlayer();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        intPlayer();

    }


    private void releasePlayer()
    {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.setPlayWhenReady(true);
            player.release();
            player = null;
        }
    }
    @Override
    protected void onPause() {
        super.onPause();

        releasePlayer();

    }

    @Override
    protected void onStop() {
        super.onStop();
        if(Util.SDK_INT>23)
        {
            releasePlayer();
        }
        //playbackPosition=player.getCurrentPosition();

    }


    @Override
    public void onItemClick(View view, int position) {

        VideoModel videoModel = (VideoModel) view.getTag();
        final int lectID =videoModel.getLectureID();
        final int cmplflag=videoModel.getCompleteflag();
        releasePlayer();
        playerView.requestFocus();
        TrackSelection.Factory videoTrackselection = new AdaptiveTrackSelection.Factory(bandwidthMeter);
        trackSelector = new DefaultTrackSelector(videoTrackselection);
        player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getApplicationContext()), trackSelector, new DefaultLoadControl());
        playerView.setPlayer(player);
        Uri uri= Uri.parse(videoDetail.get(position).getUrl());
        MediaSource mediaSource= builMediaSource(uri);
        player.prepare(mediaSource);
        player.setPlayWhenReady(true);

        player.addListener(new Player.EventListener() {
            @Override
            public void onTimelineChanged(Timeline timeline, Object manifest, int reason) {
                //Toast.makeText(getApplicationContext(),"TimeLine Called",Toast.LENGTH_SHORT).show();
                player.seekTo(currentWindow,playbackPosition);
            }

            @Override
            public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
                //Toast.makeText(getApplicationContext(),"Track Called",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onLoadingChanged(boolean isLoading) {

            }

            @Override
            public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
                switch(playbackState) {
                    case Player.STATE_BUFFERING:
                        progressBar.setVisibility(View.VISIBLE);
                        break;
                    case Player.STATE_ENDED:
                       if(cmplflag==1)
                        Toast.makeText(getApplicationContext(),"Already completed",Toast.LENGTH_SHORT).show();
                       else{
                           Toast.makeText(getApplicationContext(),"End",Toast.LENGTH_SHORT).show();
                           getCompleteLec(lectID);
                       }
                        break;
                    case Player.STATE_IDLE:
                        progressBar.setVisibility(View.INVISIBLE);
                        //Toast.makeText(getApplicationContext(),"State Idle",Toast.LENGTH_SHORT).show();
                        break;
                    case Player.STATE_READY:
                        progressBar.setVisibility(View.INVISIBLE);
                        break;
                    default:
                        break;
                }

            }

            @Override
            public void onRepeatModeChanged(int repeatMode) {

            }

            @Override
            public void onShuffleModeEnabledChanged(boolean shuffleModeEnabled) {

            }

            @Override
            public void onPlayerError(ExoPlaybackException error) {

            }

            @Override
            public void onPositionDiscontinuity(int reason) {

            }

            @Override
            public void onPlaybackParametersChanged(PlaybackParameters playbackParameters) {

            }

            @Override
            public void onSeekProcessed() {

            }
        });


//        player.seekTo(currentWindow, C.TIME_UNSET);
//        player.prepare(mediaSource, true, false);
    }

    public void getCompleteLec(final int lecID)
    {
        String checkUrl=base_url+"GetCompleteLectureCoursecount/"+lecID+"/"+coursecode+"/"+EmpId;
        Ion.with(getApplicationContext())
                .load("GET",checkUrl)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Log.d("Checkurl",result);

                        try {
                            JSONObject obj= new JSONObject(result);
//
//                            if(obj.isNull("checkcurrentwishlistResult"))
//                            {
//                                addCompltCourse(lecID);
//                                Log.d("if block","compiled");
//                            }
//                            else if (!obj.equals(null))
//                            {
//                                String rslt=obj.getString("checkcurrentwishlistResult");
//                                JSONObject dataobj = new JSONObject(rslt);
//                                Log.d("new",dataobj.toString());
//                                String crnt_Coursecode = dataobj.getString("CourseCode");
//                                String crnt_CorseName = dataobj.getString("CourseName");
//                                String crnt_flag=dataobj.getString("WishFlag");
//
//                                if (crnt_CorseName.equals(output) && crnt_Coursecode.equals(courseCode) &&crnt_flag.equals("1")) {
//                                    Toast.makeText(getApplicationContext(), "Already added", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    changeCompleteFlag(lecID);
//                                }
//                            }

                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }
                    }
                });
    }

    public void changeCompleteFlag(int lecID)
    {
        String url=base_url+"Update_CompleteLectureCourse/"+coursecode+"/"+"1"+"/"+lecID+"/"+EmpId;

        Ion.with(getApplicationContext())
                .load(url)
                .setBodyParameter("","")
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        Log.d("Flag",result);
                    }
                });

    }
    public void addCompltCourse(int lecID)
    {}


}