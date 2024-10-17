package models.enemy;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class RenderComponent {
    Texture texture;
    private float width, height;

    public RenderComponent(Texture texture, float width, float height) {
        this.texture = texture;
        this.width = width;
        this.height = height;
    }

    public void render(SpriteBatch batch, Body body) {
        batch.draw(texture, body.getPosition().x - width / 2, body.getPosition().y - height / 2, width, height);
    }

    public void dispose() {
        texture.dispose();
    }
}
