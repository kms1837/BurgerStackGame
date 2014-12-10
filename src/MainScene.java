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
	private boolean flag;
	// 본래는 interrupt를 이용하려 했으나 INTERRUPTED while loading Image가 나온거 보니 이미지로딩 쓰레드와 충돌이나는 문제를 해결못하여 사용

	Image image1;
	Image logo;
	
	public MainScene(int intputWidth, int inputHeight, CustomMouse inputMouseListener, MainManagment inputRootFrame)
	{
		//super();
		
		
		image1 = new ImageIcon("resource/background.png").getImage();
		logo = new ImageIcon("resource/logo.png").getImage();
		
		setWidth  = intputWidth;
		setHeight = inputHeight;
		mouse 	  = inputMouseListener;
		rootFrame = inputRootFrame;
		flag = false;
		
		mainSceneThread = new Thread(this);
		mainSceneThread.start();
	}

	public void clearExitScene()
	{
		if(mainSceneThread != null) {
			flag = true;
			mainSceneThread.interrupt();
			System.gc();
		}
	}

	public void run()
	{
		try{
			while(!flag){
				buttonEvent();
				
				repaint();
				revalidate();
				
				mainSceneThread.sleep(17);
			}
		}catch(InterruptedException ex){	
		} finally {
			System.out.println("mainScene Thread dead");
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
			System.out.println("클릭");
			clearExitScene();
			rootFrame.moveClothingScene();
		}
		else if(mouse.getMouseClickPositionX()>1170 && mouse.getMouseClickPositionX()<1250 && mouse.getMouseClickPositionY()>30 && mouse.getMouseClickPositionY()<110)
		{
			System.exit(0);
		}
	}

	public void paintComponent(Graphics g)
	{

		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(image1, 0, 0, getWidth(), getHeight(), this);
		g2.drawImage(logo, 400, 70, 430, 380, this);
		g2.drawImage(startButtonImg, 330, 455, this);
		g2.drawImage(endImg, 1100, 30, this);
	}
}
