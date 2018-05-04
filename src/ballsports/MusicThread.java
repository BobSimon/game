package ballsports;

import java.io.FileInputStream;

import sun.audio.AudioPlayer;
import sun.audio.AudioStream;

/**
 * 音乐线程
 * @Author  Bob Simon
 * @Date 2018-01-07 15:06
 **/
@SuppressWarnings("restriction")
public class MusicThread extends Thread {

   @Override
   public void run(){
      try{

         FileInputStream fileau=new FileInputStream("src/bom.wav");

         AudioStream as=new AudioStream(fileau);

         AudioPlayer.player.start(as);

      }catch(Exception e){
         e.printStackTrace();
      }

   }

}

