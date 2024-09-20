package com.game.mygame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.ScreenUtils;
import java.util.Random;
public class GameScreen implements Screen {

    OrthographicCamera camera;
    final Roque game;
    Stage stage;
    ImageButton playButton;
    Texture playButtonImage;
    Texture[] groundTextures;
    Texture stoneGround;
    int [][]grid;
    int tileSize=64;
    public GameScreen(final Roque game){
        this.game=game;

        Texture playButtonImage=new Texture("images/screen/buttonplay.png");
        groundTextures=new Texture []{
                new Texture ("images/texture/greenground2.png")
        };
        stoneGround=new Texture("images/texture/stoneground.png");
        int rows = Gdx.graphics.getHeight() / tileSize;
        int cols = Gdx.graphics.getWidth() / tileSize;

        // Создаем сетку, которая будет хранить случайные текстуры
        grid = new int[rows][cols];

        // Заполняем сетку случайными индексами текстур
        Random random = new Random();
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                // Определяем, что клетки на определенной позиции будут тропинкой
                if (col == cols / 2 || col == cols / 2 + 1) { // Широкая вертикальная тропинка по центру
                    grid[row][col] = -1; // Для тропинки ставим специальный маркер
                } else {
                    // Для остальных клеток выбираем случайную текстуру земли
                    grid[row][col] = random.nextInt(groundTextures.length); // Случайная земля
                }
            }
        }

        stage=new Stage();
        Gdx.input.setInputProcessor(stage);

        camera=new OrthographicCamera();
        camera.setToOrtho(false,800,480);
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


        playButton.setSize(64,64);
        playButton.setPosition(400-64,480-64);
        stage.addActor(playButton);
    }
    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(1,1,1,1);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.batch.begin();
        for (int row = 0; row < grid.length; row++) {
            for (int col = 0; col < grid[row].length; col++) {
                if (grid[row][col] == -1) {
                    // Если это тропинка, отрисовываем текстуру булыжников
                    game.batch.draw(stoneGround, col * tileSize, row * tileSize, tileSize, tileSize);
                } else {
                    // Если это земля, отрисовываем случайную текстуру земли
                    int textureIndex = grid[row][col];
                    game.batch.draw(groundTextures[textureIndex], col * tileSize, row * tileSize, tileSize, tileSize);
                }
            }
        }
        game.batch.end();
        stage.act();
        stage.draw();

    }

    @Override
    public void dispose() {
        stage.dispose();
        playButtonImage.dispose();
        for (Texture texture : groundTextures) {
            texture.dispose();
        }
        stoneGround.dispose();
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
