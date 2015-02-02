package com.elektryczny.rzengineer.android.multimediasolid;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.elektryczny.rzengineer.android.MultimediaFileManager;

import java.io.File;

public class MultimediaSolidActivity extends AndroidApplication {
    private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
    private static final Integer SOLID_WALLS = 6;
    private static Uri PictureFileUri;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Integer imagesFiles = new File(MultimediaFileManager.getResourcesDirectory() + MultimediaFileManager.getSolidImagesDirectoryName()).listFiles().length;
        for (Integer i = imagesFiles + 1; i <= SOLID_WALLS; i++) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            PictureFileUri = Uri.fromFile(new File(MultimediaFileManager.getResourcesDirectory() + MultimediaFileManager.getSolidImagesDirectoryName() + i + ".jpg"));
            intent.putExtra(MediaStore.EXTRA_OUTPUT, PictureFileUri);
            startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
        }

        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new Basic3D(), config);
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        this.exit();
        super.onDestroy();
    }
}
