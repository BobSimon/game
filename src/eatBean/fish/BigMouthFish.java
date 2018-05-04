package eatBean.fish;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * 大嘴鱼类<br>
 * 此类继承AcitonListener，实现AcitonPerformed方法，练习ActionListener的另一种实现方法<br>
 * 此类还练习定时器Timer的使用<br>
 * 此类还练习图形类Graphics的使用<br>
 *
 * @author Bob Simon
 * @version 1.0
 * @since JDK1.6(建议)
 */
public class BigMouthFish implements ActionListener {

    /**
     * 大嘴鱼嘴的方向：0-上
     */
    public static int UP = 0;
    /**
     * 大嘴鱼嘴的方向：1-右
     */
    public static int RIGHT = 1;
    /**
     * 大嘴鱼嘴的方向：2-下
     */
    public static int DOWN = 2;
    /**
     * 大嘴鱼嘴的方向：3-左
     */
    public static int LEFT = 3;

    /**
     * 大嘴鱼的身体大小，以size为半径画圆
     */
    public int size = 50;
    /**
     * 大嘴鱼现在的方向(以嘴的方向为基准)
     */
    public int direction = RIGHT;
    /**
     * 大嘴鱼身体的颜色
     */
    private Color color = Color.CYAN;
    /**
     * 大嘴鱼的位置x
     */
    public int posx = 80;
    /**
     * 大嘴鱼的位置y
     */
    public int posy = 80;
    /**
     * 大嘴鱼的速度，鱼每次前进的像素
     */
    public int speed = 4;
    /**
     * 大嘴鱼眼睛的大小
     */
    private int eyesize = size / 5;
    /**
     * 大嘴鱼眼睛的位置x
     */
    private int eyeposx = posx + size / 2;
    /**
     * 大嘴鱼眼睛的位置y
     */
    private int eyeposy = posy + size / 5;
    /**
     * 大嘴鱼眼睛的颜色
     */
    private Color eyecolor = Color.RED;
    /**
     * 大嘴鱼嘴的最大角度的一半
     */
    private int maxMonth = 30;
    /**
     * 大嘴鱼现在嘴角的角度
     */
    private int monthsize = 30;

    /**
     * 大嘴鱼的嘴是否张开
     */
    private boolean isOpen = true;

    private Timer time = null;

    /**
     * 大嘴鱼缺省构造函数.<br>
     * 创建一个位置为(200,200)，大小为50，方向为右，颜色为Color.CYAN，速度为10的大嘴鱼
     */
    public BigMouthFish() {
        //this的使用。
        this(200, 200, 50, RIGHT, Color.CYAN, 4);
    }

    /**
     * 根据位置、大小、方向、颜色、速度构造一个大嘴鱼。
     *
     * @param posx      大嘴鱼的位置x。
     * @param posy      大嘴鱼的位置y。
     * @param size      大嘴鱼的大小。
     * @param direction 大嘴鱼的方向：0-上；1-右；2-下；3-左。出入错误时，默认改为1。
     * @param color     大嘴鱼的颜色。
     * @param speed     大嘴鱼的速度。
     */
    public BigMouthFish(int posx, int posy, int size, int direction, Color color, int speed) {
        this.posx = posx;
        this.posy = posy;
        this.size = size;
        if (direction == 1 || direction == 2 || direction == 3 || direction == 4) {
            this.direction = direction;
        }

        this.color = color;
        this.speed = speed;
        eyesize = size / 7;
        initEye();

        time = new Timer(FishPool.reTime, this);
        time.start();
    }

    /**
     * 大嘴鱼移动。根据鱼的方向移动鱼。
     */
    public void move() {
        switch (direction) {
            case 0:
                posy--;
                break;
            case 1:
                posx++;
                break;
            case 2:
                posy++;
                break;
            case 3:
                posx--;
                break;
            default:
                break;
        }
    }

    /**
     * 改变大嘴鱼身体的颜色。
     *
     * @param color 欲改变大嘴鱼身体的颜色
     */
    public void changeColor(Color color) {
        this.color = color;
    }

    /**
     * 改变大嘴鱼的方向
     *
     * @param direction 欲改变大嘴鱼的方向
     */
    public void changeDir(int direction) {
        this.direction = direction;
    }

    /**
     * 使用画笔绘画大嘴鱼.大嘴鱼的头像：一个扇形的是鱼脸，上面有一个小圆是眼睛<br>
     * 1.保存画笔颜色<br>
     * 2.绘制大嘴鱼的脸<br>
     * 3.绘制大嘴鱼的眼睛<br>
     * 4.恢复画笔颜色<br>
     *
     * @param g 画笔
     */
    public void paint(Graphics g) {
        //保存画笔的颜色
        Color c = g.getColor();
        //绘制鱼脸
        g.setColor(color);
        //从(posx,posy)点开始，绘制宽为size,高为size,开始角度为(direction%2==0?(direction+1):(direction-1))*90+monthsize，弧度为360-2*maxMonth的弧形
        g.fillArc(posx, posy, size, size, (direction % 2 == 0 ? (direction + 1) : (direction - 1)) * 90 + monthsize, 360 - 2 * monthsize);
        //绘制鱼眼
        initEye();
        g.setColor(eyecolor);
        g.fillOval(eyeposx, eyeposy, eyesize, eyesize);
        //恢复画笔颜色
        g.setColor(c);
    }

    /**
     * 大嘴鱼张嘴闭嘴事件<br>
     * 此处只负责鱼的角度的变化，不负责鱼的重绘。
     * 重绘在鱼池中实现。<br>
     * 这样的好处：保证鱼的绘制和步伐分开。显示层和逻辑层单独处理。（面向对象的责任问题）。
     *
     * @param e 事件对象
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (isOpen) {
            monthsize -= 2;
            if (monthsize <= 0) {
                isOpen = false;
            }

        } else {
            monthsize += 2;
            if (monthsize >= maxMonth) {
                isOpen = true;
            }

        }
    }

    /**
     * 方向改变时，眼睛的改变。
     */
    private void initEye() {

        switch (direction) {

            case 0:
                eyeposx = posx + size / 7;
                eyeposy = posy + size / 2 - eyesize;
                break;

            case 1:
                eyeposx = posx + size / 2;
                eyeposy = posy + size / 7;
                break;

            case 2:
                eyeposx = posx + size * 5 / 7;
                eyeposy = posy + size / 2;
                break;

            case 3:
                eyeposx = posx + size / 2 - eyesize;
                eyeposy = posy + size / 7;
                break;

            default:
                break;
        }
    }

}
