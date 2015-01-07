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
import com.badlogic.gdx.graphics.g3d.particles.batches.PointSpriteParticleBatch;
import com.badlogic.gdx.graphics.g3d.utils.CameraInputController;
import com.badlogic.gdx.graphics.g3d.utils.MeshPartBuilder;
import com.badlogic.gdx.graphics.g3d.utils.ModelBuilder;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.elektryczny.rzengineer.android.MultimediaFileManager;

import java.io.File;

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
    public static final String DEFAULT_PARTICLE = "particle/pre_particle.png";
    private ParticleEffect currentEffects;
    private ParticleSystem particleSystem;
//    class CameraController implements GestureDetector.GestureListener {
//        public CameraInputController controller;
//        private float previousZoom;
//        private ModelInstance instance;
//        @Override
//        public boolean touchDown(float x, float y, int pointer, int button) {
//            previousZoom = 0;
//            return false;
//        }
//
//        @Override
//        public boolean tap(float x, float y, int count, int button) {
//            return false;
//        }
//
//        @Override
//        public boolean longPress(float x, float y) {
//            return false;
//        }
//
//        @Override
//        public boolean fling(float velocityX, float velocityY, int button) {
//            return false;
//        }
//
//        @Override
//        public boolean pan(float x, float y, float deltaX, float deltaY) {
//            return false;
//        }
//
//        @Override
//        public boolean panStop(float x, float y, int pointer, int button) {
//            return false;
//        }
//
//        @Override
//        public boolean zoom(float initialDistance, float distance) {
////            float newZoom = distance - initialDistance;
////            float amount = newZoom - previousZoom;
////            previousZoom = newZoom;
////            float w = Gdx.graphics.getWidth(), h = Gdx.graphics.getHeight();
////            return controller.pinchZoom(amount / ((w > h) ? h : w));
//            return true;
//        }
//
//        @Override
//        public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
//            return false;
//        }
//    }

    @Override
    public void create() {
        //create folder with images
        String path = MultimediaFileManager.getResourcesDirectory() + MultimediaFileManager.getSolidImagesDirectoryName();
        MultimediaFileManager.createDirIfNotExists(path);

        environment = new Environment();
        environment.set(new ColorAttribute(ColorAttribute.AmbientLight, 0.9f, 0.9f, 0.9f, 1f));
        environment.add(new DirectionalLight().set(0.8f, 0.8f, 0.8f, -1f, -0.8f, -0.2f));

        modelBatch = new ModelBatch();

        cam = new PerspectiveCamera(67, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        cam.position.set(3f, 3f, 3f);
        cam.lookAt(0, 0, 0);
        cam.near = 1f;
        cam.far = 30f;
        cam.update();

//        ModelBuilder modelBuilder = new ModelBuilder();
//        model = modelBuilder.createBox(5f, 5f, 5f,
//                new Material(ColorAttribute.createDiffuse(com.badlogic.gdx.graphics.Color.GREEN)),
//                VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal);
//        instance = new ModelInstance(model);

//        FileHandle texTop = new FileHandle(new File(MultimediaFileManager.getPathToFile(FileEnum.IMAGE_FILE)));
//        FileHandle texBottom = new FileHandle(new File(MultimediaFileManager.getPathToFile(FileEnum.IMAGE_LAYER_FILE)));
//        Texture texTile = new Texture(texTop);
//        Texture texTile1 = new Texture(texBottom);
//        Material mat = new Material(TextureAttribute.createDiffuse(texTile));
//        Material mat1 = new Material(TextureAttribute.createDiffuse(texTile1));

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
//        modelBuilder.
        MeshPartBuilder tileBuilder;
        tileBuilder = modelBuilder.part("top", GL10.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, mat1);
//        tileBuilder.rect(-1f, 0.1f, 1f, 1f, 0.1f, 1f, 1f, 0.1f, -1f, -1f, 0.1f, -1f, -1f, 1f, 0f);
        tileBuilder.rect(-1f, 1f, 1f, 1f, 1f, 1f, 1f, 1f, -1f, -1f, 1f, -1f, -1f, 1f, 0f);
        tileBuilder = modelBuilder.part("bottom", GL10.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, mat2);
//        tileBuilder.rect(-1f, -1f, -1f, 1f, -1f, -1f, 1f, -1f, 1f, -1f, -1f, 1f, 0f, -1f, 0f);
        tileBuilder.rect(-1f, -1f, -1f, 1f, -1f, -1f, 1f, -1f, 1f, -1f, -1f, 1f, 0f, -1f, 0f);
        tileBuilder = modelBuilder.part("front", GL10.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, mat3);
//        tileBuilder.rect(-1f, 0.1f, 1f, -1f, -1f, 1f, 1f, -1f, 1f, 1f, 0.1f, 1f, 0f, 0f, 1f);
        tileBuilder.rect(-1f, 1f, 1f, -1f, -1f, 1f, 1f, -1f, 1f, 1f, 1f, 1f, 0f, 0f, 1f);
        tileBuilder = modelBuilder.part("left", GL10.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, mat4);
//        tileBuilder.rect(-1f, 0.1f, 1f, -1f, 0.1f, -1f, -1f, -1f, -1f, -1f, -1f, 1f, -1f, 0f, 0f);
        tileBuilder.rect(-1f, 1f, 1f, -1f, 1f, -1f, -1f, -1f, -1f, -1f, -1f, 1f, -1f, 0f, 0f);
        tileBuilder = modelBuilder.part("right", GL10.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, mat5);
//        tileBuilder.rect(1f, 0.1f, 1f, 1f, -1f, 1f, 1f, -1f, -1f, 1f, 0.1f, -1f, 0f, 0f, 1f);
        tileBuilder.rect(1f, 1f, 1f, 1f, -1f, 1f, 1f, -1f, -1f, 1f, 1f, -1f, 0f, 0f, 1f);
        tileBuilder = modelBuilder.part("back", GL10.GL_TRIANGLES, VertexAttributes.Usage.Position | VertexAttributes.Usage.Normal | VertexAttributes.Usage.TextureCoordinates, mat6);
//        tileBuilder.rect(-1f, 0.1f, -1f, 1f, 0.1f, -1f, 1f, -1f, -1f, -1f, -1f, -1f, 0f, 0f, 1f);
        tileBuilder.rect(-1f, 1f, -1f, 1f, 1f, -1f, 1f, -1f, -1f, -1f, -1f, -1f, 0f, 0f, 1f);
        model = modelBuilder.end();

//        AssetManager am = new AssetManager();
//        am.load("data/1200.jpg", Texture.class);
//        am.update();
//        am.finishLoading();
//        Texture texTile = am.get("data/1200.jpg", Texture.class);


        instance = new ModelInstance(model); //Gdx.input.getAccelerometerY()
        //instance.transform.rotate(Vector3.Z, 90f);

//        instance.nodes.get(0).parts.get(0).material.set(mat);
//        instance.nodes.get(0).parts.get(1).material.set(mat);
//        instance.nodes.get(0).parts.get(2).material.set(mat);
//        instance.nodes.get(0).parts.get(3).material.set(mat1);
//        instance.nodes.get(0).parts.get(4).material.set(mat1);
//        instance.nodes.get(0).parts.get(5).material.set(mat);

//        Array<NodePart> x = instance.nodes.get(0).parts;
//        Integer xd = x.size;


        AssetManager assets = new AssetManager();
//        assets.load(DEFAULT_PARTICLE, Texture.class);

        particleSystem = ParticleSystem.get();
        PointSpriteParticleBatch pointSpriteBatch = new PointSpriteParticleBatch();
//        pointSpriteBatch.setTexture(assets.get(DEFAULT_PARTICLE, Texture.class));
//        BillboardParticleBatch pointSpriteBatch = new BillboardParticleBatch();
        pointSpriteBatch.setCamera(cam);
        particleSystem = ParticleSystem.get();
        particleSystem.add(pointSpriteBatch);

        ParticleEffectLoader.ParticleEffectLoadParameter loadParam = new ParticleEffectLoader.ParticleEffectLoadParameter(particleSystem.getBatches());
        ParticleEffectLoader loader = new ParticleEffectLoader(new InternalFileHandleResolver());
        assets.setLoader(ParticleEffect.class, loader);
        assets.load("particle/point.pfx", ParticleEffect.class, loadParam);
        assets.finishLoading();

        ParticleEffect originalEffect = assets.get("particle/point.pfx");
        ParticleEffect effect = originalEffect.copy();
        effect.init();
        effect.start();
        particleSystem.add(effect);

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
        }
//        if (Gdx.input.isTouched()) {
//            instance.transform.rotate(Vector3.Z, 45f);
//        }
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
