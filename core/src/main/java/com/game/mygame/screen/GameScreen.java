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
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;

import com.badlogic.gdx.scenes.scene2d.ui.Touchpad;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.badlogic.gdx.physics.box2d.World;

import GameMechanic.GameLogic;
import GameMechanic.MyContactListener;
import models.Player;
import map.Leaf;
import map.LeafGenerator;
import map.Map;
import models.PlayerUI;

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
    private Button  playButton;
    private Texture  playTexture;
    boolean mapGenereted=false;
    Texture roomBackground;
    Texture hallWayBackground;
    private Texture touchBackground;
    private Texture touchKnob;
    Texture character;
    int width=1920,cameraWidth=800;
    int height=1080 ,cameraHeight=480;
    GameLogic gameLogic;
    PlayerUI playerUI;
    Box2DDebugRenderer debugRenderer;
    Map map;
    Texture outDoorBackground;
    public GameScreen(final Roque game){
        this.game=game;
        int maxRooms=20;

        world = new World(new Vector2(0, -9.8f), true);
        world.setContactListener(new MyContactListener());
        debugRenderer = new Box2DDebugRenderer();



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

        leaf=new LeafGenerator();
        leaf.generateLeaves(1500,1200,maxRooms);

        map=new Map(world,game);

//
//        for (Leaf currentLeaf : leaf.getLeafs()) {
//            map.createRoom( currentLeaf.room.x, currentLeaf.room.y,
//                currentLeaf.room.width, currentLeaf.room.height,currentLeaf.halls);
//        }


        Gdx.input.setInputProcessor(uiStage);
    }
        @Override
        public void show() {
            character=new Texture("images/texture/character/character.png");
            float playerPosX=0,playerPosY=0;
            for (var currnetLeaf:leaf.getLeafs()) {
                if((currnetLeaf.room.x+ currnetLeaf.room.width)!=0 && ((currnetLeaf.room.y+ currnetLeaf.room.height)!=0)){
                    playerPosY=currnetLeaf.room.x+(currnetLeaf.room.width/2);
                    playerPosX=currnetLeaf.room.y+(currnetLeaf.room.height/2);
                    break;
                }
            }
            player=new Player(world,character,playerPosY,playerPosX,500);
            gameLogic=new GameLogic(player,leaf.getLeafs());
            playerUI=new PlayerUI(uiStage,player);
            roomBackground=new Texture("images/texture/room.jpg");
            outDoorBackground=new Texture ("images/texture/greenground2.png");
            roomBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
            hallWayBackground=new Texture("images/texture/room.jpg");
            hallWayBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);


            touchBackground = new Texture(Gdx.files.internal("images/texture/touchBackground.png")); // Замените на путь к вашей текстуре
            touchKnob = new Texture(Gdx.files.internal("images/texture/touchknob.png")); // Замените на путь к вашей текстуре

            // Создаем стиль для джойстика, используя загруженные текстуры
            touchpadStyle = new Touchpad.TouchpadStyle();
            touchpadStyle.background = new TextureRegionDrawable(new TextureRegion(touchBackground));
            touchpadStyle.knob = new TextureRegionDrawable(new TextureRegion(touchKnob));

            // Создаем Touchpad с настроенным стилем
            touchpad = new Touchpad(10, touchpadStyle); // Параметр deadzone (мертвая зона)
            touchpad.setBounds(150, 150, 400, 400);

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

            uiStage.addActor(touchpad);
            uiStage.addActor(playButton);
        }
        @Override
        public void render(float delta) {
            ScreenUtils.clear(0, 0, 0, 1);
            world.step(1/60f, 6, 2);

            camera.position.set(player.body.getPosition().x, player.body.getPosition().y, 0);
            camera.update();
            uiStage.getViewport().apply();
            game.batch.setProjectionMatrix(camera.combined);
            game.batch.begin();
            game.batch.draw(outDoorBackground, leaf.getLeafs().get(0).x,  leaf.getLeafs().get(0).y,  leaf.getLeafs().get(0).width,  leaf.getLeafs().get(0).height);
            for (Leaf currentLeaf : leaf.getLeafs())
                for (Rectangle currentHall : currentLeaf.halls)
                    drawTiledTexture(game.batch, hallWayBackground, currentHall.x, currentHall.y, currentHall.width, currentHall.height);

            for (Leaf currentLeaf : leaf.getLeafs())
                game.batch.draw(roomBackground, currentLeaf.room.x, currentLeaf.room.y, currentLeaf.room.width, currentLeaf.room.height);


            float moveX = touchpad.getKnobPercentX();
            float moveY = touchpad.getKnobPercentY();

//            player.body.setLinearVelocity(moveX * player.speed, moveY * player.speed);
            gameLogic.update(delta,moveX,moveY);

            player.render(game.batch);
                game.batch.end();
                // Обновляем и рисуем персонажа
                playerUI.update();
                uiStage.act(delta);
                uiStage.draw();
                debugRenderer.render(world, camera.combined);
        }
    @Override
    public void dispose() {
        stage.dispose();
        stage.dispose();
        uiStage.dispose();
        roomBackground.dispose();
        hallWayBackground.dispose();
        world.dispose();
        player.dispose();
        playerUI.dispose();
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
