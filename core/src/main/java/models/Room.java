package common;

public class Room {
    public int x;
    public int y;
    public int w;
    public int h;
    public Room(int x,int y,int w,int h){
        this.x=x;
        this.y=y;
        this.w=w;
        this.h=h;
    }
    public Boolean intersect(final Room r)  {
        return  !(r.x >= (x + w) || x >= (r.x + r.w) || r.y >= (y + h) || y >= (r.y + r.h));
    }

    public Point getCenter() {
        return new Point(x + w / 2, y + h / 2,0);
    }
}
