package planegame;

import java.util.Random;

/**
 * 
 * <p color=blue size=5>Title:Bee</p>
 * <p color=blue size=5>Description:蜜蜂：是飞行物</p>
 * <p color=blue size=5>Company:</p>
 * <p color=blue size=5>@author Bob Simon</p>
 * <p color=blue size=5>2017-11-23 下午06:40:00</p>
 *
 */
public class Bee extends FlyingObject implements Award{

	//x坐标移动速度
	private int xSpeed = 1;

	//y坐标移动速度
	private int ySpeed = 2;

	//奖励类型
	private int awardType;
	
	/**
	 * 
	 * <p>Description: 构造方法:初始化数据</p>
	 */
	public Bee(){
		this.image = ShootGame.bee;
		width = image.getWidth();
		height = image.getHeight();
		y = -height;
		Random rand = new Random();
		x = rand.nextInt(ShootGame.WIDTH - width);

		//初始化时给奖励
		awardType = rand.nextInt(2);
	}
	
	/** 获得奖励类型 */
	public int getType(){
		return awardType;
	}

	/** 越界处理 */
	@Override
	public boolean outOfBounds() {
		return y>ShootGame.HEIGHT;
	}

	/** 移动，可斜着飞 */
	@Override
	public void step() {      
		x += xSpeed;
		y += ySpeed;
		if(x > ShootGame.WIDTH-width){  
			xSpeed = -1;
		}
		if(x < 0){
			xSpeed = 1;
		}
	}
}

