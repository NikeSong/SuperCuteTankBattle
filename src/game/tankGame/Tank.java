package game.tankGame;

import java.awt.*;
import java.util.Vector;

public class Tank
{
    public int x;
    public int y;
    boolean isLive = true;
    private static int sizeRed = 2;   //  坦克原图片尺寸减小的倍数
    public static int width = 100/sizeRed;
    public static int height = 100/sizeRed;
    public int speed = 10;
    public Dir dir = Dir.UP;
    public TankType type = TankType.NORMAL;
    public Tank(int x, int y, Dir dir, TankType type) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.type = type;
    }
/*    Boolean OutOfBorder(int nx,int ny)//
    {

    }*/
/*    public void Move(Dir dir)
    {
        //1.判断在当前方向下移动一个speed会不会碰壁
        int nx = x,ny = y;
        switch(dir)
        {
            case UP:
                ny-=speed;
                if(OutOfBorder(nx,ny)) y=0;
            case DOWN:  speed+=dis;break;
            case LEFT:  speed-=dis;break;
            case RIGHT:  speed+=dis;break;
        }

    }*/

    public void setType(TankType type) {
        this.type = type;
    }

    public TankType getType() {
        return type;
    }


    public void setDir(Dir dir) {
        this.dir = dir;
    }
    public Dir getDir() {
        return dir;
    }
    public void setX(int x) {
        this.x = x;
    }
    public void setY(int y) {
        this.y = y;
    }
    public int getX() {
        return x;
    }
    public int getY() {
        return y;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        Vector<Point> points = new Vector<Point>();
        points.add(new Point(x,y));
        points.add(new Point(x+Tank.width,y));
        points.add(new Point(x,y+Tank.height));
        points.add(new Point(x+Tank.width,y+Tank.height));
        return "This tank position:"+points+"   Direction:"+dir;
    }
}
enum Dir
{
    UP,
    DOWN,
    LEFT,
    RIGHT
}

enum TankType
{
    NORMAL,
    ENEMY
}