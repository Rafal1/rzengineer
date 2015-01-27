package com.elektryczny.rzengineer.android.video;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.elektryczny.rzengineer.android.FileEnum;
import com.elektryczny.rzengineer.android.MultimediaFileManager;
import com.elektryczny.rzengineer.android.R;

import java.io.File;

public class VideoActivity extends Activity {
    private static final int CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE = 200;
    public static Uri fileUri;
    public static final int MEDIA_TYPE_VIDEO = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);
        TextView videoDesc = (TextView) findViewById(R.id.videoDesc);
        videoDesc.setText("W celu przedstawienia sytuacji, transmisji informacji " +
                "w dzisiajeszych czasach wykorzystuje się sekwencje video. System Android pozwala" +
                "na ich wykorzystywanie.");
        TextView videoDesc2 = (TextView) findViewById(R.id.videoDesc2);
        videoDesc2.setText("W celu oznaczenia istotnych elementów na film video można nanieść warstwę, na której możliwe jest pisanie. " +
                "Maksymlany czas nagrania wynosi 10 sekund.");

        Button cuptureVideoB = (Button) findViewById(R.id.captureVideoButton);
        cuptureVideoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
                fileUri = getOutputMediaFileUri(MEDIA_TYPE_VIDEO);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);
                intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                intent.putExtra(MediaStore.EXTRA_DURATION_LIMIT, 10);
                startActivityForResult(intent, CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE);
            }
        });

        Button playVideoB = (Button) findViewById(R.id.playVideoButton);
        playVideoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vi = new Intent(getBaseContext(), PlayVideoActivity.class);
                startActivity(vi);
            }
        });

        Button modVideoB = (Button) findViewById(R.id.modifyingVideoButton);
        modVideoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vi = new Intent(getBaseContext(), ModyfingVideoActivity.class);
                startActivity(vi);
            }
        });

    }

    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        File mediaFile;
        if (type == MEDIA_TYPE_VIDEO) {
            mediaFile = new File(MultimediaFileManager.getPathToFile(FileEnum.VIDEO_FILE));
        } else {
            return null;
        }
        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_VIDEO_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Zapisano klip video", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Operacja anulowana", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Błąd podczas nagrywania klipu video", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.video, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
