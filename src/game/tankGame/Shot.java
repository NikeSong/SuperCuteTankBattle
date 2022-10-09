package game.tankGame;

public class Shot implements Runnable{
    private int x;
    private int y;
    private Dir dir;
    private int speed = 20;
    public static int width = 10;
    public static int height = 20;
    boolean isLive = true;

    @Override
    public void run() {
        while(true)
        {
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            switch(dir){
                case UP :
                    y -= speed;
                    break;
                case DOWN:
                    y += speed;
                    break;
                case LEFT:
                    x -= speed;
                    break;
                case RIGHT:
                    x += speed;
                    break;
            }
            if(!(x>=0 && x<= DynamicPanel.PanelWidth && y>=0 && y<= DynamicPanel.PanelHeight)) break;

        }
        isLive = false;
    }

    public Shot(int x, int y, Dir dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Dir getDir() {
        return dir;
    }
}
