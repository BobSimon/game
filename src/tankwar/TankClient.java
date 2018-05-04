package tankwar;

import java.util.ArrayList;
import java.awt.*;
import java.awt.event.*;
import java.util.List;
import javax.swing.JOptionPane;

/**
 * <p color=blue size=5>
 * Title:TankClient
 * </p>
 * <p color=blue size=5>
 * Description:启动坦克大战游戏入口
 * </p>
 * <p color=blue size=5>
 * Company:
 * </p>
 * <p color=blue size=5>
 *
 * @author Bob Simon
 * </p>
 * <p color=blue size=5>
 * 2017-11-24 上午11:11:02
 * </p>
 */
public class TankClient extends Frame implements ActionListener {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // 静态全局窗口大小
    public static final int Fram_width = 800;

    public static final int Fram_length = 600;

    public static boolean printable = true;

    MenuBar jmb = null;

    Menu jm1 = null, jm2 = null, jm3 = null, jm4 = null;

    MenuItem jmi1 = null, jmi2 = null, jmi3 = null, jmi4 = null, jmi5 = null,
            jmi6 = null, jmi7 = null, jmi8 = null, jmi9 = null;

    Image screenImage = null;

    /**
     * 实例化坦克
     */
    Tank homeTank = new Tank(300, 560, true, Direction.STOP, this);

    /**
     *实例化生命
     */
    GetBlood blood = new GetBlood();

    /**
     * 实例化home
     */
    Home home = new Home(373, 545, this);

    List<River> theRiver = new ArrayList<River>();

    List<Tank> tanks = new ArrayList<Tank>();

    List<BombTank> bombTanks = new ArrayList<BombTank>();

    List<Bullets> bullets = new ArrayList<Bullets>();

    List<Tree> trees = new ArrayList<Tree>();

    /**
     * 实例化对象容器
     */
    List<CommonWall> homeWall = new ArrayList<CommonWall>();

    List<CommonWall> otherWall = new ArrayList<CommonWall>();

    List<MetalWall> metalWall = new ArrayList<MetalWall>();

    @Override
    public void update(Graphics g) {

        screenImage = this.createImage(Fram_width, Fram_length);

        Graphics gps = screenImage.getGraphics();
        Color c = gps.getColor();
        gps.setColor(Color.GRAY);
        gps.fillRect(0, 0, Fram_width, Fram_length);
        gps.setColor(c);
        framPaint(gps);
        g.drawImage(screenImage, 0, 0, null);
    }

    public void framPaint(Graphics g) {

        Color c = g.getColor();

        // 设置字体显示属性
        g.setColor(Color.green);
        Font f1 = g.getFont();
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.drawString("区域内还有敌方坦克: ", 200, 70);
        g.setFont(new Font("TimesRoman", Font.ITALIC, 30));
        g.drawString("" + tanks.size(), 400, 70);
        g.setFont(new Font("TimesRoman", Font.BOLD, 20));
        g.drawString("剩余生命值: ", 500, 70);
        g.setFont(new Font("TimesRoman", Font.ITALIC, 30));
        g.drawString("" + homeTank.getLife(), 650, 70);
        g.setFont(f1);

        if (tanks.size() == 0 && home.isLive() && homeTank.isLive()) {
            Font f = g.getFont();

            // 判断是否赢得比赛
            g.setFont(new Font("TimesRoman", Font.BOLD, 60));
            this.otherWall.clear();
            g.drawString("你赢了！ ", 310, 300);
            g.setFont(f);
        }

        if (homeTank.isLive() == false) {
            Font f = g.getFont();
            g.setFont(new Font("TimesRoman", Font.BOLD, 40));
            tanks.clear();
            bullets.clear();
            g.setFont(f);
        }
        g.setColor(c);

        // 画出河流
        for (int i = 0; i < theRiver.size(); i++) {
            River r = theRiver.get(i);
            r.draw(g);
        }

        for (int i = 0; i < theRiver.size(); i++) {
            River r = theRiver.get(i);
            homeTank.collideRiver(r);

            r.draw(g);
        }

        // 画出home
        home.draw(g);

        // 画出自己家的坦克
        homeTank.draw(g);

        // 充满血--生命值
        homeTank.eat(blood);

        // 对每一个子弹
        for (int i = 0; i < bullets.size(); i++) {
            Bullets m = bullets.get(i);

            // 每一个子弹打到坦克上
            m.hitTanks(tanks);

            // 每一个子弹打到自己家的坦克上时
            m.hitTank(homeTank);

            // 每一个子弹打到家里是
            m.hitHome();

            // 每一个子弹打到金属墙上
            for (int j = 0; j < metalWall.size(); j++) {
                MetalWall mw = metalWall.get(j);
                m.hitWall(mw);

            }

            // 每一个子弹打到其他墙上
            for (int j = 0; j < otherWall.size(); j++) {
                CommonWall w = otherWall.get(j);
                m.hitWall(w);

            }

            // 每一个子弹打到家的墙上
            for (int j = 0; j < homeWall.size(); j++) {
                CommonWall cw = homeWall.get(j);
                m.hitWall(cw);

            }

            // 画出效果图
            m.draw(g);
        }

        for (int i = 0; i < tanks.size(); i++) {

            /**
             * 获得键值对的键
             */
            Tank t = tanks.get(i);

            for (int j = 0; j < homeWall.size(); j++) {
                CommonWall cw = homeWall.get(j);

                // 每一个坦克撞到家里的墙时
                t.collideWithWall(cw);
                cw.draw(g);
            }

            // 每一个坦克撞到家以外的墙
            for (int j = 0; j < otherWall.size(); j++) {
                CommonWall cw = otherWall.get(j);
                t.collideWithWall(cw);
                cw.draw(g);
            }

            // 每一个坦克撞到金属墙
            for (int j = 0; j < metalWall.size(); j++) {
                MetalWall mw = metalWall.get(j);
                t.collideWithWall(mw);
                mw.draw(g);
            }
            for (int j = 0; j < theRiver.size(); j++) {

                /**
                 * 每一个坦克撞到河流时
                 */
                River r = theRiver.get(j);
                t.collideRiver(r);
                r.draw(g);
                // t.draw(g);
            }

            /*** 撞到自己的人*/
            t.collideWithTanks(tanks);
            t.collideHome(home);

            t.draw(g);
        }

        blood.draw(g);

        // 画出trees
        for (int i = 0; i < trees.size(); i++) {
            Tree tr = trees.get(i);
            tr.draw(g);
        }

        // 画出爆炸效果
        for (int i = 0; i < bombTanks.size(); i++) {
            BombTank bt = bombTanks.get(i);
            bt.draw(g);
        }

        // 画出otherWall
        for (int i = 0; i < otherWall.size(); i++) {
            CommonWall cw = otherWall.get(i);
            cw.draw(g);
        }

        // 画出metalWall
        for (int i = 0; i < metalWall.size(); i++) {
            MetalWall mw = metalWall.get(i);
            mw.draw(g);
        }

        homeTank.collideWithTanks(tanks);
        homeTank.collideHome(home);

        // 撞到金属墙
        for (int i = 0; i < metalWall.size(); i++) {
            MetalWall w = metalWall.get(i);
            homeTank.collideWithWall(w);
            w.draw(g);
        }

        for (int i = 0; i < otherWall.size(); i++) {
            CommonWall cw = otherWall.get(i);
            homeTank.collideWithWall(cw);
            cw.draw(g);
        }

        // 家里的坦克撞到自己家
        for (int i = 0; i < homeWall.size(); i++) {
            CommonWall w = homeWall.get(i);
            homeTank.collideWithWall(w);
            w.draw(g);
        }

    }

    public TankClient() {
        // printable = false;
        // 创建菜单及菜单选项
        jmb = new MenuBar();
        jm1 = new Menu("Game");
        jm2 = new Menu("stop/continue");
        jm3 = new Menu("help");
        jm4 = new Menu("game level");

        // 设置菜单显示的字体
        jm1.setFont(new Font("TimesRoman", Font.BOLD, 15));

        // 设置菜单显示的字体
        jm2.setFont(new Font("TimesRoman", Font.BOLD, 15));

        // 设置菜单显示的字体
        jm3.setFont(new Font("TimesRoman", Font.BOLD, 15));

        // 设置菜单显示的字体
        jm4.setFont(new Font("TimesRoman", Font.BOLD, 15));

        jmi1 = new MenuItem("start new game");
        jmi2 = new MenuItem("exit");
        jmi3 = new MenuItem("stop");
        jmi4 = new MenuItem("contimue");
        jmi5 = new MenuItem("game statement");
        jmi6 = new MenuItem("level1");
        jmi7 = new MenuItem("level2");
        jmi8 = new MenuItem("level3");
        jmi9 = new MenuItem("level4");
        jmi1.setFont(new Font("TimesRoman", Font.BOLD, 15));
        jmi2.setFont(new Font("TimesRoman", Font.BOLD, 15));
        jmi3.setFont(new Font("TimesRoman", Font.BOLD, 15));
        jmi4.setFont(new Font("TimesRoman", Font.BOLD, 15));
        jmi5.setFont(new Font("TimesRoman", Font.BOLD, 15));

        jm1.add(jmi1);
        jm1.add(jmi2);
        jm2.add(jmi3);
        jm2.add(jmi4);
        jm3.add(jmi5);
        jm4.add(jmi6);
        jm4.add(jmi7);
        jm4.add(jmi8);
        jm4.add(jmi9);

        jmb.add(jm1);
        jmb.add(jm2);

        jmb.add(jm4);
        jmb.add(jm3);

        jmi1.addActionListener(this);
        jmi1.setActionCommand("NewGame");
        jmi2.addActionListener(this);
        jmi2.setActionCommand("Exit");
        jmi3.addActionListener(this);
        jmi3.setActionCommand("Stop");
        jmi4.addActionListener(this);
        jmi4.setActionCommand("Continue");
        jmi5.addActionListener(this);
        jmi5.setActionCommand("help");
        jmi6.addActionListener(this);
        jmi6.setActionCommand("level1");
        jmi7.addActionListener(this);
        jmi7.setActionCommand("level2");
        jmi8.addActionListener(this);
        jmi8.setActionCommand("level3");
        jmi9.addActionListener(this);
        jmi9.setActionCommand("level4");

        // 菜单Bar放到JFrame上
        this.setMenuBar(jmb);
        this.setVisible(true);

        // 家的格局
        for (int i = 0; i < 10; i++) {
            if (i < 4){
                homeWall.add(new CommonWall(350, 580 - 21 * i, this));
            } else if (i < 7){
                homeWall.add(new CommonWall(372 + 22 * (i - 4), 517, this));
            } else{
                homeWall.add(new CommonWall(416, 538 + (i - 7) * 21, this));
            }

        }

        for (int i = 0; i < 32; i++) {
            if (i < 16) {

                // 普通墙布局
                otherWall.add(new CommonWall(220 + 20 * i, 300, this));
                otherWall.add(new CommonWall(500 + 20 * i, 180, this));
                otherWall.add(new CommonWall(200, 400 + 20 * i, this));
                otherWall.add(new CommonWall(500, 400 + 20 * i, this));
            } else if (i < 32) {
                otherWall.add(new CommonWall(220 + 20 * (i - 16), 320, this));
                otherWall.add(new CommonWall(500 + 20 * (i - 16), 220, this));
                otherWall.add(new CommonWall(220, 400 + 20 * (i - 16), this));
                otherWall.add(new CommonWall(520, 400 + 20 * (i - 16), this));
            }
        }

        // 金属墙布局
        for (int i = 0; i < 20; i++) {
            if (i < 10) {
                metalWall.add(new MetalWall(140 + 30 * i, 150, this));
                metalWall.add(new MetalWall(600, 400 + 20 * (i), this));
            } else if (i < 20){
                metalWall.add(new MetalWall(140 + 30 * (i - 10), 180, this));
            } else{
                metalWall.add(new MetalWall(500 + 30 * (i - 10), 160, this));
            }

        }

        // 树的布局
        for (int i = 0; i < 4; i++) {
            if (i < 4) {
                trees.add(new Tree(0 + 30 * i, 360, this));
                trees.add(new Tree(220 + 30 * i, 360, this));
                trees.add(new Tree(440 + 30 * i, 360, this));
                trees.add(new Tree(660 + 30 * i, 360, this));
            }

        }

        theRiver.add(new River(85, 100, this));

        // 初始化20辆坦克
        for (int i = 0; i < 20; i++) {

            // 设置坦克出现的位置
            if (i < 9){
                tanks.add(new Tank(150 + 70 * i,40,false,Direction.D,this));
            }else if (i < 15){
                tanks.add(new Tank(700,140 + 50 * (i - 6),false, Direction.D,this));
            } else{
                tanks.add(new Tank(10, 50 * (i - 12), false, Direction.D,this));
            }

        }

        // 设置界面大小
        this.setSize(Fram_width, Fram_length);

        // 设置界面出现的位置
        this.setLocation(280, 50);

        this.setTitle("坦克大战——(重新开始：R键  开火：F键)                 作者：余楚波           英文名：Bob Simon");

        /**
         *  窗口监听关闭
         */
        this.addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.setResizable(false);
        this.setBackground(Color.GREEN);
        this.setVisible(true);

        // 键盘监听
        this.addKeyListener(new KeyMonitor());

        // 线程启动
        new Thread(new PaintThread()).start();
    }

    public static void main(String[] args) {

        // 实例化
        new TankClient();
    }

    private class PaintThread implements Runnable {

        @Override
        public void run() {
            // TODO Auto-generated method stub
            while (printable) {
                repaint();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class KeyMonitor extends KeyAdapter {

        /**
         * 监听键盘释放
         * @param e
         */
        @Override
        public void keyReleased(KeyEvent e) {
            homeTank.keyReleased(e);
        }

        /**
         * 监听键盘按下
         * @param e
         */
        @Override
        public void keyPressed(KeyEvent e) {
            homeTank.keyPressed(e);
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getActionCommand().equals("NewGame")) {
            printable = false;
            Object[] options = {"确定", "取消"};
            int response = JOptionPane.showOptionDialog(this, "您确认要开始新游戏！", "",
                    JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (response == 0) {

                printable = true;
                this.dispose();
                new TankClient();
            } else {
                printable = true;

                // 线程启动
                new Thread(new PaintThread()).start();
            }

        } else if (e.getActionCommand().endsWith("Stop")) {
            printable = false;
            // try {
            // Thread.sleep(10000);
            //
            // } catch (InterruptedException e1) {
            // // TODO Auto-generated catch block
            // e1.printStackTrace();
            // }
        } else if (e.getActionCommand().equals("Continue")) {

            if (!printable) {
                printable = true;

                // 线程启动
                new Thread(new PaintThread()).start();
            }
            // System.out.println("继续");
        } else if (e.getActionCommand().equals("Exit")) {
            printable = false;
            Object[] options = {"确定", "取消"};
            int response = JOptionPane.showOptionDialog(this, "您确认要退出吗", "",
                    JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                    options, options[0]);
            if (response == 0) {
                System.out.println("退出");
                System.exit(0);
            } else {
                printable = true;

                // 线程启动
                new Thread(new PaintThread()).start();
            }

        } else if (e.getActionCommand().equals("help")) {
            printable = false;
            JOptionPane.showMessageDialog(null, "用→ ← ↑ ↓控制方向，F键盘发射，R重新开始！",
                    "提示！", JOptionPane.INFORMATION_MESSAGE);
            this.setVisible(true);
            printable = true;
            // 线程启动
            new Thread(new PaintThread()).start();
        } else if (e.getActionCommand().equals("level1")) {
            Tank.count = 12;
            Tank.speedX = 6;
            Tank.speedY = 6;
            Bullets.speedX = 10;
            Bullets.speedY = 10;
            this.dispose();
            new TankClient();
        } else if (e.getActionCommand().equals("level2")) {
            Tank.count = 12;
            Tank.speedX = 10;
            Tank.speedY = 10;
            Bullets.speedX = 12;
            Bullets.speedY = 12;
            this.dispose();
            new TankClient();

        } else if (e.getActionCommand().equals("level3")) {
            Tank.count = 20;
            Tank.speedX = 14;
            Tank.speedY = 14;
            Bullets.speedX = 16;
            Bullets.speedY = 16;
            this.dispose();
            new TankClient();
        } else if (e.getActionCommand().equals("level4")) {
            Tank.count = 20;
            Tank.speedX = 16;
            Tank.speedY = 16;
            Bullets.speedX = 18;
            Bullets.speedY = 18;
            this.dispose();
            new TankClient();
        }
    }
}
