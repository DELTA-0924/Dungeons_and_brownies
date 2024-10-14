package map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;
import java.util.Vector;



public class Leaf {
    private static final int MIN_LEAF_SIZE = 300;
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
        // Проверяем, уже ли есть дочерние элементы
        if (leftChild != null || rightChild != null) {
            return false; // Уже разделено
        }

        // Определяем направление деления (по горизонтали или вертикали)
        boolean splitH = new Random().nextBoolean();

        // Проверка на соотношение сторон, чтобы принять решение о направлении деления
        if (width > height && (double) width / height >= 1.25) {
            splitH = false; // Делим по вертикали
        } else if (height > width && (double) height / width >= 1.25) {
            splitH = true; // Делим по горизонтали
        }

        // Определяем максимальный размер для деления с учетом минимального размера
        int max = splitH ? height : width;

        // Убедимся, что можно выполнить деление
        if (max <= MIN_LEAF_SIZE * 2) {
            return false; // Область слишком мала для деления на две части с минимальным размером
        }

        // Определяем точку деления. Она должна быть такой, чтобы обе части после деления
        // были не меньше MIN_LEAF_SIZE
        int split = MIN_LEAF_SIZE + new Random().nextInt(max - MIN_LEAF_SIZE * 2);

        // Создаём дочерние элементы на основе выбранного направления деления
        if (splitH) {
            // Делим по горизонтали
            leftChild = new Leaf(x, y, width, split);
            rightChild = new Leaf(x, y + split, width, height - split);
        } else {
            // Делим по вертикали
            leftChild = new Leaf(x, y, split, height);
            rightChild = new Leaf(x + split, y, width - split, height);
        }

        return true; // Деление успешно!
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
            roomSize=new Vector2(randomNumber(200,width-20),randomNumber(200,height-20));
            roomPos = new Vector2(randomNumber( 1, (int) (width-roomSize.x-1)),randomNumber( 1,(int)(height-roomSize.y-1)  ));
//            System.out.println("room size"+roomSize.x+" "+roomSize.y);
//            System.out.println("room pos"+roomPos.x+" "+roomPos.y);
            room =new Rectangle(x+(int)roomPos.x,y+(int)roomPos.y,(int)roomSize.x,(int)roomSize.y);
            //System.out.println("room pos x="+room.x +" y="+room.y+" room size"+room.width+" "+ room.height);
        }
    }

    public Rectangle getRoom()
    {
        // iterate all the way through these leafs to find a room, if one exists.
        if (room != null &&(!(room.height==0 ||room.width==0 || room.x==0 ||room.y==0) )) {
            //System.out.println("Returning current room: " + room.x + ", " + room.y + " size: " + room.width + "x" + room.height);
            return room;
        }
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

    public void createHall(Rectangle l, Rectangle r) {
        // Увеличиваем ширину коридоров
        int horizontalCorridorWidth = 75;  // Увеличиваем ширину горизонтальных коридоров
        int verticalCorridorWidth = 75;    // Увеличиваем ширину вертикальных коридоров

        halls = new Vector<>();
        if(l.height==0 ||r.height==0 || l.x==0 || r.x==0 || l.width==0 || r.width==0)
            return;
        // Выбираем случайные точки внутри двух комнат
        Vector2 point1=new Vector2(0,0);
        Vector2 point2=new Vector2(0,0);
        try {
            point1=new Vector2(l.x+l.width/2,l.y+l.height/2);
            point2=new Vector2(r.x+r.width/2,r.y+r.height/2);
//             point1 = new Vector2(
//                randomNumber((int) l.x + 75, (int) (l.x + l.width) - 75),
//                randomNumber((int) l.y + 75, (int) (l.y + l.height) - 75)
//            );
//             point2 = new Vector2(
//                randomNumber((int) r.x + 75, (int) (r.x + r.width) - 75),
//                randomNumber((int) r.y + 75, (int) (r.y + r.height) - 75)
//            );
        }catch (IllegalArgumentException e){
        }
        point1.set(point1.x,point1.y);
        point2.set(point2.x,point2.y);
        // Вычисляем разницы между точками по X и Y
        float w = (point2.x) -(point1.x);
        float h =( point2.y)-( point1.y);
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
        Random random = new Random();
        return random.nextInt(max - min)+min;
    }

}

