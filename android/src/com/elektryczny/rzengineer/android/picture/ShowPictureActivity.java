package com.elektryczny.rzengineer.android.picture;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.elektryczny.rzengineer.android.FileEnum;
import com.elektryczny.rzengineer.android.MultimediaFileManager;
import com.elektryczny.rzengineer.android.R;

import java.io.File;

public class ShowPictureActivity extends Activity {
    private ImageView drawView;
    private ImageView normalView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_picture);

        normalView = (ImageView) findViewById(R.id.picture_activity_image_to_show);
        File file = new File(MultimediaFileManager.getPathToFile(FileEnum.IMAGE_FILE));
        if (file.exists()) {
            Uri normalPictureImage = Uri.fromFile(file);
            normalView.setImageURI(normalPictureImage);
        }

        drawView = (ImageView) findViewById(R.id.picture_activity_image_to_show_layer);
        File lfile = new File(MultimediaFileManager.getPathToFile(FileEnum.IMAGE_LAYER_FILE));
        if (lfile.exists()) {
            Uri layerPictureImage = Uri.fromFile(lfile);
            drawView.setImageURI(layerPictureImage);
        }

        final Button backVideoB = (Button) findViewById(R.id.pictureShowBackButton);
        backVideoB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

}
