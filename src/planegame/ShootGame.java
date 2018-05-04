package planegame;

import java.awt.Font;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * 
 * <p color=blue size=5>
 * Title:ShootGame
 * </p>
 * <p color=blue size=5>
 * Description:射击游戏主页面
 * </p>
 * <p color=blue size=5>
 * Company:
 * </p>
 * <p color=blue size=5>
 * @author Bob Simon
 * </p>
 * <p color=blue size=5>
 * 2017-11-23 下午06:36:35
 * </p>
 * 
 */
public class ShootGame extends JPanel {

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	// 面板宽
	public static final int WIDTH = 400;

	// 面板高
	public static final int HEIGHT = 654;

	/** 游戏的当前状态: START RUNNING PAUSE GAME_OVER */
	private int state;
	private static final int START = 0;
	private static final int RUNNING = 1;
	private static final int PAUSE = 2;
	private static final int GAME_OVER = 3;

	// 得分
	private int score = 0;

	// 定时器
	private Timer timer;

	// 时间间隔(毫秒)
	private int intervel = 1000 / 100;

	public static BufferedImage background;
	public static BufferedImage start;
	public static BufferedImage airplane;
	public static BufferedImage bee;
	public static BufferedImage bullet;
	public static BufferedImage hero0;
	public static BufferedImage hero1;
	public static BufferedImage pause;
	public static BufferedImage gameover;

	// 敌机数组
	private FlyingObject[] flyings = {};

	// 子弹数组
	private Bullet[] bullets = {};

	// 英雄机
	private Hero hero = new Hero();

	// 静态代码块，初始化图片资源
	static {
		try {
			background = ImageIO.read(ShootGame.class.getResource("background.png"));
			start = ImageIO.read(ShootGame.class.getResource("start.png"));
			airplane = ImageIO.read(ShootGame.class.getResource("airplane.png"));
			bee = ImageIO.read(ShootGame.class.getResource("bee.png"));
			bullet = ImageIO.read(ShootGame.class.getResource("bullet.png"));
			hero0 = ImageIO.read(ShootGame.class.getResource("hero0.png"));
			hero1 = ImageIO.read(ShootGame.class.getResource("hero1.png"));
			pause = ImageIO.read(ShootGame.class.getResource("pause.png"));
			gameover = ImageIO.read(ShootGame.class.getResource("gameover.png"));

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 画
	 */
	@Override
	public void paint(Graphics g) {

		// 画背景图
		g.drawImage(background,0,0,null);

		// 画英雄机
		paintHero(g);

		// 画子弹
		paintBullets(g);

		// 画飞行物
		paintFlyingObjects(g);

		// 画分数
		paintScore(g);

		// 画游戏状态
		paintState(g);
	}

	/**
	 * 
	 * @Title: paintHero
	 * @Description: 画英雄机
	 * @return void
	 * @throws
	 */
	public void paintHero(Graphics g) {
		g.drawImage(hero.getImage(),hero.getX(),hero.getY(),null);
	}

	/**
	 * 
	 * @Title: paintBullets
	 * @Description: 画子弹
	 * @return void
	 * @throws
	 */
	public void paintBullets(Graphics g) {
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			g.drawImage(b.getImage(), b.getX() - b.getWidth() / 2, b.getY(),
					null);
		}
	}

	/**
	 * 
	 * @Title: paintFlyingObjects
	 * @Description: 画飞行物
	 * @return void
	 * @throws
	 */
	public void paintFlyingObjects(Graphics g) {
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			g.drawImage(f.getImage(), f.getX(), f.getY(), null);
		}
	}

	/**
	 * 
	 * @Title: paintScore
	 * @Description: 画分数
	 * @return void
	 * @throws
	 */
	public void paintScore(Graphics g) {

		// x坐标
		int x = 10;

		// y坐标
		int y = 25;

		// 字体
		Font font = new Font(Font.SANS_SERIF, Font.BOLD, 22);
		g.setColor(new Color(0xFF0000));

		// 设置字体
		g.setFont(font);

		// 画分数
		g.drawString("SCORE:" + score, x, y);

		// y坐标增20
		y = y + 20;

		// 画命
		g.drawString("LIFE:" + hero.getLife(), x, y);

		// 画命
		g.drawString("AUTHOR:Bob Simon" , x,y + 20);
	}

	/**
	 * 
	 * @Title: paintState
	 * @Description: 画游戏状态
	 * @return void
	 * @throws
	 */
	public void paintState(Graphics g) {
		switch (state) {

			// 启动状态
		case START:
			g.drawImage(start, 0, 0, null);
			break;

			// 暂停状态
		case PAUSE:
			g.drawImage(pause, 0, 0, null);
			break;

			// 游戏终止状态
		case GAME_OVER:
			g.drawImage(gameover, 0, 0, null);
			break;

		 default: break;
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("飞机大战");

		// 面板对象
		ShootGame game = new ShootGame();

		// 将面板添加到JFrame中
		frame.add(game);

		// 设置大小
		frame.setSize(WIDTH, HEIGHT);

		// 设置其总在最上
		frame.setAlwaysOnTop(true);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		// 默认关闭操作

		// 设置窗体的图标
		frame.setIconImage(new ImageIcon("images/icon.jpg").getImage());

		// 设置窗体初始位置
		frame.setLocationRelativeTo(null);

		// 尽快调用paint
		frame.setVisible(true);
        frame.setResizable(false);

		// 启动执行
		game.action();
	}

	/**
	 * 
	 * @Title: action 启动执行代码
	 * @Description: 
	 * @return void  
	 * @throws
	 */
	public void action() {
		// 鼠标监听事件
		MouseAdapter l = new MouseAdapter() {

			/**
			 * 鼠标移动
			 * @param e
			 */
			@Override
			public void mouseMoved(MouseEvent e){

				// 运行状态下移动英雄机--随鼠标位置
				if (state == RUNNING){
					int x = e.getX();
					int y = e.getY();
					hero.moveTo(x, y);
				}
			}

			/**
			 * 鼠标进入
			 * @param e
			 */
			@Override
			public void mouseEntered(MouseEvent e) {

				// 暂停状态下运行
				if (state == PAUSE){
					state = RUNNING;
				}
			}

			// 鼠标退出
			@Override
			public void mouseExited(MouseEvent e) {

				// 游戏未结束，则设置其为暂停
				if (state == RUNNING) {
					state = PAUSE;
				}
			}

			// 鼠标点击
			@Override
			public void mouseClicked(MouseEvent e) {
				switch (state) {
				case START:
					// 启动状态下运行
					state = RUNNING;
					break;

					// 游戏结束，清理现场
				case GAME_OVER:

					// 清空飞行物
					flyings = new FlyingObject[0];

					// 清空子弹
					bullets = new Bullet[0];

					// 重新创建英雄机
					hero = new Hero();

					// 清空成绩
					score = 0;

					// 状态设置为启动
					state = START;
					break;
				default: break;
				}
			}
		};

		// 处理鼠标点击操作
		this.addMouseListener(l);

		// 处理鼠标滑动操作
		this.addMouseMotionListener(l);

		// 主流程控制
		timer = new Timer();
		timer.schedule(new TimerTask() {
			@Override
			public void run() {
				// 运行状态
				if (state == RUNNING) {

					// 飞行物入场
					enterAction();

					// 走一步
					stepAction();

					// 英雄机射击
					shootAction();

					// 子弹打飞行物
					bangAction();

					// 删除越界飞行物及子弹
					outOfBoundsAction();

					// 检查游戏结束
					checkGameOverAction();
				}

				// 重绘，调用paint()方法
				repaint();
			}

		}, intervel, intervel);
	}

	// 飞行物入场计数
	int flyEnteredIndex = 0;

	/**
	 * 
	 * @Title: enterAction 
	 * @Description: 飞行物入场
	 * @return void    
	 * @throws
	 */
	public void enterAction() {
		flyEnteredIndex++;

		// 400毫秒生成一个飞行物--10*40
		if (flyEnteredIndex % 40 == 0) {

			// 随机生成一个飞行物
			FlyingObject obj = nextOne();
			flyings = Arrays.copyOf(flyings,flyings.length + 1);
			flyings[flyings.length - 1] = obj;
		}
	}

	/**
	 * 
	 * @Title: stepAction 
	 * @Description: 走一步
	 * @return void     
	 * @throws
	 */
	public void stepAction(){

		// 飞行物走一步
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			f.step();
		}

		// 子弹走一步
		for (int i = 0; i < bullets.length; i++){
			Bullet b = bullets[i];
			b.step();
		}

		// 英雄机走一步
		hero.step();
	}

	/**
	 * 
	 * @Title: flyingStepAction 
	 * @Description: 飞行物走一步
	 * @return void   
	 * @throws
	 */
	public void flyingStepAction() {
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			f.step();
		}
	}

	// 射击计数
	int shootIndex = 0;

	/**
	 * 
	 * @Title: shootAction 
	 * @Description: 射击
	 * @return void     
	 * @throws
	 */
	public void shootAction() {
		shootIndex++;

		// 300毫秒发一颗
		if (shootIndex % 30 == 0) {

			// 英雄打出子弹
			Bullet[] bs = hero.shoot();

			// 扩容
			bullets = Arrays.copyOf(bullets, bullets.length + bs.length);

			// 追加数组
			System.arraycopy(bs, 0, bullets, bullets.length - bs.length,
					bs.length);
		}
	}

	
	/**
	 * 
	 * @Title: bangAction 
	 * @Description: 子弹与飞行物碰撞检测 
	 * @return void   
	 * @throws
	 */
	public void bangAction() {

		// 遍历所有子弹
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];

			// 子弹和飞行物之间的碰撞检查
			bang(b);
		}
	}

	/**
	 * 
	 * @Title: outOfBoundsAction 
	 * @Description: 删除越界飞行物及子弹
	 * @return void   
	 * @throws
	 */
	public void outOfBoundsAction(){

		// 索引
		int index = 0;

		// 活着的飞行物
		FlyingObject[] flyingLives = new FlyingObject[flyings.length];
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject f = flyings[i];
			if (!f.outOfBounds()) {

				// 不越界的留着
				flyingLives[index++] = f;
			}
		}

		// 将不越界的飞行物都留着
		flyings = Arrays.copyOf(flyingLives, index);

		// 索引重置为0
		index = 0;

		Bullet[] bulletLives = new Bullet[bullets.length];
		for (int i = 0; i < bullets.length; i++) {
			Bullet b = bullets[i];
			if (!b.outOfBounds()) {
				bulletLives[index++] = b;
			}
		}

		// 将不越界的子弹留着
		bullets = Arrays.copyOf(bulletLives, index);
	}

	
	/**
	 * 
	 * @Title: checkGameOverAction 
	 * @Description: 检查游戏结束
	 * @return void   
	 * @throws
	 */
	public void checkGameOverAction() {
		if (isGameOver() == true){

			// 改变状态
			state = GAME_OVER;
		}
	}
    
	/**
	 * 
	 * @Title: isGameOver 
	 * @Description: 检查游戏是否结束
	 * @return boolean   
	 * @throws
	 */
	public boolean isGameOver(){

		for (int i = 0; i < flyings.length; i++){
			int index = -1;
			FlyingObject obj = flyings[i];

			// 检查英雄机与飞行物是否碰撞
			if (hero.hit(obj)){

				// 减命
				hero.subtractLife();

				// 双倍火力解除
				hero.setDoubleFire(0);

				// 记录碰上的飞行物索引
				index = i;
			}
			if (index != -1) {
				FlyingObject t = flyings[index];
				flyings[index] = flyings[flyings.length - 1];

				// 碰上的与最后一个飞行物交换
				flyings[flyings.length - 1] = t;
                // 删除碰上的飞行物
				flyings = Arrays.copyOf(flyings, flyings.length - 1);
			}
		}

		return hero.getLife() <= 0;
	}

	/**
	 * 
	 * @Title: bang 
	 * @Description: 子弹和飞行物之间的碰撞检查
	 * @return void    
	 * @throws
	 */
	public void bang(Bullet bullet){

		// 击中的飞行物索引
		int index = -1;
		for (int i = 0; i < flyings.length; i++) {
			FlyingObject obj = flyings[i];

			// 判断是否击中
			if (obj.shootBy(bullet)){

				// 记录被击中的飞行物的索引
				index = i;
				break;
			}
		}

		// 有击中的飞行物
		if (index != -1) {

			// 记录被击中的飞行物
			FlyingObject one = flyings[index];

			// 被击中的飞行物与最后一个飞行物交换
			FlyingObject temp = flyings[index];
			flyings[index] = flyings[flyings.length - 1];
			flyings[flyings.length - 1] = temp;

			// 删除最后一个飞行物(即被击中的)
			flyings = Arrays.copyOf(flyings, flyings.length - 1);

			// 检查one的类型(敌人加分，奖励获取)
			// 检查类型，是敌人，则加分
			if (one instanceof Enemy) {
				// 强制类型转换
				Enemy e = (Enemy) one;
				// 加分
				score += e.getScore();
			} else {
				// 若为奖励，设置奖励
				Award a = (Award) one;
				// 获取奖励类型
				int type = a.getType();
				switch (type) {
				case Award.DOUBLE_FIRE:
					// 设置双倍火力
					hero.addDoubleFire();
					break;
				case Award.LIFE:
					// 设置加命
					hero.addLife();
					break;

				default: break;
				}
			}
		}
	}

	/**
	 * 
	 * @Title: nextOne 
	 * @Description: 随机生成飞行物
	 * @return FlyingObject 飞行物对象
	 * @throws
	 */
	public static FlyingObject nextOne() {
		Random random = new Random();

		// [0,20)
		int type = random.nextInt(20);
		if (type < 4) {
			return new Bee();
		} else {
			return new Airplane();
		}
	}

}
