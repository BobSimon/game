package tankwar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * @Author: Bob Simon
 * @Description: 炸弹坦克
 * @Date: Created in 9:59 2018\5\4
 */
public class BombTank {

    private int x, y;

    // 初始状态为活着的
    private boolean live = true;

    private TankClient tc;
    private static Toolkit tk = Toolkit.getDefaultToolkit();

    /**
     * 存储爆炸图片 从小到大的爆炸效果图
     */
    private static Image[] imgs = {
            tk.getImage(BombTank.class.getClassLoader().getResource(
                    "images/1.gif")),
            tk.getImage(BombTank.class.getClassLoader().getResource(
                    "images/2.gif")),
            tk.getImage(BombTank.class.getClassLoader().getResource(
                    "images/3.gif")),
            tk.getImage(BombTank.class.getClassLoader().getResource(
                    "images/4.gif")),
            tk.getImage(BombTank.class.getClassLoader().getResource(
                    "images/5.gif")),
            tk.getImage(BombTank.class.getClassLoader().getResource(
                    "images/6.gif")),
            tk.getImage(BombTank.class.getClassLoader().getResource(
                    "images/7.gif")),
            tk.getImage(BombTank.class.getClassLoader().getResource(
                    "images/8.gif")),
            tk.getImage(BombTank.class.getClassLoader().getResource(
                    "images/9.gif")),
            tk.getImage(BombTank.class.getClassLoader().getResource(
                    "images/10.gif")),};
    int step = 0;

    public BombTank(int x, int y, TankClient tc) { // 构造函数
        this.x = x;
        this.y = y;
        this.tc = tc;
    }

    /**
     * 画出爆炸图像
     *
     * @param g
     */
    public void draw(Graphics g) {

        // 坦克消失后删除爆炸图
        if (!live) {
            tc.bombTanks.remove(this);
            return;
        }
        if (step == imgs.length) {
            live = false;
            step = 0;
            return;
        }

        g.drawImage(imgs[step], x, y, null);
        step++;
    }

}

