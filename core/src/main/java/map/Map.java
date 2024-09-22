package map;

import static com.badlogic.gdx.math.MathUtils.random;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

import common.Room;

public class Map {
    public int m_width;
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
            final int w=random(10,51),h=random(10,51);
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

    }

}
