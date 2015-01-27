package com.elektryczny.rzengineer.android;

import android.os.Environment;
import android.util.Log;

import java.io.File;

/**
 * @author Rafał Zawadzki
 */

public class MultimediaFileManager {
    private static final String BASIC_PATH = Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    private static final String MULTIMEDIA_DIRECTORY_NAME = "MultimediaBox" + File.separator;
    private static final String SOLID_IMAGES_DIRECTORY_NAME = "imagesForSolid" + File.separator;
    private static final String RESOURCES_DIRECTORY = BASIC_PATH + File.separator + MULTIMEDIA_DIRECTORY_NAME + File.separator;

    private static final String SOUND_FILE_NAME = "soundTrack.3gp";
    private static final String MESSAGES_FILE_NAME = "messagesCrypto.txt";
    private static final String IMAGE_FILE_NAME = "Photo.jpg";
    private static final String IMAGE_LAYER_FILE_NAME = "pictureLayer.png";
    private static final String VIDEO_FILE_NAME = "VID_COMM.mp4";
    private static final String VIDEO_LAYER_FILE_NAME = "videoLayer.png";
    private static final String MULTIMEDIA_SOLID_FILE_NAME = "X";

    private static final String MULTIMEDIA_SOLID_WALL1_NAME = "1.jpg";
    private static final String MULTIMEDIA_SOLID_WALL2_NAME = "2.jpg";
    private static final String MULTIMEDIA_SOLID_WALL3_NAME = "3.jpg";
    private static final String MULTIMEDIA_SOLID_WALL4_NAME = "4.jpg";
    private static final String MULTIMEDIA_SOLID_WALL5_NAME = "5.jpg";
    private static final String MULTIMEDIA_SOLID_WALL6_NAME = "6.jpg";

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


    public static String getBasicPath() {
        return BASIC_PATH;
    }

    public static String getMultimediaDirectoryName() {
        return MULTIMEDIA_DIRECTORY_NAME;
    }

    public static String getSolidImagesDirectoryName() {
        return SOLID_IMAGES_DIRECTORY_NAME;
    }

    public static String getResourcesDirectory() {
        return RESOURCES_DIRECTORY;
    }

    public static String getSoundFileName() {
        return SOUND_FILE_NAME;
    }

    public static String getMessagesFileName() {
        return MESSAGES_FILE_NAME;
    }

    public static String getImageFileName() {
        return IMAGE_FILE_NAME;
    }

    public static String getImageLayerFileName() {
        return IMAGE_LAYER_FILE_NAME;
    }

    public static String getVideoFileName() {
        return VIDEO_FILE_NAME;
    }

    public static String getVideoLayerFileName() {
        return VIDEO_LAYER_FILE_NAME;
    }

    public static String getMultimediaSolidFileName() {
        return MULTIMEDIA_SOLID_FILE_NAME;
    }

    public static String getMultimediaSolidWall1() {
        return MULTIMEDIA_SOLID_WALL1_NAME;
    }

    public static String getMultimediaSolidWall2() {
        return MULTIMEDIA_SOLID_WALL2_NAME;
    }

    public static String getMultimediaSolidWall3() {
        return MULTIMEDIA_SOLID_WALL3_NAME;
    }

    public static String getMultimediaSolidWall4() {
        return MULTIMEDIA_SOLID_WALL4_NAME;
    }

    public static String getMultimediaSolidWall5() {
        return MULTIMEDIA_SOLID_WALL5_NAME;
    }

    public static String getMultimediaSolidWall6() {
        return MULTIMEDIA_SOLID_WALL6_NAME;
    }
}
