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
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import com.badlogic.gdx.physics.box2d.World;

import GameMechanic.GameLogic;
import GameMechanic.MyContactListener;
import models.Enemy;
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
    private Button  playButton,attackButton;
    private Texture  playTexture,attackButtonTexture;
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
    Enemy enemy;
    GameOverScreen gameover;
    Array<Enemy> enemies = new Array<>();
    private MyContactListener contactListener;
    public GameScreen(final Roque game){
        this.game=game;
        int maxRooms=20;

        world = new World(new Vector2(0, 0), true);
        contactListener=new MyContactListener();
        world.setContactListener(contactListener);
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
//        for (Leaf currentLeaf : leaf.getLeafs()) {
//            map.createRoom( currentLeaf.room.x, currentLeaf.room.y,
//
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
            player=new Player(world,character,playerPosY,playerPosX,uiStage);
            gameover=new GameOverScreen(player,game,uiStage);
            character=new Texture("images/texture/character/knight-type-1.png");

            Leaf currentLeaf;
            for (int i = leaf.getLeafs().size() - 1; i >= 0; i--) {
                 currentLeaf = leaf.getLeafs().get(i);
                if((currentLeaf.room.x+ currentLeaf.room.width)!=0 && ((currentLeaf.room.y+ currentLeaf.room.height)!=0)){
                    enemy=new Enemy(world,player,10,5,500,10,currentLeaf.room,character,uiStage);
                    enemies.add(enemy);
                    if(enemies.size>2)
                        break;
                }
            }
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
            float scaleFactor = 0.7f; // Масштаб уменьшаем на 30%
            touchpadStyle.knob.setMinWidth(touchKnob.getWidth() * scaleFactor);
            touchpadStyle.knob.setMinHeight(touchKnob.getHeight() * scaleFactor);
            // Создаем Touchpad с настроенным стилем
            touchpad = new Touchpad(10, touchpadStyle); // Параметр deadzone (мертвая зона)
            touchpad.setBounds(150, 150, 400, 400);

            attackButtonTexture=new Texture("images/screen/attackButton.png");
            attackButton=new Button(new TextureRegionDrawable(attackButtonTexture));

            attackButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    if (contactListener.isPlayerTouchingEnemy && contactListener.currentEnemy != null) {
                        contactListener.currentEnemy.takeDamage(player.getAttack()); // Наносим урон текущему врагу
                    }
                }
            });
            attackButton.setSize(128,128);
            attackButton.setPosition(width-300,200);

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
            uiStage.addActor(attackButton);
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
          gameLogic.update(delta,moveX,moveY);

          player.render(game.batch);
          for(Enemy enemy:enemies) {
              enemy.render(game.batch);
              enemy.update();
          }
          game.batch.end();

          playerUI.update();

          attackButton.setVisible(contactListener.isPlayerTouchingEnemy);

        if(player.dead) {
            attackButton.setVisible(false);
            playerUI.uiTable.setVisible(false);
            playButton.setVisible(false);
            touchpad.setVisible(false);
            gameover.render(delta);
            enemy.enemyUI.uiTable.setVisible(false);
        }
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
        gameover.dispose();
        enemy.dispose();
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
