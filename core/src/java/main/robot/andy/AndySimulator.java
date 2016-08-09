package robot.andy;

import java.awt.Robot;
import java.awt.event.InputEvent;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

import modules.utilities.RobotHandler;

public class AndySimulator {
	
	Properties prop;
	
	
	
	public AndySimulator(){
		init();
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
	
	public void maximizeWindow(){
		
		moveToAndyApp();
		pressAndRelease();
	}
	
	
	private void moveToAndyApp(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("AndyAppPosX");
		String posYStr =  prop.getProperty("AndyAppPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
	}
	
	
	private void pressAndRelease(){
		
		Robot r = RobotHandler.getRobot();
		
		r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	public static void main(String[] args) {
		
		AndySimulator as = new AndySimulator();
		as.maximizeWindow();
		
	}
}
