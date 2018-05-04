package tankwar;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * @Author: Bob Simon
 * @Description:大本营
 * @Date: Created in 10:09 2018\5\4
 */
public class Home {
    private int x, y;

    private TankClient tc;

    // 全局静态变量长宽
    public static final int width = 30, length = 30;

    private boolean live = true;

    // 全局静态变量
    private static Toolkit tk = Toolkit.getDefaultToolkit();

    private static Image[] homeImags = null;

    static {
        homeImags = new Image[]{tk.getImage(CommonWall.class
                .getResource("images/home.jpg")),};
    }

    /**
     * 构造函数，传递Home的参数并赋值
     *
     * @param x
     * @param y
     * @param tc
     */
    public Home(int x, int y, TankClient tc) {
        this.x = x;
        this.y = y;

        // 获得控制
        this.tc = tc;
    }

    public void gameOver(Graphics g) {

        // 作清理页面工作
        tc.tanks.clear();
        tc.metalWall.clear();
        tc.otherWall.clear();
        tc.bombTanks.clear();
        tc.theRiver.clear();
        tc.trees.clear();
        tc.bullets.clear();
        tc.homeTank.setLive(false);

        // 设置参数
        Color c = g.getColor();
        g.setColor(Color.green);
        Font f = g.getFont();
        g.setFont(new Font(" ", Font.PLAIN, 40));
        g.drawString("你输了！", 220, 250);
        g.drawString("  游戏结束！ ", 220, 300);
        g.setFont(f);
        g.setColor(c);

    }

    public void draw(Graphics g) {

        // 如果活着，则画出home
        if (live) {
            g.drawImage(homeImags[0], x, y, null);

            for (int i = 0; i < tc.homeWall.size(); i++) {
                CommonWall w = tc.homeWall.get(i);
                w.draw(g);
            }
        } else {
            // 调用游戏结束
            gameOver(g);
        }
    }

    /**
     * 判读是否还活着
     */
    public boolean isLive() {
        return live;
    }

    /**
     * 设置生命
     *
     * @param live
     */
    public void setLive(boolean live) {
        this.live = live;
    }

    /**
     * 返回长方形实例
     *
     * @return
     */
    public Rectangle getRect() {
        return new Rectangle(x, y, width, length);
    }

}
