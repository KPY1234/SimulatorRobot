package robot.nox.games.SanguoCute;

import java.util.Timer;

public class SanguoScreenshotMonitor {

	public SanguoScreenshotMonitor(){
		run();
	}
	
	
	private void run(){
		
		Timer timer = new Timer();
		timer.schedule(new SanguoScreenshotAnalyze(), 1000, 4000);
		
		
		
	}
	
	
	public static void main(String[] args) {
		
		
		SanguoScreenshotMonitor sm = new SanguoScreenshotMonitor();
		
	}
}
