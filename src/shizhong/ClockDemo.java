package shizhong;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.image.ImageObserver;
import java.awt.image.ImageProducer;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.imageio.ImageIO;
import javax.swing.JFrame;


/**
 * <p color=blue size=5>Title:ClockDemo</p>
 * <p color=blue size=5>Description:模拟时钟</p>
 * <p color=blue size=5>Company:</p>
 * <p color=blue size=5>@author Bob Simon</p>
 * <p color=blue size=5>2017-11-24 下午09:05:55</p>
 */
public class ClockDemo extends JFrame implements Runnable{

	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	
	Thread clock;

	final int xPoint = 180;

	final int yPoint = 180;

	final int R = 80;

	int xHour = 0, yHour = 0, xSecond = 0, ySecond = 0, xMin = 0, yMin = 0;

	/**
	 * 构造方法
	 */
	public ClockDemo() {

		// 调用父类构造函数
		super("余楚波制作");

		// 设置时钟的显示字体
		setFont(new Font("仿宋体",Font.BOLD,24));

		// 开始进程
		start();

		// 设置窗口尺寸
		setSize(360,360);

		// 窗口可视
		setVisible(true);

		//不可重新调整大小
		setResizable(false);

		// 关闭窗口时退出程序
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * 开始进程
	 */
	public void start() {

		// 如果进程为空值
		if (clock == null)
		{
			// 实例化进程
			clock = new Thread(this);

			// 开始进程
			clock.start();
		}
	}

	/**
	 *  运行进程
	 */
	@Override
	public void run(){
		while (clock != null) {

			// 调用paint方法重绘界面
			repaint();
			try {

				// 线程暂停一秒(1000毫秒)
				Thread.sleep(1000);
			} catch (InterruptedException ex) {
				ex.printStackTrace();
			}
		}
	}

	/**
	 * 停止进程
	 */
	public void stop()
	{
		clock = null;
	}

	/**
	 *  重载组件的paint方法
	 * @param g
	 */
	@Override
	public void paint(Graphics g){

		// 得到Graphics2D对象,下面用g2就相当于用g，两个对象等同于
		Graphics2D g2 = (Graphics2D) g;

		DateFormat dateFormat = DateFormat.getDateInstance(DateFormat.FULL);

		// 实例化日历对象
		Calendar now = new GregorianCalendar();

		// dateFormat.format(now.getTime())
		now.setTime(new Date());

		// 输出信息
		String timeInfo = "";

		// 得到小时数
		int hour = now.get(Calendar.HOUR_OF_DAY);

		// 得到分数
		int minute = now.get(Calendar.MINUTE);

		// 得到秒数
		int second = now.get(Calendar.SECOND);

		// 格式化输出
		if(hour <= 9){
			timeInfo += "0" + hour + ":";
		} else{
			timeInfo += hour + ":";
		}

		if (minute <= 9){
			timeInfo += "0" + minute + ":";
		} else{
			timeInfo += minute + ":";
		}

		if (second <= 9){
			timeInfo += "0" + second;
		} else{
			timeInfo += second;
		}

		// 设置当前颜色为白色,为了让黑点显示出来
		g.setColor(Color.WHITE);

		// 得到窗口尺寸
		Dimension dim = getSize();

		// 填充背景色为白色,相当于线程启动一次填充一次，才不会让上次所执行的操作留下残余
		g2.fillRect(0, 0, dim.width, dim.height);

		// 设置当前时间颜色为CYAN色
		g2.setColor(Color.CYAN);

		// 显示时间字符串
		g2.drawString(timeInfo, 130, 340);
		g.setColor(Color.green);
		g.drawString(dateFormat.format(now.getTime()), 20, 60);
		g.setColor(Color.black);
		g.setFont(new Font("SAN_SERIF", Font.BOLD, 15));

		for (int i = 0, num = 0; i < 360; i += 6) {

			// 获得弧度数
			double alfa = Math.toRadians(i);

			int xPos = xPoint + (int) (R * Math.sin(alfa));

			int yPos = yPoint - (int) (R * Math.cos(alfa));

			if (i % 90 == 0) {
				if (num == 0) {
					num = 12;
					g.setColor(Color.RED);
					g.drawString("" + num, xPos - 5, yPos + 3);
					num = 3;
				} else if (num == 3) {
					g.setColor(Color.BLUE);
					g.drawString("" + num, xPos - 5, yPos + 3);
					num = 6;
				} else if (num == 6) {
					g.setColor(Color.GRAY);
					g.drawString("" + num, xPos - 5, yPos + 3);
					num = 9;
				} else {
					g.setColor(Color.MAGENTA);
					g.drawString("" + num, xPos - 5, yPos + 3);
				}
				
//				 if (num%3==0)
//				 g.setColor(Color.red); // 数字3,6,9,12为红色
//				 else
//				 g.setColor(Color.black); // 其余数字为黑色
//				 g.drawString(""+num,xPos-5,yPos+3); // 写数字
//				 num=(num+1);
				 
			} else {
				g.setColor(Color.GRAY);
				g.drawString(".", xPos, yPos);
			}
		}
		
		g.setColor(Color.black);

		g.fillOval(xPoint - 4,yPoint - 4,8,8);

		// 画秒针
		xSecond = (int)(xPoint + (R - 10)
				* Math.sin(second * (2 * Math.PI / 60)));

		ySecond = (int)(yPoint - (R - 10)
				* Math.cos(second * (2 * Math.PI / 60)));

		g.setColor(Color.red);
		g.drawLine(xPoint,yPoint,xSecond, ySecond);

		// 画分针
		xMin = (int) (xPoint + (R - 35)
				* Math.sin((minute + second / 60) * (2 * Math.PI / 60)));

		yMin = (int) (yPoint - (R - 35)
				* Math.cos((minute + second / 60) * (2 * Math.PI / 60)));
		
		g.setColor(Color.BLUE);
		g.drawLine(xPoint,yPoint, xMin, yMin);

		// 画时针
		xHour = (int) (xPoint + (R - 50)
				* Math.sin((hour + minute / 60 + second / 60 / 60)
						* (2 * Math.PI / 12)));

		yHour = (int) (yPoint - (R - 50)
				* Math.cos((hour + minute / 60 + second / 60 / 60)
						* (2 * Math.PI / 12)));
		g.setColor(Color.BLACK);

		g.drawLine(xPoint, yPoint, xHour, yHour);
	}

	public static void main(String[] args){

		new ClockDemo();

	}

}