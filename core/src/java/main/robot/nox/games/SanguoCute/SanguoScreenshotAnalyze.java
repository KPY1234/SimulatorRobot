package robot.nox.games.SanguoCute;

import java.awt.AWTException;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.InputEvent;
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

public class SanguoScreenshotAnalyze extends TimerTask {
	
	final int SANGUO_AVAILABLE_FOOD_STATE = 1;
	final int SANGUO_CITY_INSIDE_STATE = 2;
	final int SANGUO_EXPAND_FIGHT_STATE = 3;
	final int SANGUO_FIGHT_START_STATE = 4;
	final int SANGUO_FIGHT_PREPARE_STATE = 5;
	final int SANGUO_FIGHT_CHOOSER_STATE = 6;
	final int SANGUO_CONTINUE_FIGHT_CONFIRM_STATE = 7;
	final int SANGUO_END_FIGHT_STATE = 8;
//	final int MLB_BREAK_DAY_STATE = 8;
//	final int MLB_NETWORK_UNSTABLE_STATE = 10;
//	final int MLB_WORLD_SERIES_STATE = 11;
//	final int MLB_GOLD_GLOVE_STATE = 12;
//	final int MLB_GOLD_GLOVE_AWARD_STATE = 13;
//	final int MLB_GOLD_GLOVE_AGAIN_STATE = 14;
//	final int MLB_RESET_STATE = 99;
	
	int STATE = -1;
	
	long noneStateStartTime;
	
	Properties prop;
	
	int idleTime = 300000; //ms
	
	public SanguoScreenshotAnalyze(){
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
		
		try {
			Thread.sleep(3000);
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		int diff_city_inside = get_diff_city_inside();
		int diff_expand_fight = get_diff_expand_fight();
		int diff_fight_start = get_diff_fight_start();
		int diff_fight_prepare = get_diff_fight_prepare();
		int diff_continue_fight = get_diff_continue_fight();
		int diff_continue_fight_confirm = get_diff_continue_fight_confirm();
		int diff_end_fight = get_diff_end_fight();
		int diff_collect_food = get_diff_collect_food();
//		int diff_mlb_8 = getMLB_8();
//		int diff_mlb_9 = getMLB_9();
//		int diff_mlb_10 = getMLB_10();
//		int diff_mlb_11 = getMLB_11();
//		int diff_mlb_12 = getMLB_12();
//		int diff_mlb_13 = getMLB_13();
//		int diff_mlb_14 = getMLB_14();
		
		System.out.println("diff\t"+diff_continue_fight_confirm);
		
		if(diff_collect_food < 500){
			STATE = SANGUO_AVAILABLE_FOOD_STATE;
			System.out.println(diff_collect_food);
		}
		else if(diff_city_inside < 2300){
			STATE = SANGUO_CITY_INSIDE_STATE;
			System.out.println(diff_city_inside);
		}
		else if(diff_expand_fight < 50000){
			STATE = SANGUO_EXPAND_FIGHT_STATE;
			System.out.println(diff_expand_fight);
		}
		else if(diff_fight_start < 55000){
			STATE = SANGUO_FIGHT_START_STATE;
			System.out.println(diff_fight_start);
		}
		else if(diff_fight_prepare < 850000){
			STATE = SANGUO_FIGHT_PREPARE_STATE;
			System.out.println(diff_fight_prepare);
		}
		else if(diff_continue_fight < 44000){
			STATE = SANGUO_FIGHT_CHOOSER_STATE;
			System.out.println(diff_continue_fight);
		}
		else if(diff_continue_fight_confirm < 130000){
			STATE = SANGUO_CONTINUE_FIGHT_CONFIRM_STATE;
			System.out.println(diff_continue_fight_confirm);
		}
		else if(diff_end_fight < 53000){
			STATE = SANGUO_END_FIGHT_STATE;
			System.out.println(diff_end_fight);
		}
		else{
			STATE = -1;
		}

		if(STATE == SANGUO_AVAILABLE_FOOD_STATE){
			System.out.println("SANGUO_AVAILABLE_FOOD_STATE");
			collectFood();
			
		}
		else if(STATE == SANGUO_CITY_INSIDE_STATE){
			System.out.println("SANGUO_CITY_INSIDE_STATE");
			startFight();
			
		}
		else if(STATE == SANGUO_EXPAND_FIGHT_STATE){
			System.out.println("SANGUO_EXPAND_FIGHT_STATE");
			enterFight();
			
		}
		else if(STATE == SANGUO_FIGHT_START_STATE){
			System.out.println("SANGUO_FIGHT_START_STATE");
			startWarSetting();
			
		}
		else if(STATE == SANGUO_FIGHT_PREPARE_STATE){
			System.out.println("SANGUO_FIGHT_PREPARE_STATE");
			fightPrepare();
			
		}
		else if(STATE == SANGUO_FIGHT_CHOOSER_STATE){
			System.out.println("SANGUO_FIGHT_CHOOSER_STATE");
			startContinueFight();
		}
		else if(STATE == SANGUO_CONTINUE_FIGHT_CONFIRM_STATE){
			System.out.println("SANGUO_CONTINUE_FIGHT_CONFIRM_STATE");
			confirmContinueFight();
		}
		else if(STATE == SANGUO_END_FIGHT_STATE){
			System.out.println("SANGUO_END_FIGHT_STATE");
			confirmEndFightAndEscapeToCityInside();
		}

		
		
//		System.out.println(STATE);
//		System.out.println(diff_mlb_1);
	}
	
	
//	private int getMLB_0(){
//		
//		String pic = "./screenshot/mlb_00.jpg";
//		
//		int x1 = 927;
//		int y1 = 185;
//		int x2 = 1303;
//		int y2 = 685;
//		
//		int diff = 0;
//	
//		try {
//			diff = getPicDiff(pic, x1, y1, x2, y2);
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (AWTException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		return diff;
//		
//	}
	
	private int get_diff_city_inside(){
		
		String pic = "./screenshot/sanguoCute/sanguo_cityInside.jpg";
		
		int x1 = 1253;
		int y1 = 710;
		int x2 = 1288;
		int y2 = 730;
		
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
	
	private int get_diff_expand_fight(){
		
		String pic = "./screenshot/sanguoCute/sanguo_expandFight.jpg";
		
		int x1 = 1246;
		int y1 = 598;
		int x2 = 1306;
		int y2 = 654;
		
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
	
	private int get_diff_fight_start(){
		
		String pic = "./screenshot/sanguoCute/sanguo_fightStart.jpg";
		
		int x1 = 610;
		int y1 = 689;
		int x2 = 784;
		int y2 = 731;
		
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
	
	private int get_diff_fight_prepare(){
		
		String pic = "./screenshot/sanguoCute/sanguo_fightPrepare.jpg";
		
		int x1 = 1047;
		int y1 = 689;
		int x2 = 1210;
		int y2 = 727;
		
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
	
	private int get_diff_continue_fight(){
		
		String pic = "./screenshot/sanguoCute/sanguo_fightChooser.jpg";
		
		int x1 = 1213;
		int y1 = 528;
		int x2 = 1328;
		int y2 = 551;
		
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
	
	private int get_diff_continue_fight_confirm(){
		
		String pic = "./screenshot/sanguoCute/sanguo_continueFightConfirm.jpg";
		
		int x1 = 535;
		int y1 = 503;
		int x2 = 890;
		int y2 = 631;
		
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
	
	private int get_diff_end_fight(){
		
		String pic = "./screenshot/sanguoCute/sanguo_endFight.jpg";
		
		int x1 = 612;
		int y1 = 638;
		int x2 = 788;
		int y2 = 683;
		
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
	
	private int get_diff_collect_food(){
		
		String pic = "./screenshot/sanguoCute/sanguo_availableFood.jpg";
		
		int x1 = 806;
		int y1 = 714;
		int x2 = 827;
		int y2 = 723;
		
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
	

	
	private void collectFood(){
		moveToFoodIcon();
		
		Robot r = RobotHandler.getRobot();
		
		r.mousePress(InputEvent.BUTTON1_DOWN_MASK);
		r.delay(3000); // Click three second
		r.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
	}
	
	private void startFight(){
		
		moveToFightButton();
		RobotHandler.setRobotDelay(0);
		RobotHandler.pressAndRelease();
		RobotHandler.setRobotDelay(500);
		
	}
	
	private void enterFight(){
		
		moveToEnterFightButton();
		RobotHandler.pressAndRelease();

		
	}
	
	private void startWarSetting(){
		
		moveToFightStartButton();
		RobotHandler.pressAndRelease();
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		moveToFightLevelPoint();
		RobotHandler.pressAndRelease();
	}
	
	private void fightPrepare(){
		
		moveToFightPrepareButton();
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RobotHandler.pressAndRelease();
		
		
	}
	
	private void startContinueFight(){
		
		moveToContinueFightButton();
		RobotHandler.pressAndRelease();
		
	}
	
	private void confirmContinueFight(){
		
		moveToContinueFightConfirm();
		RobotHandler.pressAndRelease();
		
	}
	
	private void confirmEndFightAndEscapeToCityInside(){
		
		moveToEndFightConfirmButton();
		RobotHandler.pressAndRelease();
		
		for(int i=0;i<3;i++)
			Escape();

	}
	
	private void Escape(){
		
		Robot r = RobotHandler.getRobot();
		r.keyPress(KeyEvent.VK_ESCAPE);
	}
	
	private void moveToFoodIcon(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("Sanguo_FoodIconPosX");
		String posYStr =  prop.getProperty("Sanguo_FoodIconPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	
	private void moveToFightButton(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("Sanguo_StartFightButtonPosX");
		String posYStr =  prop.getProperty("Sanguo_StartFightButtonPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToEnterFightButton(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("Sanguo_EnterFightButtonPosX");
		String posYStr =  prop.getProperty("Sanguo_EnterFightButtonPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToFightStartButton(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("Sanguo_FightStartButtonPosX");
		String posYStr =  prop.getProperty("Sanguo_FightStartButtonPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToFightLevelPoint(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("Sanguo_FightLevelPointPosX");
		String posYStr =  prop.getProperty("Sanguo_FightLevelPointPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	

	private void moveToFightPrepareButton(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("Sanguo_FightPreparePosX");
		String posYStr =  prop.getProperty("Sanguo_FightPreparePosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToContinueFightButton(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("Sanguo_ContinueFightButtonPosX");
		String posYStr =  prop.getProperty("Sanguo_ContinueFightButtonPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToContinueFightConfirm(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("Sanguo_ContinueFightConfirmButtonPosX");
		String posYStr =  prop.getProperty("Sanguo_ContinueFightConfirmButtonPosY");
		
		int x = Integer.parseInt(posXStr);
		int y = Integer.parseInt(posYStr);
		
		r.mouseMove(x, y);
		
	}
	
	private void moveToEndFightConfirmButton(){
		
		Robot r = RobotHandler.getRobot();
		
		String posXStr =  prop.getProperty("Sanguo_ConfirmEndFightPosX");
		String posYStr =  prop.getProperty("Sanguo_ConfirmEndFightPosY");
		
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
		
		SanguoScreenshotAnalyze sa = new SanguoScreenshotAnalyze();
		sa.run();
	}
	
}
