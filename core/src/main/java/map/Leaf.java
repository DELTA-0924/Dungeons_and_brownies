package map;

import java.awt.Rectangle;
import java.util.Random;
import java.util.Vector;

public class Leaf {
    private static final int MIN_LEAF_SIZE = 150;
    public int y, x, width, height; // позиция и размер Leaf
    public Leaf leftChild; // левый дочерний Leaf
    public Leaf rightChild; // правый дочерний Leaf
    public Rectangle room; // комната внутри Leaf
    public Vector<Rectangle> halls; // коридоры для соединения с другими Leaf

    public Leaf(int x, int y, int width, int height) {
        // инициализация Leaf
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        halls = new Vector<>();
    }

    public boolean split() {
        // начать деление Leaf на два дочерних
        if (leftChild != null || rightChild != null)
            return false; // уже разделено! Завершение!

        // определить направление деления
        boolean splitH = new Random().nextBoolean();
        if (width > height && (double) width / height >= 1.25)
            splitH = false;
        else if (height > width && (double) height / width >= 1.25)
            splitH = true;

        int max = splitH ? height : width; // определить максимальную высоту или ширину
        max -= MIN_LEAF_SIZE;

        if (max <= MIN_LEAF_SIZE)
            return false; // область слишком мала для дальнейшего деления

        int split = MIN_LEAF_SIZE + new Random().nextInt(max - MIN_LEAF_SIZE); // определить, где будем делить

        // создать левые и правые дочерние Leaf в зависимости от направления деления
        if (splitH) {
            leftChild = new Leaf(x, y, width, split);
            rightChild = new Leaf(x, y + split, width, height - split);
        } else {
            leftChild = new Leaf(x, y, split, height);
            rightChild = new Leaf(x + split, y, width - split, height);
        }
        return true; // деление успешно!
    }
}

