package com.game.mygame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;


public class MainMenuScreen implements Screen {

    AssetManager assetManager;
    final Roque game;
    Music music;
    Texture playButtonTexture,quitButtonTexture, backgroundTexture;
    OrthographicCamera camera;
    Button playButton,quitButton;
    Stage stage;

    public MainMenuScreen(final Roque game){
        this.game=game;
        stage=new Stage();
        Gdx.input.setInputProcessor(stage);

        camera=new OrthographicCamera();
        camera.setToOrtho(false,800,480);
        stage.getViewport().setCamera(camera);


        playButtonTexture=new Texture("images/screen/playButtonTexture.png");
        quitButtonTexture=new Texture("images/screen/quitButtonTexture.png");
        backgroundTexture=new Texture("images/screen/mainmenu-background.jpg");

        playButton=new Button(new TextureRegionDrawable(playButtonTexture));
        quitButton=new Button(new TextureRegionDrawable(quitButtonTexture));


        playButton.setWidth(250);
        playButton.setHeight(150);

        quitButton.setWidth(250);
        quitButton.setHeight(150);

        playButton.setPosition(100,180);
        quitButton.setPosition(100,30);

        playButton.addListener(new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y){
                game.setScreen(new GameScreen(game));
                dispose();
            }
        });
        quitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });
        stage.addActor(playButton);
        stage.addActor(quitButton);
    }


    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.font.getData().setScale(5,5);
        game.batch.begin();
        game.batch.draw(backgroundTexture,0,0);

        game.batch.end();
        stage.act();
        stage.draw();
    }
    @Override
    public void dispose() {
        backgroundTexture.dispose(); // Dispose the texture
        playButtonTexture.dispose();
        quitButtonTexture.dispose();
        stage.dispose(); // Dispose the stage, which removes and disposes of actors like buttons


    }


    @Override
    public void show() {
//        // Инициализация AssetManager
//        assetManager = new AssetManager();
//
//        // Асинхронная загрузка музыки
//        assetManager.load("music/mainmenu.mp3", Music.class);
//
//        // Запускаем новый поток для проверки статуса загрузки
//        new Thread(() -> {
//            assetManager.finishLoading(); // Дожидаемся завершения загрузки
//            music = assetManager.get("music/mainmenu.mp3", Music.class);
//
//            // Настраиваем и запускаем музыку в основном потоке
//            Gdx.app.postRunnable(() -> {
//                music.setLooping(true);
//                music.setVolume(1);
//                music.play();
//            });
//        }).start();
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
    public void hide() {


    }

}
