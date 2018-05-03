/*import com.swetake.util.Qrcode;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;*/

/**
* 二维码工具类
* @Author  Bob Simon
* @Date 2017-12-21 18:31
**/
public class QrCodeUtil {


    public static void  getQrCode(String content,String imgPath){
      /*  try{
        Qrcode qrcode =  new Qrcode();
        qrcode.setQrcodeErrorCorrect('M');
        qrcode.setQrcodeEncodeMode('B');;
        qrcode.setQrcodeVersion(15);
        int width = 235,height = 235;
        BufferedImage bi =  new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics2D = bi.createGraphics();
        graphics2D.setBackground(Color.WHITE);
        graphics2D.setColor(Color.BLACK);
        graphics2D.clearRect(0, 0, width, height);
        byte[] codeout = content.getBytes();
        boolean[][] code = qrcode.calQrcode(codeout);
        for(int i = 0;i < code.length;i++ ){
            for(int j = 0;j < code.length;j++ ){
                graphics2D.fillRect(j*3,i*3,3,3);
            }
        }
        graphics2D.dispose();;
        bi.flush();
        ImageIO.write(bi,"png",new File(imgPath));
        System.out.println("二维码生成成功！！");
        }catch(Exception e){
            e.printStackTrace();
        }*/
    }

   public static  void main(String a[]){
       getQrCode("Bob Simon最帅","d://QQ");
   }
}
