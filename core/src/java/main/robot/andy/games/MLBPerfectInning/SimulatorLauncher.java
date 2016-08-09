package robot.andy.games.MLBPerfectInning;

import java.awt.Robot;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import modules.utilities.RobotHandler;
import robot.andy.AndySimulator;

public class SimulatorLauncher extends AndySimulator{

	Properties prop;
	
	public SimulatorLauncher(){
		
		init();
		try {
			run();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void init(){
		
		prop = new Properties();
		
		try {
			getPositionSettings();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void getPositionSettings() throws IOException{
		
		BufferedReader br = new BufferedReader(new FileReader("./settings/position.setting"));
		
		String line;
		
		while((line = br.readLine())!=null){
			
			String[] lineSplit = line.split("=");
			prop.put(lineSplit[0].trim(), lineSplit[1].trim());
		}
		
		br.close();
	}
	
	private void run() throws InterruptedException{
		
		maximizeWindow();
		
	}
	
	
	public static void main(String[] args) {
		
		SimulatorLauncher mlbpi = new SimulatorLauncher();
		ScreenshotMonitor sm = new ScreenshotMonitor();
		
	}

}
