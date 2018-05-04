package eatBean.tools;

import java.util.Random;

/**
 * 
 * <p color=blue size=5>Title:RandomUtil</p>
 * <p color=blue size=5>Description:数学类-随机数生成类</p>
 * <p color=blue size=5>Company:</p>
 * <p color=blue size=5>@author Bob Simon</p>
 * <p color=blue size=5>2017-11-24 上午10:31:05</p>
 *
 */
public class RandomUtil {

	/**
	 * 生成a-b的随机数
	 * @param a 整数a
	 * @param b 整数b
	 * @return a-b的随机数
	 */
	public static int randomInt(int a,int b){
		int t, n = 0;

		if(a>b){
			t = a;
			a = b;
			b = t;
		}

		//对b向上取整
		t = (int)(Math.ceil(Math.log10(b)));
		while(true)
		{
			//随机数*10的t次方
			n=(int)(Math.random()*Math.pow(10,t));

			if(n>=a && n<=b){
				break;
			}

		}
		//System.out.println("生成的随机数如下:"+n);
		return n;
	}
	
	/**
	 * 返回0-a的随机数。
	 * @param a 整数a。
	 * @return 返回0-a的随机数。
	 */
	public static int randomInt(int a){
		return new Random().nextInt(a);
	}


}
