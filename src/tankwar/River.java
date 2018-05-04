package tankwar;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;

/**
 * @Author: Bob Simon
 * @Description: 河流
 * @Date: Created in 10:12 2018\5\4
 */
public class River {
	public static final int riverWidth = 40;

	//静态全局变量
	public static final int riverLength = 100;

	private int x, y;

	TankClient tc ;
	
	private static Toolkit tk = Toolkit.getDefaultToolkit();

	private static Image[] riverImags = null;

	//存储图片
	static {
		riverImags = new Image[]{
			tk.getImage(CommonWall.class.getResource("images/river.jpg")),
		};
	}

	/**
	 * River的构造方法
	 * @param x
	 * @param y
	 * @param tc
	 */
	public River(int x, int y, TankClient tc){

		this.x = x;

		this.y = y;

		//获得控制
		this.tc = tc;
	}
	
	public void draw(Graphics g){

		//在对应X，Y出画河
		g.drawImage(riverImags[0],x, y, null);
	}

	public static int getRiverWidth() {
		return riverWidth;
	}

	public static int getRiverLength() {
		return riverLength;
	}
	
	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, riverWidth, riverLength);
	}


}
