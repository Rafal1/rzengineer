package com.elektryczny.rzengineer.android.sound;

import android.app.Activity;
import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.view.View;
import android.content.Context;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.elektryczny.rzengineer.android.FileEnum;
import com.elektryczny.rzengineer.android.MultimediaFileManager;
import com.elektryczny.rzengineer.android.R;

import java.io.IOException;

public class SoundCaptureActivity extends Activity {
    private static final String LOG_TAG = "AudioRecord";
    private static final Integer RECORDING_TIME = 10000; //miliseconds
    private static String mFileName = null;
    private static final String START_RECORDING_DESCRIPTION = "Rozpocznij nagrywanie";
    private static final String STOP_RECORDING_DESCRIPTION = "Zatrzymaj nagrywanie";
    private static final String START_PLAYING_DESCRIPTION = "Rozpocznij odtwarzanie";
    private static final String STOP_PLAYING_DESCRIPTION = "Zatrzymaj odtwarzanie";

    private RecordButton mRecordButton = null;
    private MediaRecorder mRecorder = null;

    private PlayButton mPlayButton = null;
    private MediaPlayer mPlayer = null;

    private Boolean paramNoPlay = false;

    private void onRecord(boolean start) {
        if (start) {
            startRecording();
        } else {
            stopRecording();
        }
    }

    private void onPlay(boolean start) {
        if (start) {
            startPlaying();
        } else {
            stopPlaying();
        }
    }

    private void startPlaying() {
        mPlayer = new MediaPlayer();
        mPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mp) {
                stopPlaying();
                mPlayButton.setText(START_PLAYING_DESCRIPTION);
                paramNoPlay = true;
            }
        });
        try {
            mPlayer.setDataSource(mFileName);
            mPlayer.prepare();
            mPlayer.start();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }
    }

    private void stopPlaying() {
        mPlayer.release();
        mPlayer = null;
    }

    private void startRecording() {
        mRecorder = new MediaRecorder();
        mRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
        mRecorder.setOutputFile(mFileName);
        mRecorder.setMaxDuration(RECORDING_TIME); //10sec
        mRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB);
        try {
            mRecorder.prepare();
        } catch (IOException e) {
            Log.e(LOG_TAG, "prepare() failed");
        }

        mRecorder.start();
    }

    private void stopRecording() {
        mRecorder.stop();
        mRecorder.release();
        mRecorder = null;
    }

    public class RecordButton extends Button {
        boolean mStartRecording = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                onRecord(mStartRecording);
                if (mStartRecording) {
                    setText(STOP_RECORDING_DESCRIPTION);
                    Handler handler = new Handler();
                    Runnable r = new Runnable() {
                        public void run() {
                            if (mRecorder != null) {
                                mRecordButton.setText(START_RECORDING_DESCRIPTION);
                                mRecorder.stop();
                                mRecorder.release();
                                mRecorder = null;
                                mStartRecording = !mStartRecording;
                            }
                        }
                    };
                    handler.postDelayed(r, RECORDING_TIME);
                } else {
                    setText(START_RECORDING_DESCRIPTION);
                }
                mStartRecording = !mStartRecording;
            }
        };

        public RecordButton(Context ctx) {
            super(ctx);
            setText(START_RECORDING_DESCRIPTION);
            setOnClickListener(clicker);
        }
    }

    public class PlayButton extends Button {
        boolean mStartPlaying = true;

        OnClickListener clicker = new OnClickListener() {
            public void onClick(View v) {
                if (paramNoPlay) {
                    paramNoPlay = !paramNoPlay;
                    mStartPlaying = !mStartPlaying;
                }
                onPlay(mStartPlaying);
                if (mStartPlaying) {
                    setText(STOP_PLAYING_DESCRIPTION);
                } else {
                    setText(START_PLAYING_DESCRIPTION);
                }
                mStartPlaying = !mStartPlaying;
            }
        };

        public PlayButton(Context ctx) {
            super(ctx);
            setText(START_PLAYING_DESCRIPTION);
            setOnClickListener(clicker);
        }
    }

    public SoundCaptureActivity() {
        mFileName = MultimediaFileManager.getPathToFile(FileEnum.SOUND_FIlE);
    }

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);
        setContentView(R.layout.activity_sound_capture);

        RelativeLayout ll = new RelativeLayout(this);
        ll.setPadding(16, 16, 16, 16);

        TextView title = new TextView(this);
        title.setText("Dźwięk");
        title.setId(new Integer(4));
        title.setTextAppearance(this, android.R.style.TextAppearance_Large);
        RelativeLayout.LayoutParams titleParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        titleParams.addRule(RelativeLayout.CENTER_HORIZONTAL, title.getId());
        ll.addView(title, titleParams);

        TextView desc = new TextView(this);
        desc.setText("Aplikacja pozwala na nagranie i późniejsze odtworzenie ścieżki dźwiękowej. " +
                "Dźwięk jest naturalnym sposobem komunikacji międzyludzkiej. " +
                "Gotowy do wysłania zapisywany jest na karcie pamięci w formacie .3gp. " +
                "Maksymalny czas nagrania wynosi 10 sekund. \n");
        desc.setId(new Integer(1));
        desc.setTextAppearance(this, android.R.style.TextAppearance_Medium);
        RelativeLayout.LayoutParams descParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        descParams.addRule(RelativeLayout.BELOW, title.getId());
        ll.addView(desc, descParams);

        mRecordButton = new RecordButton(this);
        mRecordButton.setId(new Integer(2));
        RelativeLayout.LayoutParams recParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        recParams.addRule(RelativeLayout.BELOW, desc.getId());
        ll.addView(mRecordButton, recParams);

        mPlayButton = new PlayButton(this);
        RelativeLayout.LayoutParams playParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        playParams.addRule(RelativeLayout.BELOW, mRecordButton.getId());
        playParams.setMargins(0, 10, 0, 0);

        mPlayButton.setId(new Integer(3));
        ll.addView(mPlayButton, playParams);

        setContentView(ll);
    }

    @Override
    public void onPause() {
        super.onPause();
        if (mRecorder != null) {
            mRecorder.release();
            mRecorder = null;
        }
        if (mPlayer != null) {
            mPlayer.release();
            mPlayer = null;
        }
    }
}
