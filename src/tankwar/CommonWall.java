package tankwar;

import java.awt.*;

/**
 * @Author: Bob Simon
 * @Description:普通的城墙
 * @Date: Created in 10:06 2018\5\4 0004
 */
public class CommonWall {

    //设置墙的固定参数
    public static final int width = 20;

    public static final int length = 20;
    int x, y;

    TankClient tc;
    private static Toolkit tk = Toolkit.getDefaultToolkit();

    private static Image[] wallImags = null;

    static {
        // 储存commonWall的图片
        wallImags = new Image[]{
                tk.getImage(CommonWall.class.getResource("images/commonWall.gif")),};
    }

    /**
     *  构造函数
     * @param x
     * @param y
     * @param tc
     */
    public CommonWall(int x, int y, TankClient tc){

        this.x = x;

        this.y = y;

        // 获得界面控制
        this.tc = tc;
    }

    /**
     * 画commonWall
     * @param g
     */
    public void draw(Graphics g){
        g.drawImage(wallImags[0], x, y,null);
    }

    /**
     * 构造指定参数的长方形实例
     * @return
     */
    public Rectangle getRect(){
        return new Rectangle(x, y, width, length);
    }


}
