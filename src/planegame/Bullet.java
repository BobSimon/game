package planegame;

/***
 * <p color=blue size=5>Title:Bullet</p>
 * <p color=blue size=5>Description:子弹类：是飞行物</p>
 * <p color=blue size=5>Company:</p>
 * <p color=blue size=5>@author Bob Simon</p>
 * <p color=blue size=5>2017-11-23 下午06:38:04</p>
 */
public class Bullet extends FlyingObject {

	//移动的速度
	private int speed = 3;
	
	/** 初始化数据 */
	public Bullet(int x,int y){
		this.x = x;
		this.y = y;
		this.image = ShootGame.bullet;
	}

	/** 移动 */
	@Override
	public void step(){   
		y-=speed;
	}

	/** 越界处理 */
	@Override
	public boolean outOfBounds() {
		return y<-height;
	}

}
