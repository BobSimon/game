package planegame;

/**
 * 
 * <p color=blue size=5>Title:Award</p>
 * <p color=blue size=5>Description:奖励接口</p>
 * <p color=blue size=5>Company:</p>
 * <p color=blue size=5>@author Bob Simon</p>
 * <p color=blue size=5>2017-11-23 下午06:41:49</p>
 *
 */
public interface Award {

	//双倍火力
	int DOUBLE_FIRE = 0;

	//1条命
	int LIFE = 1;

	/**
	 * 获得奖励类型(上面的0或1)
	 */
	int getType();
}