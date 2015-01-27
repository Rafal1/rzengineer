package com.elektryczny.rzengineer.android.multimediasolid;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.PerspectiveCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.VertexAttributes;
import com.badlogic.gdx.graphics.g3d.Environment;
import com.badlogic.gdx.graphics.g3d.Material;
import com.badlogic.gdx.graphics.g3d.Model;
import com.badlogic.gdx.graphics.g3d.ModelBatch;
import com.badlogic.gdx.graphics.g3d.ModelInstance;
import com.badlogic.gdx.graphics.g3d.attributes.ColorAttribute;
import com.badlogic.gdx.graphics.g3d.attributes.TextureAttribute;
import com.badlogic.gdx.graphics.g3d.environment.DirectionalLight;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffect;
import com.badlogic.gdx.graphics.g3d.particles.ParticleEffectLoader;
import com.badlogic.gdx.graphics.g3d.particles.ParticleSystem;
import com.badlogic.gdx.graphics.g3d.particles.batches.BillboardParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.elektryczny.rzengineer.android.MultimediaFileManager;

import java.io.File;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author Rafał Zawadzki
 */
public class Basic3D extends ApplicationAdapter implements GestureDetector.GestureListener {
    public Environment environment;
    public PerspectiveCamera cam;
    public CameraInputController camController;
    public ModelBatch modelBatch;
    public Model model;
    public ModelInstance instance;
    private ArrayList<ParticleEffect> currentCornerEffects = new ArrayList<ParticleEffect>();
    private static Integer CORNER_EFFECTS = 8;
    private ParticleSystem particleSystem;

    @Override
    public void create() {
        //create folder with images
        String path = MultimediaFileManager.getResourcesDirectory() + MultimediaFileManager.getSolidImagesDirectoryName();
        MultimediaFileManager.createDirIfNotExists(path);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.9f, 0.9f, 0.9f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        modelBatch = new ModelBatch();

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()); //67
        cam.position.set(3f, 3f, 3f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 30f;
        cam.update();

        FileHandle texTop = new FileHandle(new File(MultimediaFileManager.getResourcesDirectory() + MultimediaFileManager.getSolidImagesDirectoryName() + MultimediaFileManager.getMultimediaSolidWall1()));
        FileHandle texBottom = new FileHandle(new File(MultimediaFileManager.getResourcesDirectory() + MultimediaFileManager.getSolidImagesDirectoryName() + MultimediaFileManager.getMultimediaSolidWall2()));
        FileHandle texFront = new FileHandle(new File(MultimediaFileManager.getResourcesDirectory() + MultimediaFileManager.getSolidImagesDirectoryName() + MultimediaFileManager.getMultimediaSolidWall3()));
        FileHandle texLeft = new FileHandle(new File(MultimediaFileManager.getResourcesDirectory() + MultimediaFileManager.getSolidImagesDirectoryName() + MultimediaFileManager.getMultimediaSolidWall4()));
        FileHandle texRight = new FileHandle(new File(MultimediaFileManager.getResourcesDirectory() + MultimediaFileManager.getSolidImagesDirectoryName() + MultimediaFileManager.getMultimediaSolidWall5()));
        FileHandle texBack = new FileHandle(new File(MultimediaFileManager.getResourcesDirectory() + MultimediaFileManager.getSolidImagesDirectoryName() + MultimediaFileManager.getMultimediaSolidWall6()));

        Texture texTile1 = new Texture(texTop);
        Texture texTile2 = new Texture(texBottom);
        Texture texTile3 = new Texture(texFront);
        Texture texTile4 = new Texture(texLeft);
        Texture texTile5 = new Texture(texRight);
        Texture texTile6 = new Texture(texBack);

        Material mat1 = new Material(TextureAttribute.createDiffuse(texTile1));
        Material mat2 = new Material(TextureAttribute.createDiffuse(texTile2));
        Material mat3 = new Material(TextureAttribute.createDiffuse(texTile3));
        Material mat4 = new Material(TextureAttribute.createDiffuse(texTile4));
        Material mat5 = new Material(TextureAttribute.createDiffuse(texTile5));
        Material mat6 = new Material(TextureAttribute.createDiffuse(texTile6));

        ModelBuilder modelBuilder = new ModelBuilder();
        modelBuilder.begin();
        MeshPartBuilder tileBuilder;
        tileBuilder = modelBuilder.part("top", GL10.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, mat1);
        tileBuilder.rect(-1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, -1f, -1f, 1f, -1f, -1f, 1f, 0f);
        tileBuilder = modelBuilder.part("bottom", GL10.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, mat2);
        tileBuilder.rect(-1f, -1f, -1f, 1f, -1f, -1f, 1f, -1f, 1f, -1f, -1f, 1f, 0f, -1f, 0f);
        tileBuilder = modelBuilder.part("front", GL10.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, mat3);
        tileBuilder.rect(-1f, 1f, 1f, -1f, -1f, 1f, 1f, -1f, 1f, 1f, 1f, 1f, 0f, 0f, 1f);
        tileBuilder = modelBuilder.part("left", GL10.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, mat4);
        tileBuilder.rect(-1f, 1f, 1f, -1f, 1f, -1f, -1f, -1f, -1f, -1f, -1f, 1f, -1f, 0f, 0f);
        tileBuilder = modelBuilder.part("right", GL10.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, mat5);
        tileBuilder.rect(1f, 1f, 1f, 1f, -1f, 1f, 1f, -1f, -1f, 1f, 1f, -1f, 0f, 0f, 1f);
        tileBuilder = modelBuilder.part("back", GL10.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, mat6);
        tileBuilder.rect(-1f, 1f, -1f, 1f, 1f, -1f, 1f, -1f, -1f, -1f, -1f, -1f, 0f, 0f, 1f);
        model = modelBuilder.end();
        instance = new ModelInstance(model); //Gdx.input.getAccelerometerY()
        AssetManager assets = new AssetManager();

        particleSystem = ParticleSystem.get();
        PointSpriteParticleBatch pointSpriteBatch = new PointSpriteParticleBatch();
        BillboardParticleBatch pointBillboardBatch = new BillboardParticleBatch();
        pointSpriteBatch.setCamera(cam);
        particleSystem = ParticleSystem.get();
        particleSystem.add(pointSpriteBatch);
        particleSystem = ParticleSystem.get();
        particleSystem.add(pointBillboardBatch);

        ParticleEffectLoader.ParticleEffectLoadParameter loadParam = new ParticleEffectLoader.ParticleEffectLoadParameter(particleSystem.getBatches());
        ParticleEffectLoader loader = new ParticleEffectLoader(new InternalFileHandleResolver());
        assets.setLoader(ParticleEffect.class, loader);
        assets.load("particle/hej.pfx", ParticleEffect.class, loadParam);
        assets.load("particle/dust.pfx", ParticleEffect.class, loadParam);
        assets.finishLoading();

        ParticleEffect originalEffect = assets.get("particle/hej.pfx");
        ParticleEffect effect = originalEffect.copy();
        effect.init();
        effect.start();
        particleSystem.add(effect);

        ArrayList<Vector3> vecDustTab = new ArrayList<Vector3>();
        Vector3 moveDust = new Vector3(1f, 1f, 1f);
        vecDustTab.add(moveDust);
        moveDust = new Vector3(1f, -1f, 1f);
        vecDustTab.add(moveDust);
        moveDust = new Vector3(-1f, -1f, 1f);
        vecDustTab.add(moveDust);
        moveDust = new Vector3(-1f, 1f, 1f);
        vecDustTab.add(moveDust);
        moveDust = new Vector3(1f, 1f, -1f);
        vecDustTab.add(moveDust);
        moveDust = new Vector3(1f, -1f, -1f);
        vecDustTab.add(moveDust);
        moveDust = new Vector3(-1f, -1f, -1f);
        vecDustTab.add(moveDust);
        moveDust = new Vector3(-1f, 1f, -1f);
        vecDustTab.add(moveDust);

        for (int i = 0; i < CORNER_EFFECTS; i++) {
            ParticleEffect originalEffect1 = assets.get("particle/dust.pfx"); // after creating effects "particle/dust"+i+".pfx"
            currentCornerEffects.add(i, originalEffect1.copy());
            currentCornerEffects.get(i).translate(vecDustTab.get(i));
            currentCornerEffects.get(i).init();
            currentCornerEffects.get(i).start();
            particleSystem.add(currentCornerEffects.get(i));
        }

        camController = new
                CameraInputController(cam);
        camController.setLongPressSeconds(5f);

        Gdx.input.setInputProcessor(camController);
    }

    @Override
    public void render() {
        camController.update();

        Gdx.gl.glViewport(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT | GL20.GL_DEPTH_BUFFER_BIT);

        modelBatch.begin(cam);
        modelBatch.render(instance, environment);
        modelBatch.end();

        if (camController.isLongPressed()) {
            instance.transform.rotate(Vector3.Z, 1f);
            for (int i = 0; i < CORNER_EFFECTS; i++) { // CORNER_EFFECTS
//                cam.rotate(Vector3.X, 0.1f);
//                float xf = (float) (Math.sqrt(2) * Math.cos(CORNER_EFFECT_ROTATION_EAGLE));
//                                float yf = (float) (Math.sqrt(2) * Math.sin(CORNER_EFFECT_ROTATION_EAGLE));
//                cam.translate(new Vector3(0f, 0f, 0.1f));
                particleSystem.update();
                particleSystem.remove(currentCornerEffects.get(i));
//
//                if (CORNER_EFFECT_ROTATION_EAGLE.equals(360.0)) {
//                    CORNER_EFFECT_ROTATION_EAGLE = 0.0;
//                }
//                float xf = (float) ( Math.cos(MultimediaFileManager.CORNER_EFFECT_ROTATION_EAGLE));
//                float yf = (float) (Math.sin(MultimediaFileManager.CORNER_EFFECT_ROTATION_EAGLE));
//                MultimediaFileManager.CORNER_EFFECT_ROTATION_EAGLE = MultimediaFileManager.CORNER_EFFECT_ROTATION_EAGLE + 1.0;

//                currentCornerEffects.get(i).translate(new Vector3(xf, yf, 0f));
//                currentCornerEffects.get(i).init();
//                currentCornerEffects.get(i).start();
////                particleSystem.add(currentCornerEffects.get(i));
//                particleSystem.update();
//                particleSystem.begin();
//                particleSystem.draw();
//                particleSystem.end();
//                modelBatch.render(particleSystem);
            }
        }

        particleSystem.update(); // technically not necessary for rendering
        particleSystem.begin();
        particleSystem.draw();
        particleSystem.end();
        modelBatch.render(particleSystem);
    }

    @Override
    public void dispose() {
        modelBatch.dispose();
        model.dispose();
    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        return false;
    }
}