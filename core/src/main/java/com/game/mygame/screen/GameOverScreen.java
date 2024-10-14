package com.game.mygame.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

import models.Player;

public class GameOverScreen {
    private Stage stage;
    private Texture blackBackground;
    private Texture playerSpriteTexture;
    private Texture restartButtonTexture;
    private Player player;
    private Roque game;
    public ImageButton restartButton;
    public Image blackScreen,playerSprite;
    public Label gameOverLabel;
    public GameOverScreen(Player player,Roque game,Stage stage) {
        this.player = player;
        this.game=game;
        this.stage =stage;

        // Чёрный фон
        blackBackground = new Texture("images/screen/gameoverBackground.jpg");
         blackScreen = new Image(blackBackground);
        blackScreen.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        stage.addActor(blackScreen);

        // Спрайт игрока (например, в центре экрана)
        playerSpriteTexture = new Texture("images/texture/character/character-dead.png");
         playerSprite = new Image(playerSpriteTexture);
        playerSprite.setPosition(
            Gdx.graphics.getWidth() / 2f - playerSprite.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - playerSprite.getHeight() / 2f + 100
        );
        stage.addActor(playerSprite);
        // Текст "Game Over"
        Label.LabelStyle labelStyle = new Label.LabelStyle();
        labelStyle.font = new BitmapFont(); // Шрифт по умолчанию
         gameOverLabel = new Label(" No Game Over", labelStyle);
        gameOverLabel.setFontScale(10); // Увеличиваем текст
        gameOverLabel.setPosition(
            Gdx.graphics.getWidth() / 2f+50,
            Gdx.graphics.getHeight() / 2f + 50
        );
        stage.addActor(gameOverLabel);
        // Кнопка "Restart"
        restartButtonTexture = new Texture("images/screen/restartButtonTexture.png");
         restartButton = createRestartButton();
        restartButton.setPosition(
            Gdx.graphics.getWidth() / 2f - restartButton.getWidth() / 2f,
            Gdx.graphics.getHeight() / 2f - restartButton.getHeight() / 2f - 100
        );
        stage.addActor(restartButton);
        blackScreen.setVisible(false);
        restartButton.setVisible(false);
        gameOverLabel.setVisible(false);
        playerSprite.setVisible(false);
    }

    private ImageButton createRestartButton() {
        ImageButton.ImageButtonStyle buttonStyle = new ImageButton.ImageButtonStyle();
        buttonStyle.imageUp = new TextureRegionDrawable(new TextureRegion(restartButtonTexture));

        ImageButton button = new ImageButton(buttonStyle);
        button.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Логика перезапуска игры
                restartGame();
            }
        });
        return button;
    }

    private void restartGame() {
        player.reset();
        game.setScreen(new MainMenuScreen(game));
    }

    public void render(float delta) {
        blackScreen.setVisible(true);
        restartButton.setVisible(true);
        gameOverLabel.setVisible(true);
        playerSprite.setVisible(true);
    }

    public void dispose() {
        stage.dispose();
        blackBackground.dispose();
        playerSpriteTexture.dispose();
        restartButtonTexture.dispose();
    }
}
