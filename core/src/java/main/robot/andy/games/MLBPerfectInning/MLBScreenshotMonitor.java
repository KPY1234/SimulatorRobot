package robot.andy.games.MLBPerfectInning;

import java.util.Timer;

public class MLBScreenshotMonitor {

	public MLBScreenshotMonitor(){
		run();
	}
	
	
	private void run(){
		
		Timer timer = new Timer();
		timer.schedule(new MLBScreenshotAnalyze(), 1000, 4000);
		
		
		
	}
	
	
	public static void main(String[] args) {
		
		
		MLBScreenshotMonitor sm = new MLBScreenshotMonitor();
		
	}
}
