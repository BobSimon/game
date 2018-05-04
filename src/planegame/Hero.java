package planegame;

import java.awt.image.BufferedImage;

/**
 * <p color=blue size=5>Title:Hero</p>
 * <p color=blue size=5>Description:英雄机：是飞行物</p>
 * <p color=blue size=5>Company:</p>
 * <p color=blue size=5>@author Bob Simon</p>
 * <p color=blue size=5>2017-11-23 下午10:04:18</p>
 */
public class Hero extends FlyingObject {

    //英雄机图片
    private BufferedImage[] images = {};

    //英雄机图片切换索引
    private int index = 0;

    //双倍火力
    private int doubleFire;

    //命
    private int life;

    /**
     * <p>Description: 构造方法:初始化数据</p>
     */
    public Hero() {

        //初始3条命
        life = 3;

        //初始火力为0
        doubleFire = 0;
        //英雄机图片数组
        images = new BufferedImage[]{ShootGame.hero0, ShootGame.hero1};
        image = ShootGame.hero0;   //初始为hero0图片
        width = image.getWidth();
        height = image.getHeight();
        x = 150;
        y = 400;
    }

    /**
     * @return int
     * @throws
     * @Title: isDoubleFire
     * @Description: 获取双倍火力
     */
    public int isDoubleFire() {
        return doubleFire;
    }

    /**
     * @return void
     * @throws
     * @Title: setDoubleFire
     * @Description: 设置双倍火力
     */
    public void setDoubleFire(int doubleFire) {
        this.doubleFire = doubleFire;
    }

    /**
     * @return void
     * @throws
     * @Title: addDoubleFire
     * @Description: 增加火力
     */
    public void addDoubleFire() {
        doubleFire = 40;
    }

    /**
     * @return void
     * @throws
     * @Title: addLife
     * @Description: 增命
     */
    public void addLife() {  //增命
        life++;
    }

    /**
     * @return void
     * @throws
     * @Title: subtractLife
     * @Description: 减命
     */
    public void subtractLife() {   //减命
        life--;
    }

    /**
     * @return int
     * @throws
     * @Title: getLife
     * @Description: 获取命
     */
    public int getLife() {
        return life;
    }

    /**
     * @return void
     * @throws
     * @Title: moveTo
     * @Description: 当前物体移动了一下，相对距离，x,y鼠标位置
     */
    public void moveTo(int x, int y) {
        this.x = x - width / 2;
        this.y = y - height / 2;
    }

    /**
     * 越界处理
     */
    @Override
    public boolean outOfBounds() {
        return false;
    }

    /**
     * @return Bullet[]
     * @throws
     * @Title: shoot
     * @Description: 发射子弹
     */
    public Bullet[] shoot() {

        //4半
        int xStep = width / 4;

        //步
        int yStep = 20;

        //双倍火力
        if (doubleFire > 0) {
            Bullet[] bullets = new Bullet[2];

            //y-yStep(子弹距飞机的位置)
            bullets[0] = new Bullet(x + xStep, y - yStep);
            bullets[1] = new Bullet(x + 3 * xStep, y - yStep);
            return bullets;

            //单倍火力
        } else {
            Bullet[] bullets = new Bullet[1];
            bullets[0] = new Bullet(x + 2 * xStep, y - yStep);
            return bullets;
        }
    }

    /**
     * 移动
     */
    @Override
    public void step() {
        if (images.length > 0) {

            //切换图片hero0，hero1
            image = images[index++ / 10 % images.length];
        }
    }

    /**
     * @return boolean
     * @throws
     * @Title: hit
     * @Description: 碰撞算法
     */
    public boolean hit(FlyingObject other) {

        //x坐标最小距离
        int x1 = other.x - this.width / 2;

        //x坐标最大距离
        int x2 = other.x + this.width / 2 + other.width;

        //y坐标最小距离
        int y1 = other.y - this.height / 2;

        //y坐标最大距离
        int y2 = other.y + this.height / 2 + other.height;

        //英雄机x坐标中心点距离
        int herox = this.x + this.width / 2;

        //英雄机y坐标中心点距离
        int heroy = this.y + this.height / 2;

        //区间范围内为撞上了
        return herox > x1 && herox < x2 && heroy > y1 && heroy < y2;
    }

}

