package com.game.mygame.screen.common;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class DamageText extends Actor {
    private BitmapFont font;
    private String damageValue;
    private float displayTime;
    private float elapsedTime;
    private float startX, startY;

    public DamageText(int damage, float x, float y) {
        this.font = new BitmapFont(); // Можно загрузить кастомный шрифт при необходимости
        this.damageValue = "-" + damage;
        this.startX = x+20;
        this.startY = y;
        this.displayTime = 1.5f; // Время, в течение которого число будет отображаться
        this.elapsedTime = 0f;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsedTime += delta;

        if (elapsedTime >= displayTime) {
            this.remove(); // Удаляем объект, когда время истечет
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        // Рисуем текст красного цвета
        font.setColor(1, 0, 0, 1); // Красный цвет
        font.getData().setScale(5.0f); // Масштаб шрифта
        font.draw(batch, damageValue, startX, startY + (elapsedTime * 20)); // Число "поднимается" вверх
    }
}
