import java.awt.Color;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.ImageIcon;
import javax.swing.JComponent;
import javax.swing.JPanel;


public class MainScene extends JPanel implements Runnable{
	private int setWidth, setHeight;
	private CustomMouse mouse;
	private MainManagment rootFrame;

	private Image startButtonImg;
	private Image endImg;

	private Thread mainSceneThread;

	public MainScene(int intputWidth, int inputHeight, CustomMouse inputMouseListener, MainManagment inputRootFrame) {
		//super();
		setWidth  = intputWidth;
		setHeight = inputHeight;
		mouse 	  = inputMouseListener;
		rootFrame = inputRootFrame;

		mainSceneThread = new Thread(this);
		mainSceneThread.start();
		
		this.setLayout(new GridBagLayout());
	}

	public void clearExitScene()
	{
		if(mainSceneThread != null) {
			mainSceneThread.interrupt();
			mainSceneThread = null;
			System.gc();
		}
	}

	public void run() {
		try{
			while(!mainSceneThread.currentThread().isInterrupted()){
				
				buttonEvent();
				
				mainSceneThread.sleep(30);
				
			}
		}catch(InterruptedException ex){
			
		}
	}
	
	private void buttonEvent()
	{
		if(mouse.getMousePositionX()>353 && mouse.getMousePositionX()<927 && mouse.getMousePositionY()>455 && mouse.getMousePositionY()<718) {
			startButtonImg = new ImageIcon("resource/startbutton2.png").getImage();
		}else{
			startButtonImg = new ImageIcon("resource/startbutton1.png").getImage();
		}

		if(mouse.getMousePositionX()>1100 && mouse.getMousePositionX()<1180 && mouse.getMousePositionY()>30 && mouse.getMousePositionY()<110) {
			endImg = new ImageIcon("resource/exit2.png").getImage();
		}else{
			endImg = new ImageIcon("resource/exit1.png").getImage();
		}

		if(mouse.getMouseClickPositionX()>353 && mouse.getMouseClickPositionX()<927 && mouse.getMouseClickPositionY()>455 && mouse.getMouseClickPositionY()<718)
		{
			System.out.println("Å¬¸¯");
			mouse.mouseUp();
			rootFrame.moveClothingScene();
			//rootFrame.moveGameScene();
			//sound.myBgmEnd();
		}
		else if(mouse.getMouseClickPositionX()>1170 && mouse.getMouseClickPositionX()<1250 && mouse.getMouseClickPositionY()>30 && mouse.getMouseClickPositionY()<110)
		{
			System.exit(0);
			//sound.myBgmEnd();
		}
		
		repaint();
	}

	public void paintComponent(Graphics g) {
		Image image1;
		image1 = new ImageIcon("resource/background.png").getImage();

		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image1, 0, 0, getWidth(), getHeight(), this);
		
		g2.drawImage(startButtonImg, 330, 455, this);
		g2.drawImage(endImg, 1100, 30, this);
	}
}
