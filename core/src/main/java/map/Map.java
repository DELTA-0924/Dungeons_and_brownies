package map;

import static com.badlogic.gdx.math.MathUtils.random;

import static java.lang.Math.abs;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.PriorityQueue;
import java.util.Vector;

import common.Point;
import common.Room;

public class Map {
    public int m_width;
    static final int[][] directions={{1,0}, {0,1}, {-1,0}, {0,-1}};
    public int m_height;
    public ArrayList<Integer> m_data;
    public ArrayList<Room>m_rooms=new ArrayList<Room>();
    public Map(int width,int height){
            m_width=width;
            m_height=height;
            m_data=new ArrayList<Integer>(width*height);
            for(int i=0;i<=width*height;i++){
                m_data.add(0);
            }
    }
    public void generate(int roomsCount){
        m_rooms.clear();
        for (int i=0;i<=roomsCount;++i)for(int j=0;j<=1000;++j){
            final int w=random(10,31),h=random(10,31);
            final Room room=new Room(3+random(m_width-w-6),3+random(m_height-h-6),w,h);
            var intersect = m_rooms.stream()
                .filter(r -> room.intersect(r))
                .findFirst()
                .orElse(null);;
            if (intersect == null) {
                m_rooms.add(room);
                break;
            }
        }
        // Зануляем карту индексом 0
        Collections.fill(m_data, 0); // или mData = new ArrayList<>(Collections.nCopies(mWidth * mHeight, 0));

// Пространство комнат заполняем индексом 1
        for (Room room : m_rooms) {
            for (int x = 0; x < room.w; x++) {
                for (int y = 0; y < room.h; y++) {
                    m_data.set((room.x + x) + (room.y + y) * m_width, 1);
                }
            }
        }
        generatePassage(m_rooms.get(0).getCenter(),m_rooms.get(m_rooms.size()-1).getCenter());
    }

    public void generatePassage(final Point start , final Point finish){
        ArrayList<Integer>parents=new ArrayList<>(Collections.nCopies(m_height*m_width,-1));
        PriorityQueue<Point>active=new PriorityQueue<>(Comparator.comparingInt(p->p.cost));
        active.add(start);
        while (!active.isEmpty()) {
            // берем самую "дешевую" клетку из списка доступных
            final Point point = active.peek();
            active.poll();

            if (point.equals(finish))
                break;

            // продолжаем поиск в доступных направлениях
            for (int i = 0; i < 4; ++i) {
                Point p = new Point(point.x - directions[i][0], point.y - directions[i][1], 0);
                if (p.x < 0 || p.y < 0 || p.x >= m_width || p.y >= m_height)
                    continue;

                // если мы еще не посещали заданную клетку
                if (parents.get(p.x + p.y * m_width) < 0) {
                    // вычисляем "стоимость" указанной клетки
                    p.cost = calcCost(p, finish);
                    active.add(p);
                    parents.set(p.x + p.y * m_width, i);
                }
            }
            System.out.println("Generation ways ..." );
        }
        // путь найден - теперь прокладываем его на карте, начиная с конца
        Point point = finish;
        while (!(point.equals(start))) {
            m_data.set(point.x + point.y * m_width, 1);

            final int[] direction = directions[parents.get(point.x + point.y * m_width)];
            point.x += direction[0];
            point.y += direction[1];
            System.out.println("mark ways ..." );
        }
    }

    int calcCost(final Point p, final Point finish) {
        int cost=abs(p.x - finish.x) + abs(p.y - finish.y);
        System.out.println("const "+cost);
        return cost;
    }
}
