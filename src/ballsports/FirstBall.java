package ballsports;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Checkbox;
import java.awt.CheckboxGroup;
import java.awt.Color;
import java.awt.Dialog;
import java.awt.Frame;
import java.awt.Label;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.MenuShortcut;
import java.awt.Panel;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;

/**
 * 第一个球
 *
 * @Author Bob Simon
 * @Date 2018-01-07 15:06
 **/
public class FirstBall {
    Frame frame = new Frame();
    Ball ball;
    MyCanvas canvas = new MyCanvas();

    /**
     * 右侧panel显示按钮信息
     */
    Button btnAdd = new Button("add");
    Button btnClear = new Button("clear");

    /**
     * 随机删除小球按钮
     */
    Button btnStopAndContinue = new Button("cut one");
    Button btnstop = new Button("stop");
    Dialog cutone = new Dialog(frame, "请输入要删除的小球", true);


    /**
     * 输入小球编号
     */
    TextField choice = new TextField(20);
    Button cut = new Button("确定");
    Button no = new Button("取消");
    Dialog error = new Dialog(frame, "ERROR", true);
    Label errors = new Label("错误的小球编号!");
    Button errorExit = new Button("确定");
    Panel panel = new Panel();

    /**
     * 关闭窗口
     */
    Dialog d = new Dialog(frame, "ExitProcess", true);
    Button btnYes = new Button("YES");
    Button btnNo = new Button("NO");
    Button btnCancel = new Button("Cancel");
    Label labelExit = new Label("Exit ?");

    /**
     * 左侧状态栏
     */
    Panel panelLabel = new Panel();
    Label ballk = new Label("小球情况");
    Label[] labels = new Label[20];
    static int BALLNUM = 0;
    static int labelNum = 0;

    /**
     * 菜单
     */
    MenuBar mb = new MenuBar();
    Menu file = new Menu("文件");
    Menu help = new Menu("帮助");

    /**
     * 文件下的选项
     */
    MenuItem setItem = new MenuItem("设置", new MenuShortcut(KeyEvent.VK_B));
    CheckboxGroup cbg = new CheckboxGroup();
    Checkbox check21 = new Checkbox("10", cbg, true);
    Checkbox check22 = new Checkbox("15", cbg, false);
    Checkbox check23 = new Checkbox("20", cbg, false);
    Dialog setDl = new Dialog(frame, "设置小球上限", true);
    Button exit4 = new Button("exit");

    /**
     * 指定使用“CTRL+X"快捷键退出
     */
    MenuItem exitItem = new MenuItem("退出", new MenuShortcut(KeyEvent.VK_X));

    /**
     * 帮助下的选项
     */
    Menu brief = new Menu("简介");
    MenuItem group = new MenuItem("小组介绍", new MenuShortcut(KeyEvent.VK_A));
    MenuItem function = new MenuItem("功能介绍", new MenuShortcut(KeyEvent.VK_C));
    Dialog groupIntroduction = new Dialog(frame, "小组介绍", true);
    Dialog functionIntroduction = new Dialog(frame, "功能介绍", true);

    Label partnerText0 = new Label("组名:第九小组");
    Label partnerText1 = new Label("组长:李思雨");
    Label partnerText2 = new Label("组员:胡   杰");
    Label partnerText3 = new Label("组员:曹寅清");
    Label guideText0 = new Label("add:增加一个小球");
    Label guideText1 = new Label("clear:清除画布");
    Label guideText2 = new Label("cutone:删除任意编号小球");
    Button exit2 = new Button("exit");
    Button exit3 = new Button("exit");

    /**
     * 设置小球上限个数并用对话框显示
     */
    static int max = 10;
    Dialog dballmax = new Dialog(frame, "Warning!", true);

    /**
     * 通用
     */
    Button btnmax = new Button("exit");
    Label labelmax = new Label("小球数已经达到上限");

    ArrayList<BallThread> thread = new ArrayList<BallThread>();


    /**
     * 任意删除一个小球
     * 监听器内部类
     */
    class cutOneListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            if (btnStopAndContinue.getLabel().equals("cut one")) {
                btnStopAndContinue.setLabel("cutting");

                //Add按钮不可用
                btnAdd.setEnabled(false);
                cutone.setVisible(true);

            } else {

                btnStopAndContinue.setLabel("cut one");
                //Add按钮可用
                btnAdd.setEnabled(true);
            }
        }
    }

    /**
     * 暂停
     */
    class stopListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            if (btnstop.getLabel().equals("stop")) {
                for (BallThread thread0 : thread) {
                    thread0.stop(true);
                }
                btnstop.setLabel("continue");

                //Add按钮不可用
                btnAdd.setEnabled(false);
            } else {
                for (BallThread thread0 : thread) {
                    thread0.stop(false);
                }
                btnstop.setLabel("stop");

                //Add按钮可用
                btnAdd.setEnabled(true);
            }
        }
    }

    /**
     * 输入小球编号任意删除一个小球
     */
    class cutListener implements ActionListener {
        int aBallNumber = 0;
        boolean nope = false;

        @Override
        public void actionPerformed(ActionEvent e) {
            cutone.setVisible(false);
            try {
                aBallNumber = Integer.valueOf(choice.getText());
            } catch (Exception e1) {
                nope = true;
                error.setVisible(true);
            }
            if (!nope) {
                if (aBallNumber > thread.size() - 1 || aBallNumber < 0) {
                    error.setVisible(true);
                } else {
                    thread.get(aBallNumber).setStop(true);
                    thread.remove(aBallNumber);
                    labels[labelNum] = new Label(" ");
                    labels[aBallNumber].setText(" ");
                    labelNum--;
                    BALLNUM--;
                    canvas.deleteThisBall(aBallNumber);
                }
            }
        }
    }

    /**
     *add
     */
    class addListener implements ActionListener {
        Color[] colorArray = {Color.blue, Color.CYAN, Color.GREEN,
                Color.magenta, Color.red, Color.yellow, Color.pink, Color.orange, Color.blue};

        @Override
        public void actionPerformed(ActionEvent e) {
            if (canvas.returnBalls().size() >= max) {
                dballmax.setVisible(true);
            } else {
                int i = (int) (Math.random() * 9);

                labels[labelNum].setBounds(0,50 + labelNum * 30,100,20);

                panelLabel.add(labels[labelNum]);

                thread.add(new BallThread(colorArray[i], labels[BALLNUM], canvas));

                thread.get(BALLNUM).start();

                BALLNUM++;

                labelNum++;
            }
        }
    }

    /**
     * clear
     */
    class clearListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            for (BallThread thread0 : thread) {
                thread0.setStop(true);
            }

            //只是canvas不画小球而已我们看不到，实际小球的进程仍在运行。
            canvas.deleteBall();
            canvas.repaint();
            for (int i = 0; i < labelNum; i++) {
                labels[i].setText("  ");
            }
            labelNum = 0;
            BALLNUM = 0;
            thread.removeAll(thread);
        }
    }

    /**
     * 对话框提示是否关闭窗口
     */
    class closeWindowListener extends WindowAdapter {

        @Override
        public void windowClosing(WindowEvent e) {
            d.setVisible(true);
        }
    }

    /**
     * 对话框里的按钮yes关闭窗口
     */
    class closeListener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            // TODO Auto-generated method stub
            System.exit(0);
        }
    }

    /**
     * 对话框里的按钮cancel取消关闭窗口/删除任意一个球对话框的关闭
     */
    class cancelOrCloseListener implements ActionListener {
        private Dialog d;

        public cancelOrCloseListener(Dialog d) {
            this.d = d;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            d.setVisible(false);
        }
    }

    /**
     * 接口  传值实现通用的监听器：对话框的关闭：小球已达上限对话框/小组成员情况/功能介绍
     */
    class exitDialogListener implements ActionListener {
        private Dialog d;

        public exitDialogListener(Dialog d) {
            this.d = d;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            d.setVisible(false);
        }
    }

    /**
     * 设置小球上限为20/10/15
     */
    class setMaxListener extends FocusAdapter {
        private int ballnum;

        public setMaxListener(int ballnum) {
            this.ballnum = ballnum;
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (max < ballnum) {
                for (int i = max; i < ballnum; i++) {
                    labels[i] = new Label("  ");
                }
            }
            max = ballnum;
        }
    }

    /**
     * 通用的监听器：小组成员情况介绍+功能介绍
     */
    class displayDialogListener implements ActionListener {
        private Dialog d;

        public displayDialogListener(Dialog d) {
            this.d = d;
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            d.setVisible(true);
        }
    }

    public void init() {
        canvas.setBounds(100, 0, 800, 800);

        //鼠标点击画布聚点改变
        canvas.addMouseListener(canvas.f6);
        canvas.setBackground(Color.white);

        //左侧状态栏
        panelLabel.setBounds(5, 0, 100, 800);
        panelLabel.setBackground(Color.red);
        panelLabel.setLayout(null);
        ballk.setBounds(0, 0, 100, 50);
        panelLabel.add(ballk);
        int i = 0;
        for (i = 0; i < max; i++) {
            labels[i] = new Label(" ");
        }

        //右侧状态栏
        panel.setBounds(800, 0, 100, 800);
        panel.setBackground(Color.red);
        addListener f1 = new addListener();
        btnAdd.addActionListener(f1);
        btnAdd.setBounds(5, 60, 90, 50);
        btnAdd.setBackground(Color.white);
        panel.add(btnAdd);
        clearListener f2 = new clearListener();
        btnClear.addActionListener(f2);
        btnClear.setBounds(5, 130, 90, 50);
        btnClear.setBackground(Color.white);
        panel.add(btnClear);
        btnStopAndContinue.setBounds(5, 200, 90, 50);
        cutOneListener f7 = new cutOneListener();
        btnStopAndContinue.addActionListener(f7);
        btnStopAndContinue.setBackground(Color.white);
        panel.add(btnStopAndContinue);
        stopListener fl = new stopListener();
        btnstop.addActionListener(fl);
        btnstop.setBounds(5, 270, 90, 50);
        btnstop.setBackground(Color.white);
        panel.add(btnstop);
        panel.setLayout(null);

        /**
         *  关闭窗口显示对话框
         * 按右上角的x显示对话框
         */
        closeWindowListener f3 = new closeWindowListener();

        frame.addWindowListener(f3);

        closeListener f4 = new closeListener();

        cancelOrCloseListener f5 = new cancelOrCloseListener(d);
        labelExit.setBounds(20, 50, 100, 20);
        btnYes.setBounds(100, 100, 50, 20);
        btnYes.addActionListener(f4);
        btnCancel.setBounds(160, 100, 50, 20);
        btnCancel.addActionListener(f5);
        d.setBounds(400, 400, 250, 130);
        d.add(labelExit);
        d.add(btnYes);
        d.add(btnCancel);
        d.setLayout(null);

        //-------------菜单
        //文件
        displayDialogListener f110 = new displayDialogListener(d);
        exitItem.addActionListener(f110);
        //----设置小球上限
        check21.setBounds(0, 50, 200, 25);
        check22.setBounds(0, 75, 200, 25);
        check23.setBounds(0, 100, 200, 25);
        exitDialogListener f116 = new exitDialogListener(setDl);
        exit4.addActionListener(f116);
        exit4.setBounds(0, 125, 200, 25);
        setMaxListener f15 = new setMaxListener(10);
        check21.addFocusListener(f15);
        setMaxListener f11 = new setMaxListener(15);
        check22.addFocusListener(f11);
        setMaxListener f12 = new setMaxListener(20);
        check23.addFocusListener(f12);
        setDl.add(check21);
        setDl.add(check22);
        setDl.add(check23);
        setDl.add(exit4);
        setDl.setLayout(null);
        setDl.setBounds(500, 500, 200, 200);
        displayDialogListener f9 = new displayDialogListener(setDl);
        setItem.addActionListener(f9);
        file.add(setItem);
        //------------添加菜单分割线
        file.addSeparator();
        file.add(exitItem);
        //-----------小组介绍
        partnerText0.setBounds(110, 50, 100, 40);
        partnerText1.setBounds(110, 100, 100, 40);
        partnerText2.setBounds(110, 140, 100, 40);
        partnerText3.setBounds(110, 180, 100, 40);
        groupIntroduction.add(partnerText0);
        groupIntroduction.add(partnerText1);
        groupIntroduction.add(partnerText2);
        groupIntroduction.add(partnerText3);
        exitDialogListener f114 = new exitDialogListener(groupIntroduction);
        exit3.addActionListener(f114);
        exit3.setBounds(95, 220, 100, 40);
        groupIntroduction.add(exit3);
        groupIntroduction.setBounds(400, 400,300,300);
        groupIntroduction.setLayout(null);
        displayDialogListener f111 = new displayDialogListener(groupIntroduction);
        group.addActionListener(f111);

        //功能介绍
        exitDialogListener f115 = new exitDialogListener(functionIntroduction);
        exit2.addActionListener(f115);
        exit2.setBounds(105, 240, 100, 50);
        guideText0.setBounds(105, 60, 100, 50);
        guideText1.setBounds(105, 120, 100, 50);
        guideText2.setBounds(105, 180, 150, 50);
        functionIntroduction.add(guideText0);
        functionIntroduction.add(guideText1);
        functionIntroduction.add(guideText2);
        functionIntroduction.add(exit2);
        functionIntroduction.setBounds(400, 400, 300, 300);
        functionIntroduction.setLayout(null);
        displayDialogListener f112 = new displayDialogListener(functionIntroduction);
        function.addActionListener(f112);

        brief.add(group);
        //添加菜单分割线
        brief.add(new MenuItem("-"));
        brief.add(function);
        help.add(brief);
        mb.add(file);
        mb.add(help);

        frame.setMenuBar(mb);

        //小球数设置上限
        dballmax.setBounds(500, 500, 300, 130);
        exitDialogListener f8 = new exitDialogListener(dballmax);
        btnmax.addActionListener(f8);
        dballmax.add(labelmax, BorderLayout.CENTER);
        dballmax.add(btnmax, BorderLayout.SOUTH);

        cut.setBounds(45, 150, 100, 30);
        cutListener aCut = new cutListener();
        cut.addActionListener(aCut);
        no.setBounds(165, 150, 100, 30);
        cancelOrCloseListener noListener = new cancelOrCloseListener(cutone);
        no.addActionListener(noListener);

        errors.setBounds(95, 60, 100, 30);
        errorExit.setBounds(95, 120, 100, 30);
        cancelOrCloseListener errorListener = new cancelOrCloseListener(error);
        errorExit.addActionListener(errorListener);
        error.add(errors);
        error.add(errorExit);
        error.setLayout(null);
        error.setBounds(400, 400, 300, 200);


        choice.setBounds(45, 90, 100, 30);
        cutone.add(choice);
        cutone.add(cut);
        cutone.add(no);
        cutone.setLayout(null);
        cutone.setBounds(400, 400, 300, 200);

        frame.add(panelLabel, BorderLayout.WEST);
        frame.add(panel, BorderLayout.EAST);
        frame.add(canvas, BorderLayout.CENTER);
        frame.setBounds(80, 80, 900, 800);
        frame.setVisible(true);
    }

    public static void main(String[] args) {
        new FirstBall().init();
    }

}

