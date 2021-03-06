package com.elektryczny.rzengineer.android.video;

import android.app.Activity;
import android.graphics.PixelFormat;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.elektryczny.rzengineer.android.FileEnum;
import com.elektryczny.rzengineer.android.MultimediaFileManager;
import com.elektryczny.rzengineer.android.R;

import java.io.File;
import java.io.IOException;


public class PlayVideoActivity extends Activity implements SurfaceHolder.Callback {
    private final MediaPlayer mediaPlayer = new MediaPlayer();
    private SurfaceView surfaceView;
    private SurfaceHolder surfaceHolder;
    private boolean pausing = false;
    private ImageView drawView;
    private Boolean isStarted = false;
    private String STOP_VIDEO;
    private String RESUME_VIDEO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_video);

        getWindow().setFormat(PixelFormat.UNKNOWN);
        surfaceView = (SurfaceView) findViewById(R.id.surfaceVideoView);
        surfaceHolder = surfaceView.getHolder();
        surfaceHolder.addCallback(this);

        DisplayMetrics metrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metrics);

        surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);

        STOP_VIDEO = getResources().getString(R.string.pause_video);
        RESUME_VIDEO = getResources().getString(R.string.resume_video);

        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                isStarted = false;
            }
        });

        drawView = (ImageView) findViewById(R.id.image_to_play_video_layer);
        File file = new File(MultimediaFileManager.getPathToFile(FileEnum.VIDEO_LAYER_FILE));
        if (file.exists()) {
            Uri videoLayerImage = Uri.fromFile(file);
            drawView.setImageURI(videoLayerImage);
        }

        Button playVideoB = (Button) findViewById(R.id.realPlayVideoButton);
        playVideoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (pausing) {
                    Button pauseVideoB = (Button) findViewById(R.id.pauseVideoButton);
                    pauseVideoB.setText(STOP_VIDEO);
                }
                isStarted = true;
                pausing = false;
                mediaPlayer.reset();
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                mediaPlayer.setDisplay(surfaceHolder);

                Uri ourVideoClip;
                if (VideoActivity.fileUri == null) {
                    File mediaFile = new File(MultimediaFileManager.getPathToFile(FileEnum.VIDEO_FILE));
                    ourVideoClip = Uri.fromFile(mediaFile);
                } else {
                    ourVideoClip = VideoActivity.fileUri;
                }

                try {
                    mediaPlayer.setDataSource(getApplicationContext(), ourVideoClip);
                    mediaPlayer.prepare();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                mediaPlayer.start();
            }
        });

        final Button pauseVideoB = (Button) findViewById(R.id.pauseVideoButton);
        pauseVideoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isStarted) {
                    if (pausing) {
                        pausing = false;
                        mediaPlayer.start();
                        pauseVideoB.setText(STOP_VIDEO);
                    } else {
                        pausing = true;
                        mediaPlayer.pause();
                        pauseVideoB.setText(RESUME_VIDEO);
                    }
                }
            }
        });

        final Button backVideoB = (Button) findViewById(R.id.videoPlayBackButton);
        backVideoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public void surfaceChanged(SurfaceHolder arg0, int arg1, int arg2, int arg3) {

    }

    @Override
    public void surfaceCreated(SurfaceHolder arg0) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder arg0) {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return true;
    }

    @Override
    protected void onDestroy() {
        mediaPlayer.release();
        super.onDestroy();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
