package ballSports;
   
   /**
* 球线程
* @Author  Bob Simon
* @Date 2018-01-07 15:04
**/
import java.awt.Color;
import java.awt.Label;

public class BallThread extends Thread {
   private Ball ball;
   private Label label;//若不初始化出现NULLPOINTER错误
   volatile boolean isStop=false;
   volatile boolean stop=false;
   private MyCanvas canvas;
   public BallThread(Color color,Label label,MyCanvas canvas){
      this.label=label;
      this.canvas=canvas;
      ball=new Ball(canvas,color);
      canvas.addBall(ball);
   }

   public void run(){
      while(!isStop){
         if(!stop){
            ball.move();
            label.setText("小球"+ball.getNumber()+"斜率:"+ball.getk());
            canvas.repaint();
            try{
               sleep(6);//时延  3ms
            }
            catch(Exception e){
            };
         }
      }
   }
   public  void setStop(boolean Stop)
   {
      isStop = Stop;
   }
   public  void stop(boolean stop)
   {
      this.stop = stop;
   }
}
