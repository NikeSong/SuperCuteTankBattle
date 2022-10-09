package game.tankGame;

import java.awt.*;
import java.io.*;
import java.security.Key;
import java.util.Scanner;
import java.util.Vector;

class Node implements Serializable{
    public static final int grass = 1;//草，可以打掉,不可穿越
    public static final int stone = 3;//石头，不可以打掉，不可穿越
    public int type;
    public int x;
    public int y;

    public Node(int x, int y,int type) {
        this.type = type;
        this.x = x;
        this.y = y;
    }
}

public class Map {
    public static Vector<Node> map = new Vector<Node>();
    private static final String mapPath = "appData/map";
    private static final String mapConstructorPath = "appData/map/constructor.txt";
    public static String mapName;
    private static ObjectOutputStream oos = null;
    private static ObjectInputStream ois = null;
    private static BufferedReader br = null;
    private static Scanner scanner = new Scanner(System.in);

    public static void ConstructMap()
    {
        int x,y;
        try {
            br = new BufferedReader(new FileReader(mapConstructorPath));

            do{
                String[] s = br.readLine().split(" ");
                x = Integer.parseInt(s[1]);
                y = Integer.parseInt(s[0]);
                if(x+y!=-2) map.add(new Node(x,y,Node.grass));
            }
            while(x+y!=-2);
            do{
                String[] s = br.readLine().split(" ");
                x = Integer.parseInt(s[1]);
                y = Integer.parseInt(s[0]);
                if(x+y!=-2) map.add(new Node(x,y,Node.stone));
            }
            while(x+y!=-2);

            System.out.println("请输入地图名字");
            mapName = scanner.next();

            WriteMap();
            System.out.println("地图生成完成！");

        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                br.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void WriteMap()
    {
        String filePath = mapPath+'/'+mapName+".properties";
        try {
            oos = new ObjectOutputStream(new FileOutputStream(filePath));
            for(int i=0;i<map.size();i++)
                oos.writeObject(map.get(i));//序列化
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            try {
                oos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void ReadMap(String mapName)
    {
        String filePath = mapPath+'/'+mapName+".properties";
        try {
            ois = new ObjectInputStream(new FileInputStream(filePath));
            Node n = (Node) ois.readObject();
            while(true)
            {
                map.add(n);
                n = (Node) ois.readObject();
            }
        } catch (IOException e) {

        } catch( ClassNotFoundException e){
            e.printStackTrace();
    }
        finally {
            try {
                ois.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
