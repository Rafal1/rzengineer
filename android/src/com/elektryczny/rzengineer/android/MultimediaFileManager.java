package com.elektryczny.rzengineer.android;

import android.os.Environment;
import android.util.Log;
import java.io.File;

/**
 * @author Rafał Zawadzki
 */

public class MultimediaFileManager {
    public static final String BASIC_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    public static final String MULTIMEDIA_DIRECTORY_NAME = "MultimediaBox" + File.separator;
    public static final String RESOURCES_DIRECTORY = BASIC_PATH + File.separator + MULTIMEDIA_DIRECTORY_NAME + File.separator;

    public static final String SOUND_FILE_NAME = "soundTrack.3gp";
    public static final String MESSAGES_FILE_NAME = "messagesCrypto.txt";
    public static final String IMAGE_FILE_NAME = "Photo.jpg";
    public static final String IMAGE_LAYER_FILE_NAME = "pictureLayer.png";
    public static final String VIDEO_FILE_NAME = "VID_COMM.mp4";
    public static final String VIDEO_LAYER_FILE_NAME = "videoLayer.png";
    public static final String MULTIMEDIA_SOLID_FILE_NAME = "X";

    public static String getPathToFile(FileEnum filename) {
        String path = BASIC_PATH + MULTIMEDIA_DIRECTORY_NAME;
        if (createDirIfNotExists(path)) {
            switch (filename) {
                case SOUND_FIlE:
                    path = path + SOUND_FILE_NAME;
                    break;
                case MESSAGES_FILE:
                    path = path + MESSAGES_FILE_NAME;
                    break;
                case IMAGE_FILE:
                    path = path + IMAGE_FILE_NAME;
                    break;
                case IMAGE_LAYER_FILE:
                    path = path + IMAGE_LAYER_FILE_NAME;
                    break;
                case VIDEO_FILE:
                    path = path + VIDEO_FILE_NAME;
                    break;
                case VIDEO_LAYER_FILE:
                    path = path + VIDEO_LAYER_FILE_NAME;
                    break;
                case MULTIMEDIA_SOLID:
                    path = path + MULTIMEDIA_SOLID_FILE_NAME;
                    break;
            }
            return path;
        } else {
            return null;
        }
    }

    public static boolean createDirIfNotExists(String path) {
        boolean ret = true;
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("Log :: ", "Problem z tworzeniem foldera");
                ret = false;
            }
        }
        return ret;
    }

    //todo open file, write to file

}
