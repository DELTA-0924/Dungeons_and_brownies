package com.game.mygame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;


import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;

import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.badlogic.gdx.physics.box2d.World;
import common.Point;
import map.Leaf;
import map.LeafGenerator;
import map.Map;

public class GameScreen implements Screen , InputProcessor {

    private World world;
    OrthographicCamera camera;
    private Touchpad touchpad;
    private Touchpad.TouchpadStyle touchpadStyle;
    LeafGenerator leaf;
    Player player;
    final Roque game;
    Stage stage;
    Stage uiStage;
    private Button upButton, downButton, leftButton, rightButton, playButton;
    private Texture upTexture, downTexture, leftTexture, rightTexture,playTexture;
    boolean mapGenereted=false;
    Texture roomBackground;
    Texture hallWayBackground;
    private Texture touchBackground;
    private Texture touchKnob;
    Texture character;
    int width=1920,cameraWidth=800;
    int height=1080 ,cameraHeight=480;
    float buttonWidth = 150;  // 50 пикселей
    float buttonHeight = 150; // 50 пикселей
    Map map;
    public GameScreen(final Roque game){
        this.game=game;
        int maxRooms=20;
        world = new World(new Vector2(0, 0), true);

        camera=new OrthographicCamera();
        camera.setToOrtho(false,cameraWidth,cameraHeight);

        uiStage = new Stage(new ScreenViewport());

/*
        upTexture=new Texture("images/screen/buttonup.png");
        downTexture=new Texture("images/screen/buttondown.png");
        leftTexture=new Texture("images/screen/buttonleft.png");
        rightTexture=new Texture("images/screen/buttonright.png");

        upButton=new Button(new TextureRegionDrawable(upTexture));
        downButton=new Button(new TextureRegionDrawable(downTexture));
        leftButton=new Button(new TextureRegionDrawable(leftTexture));
        rightButton=new Button(new TextureRegionDrawable(rightTexture));

        upButton.setSize(buttonWidth, buttonHeight);
        downButton.setSize(buttonWidth, buttonHeight);
        leftButton.setSize(buttonWidth, buttonHeight);
        rightButton.setSize(buttonWidth, buttonHeight);

        upButton.setPosition(300, 325);
        downButton.setPosition(300, 50);
        leftButton.setPosition(150, 175);
        rightButton.setPosition(450, 175);

        upButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.move(0, player.speed * Gdx.graphics.getDeltaTime()); // Движение вверх
            }
        });

        downButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.move(0, -player.speed * Gdx.graphics.getDeltaTime()); // Движение вниз
            }
        });

        leftButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.move(-player.speed * Gdx.graphics.getDeltaTime(), 0); // Движение влево
            }
        });

        rightButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                player.move(player.speed * Gdx.graphics.getDeltaTime(), 0); // Движение вправо
            }
        });

*/

        touchBackground = createCircleTexture(800, Color.GRAY, 0.5f); // Полупрозрачный серый фон
        touchKnob = createCircleTexture(160, Color.WHITE, 1f);         // Непрозрачная белая ручка

        // Создаем стиль для джойстика
        touchpadStyle = new Touchpad.TouchpadStyle();
        touchpadStyle.background = new TextureRegionDrawable(new TextureRegion(touchBackground));
        touchpadStyle.knob = new TextureRegionDrawable(new TextureRegion(touchKnob));

        // Создаем Touchpad
        touchpad = new Touchpad(10, touchpadStyle); // Параметр deadzone
        touchpad.setBounds(300, 200, 200, 200);
        leaf=new LeafGenerator();


        playTexture=new Texture("images/screen/playbutton.png");
        playButton=new Button(new TextureRegionDrawable(playTexture));
        playButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    game.setScreen(new MainMenuScreen(game));
                }
            });
        playButton.setSize(64,64);
        playButton.setPosition(width-64,height-64);

            leaf.generateLeaves(1500,1200,maxRooms);





//        uiStage.addActor(playButton);
//        uiStage.addActor(upButton);
//        uiStage.addActor(downButton);
//        uiStage.addActor(leftButton);
//        uiStage.addActor(rightButton);
        uiStage.addActor(touchpad);
        Gdx.input.setInputProcessor(uiStage);
        map=new Map(world);
    }
        @Override
        public void show() {
            character=new Texture("images/texture/character/character.png");
            float playerPosX=0,playerPosY=0;
            for (var currnetLeaf:leaf.getLeafs()) {
                if((currnetLeaf.room.x+ currnetLeaf.room.width)!=0 && ((currnetLeaf.room.y+ currnetLeaf.room.height)!=0)){
                    playerPosY=currnetLeaf.room.x;
                    playerPosX=currnetLeaf.room.y;
                    break;
                }

            }
            player=new Player(world,character,playerPosY,playerPosX,100);


            roomBackground=new Texture("images/texture/room.jpg");
            roomBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
            hallWayBackground=new Texture("images/texture/room.jpg");
            hallWayBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        }
        @Override
        public void render(float delta) {
            ScreenUtils.clear(0, 0, 0, 1);


            camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
            camera.update();
            uiStage.getViewport().apply();
            game.batch.setProjectionMatrix(camera.combined);
            game.batch.begin();
                // Рисуем карту с использованием SpriteBatch
                for (Leaf currentLeaf : leaf.getLeafs()) {
                    // Отрисовка комнат
                    game.batch.draw(roomBackground, currentLeaf.room.x, currentLeaf.room.y, currentLeaf.room.width, currentLeaf.room.height);
                    map.createRoom( currentLeaf.room.x, currentLeaf.room.y, currentLeaf.room.width, currentLeaf.room.height);
                    // Отрисовка коридоров
                    for (Rectangle currentHall : currentLeaf.halls) {
                        drawTiledTexture(game.batch, hallWayBackground, currentHall.x, currentHall.y, currentHall.width, currentHall.height);
                        map.createCorridor( currentHall.x, currentHall.y, currentHall.width, currentHall.height);
                    }
                }
                float moveX = touchpad.getKnobPercentX();
                float moveY = touchpad.getKnobPercentY();
                player.move(moveX * player.speed * delta, moveY * player.speed * delta);
                player.render(game.batch);
                game.batch.end();
                // Обновляем и рисуем персонажа

                uiStage.act(delta);
                uiStage.draw();
            }


    @Override
    public void dispose() {
        stage.dispose();
        stage.dispose();
        uiStage.dispose();
        upTexture.dispose();
        downTexture.dispose();
        leftTexture.dispose();
        rightTexture.dispose();
        roomBackground.dispose();
        hallWayBackground.dispose();
    }


    private Texture createCircleTexture(int diameter, Color color, float alpha) {
        Pixmap pixmap = new Pixmap(diameter, diameter, Pixmap.Format.RGBA8888);
        pixmap.setBlending(Pixmap.Blending.None);
        pixmap.setColor(color.r, color.g, color.b, alpha);
        pixmap.fillCircle(diameter / 2, diameter / 2, diameter / 2);
        Texture texture = new Texture(pixmap);
        pixmap.dispose(); // Освобождаем ресурсы Pixmap
        return texture;
    }

    private void drawTiledTexture(SpriteBatch batch, Texture texture, float x, float y, float width, float height) {
        // Рассчитываем масштабирование текстуры для повторения по ширине и высоте
        float u = width / texture.getWidth();
        float v = height / texture.getHeight();

        // Используем метод batch.draw для рисования тайловой текстуры
        batch.draw(texture,
            x, y,
            width, height,
            0, 0, u, v);  // Параметры для повторения текстуры
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



    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return true;
    }


    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {

        return false;
    }

    @Override
    public boolean keyTyped(char character) {

        return false;
    }


    @Override
    public boolean touchCancelled(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        return false;
    }



}
