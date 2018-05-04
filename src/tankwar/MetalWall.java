package tankwar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * @Author: Bob Simon
 * @Description:金属墙
 * @Date: Created in 10:11 2018\5\4
 */
public class MetalWall {

	// 设置金属墙的长宽静态全局参数
	public static final int width = 30;

	public static final int length = 30;

	private int x, y;

	TankClient tc;

	private static Toolkit tk = Toolkit.getDefaultToolkit();

	private static Image[] wallImags = null;
	static {
		wallImags = new Image[] { tk.getImage(CommonWall.class
				.getResource("images/metalWall.gif")), };
	}

	/**
	 *  构造函数，传递要构造的长宽并赋值
	 * @param x
	 * @param y
	 * @param tc
	 */
	public MetalWall(int x, int y, TankClient tc) {
		this.x = x;
		this.y = y;
		this.tc = tc;
	}

	/**
	 * 画金属墙
	 * @param g
	 */
	public void draw(Graphics g) {
		g.drawImage(wallImags[0], x, y, null);
	}

	/**
	 * 构造指定参数的长方形实例
	 * @return
	 */
	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}


}
