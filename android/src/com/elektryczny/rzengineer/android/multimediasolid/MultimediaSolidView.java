package com.elektryczny.rzengineer.android.multimediasolid;

import android.content.Context;
import android.opengl.GLSurfaceView;

/**
 * @author Rafał Zawadzki
 */
public class MultimediaSolidView extends GLSurfaceView {
    private final MultimediaSolidRenderer renderer;

    MultimediaSolidView(Context context) {
        super(context);
        renderer = new MultimediaSolidRenderer(context);
        setRenderer(renderer);
    }
}