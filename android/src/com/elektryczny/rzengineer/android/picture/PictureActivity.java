package com.elektryczny.rzengineer.android.picture;


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

public class PictureActivity extends Activity {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    public static Uri PictureFileUri;
    public static final int MEDIA_TYPE_IMAGE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);

        TextView pictureDesc = (TextView) findViewById(R.id.pictureDesc);
        pictureDesc.setText("Obrazki i zdjęcia znaczącą wzbogacają komunikacje za pomocą treści multimedialnych.");
        TextView videoDesc2 = (TextView) findViewById(R.id.pictureDesc2);
        videoDesc2.setText("Aplikacja potrafi wykonać zdjęcie, nanieść na nie oznaczenia pędzelkiem i poprawić jego cechy za pomocą filtrów.");

        Button cuptureVideoB = (Button) findViewById(R.id.capturePictureButton);
        cuptureVideoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                PictureFileUri = getOutputMediaFileUri(MEDIA_TYPE_IMAGE); // create a file to save the image
                intent.putExtra(MediaStore.EXTRA_OUTPUT, PictureFileUri); // set the image file name
                startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
            }
        });

        Button playVideoB = (Button) findViewById(R.id.showPictureButton);
        playVideoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vi = new Intent(getBaseContext(), ShowPictureActivity.class);
                startActivity(vi);
            }
        });

        Button modVideoB = (Button) findViewById(R.id.modifyingPictureButton);
        modVideoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vi = new Intent(getBaseContext(), ModyfingPictureActivity.class);
                startActivity(vi);
            }
        });

    }

    private static Uri getOutputMediaFileUri(int type) {
        return Uri.fromFile(getOutputMediaFile(type));
    }

    private static File getOutputMediaFile(int type) {
        File mediaFile;
        if (type == MEDIA_TYPE_IMAGE) {
            mediaFile = new File(MultimediaFileManager.getPathToFile(FileEnum.IMAGE_FILE));
        } else {
            return null;
        }
        return mediaFile;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Toast.makeText(this, "Zapisano obrazek", Toast.LENGTH_LONG).show();
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Operacja anulowana", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Błąd podczas wykonywania zdjęcia", Toast.LENGTH_LONG).show();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.video, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
