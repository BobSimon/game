package tankwar;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Author: Bob Simon
 * @Description:子弹
 * @Date: Created in 10:01 2018\5\4 0004
 */
public class Bullets {
	public static  int speedX = 10;

	// 子弹的全局静态速度
	public static  int speedY = 10;

	public static final int width = 10;
	public static final int length = 10;

	private int x, y;
	Direction diretion;

	private boolean good;
	private boolean live = true;

	private TankClient tc;

	private static Toolkit tk = Toolkit.getDefaultToolkit();
	private static Image[] bulletImages = null;

	/**
	 * 定义Map键值对，是不同方向对应不同的弹头
	 */
	private static Map<String, Image> imgs = new HashMap<String, Image>();

	// 不同方向的子弹
	static {
		bulletImages = new Image[] {
				tk.getImage(Bullets.class.getClassLoader().getResource(
						"images/bulletL.gif")),

				tk.getImage(Bullets.class.getClassLoader().getResource(
						"images/bulletU.gif")),

				tk.getImage(Bullets.class.getClassLoader().getResource(
						"images/bulletR.gif")),

				tk.getImage(Bullets.class.getClassLoader().getResource(
						"images/bulletD.gif")),

		};

		// 加入Map容器
		imgs.put("L", bulletImages[0]);

		imgs.put("U", bulletImages[1]);

		imgs.put("R", bulletImages[2]);

		imgs.put("D", bulletImages[3]);

	}

	/**
	 *  构造函数1，传递位置和方向
	 * @param x
	 * @param y
	 * @param dir
	 */
	public Bullets(int x, int y, Direction dir) {
		this.x = x;
		this.y = y;
		this.diretion = dir;
	}

	/**
	 * 构造函数2，接受另外两个参数
 	 */
	public Bullets(int x, int y, boolean good, Direction dir, TankClient tc) {
		this(x, y, dir);
		this.good = good;
		this.tc = tc;
	}

	private void move() {

		switch (diretion) {
		case L:
			// 子弹不断向左进攻
			x -= speedX;
			break;

		case U:
			y -= speedY;
			break;

		case R:

			// 子弹不断向右
			x += speedX;
			break;

		case D:
			y += speedY;
			break;

		case STOP:
			break;

		default: break;
		}

		if (x < 0 || y < 0 || x > TankClient.Fram_width
				|| y > TankClient.Fram_length) {
			live = false;
		}
	}

	public void draw(Graphics g) {
		if (!live) {
			tc.bullets.remove(this);
			return;
		}

		// 选择不同方向的子弹
		switch (diretion){
		case L:
			g.drawImage(imgs.get("L"), x, y, null);
			break;

		case U:
			g.drawImage(imgs.get("U"), x, y, null);
			break;

		case R:
			g.drawImage(imgs.get("R"), x, y, null);
			break;

		case D:
			g.drawImage(imgs.get("D"), x, y, null);
			break;
        default: break;
		}

		// 调用子弹move()函数
		move();
	}

	/**
	 * 判读是否还活着
	 * @return
	 */
	public boolean isLive() {
		return live;
	}

	public Rectangle getRect() {
		return new Rectangle(x, y, width, length);
	}

	/**
	 * 当子弹打到坦克时
	 * @param tanks
	 * @return
	 */
	public boolean hitTanks(List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {

			// 对每一个坦克，调用hitTank
			if (hitTank(tanks.get(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 当子弹打到坦克上
	 * @param t
	 * @return
	 */
	public boolean hitTank(Tank t){

		if (this.live && this.getRect().intersects(t.getRect()) && t.isLive()
				&& this.good != t.isGood()) {

			BombTank e = new BombTank(t.getX(), t.getY(), tc);
			tc.bombTanks.add(e);
			if (t.isGood()) {

				// 受一粒子弹寿命减少50，接受4枪就死,总生命值200
				t.setLife(t.getLife() - 50);
				if (t.getLife() <= 0){
					// 当寿命为0时，设置寿命为死亡状态
					t.setLive(false);
				}
			} else {
				t.setLive(false); 
			}

			this.live = false;

			// 射击成功，返回true
			return true;
		}

		// 否则返回false
		return false;
	}

	/**
	 * 子弹打到CommonWall上
	 * @param w
	 * @return
	 */
	public boolean hitWall(CommonWall w) {
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;

			// 子弹打到CommonWall墙上时则移除此击中墙
			this.tc.otherWall.remove(w);
			this.tc.homeWall.remove(w);
			return true;
		}
		return false;
	}

	/**
	 * 子弹打到金属墙上
	 * @param w
	 * @return
	 */
	public boolean hitWall(MetalWall w) {
		if (this.live && this.getRect().intersects(w.getRect())) {
			this.live = false;

			//子弹不能穿越金属墙
			//this.tc.metalWall.remove(w);
			return true;
		}
		return false;
	}

	/**
	 * 当子弹打到家时
	 * @return
	 */
	public boolean hitHome() {
		if (this.live && this.getRect().intersects(tc.home.getRect())) {
			this.live = false;

			// 当家接受一枪时就死亡
			this.tc.home.setLive(false);
			return true;
		}

		return false;
	}

}
