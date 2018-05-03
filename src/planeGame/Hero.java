package planeGame;

import java.awt.image.BufferedImage;

/**
 * 
 * <p color=blue size=5>Title:Hero</p>
 * <p color=blue size=5>Description:英雄机：是飞行物</p>
 * <p color=blue size=5>Company:</p>
 * <p color=blue size=5>@author Bob Simon</p>
 * <p color=blue size=5>2017-11-23 下午10:04:18</p>
 *
 */
public class Hero extends FlyingObject{
	
	private BufferedImage[] images = {};  //英雄机图片
	private int index = 0;                //英雄机图片切换索引
	
	private int doubleFire;   //双倍火力
	private int life;   //命
	
	/**
	 * 
	 * <p>Description: 构造方法:初始化数据</p>
	 */
	public Hero(){
		life = 3;   //初始3条命
		doubleFire = 0;   //初始火力为0
		images = new BufferedImage[]{ShootGame.hero0, ShootGame.hero1}; //英雄机图片数组
		image = ShootGame.hero0;   //初始为hero0图片
		width = image.getWidth();
		height = image.getHeight();
		x = 150;
		y = 400;
	}
	
	/**
	 * 
	 * @Title: isDoubleFire 
	 * @Description: 获取双倍火力
	 * @return int   
	 * @throws
	 */
	public int isDoubleFire() {
		return doubleFire;
	}

	/**
	 * 
	 * @Title: setDoubleFire 
	 * @Description: 设置双倍火力
	 * @return void    
	 * @throws
	 */
	public void setDoubleFire(int doubleFire) {
		this.doubleFire = doubleFire;
	}
	
	/**
	 * 
	 * @Title: addDoubleFire 
	 * @Description: 增加火力 
	 * @return void   
	 * @throws
	 */
	public void addDoubleFire(){
		doubleFire = 40;
	}
	
	/**
	 * 
	 * @Title: addLife 
	 * @Description: 增命  
	 * @return void   
	 * @throws
	 */
	public void addLife(){  //增命
		life++;
	}
	
	/**
	 * 
	 * @Title: subtractLife 
	 * @Description: 减命
	 * @return void   
	 * @throws
	 */
	public void subtractLife(){   //减命
		life--;
	}
	
	/**
	 * 
	 * @Title: getLife 
	 * @Description: 获取命
	 * @return int   
	 * @throws
	 */
	public int getLife(){
		return life;
	}
	
	/**
	 * 
	 * @Title: moveTo 
	 * @Description: 当前物体移动了一下，相对距离，x,y鼠标位置 
	 * @return void     
	 * @throws
	 */
	public void moveTo(int x,int y){   
		this.x = x - width/2;
		this.y = y - height/2;
	}

	/**
	 * 越界处理
	 */
	@Override
	public boolean outOfBounds() {
		return false;  
	}

	/**
	 * 
	 * @Title: shoot 
	 * @Description: 发射子弹
	 * @return Bullet[] 
	 * @throws
	 */
	public Bullet[] shoot(){   
		int xStep = width/4;      //4半
		int yStep = 20;  //步
		if(doubleFire>0){  //双倍火力
			Bullet[] bullets = new Bullet[2];
			bullets[0] = new Bullet(x+xStep,y-yStep);  //y-yStep(子弹距飞机的位置)
			bullets[1] = new Bullet(x+3*xStep,y-yStep);
			return bullets;
		}else{      //单倍火力
			Bullet[] bullets = new Bullet[1];
			bullets[0] = new Bullet(x+2*xStep,y-yStep);  
			return bullets;
		}
	}

	/** 移动 */
	@Override
	public void step() {
		if(images.length>0){
			image = images[index++/10%images.length];  //切换图片hero0，hero1
		}
	}
	
	/**
	 * 
	 * @Title: hit 
	 * @Description: 碰撞算法
	 * @return boolean    
	 * @throws
	 */
	public boolean hit(FlyingObject other){
		
		int x1 = other.x - this.width/2;                 //x坐标最小距离
		int x2 = other.x + this.width/2 + other.width;   //x坐标最大距离
		int y1 = other.y - this.height/2;                //y坐标最小距离
		int y2 = other.y + this.height/2 + other.height; //y坐标最大距离
	
		int herox = this.x + this.width/2;               //英雄机x坐标中心点距离
		int heroy = this.y + this.height/2;              //英雄机y坐标中心点距离
		
		return herox>x1 && herox<x2 && heroy>y1 && heroy<y2;   //区间范围内为撞上了
	}
	
}

