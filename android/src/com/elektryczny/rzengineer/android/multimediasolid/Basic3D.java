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
import com.badlogic.gdx.graphics.g3d.particles.batches.ParticleBatch;
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.elektryczny.rzengineer.android.MultimediaFileManager;

import java.io.File;
import java.util.ArrayList;

import javax.microedition.khronos.opengles.GL10;

/**
 * @author Rafał Zawadzki
 */
public class Basic3D extends ApplicationAdapter implements GestureDetector.GestureListener {
    private Environment environment;
    private PerspectiveCamera cam;
    private CameraInputController camController;
    private ModelBatch modelBatch;
    private Model model;
    private ModelInstance instance;
    private ArrayList<ParticleEffect> currentCornerEffects = new ArrayList<ParticleEffect>();
    private AssetManager assets = AssetManagerSingleton.getInstance();
    private static Integer CORNER_EFFECTS = 8;
    private ParticleSystem particleSystem;

    @Override
    public void create() {
        //create folder with images
        String path = MultimediaFileManager.RESOURCES_DIRECTORY + MultimediaFileManager.SOLID_IMAGES_DIRECTORY_NAME;
        MultimediaFileManager.createDirIfNotExists(path);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.9f, 0.9f, 0.9f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        modelBatch = ModelBatchSingleton.getInstance();

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(3f, 3f, 3f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 30f;
        cam.update();

        FileHandle texTop = new FileHandle(new File(MultimediaFileManager.RESOURCES_DIRECTORY + MultimediaFileManager.SOLID_IMAGES_DIRECTORY_NAME + MultimediaFileManager.MULTIMEDIA_SOLID_WALL1_NAME));
        FileHandle texBottom = new FileHandle(new File(MultimediaFileManager.RESOURCES_DIRECTORY + MultimediaFileManager.SOLID_IMAGES_DIRECTORY_NAME + MultimediaFileManager.MULTIMEDIA_SOLID_WALL2_NAME));
        FileHandle texFront = new FileHandle(new File(MultimediaFileManager.RESOURCES_DIRECTORY + MultimediaFileManager.SOLID_IMAGES_DIRECTORY_NAME + MultimediaFileManager.MULTIMEDIA_SOLID_WALL3_NAME));
        FileHandle texLeft = new FileHandle(new File(MultimediaFileManager.RESOURCES_DIRECTORY + MultimediaFileManager.SOLID_IMAGES_DIRECTORY_NAME + MultimediaFileManager.MULTIMEDIA_SOLID_WALL4_NAME));
        FileHandle texRight = new FileHandle(new File(MultimediaFileManager.RESOURCES_DIRECTORY + MultimediaFileManager.SOLID_IMAGES_DIRECTORY_NAME + MultimediaFileManager.MULTIMEDIA_SOLID_WALL5_NAME));
        FileHandle texBack = new FileHandle(new File(MultimediaFileManager.RESOURCES_DIRECTORY + MultimediaFileManager.SOLID_IMAGES_DIRECTORY_NAME + MultimediaFileManager.MULTIMEDIA_SOLID_WALL6_NAME));

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

        particleSystem = ParticleSystem.get();
        particleSystem.update();
        particleSystem.removeAll(); //first
        particleSystem = ParticleSystem.get();
        PointSpriteParticleBatch pointSpriteBatch = new PointSpriteParticleBatch();
        BillboardParticleBatch pointBillboardBatch = new BillboardParticleBatch();
        pointSpriteBatch.setCamera(cam);
        particleSystem = ParticleSystem.get();
        particleSystem.add(pointSpriteBatch);
        particleSystem = ParticleSystem.get();
        particleSystem.add(pointBillboardBatch);

        assets.clear();
        Integer batNumber = particleSystem.getBatches().size;
        Array<ParticleBatch<?>> batInSystem = particleSystem.getBatches();
        if (batNumber > 2) { //second (particle effects aren't black)
            batInSystem.removeIndex(0);
            batInSystem.removeIndex(0);
        }
        ParticleEffectLoader.ParticleEffectLoadParameter loadParam = new ParticleEffectLoader.ParticleEffectLoadParameter(batInSystem);
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

        ParticleEffect originalEffect1 = assets.get("particle/dust.pfx"); // after creating effects "particle/dust"+i+".pfx"
        for (int i = 0; i < CORNER_EFFECTS; i++) {
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
            cam.rotateAround(new Vector3(0, 0, 0), Vector3.Z, 1f);
        }

        particleSystem.update(); // technically not necessary for rendering
        particleSystem.begin();
        particleSystem.draw();
        particleSystem.end();
        modelBatch.render(particleSystem);

    }

    @Override
    public void dispose() {
        assets.clear();
        particleSystem.removeAll();
        modelBatch.dispose();
        model.dispose();

    }

    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void pause() {
        super.pause();
    }

    @Override
    public void resume() {
        super.resume();
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