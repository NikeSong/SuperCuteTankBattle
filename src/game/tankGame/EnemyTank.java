package game.tankGame;

import java.awt.*;
import java.util.Vector;

public class EnemyTank extends Hero implements Runnable{
    int stepOneTime = 10;//每一次走多远

    public EnemyTank(int x, int y, Dir dir, TankType type) {
        super(x, y, dir, type);
        new Thread(this).start();
    }

    @Override
    public void run() {
        while(true)
        {
            //休息
            try {
                Thread.sleep((int)(Math.random()*800));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //当前方向走一走
            for(int i=0;i<stepOneTime;i++) {
                this.Move(getDir());
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            //射击一次
            if(Math.random()>1) this.shotEnemy();
            //休息3秒
            try {
                Thread.sleep((int)(Math.random()*300)+1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }//睡眠5秒
            //随机改变一个方向
            switch ((int)(Math.random()*4+1))
            {
                case 1:this.setDir(Dir.UP);break;
                case 2:this.setDir(Dir.DOWN);break;
                case 3:this.setDir(Dir.LEFT);break;
                case 4:this.setDir(Dir.RIGHT);break;
            }
            //如果坦克死了，则退出线程
            if(!this.isLive) break;
        }
    }

}
