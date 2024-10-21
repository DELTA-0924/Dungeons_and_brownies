package com.game.mygame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Array;
import com.game.mygame.screen.common.Toast;

import models.player.HealthComponent;


public class MainMenuScreen implements Screen {

    AssetManager assetManager;
    Toast.ToastFactory toastFactory;
    Toast toast;
    private float messageTimer = 3f;
    final Roque game;
    Music music;
    Texture playButtonTexture,quitButtonTexture, backgroundTexture,loadButtonTexture;
    OrthographicCamera camera;
    Button playButton,quitButton,loadButton;
    Stage stage;
    private boolean noData=false;
    BitmapFont font=new BitmapFont();
    public MainMenuScreen(final Roque game){
        this.game=game;
         toastFactory = new Toast.ToastFactory.Builder()
            .font(font)
            .build();
        stage=new Stage();
        Gdx.input.setInputProcessor(stage);

        camera=new OrthographicCamera();
        camera.setToOrtho(false,800,480);
        stage.getViewport().setCamera(camera);


        playButtonTexture=new Texture("images/screen/playButtonTexture.png");
        quitButtonTexture=new Texture("images/screen/quitButtonTexture.png");
        backgroundTexture=new Texture("images/screen/mainmenu-background.jpg");
        loadButtonTexture=new Texture("images/screen/loadButtonTexture.png");

        playButton=new Button(new TextureRegionDrawable(playButtonTexture));
        quitButton=new Button(new TextureRegionDrawable(quitButtonTexture));
        loadButton=new Button(new TextureRegionDrawable(loadButtonTexture));

        playButton.setWidth(150);
        playButton.setHeight(120);

        quitButton.setWidth(160);
        quitButton.setHeight(135);

        loadButton.setWidth(140);
        loadButton.setHeight(90);

        playButton.setPosition(100,250);
        quitButton.setPosition(100,20);
        loadButton.setPosition(110,150);

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
        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                HealthComponent playerStats =game.db.loadDataPlayer();
                Array<models.enemy.HealthComponent> enemyStats= game.db.loadDataEnemies();
                if(playerStats!=null || enemyStats !=null)
                {
                    GameScreen gameScreen=new GameScreen(game);
                    gameScreen.setStats(enemyStats,playerStats,true);
                    game.setScreen(gameScreen);
                    dispose();
                }else noData=true;

            }
        });
         font.getData().setScale(5);
        stage.addActor(playButton);
        stage.addActor(quitButton);
        stage.addActor(loadButton);

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

        if(noData)
        {
            font.draw(game.batch, "No saves!", 270, 250); // Отрисовка текста на экране
            messageTimer -= delta; // Уменьшение таймера
            if (messageTimer <= 0) {
                noData=false;
                messageTimer=3f;
            }
        }
        game.batch.end();
        stage.act();
        stage.draw();
    }
    @Override
    public void dispose() {
        backgroundTexture.dispose(); // Dispose the texture
        playButtonTexture.dispose();
        quitButtonTexture.dispose();
        loadButtonTexture.dispose();
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
