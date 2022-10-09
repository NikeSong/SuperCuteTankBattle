package game.tankGame;


import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

@SuppressWarnings({"all"})
//游戏绘图区域
public class DynamicPanel extends JPanel implements KeyListener,Runnable{
    //define hero
    Hero hero = null;
    Vector<EnemyTank> enemyTanks = new Vector<EnemyTank>();
    Vector<Bomb> bombs = new Vector<Bomb>();//存放提供坦克爆炸效果的爆炸对象
    public static int InienemyNum = 10;
    public static int PanelWidth = 950;
    public static int PanelHeight = 750;

    public static final int blockWidth = 19;
    public static final int blockHeight = 15;
    public static final int nodeSize = 50;
    private static final String grassPath = "appData/images/map/grass.png";
    private static final String stonePath = "appData/images/map/stone.png";
    private static final String bgPath = "appData/images/map/ground.png";


    //界面的初始化绘图
    public DynamicPanel(int key) {
        this.setSize(PanelWidth,PanelHeight);
        //绘制英雄
        hero = new Hero(PanelWidth/2,PanelHeight-Tank.height-nodeSize,Dir.UP,TankType.NORMAL);
        //创造敌人
        switch(key)
        {
            case 1 ://生成敌人坦克对象
                if(key == 1)for(int i = 0;i < InienemyNum;i++)
                enemyTanks.add(new EnemyTank(PanelWidth*(i+1)/(InienemyNum+1),0,Dir.DOWN,TankType.ENEMY));
                break;
            case 2://加载上局游戏记录
                Recorder.loadRecord();
                enemyTanks = Recorder.getEnemyInfoLoad();
                break;
            case 3://仅创建地图
                Map.ConstructMap();
                return;
        }

        //记录所有坦克的初始信息，供判断是否存在坦克相接触的情况
        Vector<Hero> allHeros = new Vector<Hero>();
        allHeros.addAll(enemyTanks);

        allHeros.add(hero);
        Hero.setEnemyTanks(allHeros);

        Recorder.setEnemyInfoKeep(enemyTanks);//记录敌人坦克信息，退出程序时会记录当前状态.以提供“继续游戏”功能
        //播放背景音乐
        new PlayWave("appData\\music\\test4.wav").start();
        // 加载地图
        Map.ReadMap("NIKECITY");
    }

    //刷新屏幕图像的函数
    @Override
    public void paint(Graphics g) {
        super.paint(g);

        //g.setColor(Color.CYAN);//设置背景颜色
        //g.fillRect(0,0,PanelWidth,PanelHeight);//填充背景颜色
        Toolkit tool = this.getToolkit();
        bgPaint(g,tool,this);
        //绘制得分信息
        ShowRecord(g);
        //绘制hero的子弹
        for(int noBullet=0; noBullet<hero.bullets.size(); noBullet++)
        {
            Shot bullet = hero.bullets.get(noBullet);
            if(bullet.isLive) DrawBullet(bullet,g);
            else hero.bullets.remove(noBullet);
        }
        //绘制hero
        if(hero.isLive) DrawTank(hero, g);
        else {
            ;//游戏结束
        }
        //绘制enemy的坦克和子弹
        for(int noTank=0;noTank<enemyTanks.size();noTank++)
        {
            //绘制enemy的子弹
            for(int noBullet = 0;noBullet<enemyTanks.get(noTank).bullets.size();noBullet++)
            {
                Shot bullet = enemyTanks.get(noTank).bullets.get(noBullet);
                if(bullet.isLive) DrawBullet(bullet,g);
                else enemyTanks.get(noTank).bullets.remove(noBullet);
            }
            //绘制enemy
            if(enemyTanks.get(noTank).isLive) DrawTank(enemyTanks.get(noTank),g);
            else//此敌人已经死亡
            {
                enemyTanks.remove(noTank);  //敌人坦克序列中删除已经死亡的坦克 — — 不再绘制
                Recorder.addScore();    //计分板分数加一
                Hero.allHeroes.remove(noTank); // 在接触检测的程序中删除已经死亡的坦克
                Recorder.setEnemyInfoKeep(enemyTanks);//记录敌人坦克信息，退出程序时会记录当前状态.以提供“继续游戏”功能
            }
        }
        //绘制爆炸效果
        for(int b = 0; b< bombs.size(); b++)
        {
            Bomb bomb = bombs.get(b);
            if(bomb.life > 6) g.drawImage(bomb.image1, bomb.x, bomb.y,Tank.width,Tank.height, this);
            else if(bomb.life >3) g.drawImage(bomb.image2, bomb.x, bomb.y,Tank.width,Tank.height,this);
            else g.drawImage(bomb.image3, bomb.x, bomb.y,Tank.width,Tank.height, this);
            bomb.lifeDown();
            if(!bomb.isLive) bombs.remove(b);
        }
        //# Test
        //System.out.println("Hero 的子弹数目为:"+ hero.bullets.size());
    }//每次界面重置会自动重新执行，在本软件中，每过一段时间也会重新执行

    //绘制背景图片
    public static void bgPaint(Graphics g, Toolkit tool, JPanel panel) {
        //加载图片信息
        Image bgImage = tool.getImage(bgPath);
        Image grassImage = tool.getImage(grassPath);
        Image stoneImage = tool.getImage(stonePath);

        //绘制背景图片
        for(int i = 0; i< blockWidth; i++)
        {
            for(int j = 0; j< blockHeight; j++)
            {
                g.drawImage(bgImage,i*nodeSize,j*nodeSize,nodeSize,nodeSize,panel);
            }
        }

        //绘制草地和石块
        for(int i=0;i<Map.map.size();i++)
        {
            Node n = Map.map.get(i);
            if(n.type == Node.stone) g.drawImage(stoneImage,n.x*nodeSize,n.y*nodeSize,nodeSize,nodeSize,panel);
            else if(n.type == Node.grass) g.drawImage(grassImage,n.x*nodeSize,n.y*nodeSize,nodeSize,nodeSize,panel);
        }
 /*       int i = 0;

        for(int j = 0; j< blockWidth; j++)  //上面横排
            g.drawImage(stoneImage,j*nodeSize,i*nodeSize,nodeSize,nodeSize,panel);
        for(int j = 1; j< blockHeight -1; j++) //左边竖着边缘
           g.drawImage(stoneImage,i*nodeSize,j*nodeSize,nodeSize,nodeSize,panel);
        i = blockHeight -1;
        for(int j = 0; j< blockWidth; j++)
            g.drawImage(stoneImage,j*nodeSize,i*nodeSize,nodeSize,nodeSize,panel);
        i = blockWidth -1;
        for(int j = 1; j< blockHeight -1; j++)  g.drawImage(stoneImage,i*nodeSize,j*nodeSize,nodeSize,nodeSize,panel);*/
    }

    /**
     *
     * @param x     坦克左上角x坐标
     * @param y     坦克左上角y坐标
     * @param g     画笔
     * @param dir   坦克朝向
     * @param type  坦克类型
     */
    //绘制坦克
    public void DrawTank(Tank tank,Graphics g)
    {
        int tankImageNum = 0;   //获得的坦克图片编号，编号解析方法见
        switch (tank.getType())
        {
            case NORMAL:
                tankImageNum = 0;break;
            case ENEMY:
                tankImageNum = 5;break;
        }

        switch (tank.getDir())
        {
            case UP:
                tankImageNum+=1;break;
            case DOWN:
                tankImageNum+=2;break;
            case LEFT:
                tankImageNum+=3;break;
            case RIGHT:
                tankImageNum+=4;break;
        }
        Toolkit tool = this.getToolkit();
        Image image = tool.getImage("appData/images/tank/"+tankImageNum + ".png");
        //Image image2 = tool.getImage("C:/Users/Administrator/Desktop/images/7.png");

        g.drawImage(image,tank.getX(),tank.getY(),Tank.width,Tank.height,this);

    }

    //绘制子弹
    public void DrawBullet(Shot bullet,Graphics g)
    {
        g.setColor(Color.RED);
        switch(bullet.getDir())
        {
            case UP :
                g.fillRect(bullet.getX(),bullet.getY(),Shot.width,Shot.height);
                break;
            case DOWN:
                g.fillRect(bullet.getX(),bullet.getY(),Shot.width,Shot.height);
                break;
            case LEFT:
                g.fillRect(bullet.getX(),bullet.getY(),Shot.height,Shot.width);
                break;
            case RIGHT:
                g.fillRect(bullet.getX(),bullet.getY(),Shot.height,Shot.width);
                break;
        }

    }

    public void ShowRecord(Graphics g)
    {
        //绘制玩家成绩
        g.setColor(Color.BLACK);
        Font font = new Font("微软雅黑",Font.BOLD,20);//字体为微软雅黑加粗25号
        g.setFont(font);
        g.drawString("累计得分:",DynamicPanel.PanelWidth+20,30);
        DrawTank(new Tank(DynamicPanel.PanelWidth+20,60,Dir.UP,TankType.ENEMY),g);
        g.drawString("x "+Recorder.getScore(),DynamicPanel.PanelWidth+90,90);
    }

    // 字符输出的监听
    @Override
    public void keyTyped(KeyEvent e) {

    }

    //按键按下的监听
    @Override
    public void keyPressed(KeyEvent e) {
        if(e.getKeyCode() == KeyEvent.VK_W) {
            hero.setDir(Dir.UP);
            hero.Move(Dir.UP);
        }
        else if(e.getKeyCode() == KeyEvent.VK_S) {
            hero.setDir(Dir.DOWN);
            hero.Move(Dir.DOWN);
        }
        else if(e.getKeyCode() == KeyEvent.VK_A)
        {
            hero.setDir(Dir.LEFT);
            hero.Move(Dir.LEFT);
        }
        else if(e.getKeyCode() == KeyEvent.VK_D)
        {
            hero.setDir(Dir.RIGHT);
            hero.Move(Dir.RIGHT);
        }
        else if(e.getKeyCode() == KeyEvent.VK_J)
        {
            hero.shotEnemy();
        }
        else System.out.println("此键盘未定义!");

        this.repaint();
    }

    //按键释放的监听
    @Override
    public void keyReleased(KeyEvent e) {

    }

    //此类的线程方法，每隔一段时间就重绘游戏界面
    @Override
    public void run()
    {
        //每隔100ms自己重绘区域
        while(true)
        {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


            //判断子弹有没有击中坦克，默认所有子弹均为有效，所有敌人都活着
            //1. hero的子弹有没有击中敌人
            for(int b = 0;b<hero.bullets.size();b++)
            {
                Shot shot = hero.bullets.get(b);
                //此处必须把shot取出来！因为子弹b杀死敌人后这个子弹就失效了，
                // 如果此时是最后一颗子弹，子弹vector就会清空，此时你再拿bullets.get(b)
                // 和其他enemy做是否击中判断就会出现越界！
                for(int e = 0;e<enemyTanks.size();e++)
                    IsHitTank(shot,enemyTanks.get(e));
            }

            //2.敌人的子弹有没有击中hero
            for(int e = 0;e<enemyTanks.size();e++)
                for(int b = 0;b<enemyTanks.get(e).bullets.size();b++)
                    IsHitTank(enemyTanks.get(e).bullets.get(b),hero);
            this.repaint();
        }

    }

    //判断子弹是否打中了坦克
    public void IsHitTank(Shot s ,Tank tank)
    {
        Vector<Point> points = new Vector<Point>();
        if(s.getDir() == Dir.UP || s.getDir() == Dir.DOWN)
        {
            points.add(new Point(s.getX(),s.getY()));
            points.add(new Point(s.getX()+Shot.width,s.getY()));
            points.add(new Point(s.getX(),s.getY()+Shot.height));
            points.add(new Point(s.getX()+Shot.width,s.getY()+Shot.height));
        }
        else {
            points.add(new Point(s.getX(),s.getY()));
            points.add(new Point(s.getX(),s.getY()+Shot.width));
            points.add(new Point(s.getX()+Shot.height,s.getY()));
            points.add(new Point(s.getX()+Shot.height,s.getY()+Shot.width));
        }
        //1.因为坦克的长和宽一样大，这里就不再区分;2.无论坦克当前朝向是哪里，它的坐标都是左上角的点
        assert Tank.width == Tank.height;
        for(int i=0;i<4;i++)
        {
            if(points.get(i).x > tank.getX() && points.get(i).x < tank.getX()+Tank.width &&
            points.get(i).y > tank.getY() && points.get(i).y < tank.getY()+Tank.width)
            {
                tank.isLive = false;
                s.isLive = false;
                //创建爆炸对象
                bombs.add(new Bomb(tank.getX(), tank.getY()));
            }

        }
    }
}
