package com.game.mygame.screen;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

public class Joystick {
    private float centerX, centerY;
    private float radius;
    private float knobRadius;
    private Vector2 knobPosition;
    private boolean isTouching;

    public Joystick(float centerX, float centerY, float radius, float knobRadius) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.radius = radius;
        this.knobRadius = knobRadius;
        this.knobPosition = new Vector2(centerX, centerY);
        this.isTouching = false;
    }

    public void update(float touchX, float touchY) {
        if (isTouching) {
            System.out.println("двигается ");
            float dx = touchX - centerX;
            float dy = touchY - centerY;
            float distance = (float) Math.sqrt(dx * dx + dy * dy);
            if (distance < radius) {
                knobPosition.set(touchX, touchY);
            } else {
                float angle = (float) Math.atan2(dy, dx);
                knobPosition.set(centerX + radius * (float) Math.cos(angle), centerY + radius * (float) Math.sin(angle));
            }
        }
    }

    public void touchDown(float x, float y) {
            System.out.println("трогаешь");
            isTouching = true;
            knobPosition.set(x, y);
    }

    public void touchUp() {
        isTouching = false;
        knobPosition.set(centerX, centerY); // Возвращаем джойстик в центр
    }

    public Vector2 getDirection() {
        if (isTouching) {
            return new Vector2(knobPosition.x - centerX, knobPosition.y - centerY).nor();
        }
        return new Vector2(0, 0);
    }

    public void draw(ShapeRenderer shapeRenderer) {

        shapeRenderer.setColor(Color.GRAY);
        shapeRenderer.circle(centerX, centerY, radius);


        shapeRenderer.setColor(Color.BLUE);
        shapeRenderer.circle(knobPosition.x, knobPosition.y, knobRadius);
    }
}
