package tankwar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;

/**
 * @Author: Bob Simon
 * @Description: 设置界面树和丛林
 * @Date: Created in 10:46 2018\5\4
 */
public class Tree {

    public static final int width = 30;

    public static final int length = 30;
    int x, y;
    TankClient tc;
    private static Toolkit tk = Toolkit.getDefaultToolkit();
    private static Image[] treeImags = null;

    static {
        treeImags = new Image[]{
                tk.getImage(CommonWall.class.getResource("images/tree.gif")),
        };
    }

    /**
     * Tree的构造方法，传递x，y和tc对象
     *
     * @param x
     * @param y
     * @param tc
     */
    public Tree(int x, int y, TankClient tc) {
        this.x = x;
        this.y = y;
        this.tc = tc;
    }

    /**
     * 画出树
     *
     * @param g
     */
    public void draw(Graphics g) {
        g.drawImage(treeImags[0], x, y, null);
    }

}
