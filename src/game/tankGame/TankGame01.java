package game.tankGame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Scanner;

@SuppressWarnings({"all"})

public class TankGame01 extends JFrame {
    MyPanel mp = null;
    int FrameWidth = 1200+14;
    int FrameHeight = 750+37;
    static Scanner scanner = new Scanner(System.in);
    public TankGame01()
    {
/*        System.out.println("请输入选择：1.新游戏。2，继续");
        String key = scanner.next();*/
        mp = new MyPanel(Integer.parseInt("1"));//是以“开始游戏”模式（1）开始，还是“继续”开始（2）
        this.add(mp);
        new Thread(mp).start();//启动刷新屏幕的线程
        this.setSize(FrameWidth,FrameHeight);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.addKeyListener(mp);
        this.setResizable(false);
        //Rectangle rc=this.getContentPane().getBounds();
        //System.out.println(rc.x+","+rc.y+","+rc.width+","+rc.height);
        this.setVisible(true);

        //在JFrame中增加响应关闭窗口动作
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                Recorder.keepRecord();
                System.exit(0);
            }
        });

    }

    public static void main(String[] args) {
        TankGame01 game = new TankGame01();

    }
}
