package trafficlamp;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class LampController {
	private Lamp currentLamp;

	public LampController() {
		// 刚开始让由南向北的灯变绿;
		currentLamp = Lamp.S2N;
		currentLamp.light();

		/* 每隔10秒将当前绿灯变为红灯，并让下一个方向的灯变绿 */
		ScheduledExecutorService timer = Executors.newScheduledThreadPool(1);
		timer.scheduleAtFixedRate(new Runnable() {
			public void run() {
				System.out.println("来啊");
				currentLamp = currentLamp.blackOut();
			}
		}, 10, 10, TimeUnit.SECONDS);
	}
}
