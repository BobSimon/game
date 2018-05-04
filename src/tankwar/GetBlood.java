package tankwar;

import java.awt.*;
import java.util.Random;

/**
 * @Author: Bob Simon
 * @Description:获得血
 * @Date: Created in 10:07 2018\5\4
 */
public class GetBlood {

    public static final int width = 36;
    public static final int length = 36;

    private int x, y;
    TankClient tc;
    private static Random r = new Random();

    int step = 0;
    private boolean live = false;

    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] bloodImags = null;

    static {
        bloodImags = new Image[]{tk.getImage(CommonWall.class
                .getResource("images/hp.png")),};
    }

    private int[][] poition = {{155, 196}, {500, 58}, {80, 340},
            {99, 199}, {345, 456}, {123, 321}, {258, 413}};

    public void draw(Graphics g) {
        if (r.nextInt(100) > 98) {
            this.live = true;
            move();
        }
        if (!live)
            return;
        g.drawImage(bloodImags[0], x, y, null);

    }

    private void move() {
        step++;
        if (step == poition.length) {
            step = 0;
        }
        x = poition[step][0];
        y = poition[step][1];

    }

    /**
     * 返回长方形实例
     * @return
     */
    public Rectangle getRect() {
        return new Rectangle(x, y, width, length);
    }

    /**
     * 判断是否还活着
     * @return
     */
    public boolean isLive() {
        return live;
    }

    /**
     * 设置生命
     * @param live
     */
    public void setLive(boolean live) {
        this.live = live;
    }

}
