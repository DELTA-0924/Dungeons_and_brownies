package map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;
import java.util.Vector;



public class Leaf {
    private static final int MIN_LEAF_SIZE = 190;
    public int y, x, width, height; // позиция и размер Leaf
    public Leaf leftChild; // левый дочерний Leaf
    public Leaf rightChild; // правый дочерний Leaf
    public Rectangle room=new Rectangle(); // комната внутри Leaf
    public Vector<Rectangle> halls; // коридоры для соединения с другими Leaf
    Random random = new Random();
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
    public void createRooms(){
        if(leftChild!=null || rightChild!=null){
            if(leftChild!=null){
                leftChild.createRooms();
            }
            if(rightChild!=null){
                rightChild.createRooms();
            }
            if (leftChild != null && rightChild != null)
            {
                createHall(leftChild.getRoom(), rightChild.getRoom());
            }
        }
        else{
            Vector2 roomSize;
            Vector2 roomPos;
//           roomSize=new Vector2(random.nextInt(3)*(width-2),random.nextInt(3)*(height-2));
//          roomPos = new Vector2(random.nextInt(1)*(width-roomSize.x-1),random.nextInt(1)*(height-roomSize.y-1));

            roomSize=new Vector2(randomNumber(80,width-2),randomNumber(80,height-2));
            roomPos = new Vector2(randomNumber( 1, (int) (width-roomSize.x-1)),randomNumber( 1,(int)(height-roomSize.y-1)  ));
            room =new Rectangle(x+(int)roomPos.x,y+(int)roomPos.y,(int)roomSize.x,(int)roomSize.y);

        }
    }

    public Rectangle getRoom()
    {
        // iterate all the way through these leafs to find a room, if one exists.
        if (room != null)
            return room;
        else
        {
            Rectangle lRoom=new Rectangle();
            Rectangle rRoom=new Rectangle();
            if (leftChild != null)
            {
                lRoom = leftChild.getRoom();
            }
            if (rightChild != null)
            {
                rRoom = rightChild.getRoom();
            }
            if (lRoom == null && rRoom == null)
                return null;
            else if (rRoom == null)
                return lRoom;
            else if (lRoom == null)
                return rRoom;
            else if (random.nextDouble() > .5)
                return lRoom;
            else
                return rRoom;
        }
    }

    public void createHall(Rectangle l,  Rectangle r)
    {
        // Вводим разные размеры для горизонтальных и вертикальных коридоров
        int horizontalCorridorWidth = 50;  // Широкие горизонтальные коридоры
        int verticalCorridorWidth = 30;    // Узкие вертикальные коридоры

        halls = new Vector<Rectangle>();

        Vector2 point1 = new Vector2(
            randomNumber((int) l.x + 10, (int) (l.x + l.width) - 20),
            randomNumber((int) l.y + 1, (int) (l.y + l.height) - 20)
        );

        Vector2 point2 = new Vector2(
            randomNumber((int) r.x + 10, (int) (r.x + r.width) - 20),
            randomNumber((int) r.y + 10, (int) (r.y + r.height) - 20)
        );

        float w = point2.x - point1.x;
        float h= point2.y - point1.y;
           w *= 3;
          h *= 2;

        if (w < 0)
        {
            if (h < 0)
            {
                if (random.nextDouble() < 0.5)
                {
                    halls.add(new Rectangle(point2.x, point1.y, Math.abs(w), horizontalCorridorWidth));
                    halls.add(new Rectangle(point2.x, point2.y, verticalCorridorWidth, Math.abs(h)));
                }
                else
                {
                    halls.add(new Rectangle(point2.x, point2.y, Math.abs(w), horizontalCorridorWidth));
                    halls.add(new Rectangle(point1.x, point2.y, verticalCorridorWidth, Math.abs(h)));
                }
            }
            else if (h > 0)
            {
                if (random.nextDouble() < 0.5)
                {
                    halls.add(new Rectangle(point2.x, point1.y, Math.abs(w), horizontalCorridorWidth));
                    halls.add(new Rectangle(point2.x, point1.y, verticalCorridorWidth, Math.abs(h)));
                }
                else
                {
                    halls.add(new Rectangle(point2.x, point2.y, Math.abs(w), horizontalCorridorWidth));
                    halls.add(new Rectangle(point1.x, point1.y, verticalCorridorWidth, Math.abs(h)));
                }
            }
            else // if (h == 0)
            {
                halls.add(new Rectangle(point2.x, point2.y, Math.abs(w), horizontalCorridorWidth));
            }
        }
        else if (w > 0)
        {
            if (h < 0)
            {
                if (random.nextDouble() < 0.5)
                {
                    halls.add(new Rectangle(point1.x, point2.y, Math.abs(w), horizontalCorridorWidth));
                    halls.add(new Rectangle(point1.x, point2.y, verticalCorridorWidth, Math.abs(h)));
                }
                else
                {
                    halls.add(new Rectangle(point1.x, point1.y, Math.abs(w), horizontalCorridorWidth));
                    halls.add(new Rectangle(point2.x, point2.y, verticalCorridorWidth, Math.abs(h)));
                }
            }
            else if (h > 0)
            {
                if (random.nextDouble() < 0.5)
                {
                    halls.add(new Rectangle(point1.x, point1.y, Math.abs(w), horizontalCorridorWidth));
                    halls.add(new Rectangle(point2.x, point1.y, verticalCorridorWidth, Math.abs(h)));
                }
                else
                {
                    halls.add(new Rectangle(point1.x, point2.y, Math.abs(w), horizontalCorridorWidth));
                    halls.add(new Rectangle(point1.x, point1.y, verticalCorridorWidth, Math.abs(h)));
                }
            }
            else // if (h == 0)
            {
                halls.add(new Rectangle(point1.x, point1.y, Math.abs(w), horizontalCorridorWidth));
            }
        }
        else // if (w == 0)
        {
            if (h < 0)
            {
                halls.add(new Rectangle(point2.x, point2.y, verticalCorridorWidth, Math.abs(h)));
            }
            else if (h > 0)
            {
                halls.add(new Rectangle(point1.x, point1.y, verticalCorridorWidth, Math.abs(h)));
            }
        }
    }

    public static int randomNumber(int min, int max) {
        if(max<=0||min<=0)
            return 0;
        Random random = new Random();
        return random.nextInt(max - min)+min;
    }

}

