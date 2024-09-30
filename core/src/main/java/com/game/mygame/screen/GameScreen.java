package com.game.mygame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
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

public class GameScreen implements Screen , InputProcessor {

    OrthographicCamera camera;
    ShapeRenderer shapeRenderer;
    LeafGenerator leaf;
    Player player;
    final Roque game;
    Stage stage;
    ImageButton playButton;
    Texture playButtonImage;
    Texture roomBackground;
    Texture hallWayBackground;
    Joystick joystick;

    Texture character;
    int width=1080,cameraWidth=480;
    int height=720 ,cameraHeight=260;


        public GameScreen(final Roque game){
            this.game=game;
            int maxRooms=15;
            playButtonImage=new Texture("images/screen/buttonplay.png");
            shapeRenderer=new ShapeRenderer();
            joystick = new Joystick(100, 100, 50, 20);
            leaf=new LeafGenerator();
            stage=new Stage();

            Gdx.input.setInputProcessor(stage);
            camera=new OrthographicCamera();
            camera.setToOrtho(false,480,260);


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
            playButton.setPosition(cameraWidth-64,cameraHeight-64);

            leaf.generateLeaves(width,height,maxRooms);
            stage.getViewport().setCamera(camera);
            stage.addActor(playButton);

        }
        @Override
        public void show() {



            character=new Texture("images/texture/character/character.png");
            player=new Player(character,
                ((leaf.getLeafs().get(1).width-player.getWidth())+(leaf.getLeafs().get(1).x+player.getWidth()))/2,
                ((leaf.getLeafs().get(1).height-player.getHeight())+(leaf.getLeafs().get(1).y+player.getHeight()))/2,100);

            roomBackground=new Texture("images/texture/room.jpg");
            roomBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);

            hallWayBackground=new Texture("images/texture/room.jpg");
            hallWayBackground.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        }
        @Override
        public void render(float delta) {
            ScreenUtils.clear(0, 0, 0, 1);
            stage.act(delta);

            camera.position.set(player.getX() + player.getWidth() / 2, player.getY() + player.getHeight() / 2, 0);
            camera.update();

            game.batch.setProjectionMatrix(camera.combined);
            game.batch.begin();

            // Рисуем карту с использованием SpriteBatch
            for (Leaf currentLeaf : leaf.getLeafs()) {
                // Отрисовка комнат
                game.batch.draw(roomBackground, currentLeaf.room.x, currentLeaf.room.y, currentLeaf.room.width, currentLeaf.room.height);

                // Отрисовка коридоров
                for (Rectangle currentHall : currentLeaf.halls) {
                    drawTiledTexture(game.batch, hallWayBackground, currentHall.x, currentHall.y, currentHall.width, currentHall.height);
                }
            }

            // Обновляем и рисуем персонажа
            //player.update(delta, moveX, moveY);
            player.render(game.batch);

            joystick.update(Gdx.input.getX(), Gdx.input.getY());

            Vector2 direction = joystick.getDirection();
            player.move(direction.x * player.getSpeed() * delta, direction.y * player.getSpeed() * delta);
            stage.draw();
            game.batch.end();

            shapeRenderer.setProjectionMatrix(camera.combined);
            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            joystick.draw(shapeRenderer);
            shapeRenderer.end();
        }

    @Override
    public void dispose() {
        stage.dispose();
        playButtonImage.dispose();
        roomBackground.dispose();
        hallWayBackground.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        joystick.touchDown(screenX, screenY);
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        joystick.touchUp();
        return true;
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
