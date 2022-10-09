package game.tankGame;

import javax.swing.*;
import java.awt.*;

//绘制画面背景及地图
public class StaticPaint {
    private static final int blockWidth = 19;
    private static final int blockHeight = 15;
    private static final int nodeSize = 50;
    private static final String grassPath = "appData/images/map/grass.png";
    private static final String stonePath = "appData/images/map/stone.png";
    private static final String bgPath = "appData/images/map/ground.png";

    public static void paint(Graphics g, Toolkit tool, JPanel panel) {
        //加载图片信息
        Image bgImage = tool.getImage(bgPath);
        Image grassImage = tool.getImage(grassPath);
        Image stoneImage = tool.getImage(stonePath);

        // 加载地图
        Map.ReadMap("NIKECITY");

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
        int i = 0;

        for(int j = 0; j< StaticPaint.blockWidth; j++)  //上面横排
            g.drawImage(stoneImage,j*nodeSize,i*nodeSize,nodeSize,nodeSize,panel);
        for(int j = 1; j< StaticPaint.blockHeight -1; j++) //左边竖着边缘
            g.drawImage(stoneImage,i*nodeSize,j*nodeSize,nodeSize,nodeSize,panel);
        i = StaticPaint.blockHeight -1;
        for(int j = 0; j< StaticPaint.blockWidth; j++)
            g.drawImage(stoneImage,j*nodeSize,i*nodeSize,nodeSize,nodeSize,panel);
        i = StaticPaint.blockWidth -1;
        for(int j = 1; j< StaticPaint.blockHeight -1; j++)  g.drawImage(stoneImage,i*nodeSize,j*nodeSize,nodeSize,nodeSize,panel);


    }
}
