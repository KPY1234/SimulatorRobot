package robot.andy.games.MLBPerfectInning;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;
import java.util.TimerTask;

import javax.imageio.ImageIO;

import modules.utilities.RobotHandler;

public class ScreenshotAnalyze extends TimerTask {
	
	final int ANDY_DESKTOP_STATE = 0;
	final int MLB_DOWNLOAD_STATE = 1;
	final int MLB_BEGIN_STATE = 2;
	final int MLB_PLAY_STATE = 3;
	final int MLB_GAMING_STATE = 4;
	final int MLB_END_GAME_STATE = 5;
	final int MLB_MVP_BOX_STATE = 6;
	final int MLB_AWARD_STATE = 7;
	final int MLB_BREAK_DAY_STATE = 8;
	final int MLB_NETWORK_UNSTABLE_STATE = 10;
	final int MLB_WORLD_SERIES_STATE = 11;
	final int MLB_GOLD_GLOVE_STATE = 12;
	final int MLB_GOLD_GLOVE_AWARD_STATE = 13;
	final int MLB_GOLD_GLOVE_AGAIN_STATE = 14;
	final int MLB_RESET_STATE = 99;
	
	int STATE = -1;
	
	long noneStateStartTime;
	
	Properties prop;
	
	int idleTime = 300000; //ms
	
	public ScreenshotAnalyze(){
		init();
	}
	
	private void init(){
		
		prop = new Properties();
		noneStateStartTime = new Date().getTime();
		
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
	
	@Override
	public void run() {
		// TODO Auto-generated method stub
		
//		try {
//			Thread.sleep(5000);
//		} catch (InterruptedException e1) {
//			// TODO Auto-generated catch block
//			e1.printStackTrace();
//		}
		
		int diff_mlb_0 = getMLB_0();
		int diff_mlb_1 = getMLB_1();
		int diff_mlb_2 = getMLB_2();
		int diff_mlb_3 = getMLB_3();
		int diff_mlb_4 = getMLB_4();
		int diff_mlb_5 = getMLB_5();
		int diff_mlb_6 = getMLB_6();
		int diff_mlb_7 = getMLB_7();
		int diff_mlb_8 = getMLB_8();
		int diff_mlb_9 = getMLB_9();
		int diff_mlb_10 = getMLB_10();
		int diff_mlb_11 = getMLB_11();
		int diff_mlb_12 = getMLB_12();
		int diff_mlb_13 = getMLB_13();
		int diff_mlb_14 = getMLB_14();
		
		
		
		if(diff_mlb_0 < 1500000){
			STATE = ANDY_DESKTOP_STATE;
			System.out.println(diff_mlb_0);
		}else if(diff_mlb_1 < 100000){
			STATE = MLB_DOWNLOAD_STATE;
			System.out.println(diff_mlb_1);
		}else if(diff_mlb_2 < 3000000){
			STATE = MLB_BEGIN_STATE;
			System.out.println(diff_mlb_2);
		}else if(diff_mlb_3 < 50000){
			STATE = MLB_PLAY_STATE;
			System.out.println(diff_mlb_3);
		}else if(diff_mlb_4 < 20000){
			STATE = MLB_GAMING_STATE;
			System.out.println(diff_mlb_4);
		}else if(diff_mlb_5 < 300000){
			STATE = MLB_END_GAME_STATE;
			System.out.println(diff_mlb_5);
		}else if(diff_mlb_6 < 30000){
			STATE = MLB_MVP_BOX_STATE;
			System.out.println(diff_mlb_6);
		}else if(diff_mlb_7 < 70000){
			STATE = MLB_AWARD_STATE;
			System.out.println(diff_mlb_7);
		}else if(diff_mlb_8 < 35000){
			STATE = MLB_BREAK_DAY_STATE;
			System.out.println(diff_mlb_8);
		}else if(diff_mlb_10 < 80000){
			STATE = MLB_NETWORK_UNSTABLE_STATE;
			System.out.println(diff_mlb_8);
		}else if(diff_mlb_11 < 300000){
			STATE = MLB_WORLD_SERIES_STATE;
			System.out.println(diff_mlb_11);
		}else if(diff_mlb_12 < 30000){
			STATE = MLB_GOLD_GLOVE_STATE;
			System.out.println(diff_mlb_12);
		}else if(diff_mlb_13 < 50000){
			STATE = MLB_GOLD_GLOVE_AWARD_STATE;
			System.out.println(diff_mlb_13);
		}else if(diff_mlb_14 < 50000){
			STATE = MLB_GOLD_GLOVE_AGAIN_STATE;
			System.out.println(diff_mlb_14);
		}else{
			long nowTime = new Date().getTime();
			System.out.println(nowTime);
			
			if(STATE!=-1)
				noneStateStartTime = nowTime;
			
			long timeDiff = nowTime - noneStateStartTime;
			System.out.println("time diff: "+timeDiff);
			if(timeDiff > idleTime && diff_mlb_9 < 10000){
				System.out.println("halt");
				resetSimulator();
				STATE = MLB_RESET_STATE;
			}
			else
				STATE = -1;
		}
//		System.out.println("diff0= "+diff_mlb_0);
//		System.out.println("diff1= "+diff_mlb_1);
//		System.out.println("diff2= "+diff_mlb_2);
//		System.out.println("diff3= "+diff_mlb_3);
//		System.out.println("diff4= "+diff_mlb_4);
//		System.out.println("diff5= "+diff_mlb_5);
//		System.out.println("diff6= "+diff_mlb_6);
//		System.out.println("diff7= "+diff_mlb_7);
//		System.out.println("diff8= "+diff_mlb_8);
//		System.out.println("diff9= "+diff_mlb_9);
//		System.out.println("diff10= "+diff_mlb_10);
//		System.out.println("diff11= "+diff_mlb_11);
//		System.out.println("diff12= "+diff_mlb_12);
//		System.out.println("diff13= "+diff_mlb_13);
		System.out.println("diff14= "+diff_mlb_14);
		
		if(STATE == ANDY_DESKTOP_STATE){
			System.out.println("ANDY_DESKTOP_STATE");
			openMLBGame();
			
		}else if(STATE == MLB_DOWNLOAD_STATE){
			System.out.println("MLB_DOWNLOAD_STATE");
			touchScreen();
			
		}else if(STATE == MLB_BEGIN_STATE){
			System.out.println("MLB_BEGIN_STATE");
			playSeasonGames();
			
		}else if(STATE == MLB_PLAY_STATE){
			System.out.println("MLB_PLAY_STATE");
			playGame();
			
		}else if(STATE == MLB_GAMING_STATE){
			System.out.println("MLB_GAMING_STATE");
			speedupGame();
		}else if(STATE == MLB_END_GAME_STATE){
			System.out.println("MLB_END_GAME_STATE");
			endGameAction();
		}else if(STATE == MLB_MVP_BOX_STATE){
			System.out.println("MLB_MVP_BOX_STATE");
			closeMVPBox();
		}else if(STATE == MLB_AWARD_STATE){
			System.out.println("MLB_AWARD_STATE");
			chooseOneAward();
		}else if(STATE == MLB_BREAK_DAY_STATE){
			System.out.println("MLB_BREAK_DAY_STATE");
			breakDayConfirm();
		}else if(STATE == MLB_NETWORK_UNSTABLE_STATE){
			System.out.println("MLB_NETWORK_UNSTABLE_STATE");
			networkUnstableConfirm();
		}else if(STATE == MLB_WORLD_SERIES_STATE){
			System.out.println("MLB_WORLD_SERIES_STATE");
			GoWorldSeries();
		}else if(STATE == MLB_GOLD_GLOVE_STATE){
			System.out.println("MLB_GOLD_GLOVE_STATE");
			PlayWheel();
		}else if(STATE == MLB_GOLD_GLOVE_AWARD_STATE){
			System.out.println("MLB_GOLD_GLOVE_AWARD_STATE");
			WheelAwardConfirm();
		}else if(STATE == MLB_GOLD_GLOVE_AGAIN_STATE){
			System.out.println("MLB_GOLD_GLOVE_AGAIN_STATE");
			WheelAwardConfirm();
		}else{
			System.out.println("NONE");
			
		}
		
		
//		System.out.println(STATE);
//		System.out.println(diff_mlb_1);
	}
	
	
	private int getMLB_0(){
		
		String pic = "./screenshot/mlb_00.jpg";
		
		int x1 = 927;
		int y1 = 185;
		int x2 = 1303;
		int y2 = 685;
		
		int diff = 0;
	
		try {
			diff = getPicDiff(pic, x1, y1, x2, y2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diff;
		
	}
	
	private int getMLB_1(){
		
		String pic = "./screenshot/mlb_01.jpg";
		
		int x1 = 988;
		int y1 = 228;
		int x2 = 1110;
		int y2 = 287;
		
		int diff = 0;
	
		try {
			diff = getPicDiff(pic, x1, y1, x2, y2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diff;
		
	}
	
	private int getMLB_2(){
		
		String pic = "./screenshot/mlb_02.jpg";
		
		int x1 = 927;
		int y1 = 185;
		int x2 = 1303;
		int y2 = 685;
		
		int diff = 0;
	
		try {
			diff = getPicDiff(pic, x1, y1, x2, y2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diff;
		
	}
	
	private int getMLB_3(){
		
		String pic = "./screenshot/mlb_03.jpg";
		
		int x1 = 498;
		int y1 = 478;
		int x2 = 951;
		int y2 = 515;
		
		int diff = 0;
	
		try {
			diff = getPicDiff(pic, x1, y1, x2, y2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diff;
		
	}
	
	private int getMLB_4(){
		
		String pic = "./screenshot/mlb_04.jpg";
		
		int x1 = 1350;
		int y1 = 54;
		int x2 = 1420;
		int y2 = 102;
		
		int diff = 0;
	
		try {
			diff = getPicDiff(pic, x1, y1, x2, y2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diff;
		
	}
	
	private int getMLB_5(){
		
		String pic = "./screenshot/mlb_05.jpg";
		
		int x1 = 180;
		int y1 = 663;
		int x2 = 1276;
		int y2 = 721;
		
		int diff = 0;
	
		try {
			diff = getPicDiff(pic, x1, y1, x2, y2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diff;
		
	}
	
	private int getMLB_6(){
		
		String pic = "./screenshot/mlb_06.jpg";
		
		int x1 = 465;
		int y1 = 119;
		int x2 = 1239;
		int y2 = 173;
		
		int diff = 0;
	
		try {
			diff = getPicDiff(pic, x1, y1, x2, y2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diff;
		
	}
	
	private int getMLB_7(){
		
		String pic = "./screenshot/mlb_07.jpg";
		
		int x1 = 144;
		int y1 = 191;
		int x2 = 1295;
		int y2 = 230;
		
		int diff = 0;
	
		try {
			diff = getPicDiff(pic, x1, y1, x2, y2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diff;
		
	}
	
	private int getMLB_8(){
		
		String pic = "./screenshot/mlb_08.jpg";
		
		int x1 = 1072;
		int y1 = 159;
		int x2 = 1274;
		int y2 = 212;
		
		int diff = 0;
	
		try {
			diff = getPicDiff(pic, x1, y1, x2, y2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diff;
		
	}
	
	private int getMLB_9(){
		
		String pic = "./screenshot/mlb_09.jpg";
		
		int x1 = 594;
		int y1 = 809;
		int x2 = 869;
		int y2 = 849;
		
		int diff = 0;
	
		try {
			diff = getPicDiff(pic, x1, y1, x2, y2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diff;
		
	}
	
	private int getMLB_10(){
		
		String pic = "./screenshot/mlb_10.jpg";
		
		int x1 = 573;
		int y1 = 256;
		int x2 = 844;
		int y2 = 531;
		
		int diff = 0;
	
		try {
			diff = getPicDiff(pic, x1, y1, x2, y2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diff;
		
	}
	
	private int getMLB_11(){
		
		String pic = "./screenshot/mlb_11.jpg";
		
		int x1 = 612;
		int y1 = 136;
		int x2 = 813;
		int y2 = 276;
		
		int diff = 0;
	
		try {
			diff = getPicDiff(pic, x1, y1, x2, y2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diff;
		
	}
	
	private int getMLB_12(){
		
		String pic = "./screenshot/mlb_12.jpg";
		
		int x1 = 1117;
		int y1 = 521;
		int x2 = 1159;
		int y2 = 605;
		
		int diff = 0;
	
		try {
			diff = getPicDiff(pic, x1, y1, x2, y2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diff;
		
	}
	
	private int getMLB_13(){
		
		String pic = "./screenshot/mlb_13.jpg";
		
		int x1 = 625;
		int y1 = 608;
		int x2 = 813;
		int y2 = 651;
		
		int diff = 0;
	
		try {
			diff = getPicDiff(pic, x1, y1, x2, y2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diff;
		
	}
	
	private int getMLB_14(){
		
		String pic = "./screenshot/mlb_14.jpg";
		
		int x1 = 627;
		int y1 = 609;
		int x2 = 813;
		int y2 = 651;
		
		int diff = 0;
	
		try {
			diff = getPicDiff(pic, x1, y1, x2, y2);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (AWTException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return diff;
		
	}
	
	private int getPicDiff(String pic, int x1, int y1, int x2, int y2) throws IOException, AWTException{

		Robot robot = new Robot();
		Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
        BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
		
		BufferedImage readImg = ImageIO.read(new File(pic));
		
		int readImgWidth = readImg.getWidth();
		int readImgHeight = readImg.getHeight();
		
	
		BufferedImage image =
		    new BufferedImage(readImgWidth, readImgHeight, BufferedImage.TYPE_INT_RGB);
		
		int diff_sum = 0;
		
		for (int y = y1; y < y2; y++) {
		    for (int x = x1; x < x2; x++) {
		    	
//		    	System.out.println("y="+y+" x="+x);
		    	
		    	int screenshot_argb = screenFullImage.getRGB(x, y);
		    	int pic_argb = readImg.getRGB(x, y);
		    	
		    	Color screenshot_color = new Color(screenshot_argb);
		    	Color pic_color = new Color(pic_argb);
		    	
		    	int screenshot_red = screenshot_color.getRed();
		    	int screenshot_green = screenshot_color.getGreen();
		    	int screenshot_blue = screenshot_color.getBlue();
		    	
		    	int pic_red = pic_color.getRed();
		    	int pic_green = pic_color.getGreen();
		    	int pic_blue = pic_color.getBlue();
		    	
		    	
		    	int diff = Math.abs(screenshot_red - pic_red) + 
		    			      Math.abs(screenshot_green - pic_green) +
		    			          Math.abs(screenshot_blue - pic_blue);
		    	
//		    	System.out.println(diff);
		    	diff_sum += diff;
		    	
		    	
		    	image.setRGB(x, y, screenshot_color.getRGB());
		    	
		    	
		    }
		}
		
		
//		ImageIO.write(image, "jpg", new File("./screenshot/snap.jpg"));
		return diff_sum;
		
		
	}
	
	private void openMLBGame(){
		
		moveToMLBGame();
		RobotHandler.setRobotDelay(0);
		RobotHandler.pressAndRelease();
		RobotHandler.setRobotDelay(500);
		
	}
	
	private void touchScreen(){
		
		moveToTouchLogo();
		RobotHandler.pressAndRelease();

		
	}
	
	private void playSeasonGames(){
		
		moveToOpenBall();
		RobotHandler.pressAndRelease();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		moveToSeasonMode();
		RobotHandler.pressAndRelease();
		
	}
	
	private void playGame(){
		
		moveToNextOne();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RobotHandler.pressAndRelease();
		moveToAutoMode();
		RobotHandler.pressAndRelease();
		moveToSubAutoMode();
		RobotHandler.pressAndRelease();
		moveToPlayBall();
		RobotHandler.pressAndRelease();
		
	}
	
	private void speedupGame(){
		
		moveToSpeedUp();
		RobotHandler.pressAndRelease();
		moveToSpeedUpConfirm();
		RobotHandler.pressAndRelease();
		
	}
	
	private void endGameAction(){
		
		moveToConfirm();
		RobotHandler.pressAndRelease();
		
		
//		try {
//			Thread.sleep(6000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		moveToCloseIcon();
//		RobotHandler.pressAndRelease();
//		try {
//			Thread.sleep(3000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		moveToRightGift();
//		RobotHandler.pressAndRelease();
//		moveToGiftConfirm();
//		RobotHandler.pressAndRelease();
//		moveToGiftDoubleConfirm();
//		RobotHandler.pressAndRelease();
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		
	}
	
	private void closeMVPBox(){
		
		moveToCloseMVPIcon();
		RobotHandler.pressAndRelease();
		
	}
	
	private void chooseOneAward(){
		
		moveToRightGift();
		RobotHandler.pressAndRelease();
		moveToGiftConfirm();
		RobotHandler.pressAndRelease();
		moveToGiftDoubleConfirm();
		RobotHandler.pressAndRelease();
		
		
	}
	
	private void breakDayConfirm(){
		
		moveToBreakDayConfirm();
		RobotHandler.pressAndRelease();
		
	}
	
	private void networkUnstableConfirm(){
		
		moveToNetworkUnstableConfirm();
		RobotHandler.pressAndRelease();
	}
	
	private void GoWorldSeries(){
		moveToWorldSeriesGo();
		RobotHandler.pressAndRelease();
	}
	
	private void PlayWheel(){
		moveToWheel();
		RobotHandler.pressAndRelease();
	}
	
	private void WheelAwardConfirm(){
		
		moveToWheelAwardConfirm();
		RobotHandler.pressAndRelease();
	}
	
	private void resetSimulator(){
		moveToSimulatorCloseIcon();
		RobotHandler.pressAndRelease();
		moveToSimulatorCloseConfirm();
		RobotHandler.pressAndRelease();
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		moveToWindowsStart();
		RobotHandler.pressAndRelease();
		moveToAndyLauncher();
		RobotHandler.pressAndRelease();
		
		try {
			Thread.sleep(15000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		maximizeWindowSize();
	}
	
	private void moveToMLBGame(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("MLBGamePosX");
		String posYStr =  prop.getProperty("MLBGamePosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToTouchLogo(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("TouchLogoPosX");
		String posYStr =  prop.getProperty("TouchLogoPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToOpenBall(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("OpenBallPosX");
		String posYStr =  prop.getProperty("OpenBallPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToSeasonMode(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("SeasonModePosX");
		String posYStr =  prop.getProperty("SeasonModePosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToNextOne(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("NextOnePosX");
		String posYStr =  prop.getProperty("NextOnePosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToAutoMode(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("AutoModePosX");
		String posYStr =  prop.getProperty("AutoModePosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToSubAutoMode(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("SubModeAutoPosX");
		String posYStr =  prop.getProperty("SubModeAutoPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToPlayBall(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("PlayBallPosX");
		String posYStr =  prop.getProperty("PlayBallPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToSpeedUp(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("SpeedUpPosX");
		String posYStr =  prop.getProperty("SpeedUpPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToSpeedUpConfirm(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("SpeedUpConfirmPosX");
		String posYStr =  prop.getProperty("SpeedUpConfirmPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToConfirm(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("ConfirmPosX");
		String posYStr =  prop.getProperty("ConfirmPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
//	private void moveToCloseIcon(){
//		
//		Robot r = RobotHandler.getRobot();
//		
//		String posXStr =  prop.getProperty("CloseIconPosX");
//		String posYStr =  prop.getProperty("CloseIconPosY");
//		
//		int x = Integer.parseInt(posXStr);
//		int y = Integer.parseInt(posYStr);
//		
//		r.mouseMove(x, y);
//		
//	}
	
	private void moveToRightGift(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("RightGiftPosX");
		String posYStr =  prop.getProperty("RightGiftPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToGiftConfirm(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("GiftConfirmPosX");
		String posYStr =  prop.getProperty("GiftConfirmPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToGiftDoubleConfirm(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("GiftDoubleConfirmPosX");
		String posYStr =  prop.getProperty("GiftDoubleConfirmPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToBreakDayConfirm(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("BreakDayConfirmPosX");
		String posYStr =  prop.getProperty("BreakDayConfirmPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToNetworkUnstableConfirm(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("NetworkUnstableConfirmPosX");
		String posYStr =  prop.getProperty("NetworkUnstableConfirmPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToWorldSeriesGo(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("WorldSeriesGoPosX");
		String posYStr =  prop.getProperty("WorldSeriesGoPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToWheel(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("WheelPosX");
		String posYStr =  prop.getProperty("WheelPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToWheelAwardConfirm(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("WheelAwardConfirmPosX");
		String posYStr =  prop.getProperty("WheelAwardConfirmPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToCloseMVPIcon(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("CloseMVPBoxPosX");
		String posYStr =  prop.getProperty("CloseMVPBoxPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToSimulatorCloseIcon(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("SimulatorCloseIconPosX");
		String posYStr =  prop.getProperty("SimulatorCloseIconPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToSimulatorCloseConfirm(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("SimulatorCloseConfirmPosX");
		String posYStr =  prop.getProperty("SimulatorCloseConfirmPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToWindowsStart(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("WindowsStartPosX");
		String posYStr =  prop.getProperty("WindowsStartPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToAndyLauncher(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("AndyLauncherPosX");
		String posYStr =  prop.getProperty("AndyLauncherPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	public void maximizeWindowSize(){
		
		Robot r = RobotHandler.getRobot();
		
		r.keyPress(KeyEvent.VK_WINDOWS);
	    r.keyPress(KeyEvent.VK_UP);
	    r.keyRelease(KeyEvent.VK_WINDOWS);
	    r.keyRelease(KeyEvent.VK_UP);
		
	}
	
	public static void main(String[] args) {
		
		ScreenshotAnalyze sa = new ScreenshotAnalyze();
		sa.run();
	}
	
}
