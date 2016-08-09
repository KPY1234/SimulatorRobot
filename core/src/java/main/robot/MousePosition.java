package robot;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.PointerInfo;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.LogManager;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;
import org.jnativehook.mouse.NativeMouseEvent;
import org.jnativehook.mouse.NativeMouseInputListener;

import com.sun.javafx.logging.Logger;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

public class MousePosition extends JFrame implements NativeMouseInputListener, NativeKeyListener{

	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	
	int xpos;
	int ypos;
	
	boolean LeftControlPressing = false;
	
	String settingsFilePath = "./settings/position.setting";
	
	/**
	 * Create the frame.
	 */
	public MousePosition() {
		
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 450, 300);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);
		
		JPanel panel = new JPanel();
		contentPane.add(panel, BorderLayout.CENTER);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel = new JLabel("X");
		panel.add(lblNewLabel);
		
		textField = new JTextField();
		textField.setEditable(false);
		panel.add(textField);
		textField.setColumns(10);
		
		JLabel lblNewLabel_1 = new JLabel("Y");
		panel.add(lblNewLabel_1);
		
		textField_1 = new JTextField();
		textField_1.setEditable(false);
		panel.add(textField_1);
		textField_1.setColumns(10);
		
	

	}
	
	public void monitor(){
		
		MouseLocation ml = new MouseLocation();
		ml.addPropertyChangeListener(new MouseListenerTest(){
			public void propertyChange(PropertyChangeEvent e){
				if(e.getPropertyName().equals("value"))
					reflesh();
					
			}
		
		});
		ml.start();
		
	}
	
	public void reflesh(){
		
		PointerInfo pointInfo = MouseInfo.getPointerInfo();
		Point pt = pointInfo.getLocation();
		int xPos = (int)pt.getX();
		int yPos = (int)pt.getY();
		
		this.xpos = xPos;
		this.ypos = yPos;

		textField.setText("\t"+xPos);
		textField_1.setText("\t"+yPos);
		
		
	}
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		
		try {
			GlobalScreen.registerNativeHook();
			
			// Clear previous logging configurations.
			LogManager.getLogManager().reset();

			
		} catch (NativeHookException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MousePosition frame = new MousePosition();
					frame.setVisible(true);
					frame.monitor();
					
					// Add the appropriate listeners.
					// Add the appropriate listeners.
			        GlobalScreen.addNativeMouseListener(frame);
			        GlobalScreen.addNativeMouseMotionListener(frame);
			        GlobalScreen.addNativeKeyListener(frame);
					
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	
	@Override
	public void nativeMouseClicked(NativeMouseEvent e) {
		// TODO Auto-generated method stub
//		System.out.println("x="+xpos+"\ty="+ypos);
		
		int clickedXPos = xpos;
		int clickedYPos = ypos;
		
		if(LeftControlPressing){
			String input=JOptionPane.showInputDialog(null,"請輸入:","輸入對話框",JOptionPane.QUESTION_MESSAGE);
			if(input == null || input.isEmpty())
				System.err.println("NULL");
			else{
				
				String XposMessage = input+"PosX = "+clickedXPos;
				String YposMessage = input+"PosY = "+clickedYPos;
				
				try {
					BufferedWriter bw = new BufferedWriter(new FileWriter(settingsFilePath, true));
					bw.write(XposMessage+"\n"+YposMessage+"\n");
					bw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
				System.out.println(input);
			}
				
			
		}
		
		
		
	}

	@Override
	public void nativeMousePressed(NativeMouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeMouseReleased(NativeMouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeMouseDragged(NativeMouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeMouseMoved(NativeMouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void nativeKeyPressed(NativeKeyEvent e) {
		// TODO Auto-generated method stub
		
		String eventKey = NativeKeyEvent.getKeyText(e.getKeyCode()).trim();
		
		if(eventKey.equals("Left Control"))
			LeftControlPressing = true;
		
//		System.out.println("Left Control Pressed: " +LeftControlPressing);
		
	}

	@Override
	public void nativeKeyReleased(NativeKeyEvent e) {
		// TODO Auto-generated method stub
		
		String eventKey = NativeKeyEvent.getKeyText(e.getKeyCode()).trim();
		
		if(eventKey.equals("Left Control"))
			LeftControlPressing = false;
		
		
	}

	@Override
	public void nativeKeyTyped(NativeKeyEvent e) {
		// TODO Auto-generated method stub
		
	}

	
	
}

class MouseListenerTest implements PropertyChangeListener{
	
	public void propertyChange(PropertyChangeEvent e){
	}
}

class MouseLocation extends Thread{
	
	private final PropertyChangeSupport pcs = new PropertyChangeSupport(this);
    
    public void addPropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        this.pcs.removePropertyChangeListener(listener);
    }

    private String value;

	
	public void run(){
		
		while(true){
			
			PointerInfo pointInfo = MouseInfo.getPointerInfo();
			Point pt = pointInfo.getLocation();
			int xPos = (int)pt.getX();
			int yPos = (int)pt.getY();
			
			setValue(xPos+"\t"+yPos);
		}
		
		
	}
	
	public void setValue(String newValue) {
        String oldValue = this.value;
        this.value = newValue;
        this.pcs.firePropertyChange("value", oldValue, newValue);
    }
	
	 public String getValue() {
         return this.value;
     }
	
}