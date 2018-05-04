package eatBean.fish;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Timer;

/**
 * <p color=blue size=5>Title:FishBean</p>
 * <p color=blue size=5>Description:豆豆类</p>
 * <p color=blue size=5>Company:</p>
 * <p color=blue size=5>@author Bob Simon</p>
 * <p color=blue size=5>2017-11-24 上午10:25:53</p>
 */
public class FishBean implements ActionListener {

    /**
     * 小鱼的位置x
     */
    public int posx = 190;
    /**
     * 小鱼的位置y
     */
    public int posy = 190;
    /**
     * 小鱼的大小，以size为半径画豆豆(圆)。
     */
    public int size = 10;
    /**
     * 小鱼的当前颜色
     */
    private Color color = Color.MAGENTA;

    /**
     * 小鱼的新颜色
     */
    private Color newColor = Color.MAGENTA;

    /**
     * 小鱼的旧颜色
     */
    private Color oldColor = Color.GRAY;

    /**
     * 小鱼消失时的闪烁时间
     */
    public static int flickerTime = 350;

    /**
     * 小鱼消失时的闪烁次数
     */
    public static int filckerNum = 8;

    private int hasFileckedNum = 0;

    /**
     * 小鱼消失时闪烁的定时器
     */
    private Timer timer = null;

    /**
     * 缺省构造函数。按位置(40,40)，大小15，颜色Color.MAGENTA构造一个豆豆。
     */
    public FishBean() {
        this(190, 190, 10, Color.MAGENTA, Color.GRAY);
    }

    /**
     * 按照位置、大小、颜色构造一个豆豆类。
     *
     * @param posx     豆豆的水平坐标x。
     * @param posy     豆豆的垂直坐标y。
     * @param size     豆豆的大小。
     * @param newColor 豆豆的颜色。
     * @param oldColor 豆豆的旧颜色
     */
    public FishBean(int posx, int posy, int size, Color newColor, Color oldColor) {
        this.posx = posx;
        this.posy = posy;
        this.size = size;
        this.newColor = newColor;
        this.oldColor = oldColor;
        this.color = newColor;
        timer = new Timer(flickerTime, this);
    }

    /**
     * 移动豆豆到新的位置。
     *
     * @param posx 豆豆的新水平坐标x。
     * @param posy 豆豆的新垂直坐标y。
     */
    public void newPos(int posx, int posy) {
        this.posx = posx;
        this.posy = posy;
    }

    /**
     * 绘制豆豆。
     *
     * @param g 画笔
     */
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(color);
        g.fillOval(posx, posy, size, size);
        g.setColor(c);
    }

    /**
     * 停止Timer。
     */
    public void stopTimer() {
        color = newColor;
        timer.stop();
        hasFileckedNum = 0;
    }

    /**
     * 启动Timer。
     */
    public void runTimer() {
        timer.start();
    }

    /**
     * 定时器事件。
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        hasFileckedNum++;
        if (color.equals(newColor) || color == newColor){
            color = oldColor;
        } else{
            color = newColor;
        }

        //定时器运行的次数完成时，停止定时器。
        if (hasFileckedNum == filckerNum && timer.isRunning()) {
            stopTimer();
        }
    }

    /**
     * Timer定时器是否运行中。
     *
     * @return 返回Timer是否运行中。
     */
    public boolean timerIsRunning() {
        return timer.isRunning();
    }


}
