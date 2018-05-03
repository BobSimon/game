package ballSports;

import java.awt.Color;
import java.awt.Graphics;

/**
 * 球实体类
 * @Author  Bob Simon
 * @Date 2018-01-07 15:03
 **/
public class Ball {
   private int x=0;
   private int y=0;
   double k=0;
   double b=0;
   private double lastX=0;
   private double lastY=0;
   int dy=1;
   int dx=1;
   int r=40;

   private Color color;
   private MyCanvas canvas;
   private static int POINTX=250;
   private static int POINTY=100;//顶点
   private int num=0;//给小球计数

   public Ball(MyCanvas canvas,Color color){
      this.canvas=canvas;
      this.color=color;
      setFirstLocation();
   }
   public void draw(Graphics g){
      g.setColor(color);//颜色
      g.drawLine(POINTX,POINTY,x+r/2,y+r/2);
      g.fillOval(x,y,r,r);//填充椭圆区域
   }

   private void setFirstLocation(){//初始化位置和斜率

      x=(int)((Math.random()*canvas.getWidth()));
      y=(int)((Math.random()*canvas.getHeight()));
      k=(y-POINTY)/((x-POINTX)+0.1);
      b=POINTY-k*POINTX;
      if(y>POINTY)//向汇聚点移动
         dy=-1;
      if(x>POINTX)
         dx=-1;
   }

   public void move(){
      lastX=x;
      lastY=y;
      if(lastY+dy<0||lastY+dy>canvas.getHeight()-r)
      {
         new MusicThread().start();
         k=-k;
         b=lastY-k*lastX;
         if(lastX+dx<0)
            dx=1;
         else if(lastX+dx>canvas.getWidth()-r)
            dx=-1;
         if(lastY+dy<0)
            dy=1;
         else if(lastY+dy>canvas.getHeight()-r)
            dy=-1;
      }

      else if(lastX+dx<0||lastX+dx>canvas.getWidth()-r)
      {
         new MusicThread().start();
         k=-k;
         b=lastY-k*lastX;
         if(lastY+dy<0)
            dy=1;
         else if(lastY+dy>canvas.getHeight()-r)
            dy=-1;
         if(lastX+dx<0)
            dx=1;
         else if(lastX+dx>canvas.getWidth()-r)
            dx=-1;
      }
      if(Math.abs(k)<1){
         x=x+dx;
         y=(int)(k*x+b);
      }
      else
      {
         y=y+dy;
         x=(int)((y-b)/k);
      }
	   /*for (int i = 0; i < canvas.returnBalls().size(); i++)   //判断小球间是否发生碰撞
       {
           Ball ball = canvas.returnBalls().get(i);
           if (this.equals(ball))  //自己和自己不碰撞
               continue;
           if (((ball.x - x) * (ball.x - x) + (ball.y - y) * (ball.y - y) <= r*r)&&ball.getk()!=this.getk())
        	   //当两球间的距离小于直径且斜率不同时，可认为两小球发生了碰撞
           {
               double degree = Math.atan((y - ball.y) / (x - ball.x));
               //获取自己与发生碰撞的小球之间所形成的夹角，因为夹角只能在-pi/2-pi/2之间，所以还需判断两球的x坐标之间的关系

           }
       } */
   }

   public void changePoint(int pointx,int pointy){//改变顶点的位置
      dy=1;
      dx=1;
      POINTX=pointx;
      POINTY=pointy;
      k=(y-POINTY)/((x-POINTX)+0.1);
      b=POINTY-k*POINTX;
      if(y>POINTY)
         dy=-1;
      if(x>POINTX)
         dx=-1;
   }

   public Double getk(){//显示斜率
      return k;
   }

   public int getX(){//位置显示每一个小球的编号
      return x+20;
   }

   public int getY(){
      return y+20;
   }

   public void setNumber(int num){//记录小球编号
      this.num=num;
   }

   public int getNumber(){//在BallThread里面label获取小球编号
      return num;
   }
}

