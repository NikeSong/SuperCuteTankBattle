package game.tankGame;

import java.util.Vector;

//自己的坦克
public class Hero extends Tank{
    public Vector<Shot> bullets = new Vector<Shot>();
    static Vector<Hero> allHeroes = new Vector<Hero>();//获得所有的坦克列表
    int interval = 0;
    //移动过程中若即将碰到block或者敌人时（距离小于一个speed单位）
    // 则移动当前坦克的距离为interval，即：碰上去.


    public static void setEnemyTanks(Vector<Hero> heroes) {
        Hero.allHeroes = heroes;
    }

    public Hero(int x, int y, Dir dir, TankType type) {
        super(x, y, dir, type);
    }
    public void shotEnemy()
    {
        Shot bullet =null;
        switch (this.getDir())
        {
            case UP :
                bullet = new Shot(this.getX()+Tank.width/2 - Shot.width/2,this.getY(),this.getDir());
                break;
            case DOWN:
                bullet = new Shot(this.getX()+Tank.width/2- Shot.width/2,this.getY()+Tank.height,this.getDir());
                break;
            case RIGHT:
                bullet = new Shot(this.getX()+Tank.height,this.getY()+Tank.width/2- Shot.width/2,this.getDir());
                break;
            case LEFT:
                bullet = new Shot(this.getX(),this.getY()+Tank.width/2- Shot.width/2,this.getDir());
                break;
        }
        bullets.add(bullet);
        //启动shot线程,子弹开始不断移动
        new Thread(bullet).start();
    }

    public void Move(Dir dir)
    {
        boolean touchOtherTank = isTouchOtherTank();
        boolean touchBlock = isTouchBlock();
        switch (dir)
        {
            case UP:
                if(!touchBlock && !touchOtherTank)//没碰到别的坦克且不会出界
                    y-=speed;
                else if(touchBlock)//没碰到别的坦克，只是出界了
                    y-=interval;//移动到界上去
                break;
            case DOWN:
                if(!touchBlock && !touchOtherTank)
                    y+=speed;
                else if(touchBlock)
                    y +=interval;
                break;
            case LEFT:
                if(!touchBlock && !touchOtherTank)
                    x-=speed;
                else if(touchBlock)
                    x -=interval;
                break;
            case RIGHT:
                if(!touchBlock && !touchOtherTank)
                    x+=speed;
                else if(touchBlock)
                    x +=interval;
                break;
        }
    }

    //编写方法，判断当前坦克是否和别的敌人坦克发生了碰撞
    public boolean isTouchOtherTank()
    {

        switch(this.getDir())
        {
            case UP :
                for(int i=0;i<allHeroes.size();i++)
                {
                    Hero tank = allHeroes.get(i);
                    if(tank!=this)
                    {
                        if(tank.getDir() ==Dir.UP||tank.getDir() ==Dir.DOWN)//上下
                        {
                            if(this.x>tank.x &&
                                    this.x<tank.x+Tank.width &&
                                    this.y>tank.y &&
                                    this.y<tank.y+Tank.height)
                                return true;
                            if(this.x+Tank.width>tank.x &&
                                    this.x+Tank.width<tank.x+Tank.width &&
                                    this.y>tank.y &&
                                    this.y<tank.y+Tank.height)
                                return true;
                        }
                        else//左右
                        {
                            if(this.x>tank.x &&
                                    this.x<tank.x+Tank.width &&
                                    this.y>tank.y &&
                                    this.y<tank.y+Tank.height)
                                return true;
                            if(this.x+Tank.width>tank.x &&
                                    this.x+Tank.width<tank.x+Tank.width &&
                                    this.y>tank.y &&
                                    this.y<tank.y+Tank.height)
                                return true;
                        }
                    }
                }
                break;
            case DOWN:
                for(int i=0;i<allHeroes.size();i++)
                {
                    Hero tank = allHeroes.get(i);
                    if(tank!=this)
                    {
                        if(tank.getDir() ==Dir.UP||tank.getDir() ==Dir.DOWN)//上下
                        {
                            if(this.x > tank.x &&
                                    this.x  < tank.x+Tank.width &&
                                    this.y + Tank.width>tank.y &&
                                    this.y + Tank.width<tank.y+Tank.height)
                                return true;
                            if(this.x+Tank.width>tank.x &&
                                    this.x+Tank.width<tank.x+Tank.width &&
                                    this.y+Tank.height>tank.y &&
                                    this.y+Tank.height<tank.y+Tank.height)
                                return true;
                        }
                        else//左右
                        {
                            if(this.x>tank.x &&
                                    this.x<tank.x+Tank.width &&
                                    this.y + Tank.width>tank.y &&
                                    this.y + Tank.width<tank.y+Tank.width)
                                return true;
                            if(this.x+Tank.width>tank.x &&
                                    this.x+Tank.width<tank.x+Tank.width &&
                                    this.y +Tank.width>tank.y &&
                                    this.y+Tank.width<tank.y+Tank.height)
                                return true;
                        }
                    }
                }
                break;
            case LEFT:
                for(int i=0;i<allHeroes.size();i++)
                {
                    Hero tank = allHeroes.get(i);
                    if(tank!=this)
                    {
                        if(tank.getDir() ==Dir.UP||tank.getDir() ==Dir.DOWN)//上下
                        {
                            if(this.x > tank.x &&
                                    this.x  < tank.x+Tank.width &&
                                    this.y >tank.y &&
                                    this.y <tank.y+Tank.height)
                                return true;
                            if(this.x>tank.x &&
                                    this.x<tank.x+Tank.width &&
                                    this.y+Tank.height>tank.y &&
                                    this.y+Tank.height<tank.y+Tank.height)
                                return true;
                        }
                        else//左右
                        {
                            if(this.x>tank.x &&
                                    this.x<tank.x+Tank.width &&
                                    this.y >tank.y &&
                                    this.y <tank.y+Tank.width)
                                return true;
                            if(this.x>tank.x &&
                                    this.x <tank.x+Tank.width &&
                                    this.y +Tank.width>tank.y &&
                                    this.y+Tank.width<tank.y+Tank.height)
                                return true;
                        }
                    }
                }
                break;
            case RIGHT:
                for(int i=0;i<allHeroes.size();i++)
                {
                    Hero tank = allHeroes.get(i);
                    if(tank!=this)
                    {
                        if(tank.getDir() ==Dir.UP||tank.getDir() ==Dir.DOWN)//上下
                        {
                            if(this.x+Tank.width > tank.x &&
                                    this.x + Tank.width < tank.x+Tank.width &&
                                    this.y>tank.y &&
                                    this.y<tank.y+Tank.height)
                                return true;
                            if(this.x+Tank.width>tank.x &&
                                    this.x+Tank.width<tank.x+Tank.width &&
                                    this.y+Tank.height>tank.y &&
                                    this.y+Tank.height<tank.y+Tank.height)
                                return true;
                        }
                        else//左右
                        {
                            if(this.x+ Tank.width>tank.x &&
                                    this.x+ Tank.width <tank.x+Tank.width &&
                                    this.y >tank.y &&
                                    this.y<tank.y+Tank.width)
                                return true;
                            if(this.x+Tank.width>tank.x &&
                                    this.x+Tank.width<tank.x+Tank.width &&
                                    this.y +Tank.width>tank.y &&
                                    this.y+Tank.width<tank.y+Tank.height)
                                return true;
                        }
                    }
                }
                break;
        }
        return false;
        //绝不能用两层循环实现这个功能，因为循环不够快，不能立即计算出结果，这就导致可以不小心发生重叠
        // 重叠程度浅还可以再次分开，重叠程度深，两个坦克就粘在一起了！
        /*Vector<Point> thisP = new Vector<Point>();
        thisP.add(new Point(this.getX(),this.getY()));
        thisP.add(new Point(this.getX(),this.getY()+Tank.height));
        thisP.add(new Point(this.getX()+Tank.width,this.getY()));
        thisP.add(new Point(this.getX()+Tank.width,this.getY()+Tank.height));
        for(int i=0;i<allHeroes.size();i++)
        {
            Hero tank = allHeroes.get(i);
            for(int j=0;j<4;j++)
            {
                if(thisP.get(i).x >tank.x && thisP.get(i).x < tank.x+Tank.width &&
                        thisP.get(i).y > tank.y && thisP.get(i).y < tank.y+Tank.height)
                {
                    System.out.println("@Collision");
                    System.out.println(this);
                    System.out.println(tank);
                    return true;
                }
            }
        }
        return false;*/
    }

    public boolean isTouchBlock()
    {
        int nodesize = DynamicPanel.nodeSize;
        switch(getDir())
        {
            case UP :
                for(int i=0;i<Map.map.size();i++)
                {
                    Node n = Map.map.get(i);
                    int xx = n.x*nodesize,yy = n.y*nodesize;
                    if(y-speed < yy+nodesize && y-speed > yy && x > xx-nodesize && x<xx+nodesize)
                    {
                        interval = y-(yy+nodesize);
                        if(this.getType() == TankType.NORMAL)
                        {
                            System.out.println(this);
                            System.out.println("xx = "+xx+"  yy = "+yy);
                        }

                        return true;
                    }
                }
                break;
            case DOWN:
                for(int i=0;i<Map.map.size();i++)
                {
                    Node n = Map.map.get(i);
                    int xx = n.x*nodesize,yy = n.y*nodesize;
                    if(y+Tank.width+speed > yy && y+Tank.width+speed < yy+nodesize && x > xx-nodesize && x<xx+nodesize)
                    {
                        interval = yy - (y+Tank.width);
                        if(this.getType() == TankType.NORMAL)
                        {
                            System.out.println(this);
                            System.out.println(xx+"down "+yy);
                        }
                        return true;
                    }
                }
                break;
            case LEFT:
                for(int i=0;i<Map.map.size();i++)
                {
                    Node n = Map.map.get(i);
                    int xx = n.x*nodesize,yy = n.y*nodesize;
                    if(x-speed < xx+nodesize && x-speed > xx && y > yy-nodesize && y<yy+nodesize)
                    {
                        interval = x - (xx+nodesize);
                        if(this.getType() == TankType.NORMAL)
                        {
                            System.out.println(this);
                            System.out.println(xx+" left "+yy);
                        }
                        return true;
                    }
                }
                break;
            case RIGHT:
                for(int i=0;i<Map.map.size();i++)
                {
                    Node n = Map.map.get(i);
                    int xx = n.x*nodesize,yy = n.y*nodesize;
                    if(x+Tank.width+speed > xx && x+Tank.width+speed < xx+nodesize && y > yy-nodesize && y<yy+nodesize)
                    {
                        interval = xx - (x+Tank.width);
                        if(this.getType() == TankType.NORMAL)
                        {
                            System.out.println(this);
                            System.out.println(xx+"  "+yy);
                        }
                        return true;
                    }
                }
                break;
        }
        return false;
    }

    @Override
    public String toString() {
        return "Hero{" +
                "x=" + x +
                ", y=" + y +
                ", speed=" + speed +
                ", dir=" + dir +
                '}';
    }
}
