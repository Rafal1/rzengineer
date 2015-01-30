package com.elektryczny.rzengineer.android.multimediasolid;

import com.badlogic.gdx.assets.AssetManager;

/**
 * @author Rafał Zawadzki
 */
public class AssetManagerSingleton {
    private static volatile AssetManager assets;

    private AssetManagerSingleton(AssetManager a) {
    }

    public static synchronized AssetManager getInstance() {
        if (assets == null) {
            assets = new AssetManager();
        }
        return assets;
    }

}
