package robot.andy.games.MLBPerfectInning;

import java.util.Timer;

public class ScreenshotMonitor {

	public ScreenshotMonitor(){
		run();
	}
	
	
	private void run(){
		
		Timer timer = new Timer();
		timer.schedule(new ScreenshotAnalyze(), 1000, 4000);
		
		
		
	}
	
	
	public static void main(String[] args) {
		
		
		ScreenshotMonitor sm = new ScreenshotMonitor();
		
	}
}
