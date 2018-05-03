package ballSports;
   
   /**
* 我的画布
* @Author  Bob Simon
* @Date 2018-01-07 15:05
**/
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

public class MyCanvas extends Canvas{
   /**
    *
    */
   private static final long serialVersionUID = 1L;

   class SixthListener extends MouseAdapter{//继承父类    鼠标点击改变小球运动方向
      @Override
      public void mousePressed(MouseEvent e){
         for(Ball ball:balls){
            ball.changePoint(e.getX(),e.getY());
         }
         repaint();
      }
   }
   SixthListener f6=new SixthListener();
   ArrayList<Ball> balls=new ArrayList<Ball>();
   Ball ball;

   @Override
   public void paint(Graphics g){
      int i=0;
      for(Ball ball:balls){
         ball.draw(g);
         g.setColor(Color.black);
         g.drawString(" "+i,ball.getX(), ball.getY()); //显示各个小球编号
         ball.setNumber(i);//记录小球编号
         i++;
      }
   }
   public void addBall(Ball ball){//加球
      balls.add(ball);
   }
   public void deleteBall(){//清空canvas
      balls.clear();
   }

   public ArrayList<Ball> returnBalls(){
      return balls;
   }

   public void deleteThisBall(int num){
      balls.remove(num);
   }
}

