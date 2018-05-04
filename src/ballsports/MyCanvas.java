package ballsports;
   
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


   private static final long serialVersionUID = 1L;

   /**
    * 继承父类    鼠标点击改变小球运动方向
    */
   class SixthListener extends MouseAdapter{

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
         //显示各个小球编号
         g.drawString(" "+i,ball.getX(), ball.getY());
         //记录小球编号
         ball.setNumber(i);
         i++;
      }
   }

   /**
    * 假球
    * @param ball
    */
   public void addBall(Ball ball){
      balls.add(ball);
   }

   /**
    * 清空canvas
    */
   public void deleteBall(){
      balls.clear();
   }

   public ArrayList<Ball> returnBalls(){
      return balls;
   }


   public void deleteThisBall(int num){
      balls.remove(num);
   }


}

