package eatBean.fish;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;

import javax.swing.AbstractAction;
import javax.swing.JLabel;
import javax.swing.KeyStroke;
import javax.swing.Timer;

import eatBean.main.BigMouthFishFrame;

import eatBean.tools.FishUtilities;
import eatBean.tools.RandomUtil;

/**
 * 鱼池类。<br>
 * 大鱼和小鱼放到此鱼池中，当大嘴鱼每吃10条小鱼的时候，鱼的size加1，速度加2。<br>
 * 每条小鱼默认存在的时间是15秒钟。10秒后自动消失。<br>
 * 此类练习if、for、Timer、内部类等的使用。<br>
 * 更重要的是，希望做这个的游戏时候能做好严谨的逻辑考虑后，再动手。<br>
 * 难点：大嘴鱼在鱼池边界的处理、大嘴鱼升级时的相关问题的处理。<br>
 *
 * @author Bob Simon
 * @version 1.0
 * @since JDK1.6(建议)
 */
public class FishPool extends JLabel {

    /**
     * serialVersionUID
     */
    private static final long serialVersionUID = 1L;

    private BigMouthFish fish = null;
    private FishBean bean = null;


    /**
     * 小鱼存在的时间长短，单位：毫秒
     */
    private int timeLegth = 20 * 1000;
    /**
     * 重绘时间长短，单位：毫秒
     */
    public static int reTime = 100;


    /**
     * 鱼池重绘定时器
     */
    private Timer timer = null;
    /**
     * 小鱼生成定时器
     */
    private Timer time = null;

    /**
     * 大嘴鱼每升级1次属性增加：大小加1
     */
    private int sizeAdd = 1;
    /**
     * 大嘴鱼每升级1次属性增加：速度加2
     */
    private int speedAdd = 2;
    /**
     * 大嘴鱼每吃1条小鱼属性增加：分数加1
     */
    private int scoreAdd = 1;

    /**
     * 大嘴鱼现在的分数
     */
    private int score = 0;
    /**
     * 大嘴鱼升级的速度：每吃10条小鱼升级1次
     */
    private int upgradeNum = 10;
    /**
     * 大嘴鱼已吃小鱼的数量，每吃upgradeNum个，变为0
     */
    private int eatNum = 0;

    /**
     * 大嘴鱼的水平最小边界
     */
    private int min_x = 0;
    /**
     * 大嘴鱼的水平最大边界
     */
    private int max_x = 0;
    /**
     * 大嘴鱼的垂直最小边界
     */
    private int min_y = 0;
    /**
     * 大嘴鱼的垂直最大边界
     */
    private int max_y = 0;

    public FishPool() {
        setSize(BigMouthFishFrame.width, BigMouthFishFrame.height);
        setLocation(10, 10);

        fish = new BigMouthFish();
        initFishBean();

        //此处加3、减3是为了大嘴鱼不直接接触鱼池边界。
        min_x = 3;
        max_x = BigMouthFishFrame.width - fish.size - 3;
        min_y = 3;
        max_y = BigMouthFishFrame.height - fish.size - 3;

        //注册向上键
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "UpPress");
        getActionMap().put("UpPress", new UpListenerImpl());
        //注册向右键
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_RIGHT, 0), "RightPress");
        getActionMap().put("RightPress", new RightListenerImpl());
        //注册向下建
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "DownPress");
        getActionMap().put("DownPress", new DownListenerImpl());
        //注册向左键
        getInputMap().put(KeyStroke.getKeyStroke(KeyEvent.VK_LEFT, 0), "LeftPress");
        getActionMap().put("LeftPress", new LeftListenerImpl());

        timer = new Timer(reTime, new TimerListenerImpl());
        timer.start();

        time = new Timer(timeLegth - FishBean.flickerTime * FishBean.filckerNum, new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //先停止本身定时器。
                time.stop();
                //启动小鱼的闪烁定时器。
                bean.runTimer();
                //检测小鱼是否已经闪烁完毕。
                new Thread() {
                    public void run() {
                        while (true) {
                            //小鱼闪烁完毕后，移动大嘴鱼。
                            //重新启动本身定时器。
                            if (!bean.timerIsRunning()) {
                                beanNewPos();
                                time.restart();
                                break;
                            }
                        }
                    }
                }.start();
            }
        });
        time.start();
    }

    /**
     * 初始化小鱼。<br>
     * 保证小鱼生成的位置与大嘴鱼的位置不重叠。
     */
    private void initFishBean() {
        int size = 15;
        int posx = 0;
        int posy = 0;
        do {
            posx = RandomUtil.randomInt(BigMouthFishFrame.width - size);
            posy = RandomUtil.randomInt(BigMouthFishFrame.height - size);
            //System.out.println("posx:"+posx);
            //System.out.println("posy:"+posy);
        }
        while (posx >= fish.posx && posx <= fish.posx + fish.size && posy >= fish.posy && posy <= fish.posy + fish.size &&
                posx + size >= fish.posx && posx + size <= fish.posx + fish.size && posy + size >= fish.posy && posy + size <= fish.posy + fish.size);
        bean = new FishBean(posx, posy, size, Color.BLUE, getBackground());
    }

    /**
     * 小鱼重新生成新位置。<br>
     * 保证小鱼生成的位置与大嘴鱼的位置不重叠。
     */
    private void beanNewPos() {
        int size = 15;
        int posx = 0;
        int posy = 0;
        do {
            posx = RandomUtil.randomInt(2 * size, BigMouthFishFrame.width - 2 * size);
            posy = RandomUtil.randomInt(2 * size, BigMouthFishFrame.height - 2 * size);
        }
        while (posx >= fish.posx && posx <= fish.posx + fish.size && posy >= fish.posy && posy <= fish.posy + fish.size &&
                posx + size >= fish.posx && posx + size <= fish.posx + fish.size && posy + size >= fish.posy && posy + size <= fish.posy + fish.size);
        bean.newPos(posx, posy);
    }

    /**
     * 覆盖JLabel的paint事件。
     *
     * @param g 画笔。
     */
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        fish.paint(g);
        bean.paint(g);
    }

    /**
     * 大嘴鱼的移动。<br>
     * 根据大嘴鱼嘴的方向，判断其是否到了鱼池边界。<br>
     *
     * @param direction 大嘴鱼移动的方向
     */
    private void bigFishMove(int direction) {
        MOVE:
        for (int i = 0; i < fish.speed; i++) {
            fish.changeDir(direction);
            //大嘴鱼到池边，不可在移动
            switch (direction) {

                case 0:
                    if (fish.posy >= min_y + 1) {
                        if (isTouched()) {
                            break MOVE;
                        }
                    }

                    break;

                case 1:
                    if (fish.posx <= max_x - 1) {
                        if (isTouched()) {
                            break MOVE;
                        }
                    }

                    break;

                case 2:
                    if (fish.posy <= max_y - 1) {
                        if (isTouched()) {
                            break MOVE;
                        }
                    }

                    break;

                case 3:
                    if (fish.posx >= min_x + 1) {
                        if (isTouched()) {
                            break MOVE;
                        }
                    }

                    break;
            }
        }
    }

    /**
     * 检测大嘴鱼是否吃到了小鱼。
     *
     * @return 吃到小鱼就重新生成小鱼。
     */
    private boolean isTouched() {
        fish.move();
        boolean b = FishUtilities.isInteraction(fish, bean);
        if (b) {
            //鱼的升级
            eatNum++;
            score += scoreAdd;
            BigMouthFishFrame.lblScore.setText(score + "");
            if (eatNum == upgradeNum) {
                eatNum = 0;
                fish.size += sizeAdd;
                fish.speed += speedAdd;

                BigMouthFishFrame.lblSize.setText(fish.size + "");
                BigMouthFishFrame.lblSpeed.setText(fish.speed + "");

                //下面的这段代码是发现游戏bug后增加的代码
                //bug：如果大嘴鱼在鱼池边界处恰好升级，将使鱼出界。
                if (fish.posx == max_x || fish.posy == max_y) {
                    fish.posx -= sizeAdd;
                    fish.posy -= sizeAdd;
                }
            }
            //下面的这段代码是发现游戏bug后增加的代码。
            //bug：如果大嘴鱼在小鱼闪烁时吃到小鱼。小鱼将触发新位置，而又将使小鱼继续闪烁。
            //停止小鱼闪烁的定时器，更改颜色为原来的颜色。已经闪烁次数为0。
            if (bean.timerIsRunning()) {
                bean.stopTimer();
            }

            beanNewPos();
            time.restart();
        }
        return b;
    }

    /**
     * <p color=blue size=5>Title:UpListenerImpl</p>
     * <p color=blue size=5>Description:内部类 - 向上按键事件。</p>
     * <p color=blue size=5>Company:</p>
     * <p color=blue size=5>@author Bob Simon</p>
     * <p color=blue size=5>2017-11-24 上午10:24:42</p>
     */
    private class UpListenerImpl extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            bigFishMove(0);
        }

    }

    /**
     * <p color=blue size=5>Title:RightListenerImpl</p>
     * <p color=blue size=5>Description:内部类 - 向右按键事件。</p>
     * <p color=blue size=5>Company:</p>
     * <p color=blue size=5>@author Bob Simon</p>
     * <p color=blue size=5>2017-11-24 上午10:25:00</p>
     */
    private class RightListenerImpl extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            bigFishMove(1);
        }

    }

    /**
     * <p color=blue size=5>Title:DownListenerImpl</p>
     * <p color=blue size=5>Description:内部类 - 向下按键事件。</p>
     * <p color=blue size=5>Company:</p>
     * <p color=blue size=5>@author Bob Simon</p>
     * <p color=blue size=5>2017-11-24 上午10:24:17</p>
     */
    private class DownListenerImpl extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            bigFishMove(2);
        }

    }

    /**
     * <p color=blue size=5>Title:LeftListenerImpl</p>
     * <p color=blue size=5>Description:内部类 - 向左按键事件。</p>
     * <p color=blue size=5>Company:</p>
     * <p color=blue size=5>@author Bob Simon</p>
     * <p color=blue size=5>2017-11-24 上午10:24:05</p>
     */
    private class LeftListenerImpl extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent e) {
            bigFishMove(3);
        }

    }

    /**
     * <p color=blue size=5>Title:TimerListenerImpl</p>
     * <p color=blue size=5>Description:内部类 - 图形重回事件.</p>
     * <p color=blue size=5>Company:</p>
     * <p color=blue size=5>@author Bob Simon</p>
     * <p color=blue size=5>2017-11-24 上午10:23:50</p>
     */
    private class TimerListenerImpl implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            repaint();
        }

    }
}
