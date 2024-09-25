package com.game.mygame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.Random;

import common.Point;
import map.Leaf;
import map.LeafGenerator;
import map.Map;

public class GameScreen implements Screen {

    boolean mapgenerated=false;
    OrthographicCamera camera;
    Map map;
    LeafGenerator leaf;
    ShapeRenderer shapeRenderer;
    final Roque game;
    Stage stage;
    ImageButton playButton;
    Texture playButtonImage;
    Texture greenGround;
    Texture stoneGround;
    int width=1080;
    int height=720;

        public GameScreen(final Roque game){
            this.game=game;
            int maxRooms=15;
            playButtonImage=new Texture("images/screen/buttonplay.png");
            greenGround=new Texture ("images/texture/greenground2.png");
            stoneGround=new Texture("images/texture/stoneground.png");
            shapeRenderer=new ShapeRenderer();
            leaf=new LeafGenerator();
            stage=new Stage();

            Gdx.input.setInputProcessor(stage);
            camera=new OrthographicCamera();
            camera.setToOrtho(false,width,height);


            ImageButton.ImageButtonStyle style=new ImageButton.ImageButtonStyle();
            style.up=new TextureRegionDrawable(playButtonImage);
            playButton=new ImageButton(style);
            playButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new MainMenuScreen(game));
                }
            });
            playButton.setSize(64,64);
            playButton.setPosition(width-64,height-64);

            leaf.generateLeaves(width,height,maxRooms);
            stage.getViewport().setCamera(camera);
            stage.addActor(playButton);

        }
        @Override
        public void show() {

        }
        @Override
        public void render(float delta) {
            ScreenUtils.clear(0,0,0,1);
            camera.update();
            shapeRenderer.setProjectionMatrix(camera.combined);

                    // Начинаем рисовать карту
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

                for (Leaf currentLeaf : leaf.getLeafs()) {

                    // Рисуем черный внутренний прямоугольник

                    shapeRenderer.setColor(Color.WHITE);
                    shapeRenderer.rect(currentLeaf.room.x, currentLeaf.room.y, currentLeaf.room.width, currentLeaf.room.height);
                    for (Rectangle currentHall : currentLeaf.halls) {
                        shapeRenderer.rect(currentHall.x, currentHall.y, currentHall.width, currentHall.height);

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
