package com.game.mygame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.Random;

import map.Map;

public class GameScreen implements Screen {

    OrthographicCamera camera;
    Map map;
    ShapeRenderer shapeRenderer;
    final Roque game;
    Stage stage;
    ImageButton playButton;
    Texture playButtonImage;
    Texture greenGround;
    Texture stoneGround;
    int [][]grid;
    int tileSize=64;
    int width=1080;
    int height=720;

    public GameScreen(final Roque game){
        this.game=game;

        playButtonImage=new Texture("images/screen/buttonplay.png");
        greenGround=new Texture ("images/texture/greenground2.png");
        stoneGround=new Texture("images/texture/stoneground.png");


        stage=new Stage();
        Gdx.input.setInputProcessor(stage);

        camera=new OrthographicCamera();
        camera.setToOrtho(false,width,height);
        stage.getViewport().setCamera(camera);

        ImageButton.ImageButtonStyle style=new ImageButton.ImageButtonStyle();

        style.up=new TextureRegionDrawable(playButtonImage);
        playButton=new ImageButton(style);
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                game.setScreen(new MainMenuScreen(game));
            }
        });

        shapeRenderer=new ShapeRenderer();
        map=new Map(100,100);
        new Thread(() -> {
            map.generate(10); // Генерация карты в отдельном потоке
        }).start();

        playButton.setSize(64,64);
        playButton.setPosition(width-64,height-64);
        stage.addActor(playButton);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1,1,1,1);
        camera.update();
        shapeRenderer.setProjectionMatrix(camera.combined);

        // Начинаем рисовать карту
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

        // Проходим по всей карте и рисуем её
        for (int x = 0; x < map.m_width; x++) {
            for (int y = 0; y < map.m_height; y++) {
                int value = map.m_data.get(x + y * map.m_width);

                if (value == 1) {
                    shapeRenderer.setColor(Color.WHITE); // Комнаты белые
                } else {
                    shapeRenderer.setColor(Color.BLACK); // Пустое пространство черное
                }
                shapeRenderer.rect(x * 10, y * 10, 10, 10); // Рисуем клетку размером 10x10 пикселей
            }
        }

        shapeRenderer.end();

    }

    @Override
    public void dispose() {
        stage.dispose();
        playButtonImage.dispose();
        greenGround.dispose();
        stoneGround.dispose();
        shapeRenderer.dispose();
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
