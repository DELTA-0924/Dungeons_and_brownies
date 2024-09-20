package com.game.mygame;



import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

public class UIElements {
    private int width;
    private BitmapFont font= new BitmapFont();
    public Skin createskin(){
        Skin skin=new Skin();
        Pixmap pixmap = new Pixmap(width / 4, width / 4, Pixmap.Format.RGBA8888); // Use TRANSLUCENT format
        skin.add("background", new Texture(pixmap));
        pixmap.setColor(0,0,0,0);
        pixmap.fill();
        skin.add("background",new Texture(pixmap));
        skin.add("default",font);

        NinePatch ninePatch = new NinePatch(new Texture ("images/screen/button-background.png"));


        // Button Style with transparent background
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = skin.getFont("default");
        BitmapFont font = textButtonStyle.font;
        font.getData().setScale(2);

        // Применяем изменения
        textButtonStyle.font = font;

        textButtonStyle.fontColor= Color.ORANGE;
        textButtonStyle.up = new NinePatchDrawable(ninePatch);
        skin.add("default", textButtonStyle);

        pixmap.dispose();
        return skin;
    }
    void dispose(){
        font.dispose();
    }
}
