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
    public static final String SOLID_IMAGES_DIRECTORY_NAME = "imagesForSolid" + File.separator;
    public static final String RESOURCES_DIRECTORY = BASIC_PATH + File.separator + MULTIMEDIA_DIRECTORY_NAME + File.separator;

    public static final String SOUND_FILE_NAME = "soundTrack.3gp";
    public static final String MESSAGES_FILE_NAME = "messagesCrypto.txt";
    public static final String IMAGE_FILE_NAME = "Photo.jpg";
    public static final String IMAGE_LAYER_FILE_NAME = "pictureLayer.png";
    public static final String VIDEO_FILE_NAME = "VID_COMM.mp4";
    public static final String VIDEO_LAYER_FILE_NAME = "videoLayer.png";
    public static final String MULTIMEDIA_SOLID_FILE_NAME = "X";

    public static final String MULTIMEDIA_SOLID_WALL1_NAME = "1.jpg";
    public static final String MULTIMEDIA_SOLID_WALL2_NAME = "2.jpg";
    public static final String MULTIMEDIA_SOLID_WALL3_NAME = "3.jpg";
    public static final String MULTIMEDIA_SOLID_WALL4_NAME = "4.jpg";
    public static final String MULTIMEDIA_SOLID_WALL5_NAME = "5.jpg";
    public static final String MULTIMEDIA_SOLID_WALL6_NAME = "6.jpg";

    public static String getPathToFile(FileEnum filename) {
        String path = BASIC_PATH + MULTIMEDIA_DIRECTORY_NAME;
        if (!createDirIfNotExists(path)) {
            return null;
        }
        switch (filename) {
            case SOUND_FIlE:
                return path + SOUND_FILE_NAME;
            case MESSAGES_FILE:
                return path + MESSAGES_FILE_NAME;
            case IMAGE_FILE:
                return path + IMAGE_FILE_NAME;
            case IMAGE_LAYER_FILE:
                return path + IMAGE_LAYER_FILE_NAME;
            case VIDEO_FILE:
                return path + VIDEO_FILE_NAME;
            case VIDEO_LAYER_FILE:
                return path + VIDEO_LAYER_FILE_NAME;
            case MULTIMEDIA_SOLID:
                return path + MULTIMEDIA_SOLID_FILE_NAME;
            default:
                return null;
        }
    }

    public static boolean createDirIfNotExists(String path) {
        File file = new File(path);
        if (!file.exists()) {
            if (!file.mkdirs()) {
                Log.e("Log :: ", "Problem z tworzeniem foldera");
                return false;
            }
        }
        return true;
    }
}
