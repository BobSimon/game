package tankwar;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

/**
 * @Author: Bob Simon
 * @Description:坦克
 * @Date: Created in 10:29 2018\5\4
 */
public class Tank {

    // 静态全局变量速度---------可以作为扩张来设置级别，速度快的话比较难
    public static int speedX = 6, speedY = 6;

    public static int count = 0;

    // 坦克的全局大小，具有不可改变性
    public static final int width = 35, length = 35;

    // 初始化状态为静止
    private Direction direction = Direction.STOP;

    // 初始化方向为向上
    private Direction Kdirection = Direction.U;

    TankClient tc;

    private boolean good;
    private int x, y;
    private int oldX, oldY;

    // 初始化为活着
    private boolean live = true;

    // 初始生命值
    private int life = 200;

    private static Random r = new Random();

    // 产生一个随机数,随机模拟坦克的移动路径
    private int step = r.nextInt(10) + 5;

    private boolean bL = false, bU = false, bR = false, bD = false;

    // 控制面板
    private static Toolkit tk = Toolkit.getDefaultToolkit();

    // 存储全局静态
    private static Image[] tankImags = null;

    static {
        tankImags = new Image[]{
                tk.getImage(BombTank.class.getResource("images/tankD.gif")),
                tk.getImage(BombTank.class.getResource("images/tankU.gif")),
                tk.getImage(BombTank.class.getResource("images/tankL.gif")),
                tk.getImage(BombTank.class.getResource("images/tankR.gif")),};

    }

    /**
     * Tank的构造函数1
     * @param x
     * @param y
     * @param good
     */
    public Tank(int x, int y, boolean good) {
        this.x = x;
        this.y = y;
        this.oldX = x;
        this.oldY = y;
        this.good = good;
    }

    /**
     * Tank的构造函数2
     * @param x
     * @param y
     * @param good
     * @param dir
     * @param tc
     */
    public Tank(int x, int y, boolean good, Direction dir, TankClient tc) {
        this(x, y, good);
        this.direction = dir;
        this.tc = tc;
    }

    public void draw(Graphics g) {
        if (!live) {
            if(!good) {
                // 删除无效的
                tc.tanks.remove(this);
            }

            return;
        }

        if (good){
            // 创造一个血包
            new DrawBloodbBar().draw(g);
        }

        switch (Kdirection) {
            //根据方向选择坦克的图片
            case D:
                g.drawImage(tankImags[0], x, y, null);
                break;

            case U:
                g.drawImage(tankImags[1], x, y, null);
                break;
            case L:
                g.drawImage(tankImags[2], x, y, null);
                break;

            case R:
                g.drawImage(tankImags[3], x, y, null);
                break;

            default:
                break;
        }

        //调用move函数
        move();
    }

    void move() {

        this.oldX = x;

        this.oldY = y;

        /**
         * 选择移动方向
         */
        switch (direction) {
            case L:
                x -= speedX;
                break;
            case U:
                y -= speedY;
                break;
            case R:
                x += speedX;
                break;
            case D:
                y += speedY;
                break;
            case STOP:
                break;

            default:
                break;
        }

        if (this.direction != Direction.STOP) {
            this.Kdirection = this.direction;
        }

        if (x < 0) {
            x = 0;
        }

        //防止走出规定区域
        if (y < 40) {
            y = 40;
        }

        //超过区域则恢复到边界
        if (x + Tank.width > TankClient.Fram_width) {
            x = TankClient.Fram_width - Tank.width;
        }

        if (y + Tank.length > TankClient.Fram_length) {
            y = TankClient.Fram_length - Tank.length;
        }

        if (!good) {
            Direction[] directons = Direction.values();
            if (step == 0) {
                //产生随机路径
                step = r.nextInt(12) + 3;
                int rn = r.nextInt(directons.length);
                //产生随机方向
                direction = directons[rn];
            }
            step--;

            //产生随机数，控制敌人开火
            if (r.nextInt(40) > 38) {
                this.fire();
            }

        }
    }

    private void changToOldDir() {
        x = oldX;
        y = oldY;
    }

    /**
     * 接受键盘事件
     *
     * @param e
     */
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {

            //当按下R时，重新开始游戏
            case KeyEvent.VK_R:
                tc.tanks.clear();  //清理
                tc.bullets.clear();
                tc.trees.clear();
                tc.otherWall.clear();
                tc.homeWall.clear();
                tc.metalWall.clear();
                tc.homeTank.setLive(false);

                //当在区域中没有坦克时，就出来坦克
                if (tc.tanks.size() == 0) {
                    for (int i = 0; i < 20; i++) {

                        //设置坦克出现的位置
                        if (i < 9) {
                            tc.tanks.add(new Tank(150 + 70 * i, 40, false,
                                    Direction.R, tc));
                        } else if (i < 15) {
                            tc.tanks.add(new Tank(700, 140 + 50 * (i - 6), false,
                                    Direction.D, tc));
                        } else {
                            tc.tanks.add(new Tank(10, 50 * (i - 12), false,
                                    Direction.L, tc));
                        }

                    }
                }

                //设置自己出现的位置
                tc.homeTank = new Tank(300, 560, true, Direction.STOP, tc);

                //将home重置生命
                if (!tc.home.isLive()) {
                    tc.home.setLive(true);
                    //重新创建面板
                    new TankClient();
                }
                break;

            //监听向右键
            case KeyEvent.VK_RIGHT:
                bR = true;
                break;

            //监听向左键
            case KeyEvent.VK_LEFT:
                bL = true;
                break;

            //监听向上键
            case KeyEvent.VK_UP:
                bU = true;
                break;

            //监听向下键
            case KeyEvent.VK_DOWN:
                bD = true;
                break;

            default:
                break;
        }

        //调用函数确定移动方向
        decideDirection();
    }

    void decideDirection() {
        if (!bL && !bU && bR && !bD){
            //向右移动
            direction = Direction.R;
        } else if (bL && !bU && !bR && !bD){
            //向左移
            direction = Direction.L;
        } else if (!bL && bU && !bR && !bD){
            //向上移动
            direction = Direction.U;
        } else if (!bL && !bU && !bR && bD){
            //向下移动
            direction = Direction.D;
        } else if (!bL && !bU && !bR && !bD){
            //没有按键，就保持不动
            direction = Direction.STOP;
        }

    }

    /**
     * 键盘释放监听
     * @param e
     */
    public void keyReleased(KeyEvent e) {
        int key = e.getKeyCode();
        switch (key) {

            case KeyEvent.VK_F:
                fire();
                break;

            case KeyEvent.VK_RIGHT:
                bR = false;
                break;

            case KeyEvent.VK_LEFT:
                bL = false;
                break;

            case KeyEvent.VK_UP:
                bU = false;
                break;

            case KeyEvent.VK_DOWN:
                bD = false;
                break;

            default: break;
        }

        //释放键盘后确定移动方向
        decideDirection();
    }

    /**
     * 开火方法
     * @return
     */
    public Bullets fire() {
        if (!live){
            return null;
        }

        //开火位置
        int x = this.x + Tank.width / 2 - Bullets.width / 2;
        int y = this.y + Tank.length / 2 - Bullets.length / 2;

        //没有给定方向时，向原来的方向发火
        Bullets m = new Bullets(x, y + 2, good, Kdirection, this.tc);
        tc.bullets.add(m);
        return m;
    }


    public Rectangle getRect() {
        return new Rectangle(x, y, width, length);
    }

    public boolean isLive() {
        return live;
    }

    public void setLive(boolean live) {
        this.live = live;
    }

    public boolean isGood() {
        return good;
    }

    /**
     * 碰撞到普通墙时
     * @param w
     * @return
     */
    public boolean collideWithWall(CommonWall w){
        if (this.live && this.getRect().intersects(w.getRect())){

            //转换到原来的方向上去
            this.changToOldDir();
            return true;
        }

        return false;
    }

    /**
     * 撞到金属墙
     * @param w
     * @return
     */
    public boolean collideWithWall(MetalWall w) {
        if (this.live && this.getRect().intersects(w.getRect())) {
            this.changToOldDir();
            return true;
        }
        return false;
    }

    /**
     * 撞到河流的时候
     * @param r
     * @return
     */
    public boolean collideRiver(River r) {
        if (this.live && this.getRect().intersects(r.getRect())) {
            this.changToOldDir();
            return true;
        }
        return false;
    }

    /**
     * 撞到家的时候
     * @param h
     * @return
     */
    public boolean collideHome(Home h) {
        if (this.live && this.getRect().intersects(h.getRect())) {
            this.changToOldDir();
            return true;
        }
        return false;
    }

    /**
     * 撞到坦克时
     * @param tanks
     * @return
     */
    public boolean collideWithTanks(java.util.List<Tank> tanks) {
        for (int i = 0; i < tanks.size(); i++) {
            Tank t = tanks.get(i);
            if (this != t) {
                if (this.live && t.isLive()
                        && this.getRect().intersects(t.getRect())) {
                    this.changToOldDir();
                    t.changToOldDir();
                    return true;
                }
            }
        }
        return false;
    }


    public int getLife() {
        return life;
    }

    public void setLife(int life) {
        this.life = life;
    }


    /**
     * <p color=blue size=5>Title:DrawBloodbBar</p>
     * <p color=blue size=5>Description:这里需要改动</p>
     * <p color=blue size=5>Company:</p>
     * <p color=blue size=5>@author Bob Simon</p>
     * <p color=blue size=5>2017-11-24 上午11:01:34</p>
     */
    private class DrawBloodbBar {
        public void draw(Graphics g) {
            Color c = g.getColor();
            g.setColor(Color.RED);
            g.drawRect(375, 585, width, 10);
            int w = width * life / 200;
            g.fillRect(375, 585, w, 10);
            g.setColor(c);
        }

    }

    public boolean eat(GetBlood b) {
        if (this.live && b.isLive() && this.getRect().intersects(b.getRect())) {
            if (this.life <= 100) {
                //每吃一个，增加100生命点
                this.life = this.life + 100;
            } else {

            }
            this.life = 200;
            b.setLive(false);
            return true;
        }
        return false;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}