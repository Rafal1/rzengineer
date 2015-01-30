package com.elektryczny.rzengineer.android.multimediasolid;

import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;

/**
 * @author Rafał Zawadzki
 */
public class ModelBatchSingleton {
    private static volatile ModelBatch batch;

    private ModelBatchSingleton(){

    }

    public static synchronized ModelBatch getInstance() {
        if (batch == null) {
            batch = new ModelBatch();
        }
        return batch;
    }
}
