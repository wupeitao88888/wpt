package com.iloomo.photoselector;

import android.content.Context;
import android.content.ContextWrapper;
import android.graphics.Color;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.view.View;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.VideoView;

import com.iloomo.global.IloomoConfig;
import com.iloomo.paysdk.R;
import com.iloomo.rxbus.BusConfig;
import com.iloomo.rxbus.RxBus;


import java.io.File;

public class PictureVideoPlayPreviewActivity extends PictureBaseActivity implements MediaPlayer.OnErrorListener, MediaPlayer.OnPreparedListener, MediaPlayer.OnCompletionListener, View.OnClickListener {
    private String video_path = "";
    private ImageView picture_left_back;
    private MediaController mMediaController;
    private VideoView mVideoView;
    private ImageView iv_play;
    private int mPositionWhenPaused = -1;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.picture_activity_video_play);
        setCtenterTitle("");
        setRightTitle("完成");
        setRightSecondTitle("编辑");
        setRightSecondTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                IloomoConfig.init(context).editVideo(PictureVideoPlayPreviewActivity.this, video_path);
                finish();
            }
        });

        setRightTitleListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString(IloomoConfig.VIDEO_PATH, video_path);
                Message message = new Message();
                message.what = BusConfig.SEND_VIDEO_INFO;
                message.obj = bundle;
                RxBus.getDefault().post(message);
                finish();
            }
        });

        video_path = getIntent().getStringExtra(IloomoConfig.VIDEO_PATH);


        picture_left_back = (ImageView) findViewById(R.id.picture_left_back);
        mVideoView = (VideoView) findViewById(R.id.video_view);
        mVideoView.setBackgroundColor(Color.BLACK);
        iv_play = (ImageView) findViewById(R.id.iv_play);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        mMediaController = new MediaController(this);
        mVideoView.setOnCompletionListener(this);
        mVideoView.setOnPreparedListener(this);
        mVideoView.setMediaController(mMediaController);
        picture_left_back.setOnClickListener(this);
        picture_left_back.setVisibility(View.GONE);
        iv_play.setOnClickListener(this);
    }


    public void onStart() {
        // Play Video
        File video = new File(video_path);
        if (video.exists()) {
            if (video_path.indexOf("http://") > -1 || video_path.indexOf("https://") > -1) {
                mVideoView.setVideoURI(Uri.parse(video_path));
                progressBar.setVisibility(View.VISIBLE);
            } else {
                mVideoView.setVideoPath(video_path);
                progressBar.setVisibility(View.GONE);
            }
            mVideoView.start();
        } else {
            showToastFiled( "路径不存在");
        }

        super.onStart();
    }

    public void onPause() {
        // Stop video when the activity is pause.
        mPositionWhenPaused = mVideoView.getCurrentPosition();
        mVideoView.stopPlayback();

        super.onPause();
    }

    @Override
    protected void onDestroy() {
        mMediaController = null;
        mVideoView = null;
        iv_play = null;
        super.onDestroy();
    }

    public void onResume() {
        // Resume video player
        if (mPositionWhenPaused >= 0) {
            mVideoView.seekTo(mPositionWhenPaused);
            mPositionWhenPaused = -1;
        }

        super.onResume();
    }

    @Override
    public boolean onError(MediaPlayer player, int arg1, int arg2) {
        mVideoView.stopPlayback(); //播放异常，则停止播放，防止弹窗使界面阻塞
        return true;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        if (null != iv_play) {
            iv_play.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        int id = v.getId();
        if (id == R.id.picture_left_back) {
            finish();
        } else if (id == R.id.iv_play) {
            mVideoView.start();
            iv_play.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(new ContextWrapper(newBase) {
            @Override
            public Object getSystemService(String name) {
                if (Context.AUDIO_SERVICE.equals(name))
                    return getApplicationContext().getSystemService(name);
                return super.getSystemService(name);
            }
        });
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.setOnInfoListener(new MediaPlayer.OnInfoListener() {
            @Override
            public boolean onInfo(MediaPlayer mp, int what, int extra) {
                if (what == MediaPlayer.MEDIA_INFO_VIDEO_RENDERING_START) {
                    // video started
                    mVideoView.setBackgroundColor(Color.TRANSPARENT);
                    return true;
                }
                return false;
            }
        });
    }
}
