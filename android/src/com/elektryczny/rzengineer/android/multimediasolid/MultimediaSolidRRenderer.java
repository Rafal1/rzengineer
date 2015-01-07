package com.elektryczny.rzengineer.android.multimediasolid;

import android.content.Context;

import com.elektryczny.rzengineer.android.R;

import javax.microedition.khronos.egl.EGLConfig;
import javax.microedition.khronos.opengles.GL10;

import rajawali.RajawaliActivity;
import rajawali.materials.TextureInfo;
import rajawali.materials.TextureManager;
import rajawali.primitives.Cube;
import rajawali.renderer.RajawaliRenderer;

/**
 * @author Rafał Zawadzki
 */
public class MultimediaSolidRRenderer extends RajawaliRenderer {

    public MultimediaSolidRRenderer(Context context) {
        super(context);
        setFrameRate(60);
    }

    public void initScene() {
//        Cube c = new Cube(100, true, true);
//        TextureInfo ti = new TextureInfo(R.drawable.ic_action_pause_over_video, TextureManager.TextureType.CUBE_MAP);
//        TextureInfo ty = new TextureInfo(R.drawable.ic_action_play_over_video, TextureManager.TextureType.CUBE_MAP);
//        c.addTexture(ti);
//        c.addTexture(ty);
//        c.addTexture(SkyBox.Face.North);
        /**
         * Create a new Skybox. The first parameter specifies the size,
         * the second parameter specifies the quality (the number of
         * segments in each plane)
         */
        setSkybox(R.drawable.ic_action_pause_over_video, R.drawable.ic_action_pause_over_video, R.drawable.ic_action_pause_over_video, R.drawable.ic_action_play_over_video, R.drawable.ic_action_play_over_video, R.drawable.ic_action_play_over_video);
//        SkyBox skyBox = new SkyBox(10, 2);

        /**
         * If you have a single texture that you want to use on all
         * faces then add the texture and pass SkyBox.Face.All as the
         * first parameter.
         */
//        skyBox.addTexture(SkyBox.Face.All, R.drawable.mytexture, "mytexture");

        /**
         * If you want to use a separate texture on each individual
         * face do this:
         */
//        skyBox.addTexture(SkyBox.Face.North, R.drawable.northtexture, "mynorthtexture");
//        skyBox.addTexture(SkyBox.Face.East, R.drawable.easttexture, "myeasttexture");
//        skyBox.addTexture(SkyBox.Face.South, R.drawable.southtexture, "mysouthtexture");
//        skyBox.addTexture(SkyBox.Face.West, R.drawable.westtexture, "mywesttexture");
//        skyBox.addTexture(SkyBox.Face.Up, R.drawable.uptexture, "myuptexture");
//        skyBox.addTexture(SkyBox.Face.Down, R.drawable.downtexture, "mydowntexture");

        /**
         * Finally, add the skybox to the scene
         */
//        scene.addChild(skyBox);
    }

    public void onSurfaceCreated(GL10 gl, EGLConfig config) {
//   		((RajawaliActivity) mContext).showLoader();
        super.onSurfaceCreated(gl, config);
//   		((RajawaliExampleActivity) mContext).hideLoader();
    }

    public void onDrawFrame(GL10 glUnused) {
        super.onDrawFrame(glUnused);
//   		mSphere.setRotY(mSphere.getRotY() + 1);
    }
}
