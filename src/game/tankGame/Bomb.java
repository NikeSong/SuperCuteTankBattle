package game.tankGame;

import java.awt.*;

public class Bomb {
    int x,y; //爆炸的位置
    int life = 9;//爆炸的生命周期
    boolean isLive = true;
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;
    public void lifeDown()
    {
        if(life>0) life--;
        else isLive = false;
    }
    public Bomb(int x, int y) {
        this.x = x;
        this.y = y;
        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage("appData/images/bomp/bump1.png");
        image2 = Toolkit.getDefaultToolkit().getImage("appData/images/bomp/bump2.png");
        image3 = Toolkit.getDefaultToolkit().getImage("appData/images/bomp/bump3.png");
    }
}
