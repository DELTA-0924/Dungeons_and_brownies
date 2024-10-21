package com.game.mygame.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.game.mygame.screen.Data.DatabaseHelper;

public class Roque extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public DatabaseHelper db;
    public void create(){
        db=new DatabaseHelper();
        db.openDatabase();
        db.createTables();
        batch=new SpriteBatch();
        font=new BitmapFont();
        this.setScreen(new MainMenuScreen(this));
    }

    public void render(){
        super.render();
    }
    public void dispose(){
        if (db != null) {
            db.closeDatabase();
        }
        batch.dispose();
        font.dispose();
    }
}
