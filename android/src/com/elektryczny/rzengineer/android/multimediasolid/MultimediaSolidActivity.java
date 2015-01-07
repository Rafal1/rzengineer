package com.elektryczny.rzengineer.android.multimediasolid;

import android.app.Activity;
import android.os.Bundle;

import com.badlogic.gdx.backends.android.AndroidApplication;
import com.badlogic.gdx.backends.android.AndroidApplicationConfiguration;
import com.elektryczny.rzengineer.android.MultimediaFileManager;
import com.elektryczny.rzengineer.android.R;

import java.io.File;
// OpenGL ES -> extends Activity {
public class MultimediaSolidActivity extends AndroidApplication { //libGDX

//    private MultimediaSolidView view;
//    private Integer imagesQunatity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//Run libGDX application
        AndroidApplicationConfiguration config = new AndroidApplicationConfiguration();
        initialize(new Basic3D(), config);

//        Normal Activity
//        setContentView(R.layout.activity_multimedia_solid);

//        view = new MultimediaSolidView(this);
//        setContentView(view);

        //create folder with images
//        String path = MultimediaFileManager.RESOURCES_DIRECTORY + MultimediaFileManager.SOLID_IMAGES_DIRECTORY_NAME;
//        MultimediaFileManager.createDirIfNotExists(path);

        //behaviour depending on the images quantity
//        File dir = new File(path);
//        if (dir.isDirectory()) {
//            String[] files = dir.list();
//            imagesQunatity = files.length;
//        }

//        if (imagesQunatity.equals(0)) {
//            todo establish default picture
//        }
    }

    @Override
    protected void onPause() {
        super.onPause();
//        view.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
//        view.onResume();
    }

}
