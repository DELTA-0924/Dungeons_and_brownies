package common;

public class Point {
    public int x,y,cost;
    public Point(int x,int y,int cost){
        this.x=x;
        this.y=y;
        this.cost=cost;
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Point p = (Point) obj;
        return x == p.x && y == p.y;
    }
    public boolean isLessThan(Point p) {
        return this.cost > p.cost;
    }

}
