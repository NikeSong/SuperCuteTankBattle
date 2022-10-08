package game.tankGame;

import java.awt.*;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

//该类记录游戏信息
public class Recorder {
    private static String userName = "Nike";
    private static int score=0;
    //写文件的类
    private static BufferedWriter bw = null;
    private static BufferedReader br = null;
    private static String recordFile = "appData/record/MyRecord.nks";

    private  static Vector<EnemyTank> enemyInfoKeep = null;
    private static Vector<EnemyTank> enemyInfoLoad = null;

    public static Vector<EnemyTank> getEnemyInfoLoad() {
        return enemyInfoLoad;
    }

    public static void setEnemyInfoKeep(Vector<EnemyTank> enemyInfoKeep) {
        Recorder.enemyInfoKeep = enemyInfoKeep;
    }

    public static int getScore() {
        return score;
    }

    public static void setScore(int score) {
        Recorder.score = score;
    }

    //击毁敌人，得分增加
    public static void addScore()
    {
        score++;
    }

    //将记录信息保存到本地
    public static void keepRecord()
    {
        try {
            // 保存本场游戏基本信息
            bw = new BufferedWriter(new FileWriter(recordFile));
            SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd'\t'HH:mm:ss");
            Date date = new Date(System.currentTimeMillis());
            bw.write(score+"\n" + userName +"\n");

            //保存敌人信息
            for(int i = 0; i< enemyInfoKeep.size(); i++)
            {
                EnemyTank enemy = enemyInfoKeep.get(i);
                String s = enemy.x+","+enemy.y+","+enemy.getDir()+"\n";
                bw.write(s);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if(bw != null)
            {
                try {
                    bw.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void loadRecord()
    {
        enemyInfoLoad = new Vector<EnemyTank>();
        try {
            br = new BufferedReader(new FileReader(recordFile));
            score = Integer.parseInt(br.readLine());
            userName = br.readLine();
            //循环读取信息，得到敌人
            String line = "";
            while((line = br.readLine())!=null)
            {
                String[] info =line.split(",");
                Dir d = switch (info[2]) {
                    case "UP" -> Dir.UP;
                    case "DOWN" -> Dir.DOWN;
                    case "LEFT" -> Dir.LEFT;
                    case "RIGHT" -> Dir.RIGHT;
                    default -> null;
                };//enhanced switch!
                enemyInfoLoad.add(new EnemyTank(Integer.parseInt(info[0]),Integer.parseInt(info[1]),d,TankType.ENEMY));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(br!=null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
