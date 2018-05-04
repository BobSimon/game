package eatBean.tools;

import eatBean.fish.BigMouthFish;
import eatBean.fish.FishBean;

/**
 * 检测大嘴鱼和小鱼碰撞类。<br>
 * 此类练习Math的静态函数的使用。
 *
 * @version 1.0
 * @since JDK1.6(建议)
 * @author Bob Simon
 */
public class FishUtilities {

    /**
     * 返回大嘴鱼是否吃掉了小鱼。<br>
     * 方式：检测2鱼(圆)之间是否相切或相交。<br>
     * 判断2圆心之间的距离小于2圆半径之和。此处判断小于大嘴鱼的半径即可。让大嘴鱼可以吃掉小鱼。
     *
     * @param fish
     * @param bean
     * @return 返回大鱼是否吃掉小鱼。
     */
    public static boolean isInteraction(BigMouthFish fish, FishBean bean) {
        return Math.pow(fish.posx + fish.size / 2 - bean.posx - bean.size / 2, 2) +
                Math.pow(fish.posy + fish.size / 2 - bean.posy - bean.size / 2, 2) <= Math.pow(fish.size / 2, 2);
    }

}
