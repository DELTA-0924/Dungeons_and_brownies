package com.game.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class MainMenuScreen implements Screen {
    final Roque game;
    Texture texture;
    OrthographicCamera camera;
    TextButton playButton,quitButton;
    Stage stage;
    Skin skin;
    private UIElements UIElements = new UIElements();
    public MainMenuScreen(final Roque game){
        this.game=game;
        stage=new Stage();
        Gdx.input.setInputProcessor(stage);

        camera=new OrthographicCamera();
        camera.setToOrtho(false,800,480);


        skin = UIElements.createskin();


        playButton=new TextButton("Play",skin);
        quitButton=new TextButton("Quit",skin);

        playButton.setWidth(200);
        playButton.setHeight(150);

        quitButton.setWidth(200);
        quitButton.setHeight(150);

        playButton.setPosition(100,180);
        quitButton.setPosition(100,30);
        stage.addActor(playButton);
        stage.addActor(quitButton);

        texture=new Texture("images/screen/backgrounde.jpg");
    }


    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.font.getData().setScale(5,5);
        game.batch.begin();
        game.batch.draw(texture,0,0);

        game.batch.end();
        if(Gdx.input.isTouched()){
            game.setScreen(new GameScreen(game));
            dispose();
        }
        stage.act();
        stage.draw();
    }


    @Override
    public void show() {

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
    public void dispose() {

    }
}
