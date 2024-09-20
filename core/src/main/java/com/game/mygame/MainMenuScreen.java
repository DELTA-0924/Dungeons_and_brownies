package com.game.mygame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class MainMenuScreen implements Screen {
    final Roque game;
    OrthographicCamera camera;
    public MainMenuScreen(final Roque game){
        this.game=game;


        camera=new OrthographicCamera();
        camera.setToOrtho(false,800,480);


    }


    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(0,0,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
        game.font.getData().setScale(5,5);
        game.batch.begin();

        game.font.draw(game.batch,"Play >",100,180);
        game.font.draw(game.batch,"Quit >",100,120);
        game.batch.end();
        if(Gdx.input.isTouched()){
            game.setScreen(new GameScreen(game));
            dispose();
        }
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
