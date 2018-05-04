package planegame;

import java.util.Random;

/**
 * <p color=blue size=5>Title:Airplane</p>
 * <p color=blue size=5>Description:敌飞机: 是飞行物，也是敌人</p>
 * <p color=blue size=5>Company:</p>
 * <p color=blue size=5>@author Bob Simon</p>
 * <p color=blue size=5>2017-11-23 下午06:28:51</p>
 */
public class Airplane extends FlyingObject implements Enemy {

    //移动步骤
    private int speed = 3;

    /**
     * 初始化数据
     */
    public Airplane() {
        this.image = ShootGame.airplane;
        width = image.getWidth();
        height = image.getHeight();
        y = -height;
        Random rand = new Random();
        x = rand.nextInt(ShootGame.WIDTH - width);
    }

    /**
     * 获取分数
     */
    @Override
    public int getScore() {
        return 5;
    }

    /**
     * //越界处理
     */
    @Override
    public boolean outOfBounds() {
        return y > ShootGame.HEIGHT;
    }

    /**
     * 移动
     */
    @Override
    public void step() {
        y += speed;
    }

}
