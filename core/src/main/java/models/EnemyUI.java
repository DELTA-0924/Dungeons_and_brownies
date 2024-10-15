package models;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class EnemyUI {
    private Stage stage;
    private Enemy enemy;

    public Table uiTable;
    private Image heartImage;
    private Label healthLabel;
    private Image shieldImage;
    private Label defenseLabel;
    private Image swordImage;
    private Label attackLabel;

    private BitmapFont font;
    private Texture heartTexture;
    private Texture shieldTexture;
    private Texture swordTexture;

    public EnemyUI(Stage stage, Enemy enemy) {
        this.stage = stage;
        this.enemy = enemy;

        // Создаём таблицу для UI
        uiTable = new Table();
        uiTable.setFillParent(true);
        uiTable.top().right(); // Располагаем UI в верхнем левом углу экрана
        uiTable.setTransform(true);


        // Загружаем текстуры
        heartTexture = new Texture("images/texture/heart.png");
        shieldTexture = new Texture("images/texture/shield.png");
        swordTexture = new Texture("images/texture/sword.png");

        // Создаём BitmapFont для отображения текста
        font = new BitmapFont(); // Либо загрузите другой шрифт, если нужно

        // Создаём картинки и метки (без Skin)
        heartImage = new Image(heartTexture);
        healthLabel = createLabel("HP: " + enemy.getHealth());

        shieldImage = new Image(shieldTexture);
        defenseLabel = createLabel("DEF: " + enemy.getProtection());

        swordImage = new Image(swordTexture);
        attackLabel = createLabel("ATK: " + enemy.getAttack());

        // Масштабируем текст в метках
        healthLabel.setFontScale(2.0f); // Увеличиваем текст в 2 раза
        defenseLabel.setFontScale(2.0f); // Увеличиваем текст в 2 раза
        attackLabel.setFontScale(2.0f); // Увеличиваем текст в 2 раза
        healthLabel.setScaleX(-1);
        defenseLabel.setScaleX(-1);
        attackLabel.setScaleX(-1);

        // Добавляем элементы в таблицу
        uiTable.add(heartImage).size(64, 64).pad(20); // Увеличили размер картинки и отступы
        uiTable.add(healthLabel).pad(20).left(); // Отступы и выравнивание текста
        uiTable.row(); // Новая строка
        uiTable.add(shieldImage).size(64, 64).pad(20);
        uiTable.add(defenseLabel).pad(20).left();
        uiTable.row(); // Новая строка
        uiTable.add(swordImage).size(64, 64).pad(20);
        uiTable.add(attackLabel).pad(20).left();
        // Добавляем таблицу на сцену
        stage.addActor(uiTable);

    }

    // Метод для создания метки без использования Skin
    private Label createLabel(String text) {
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = font;

        return new Label(text, style);
    }

    public void update() {
        // Обновляем значения здоровья, защиты и атаки
        healthLabel.setText("HP: " + enemy.getHealth());
        defenseLabel.setText("DEF: " + enemy.getProtection());
        attackLabel.setText("ATK: " + enemy.getAttack());
    }

    public void dispose() {
        // Освобождаем ресурсы
        heartTexture.dispose();
        shieldTexture.dispose();
        swordTexture.dispose();
        font.dispose();
    }
}
