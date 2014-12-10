import javax.swing.*;

import java.awt.event.*;
import java.awt.*;


public class ResultScene extends JPanel {
	Container contentPane;
	//ImageIcon retry = new ImageIcon("resource/retry.png");
	ImageIcon successIcon 	= new ImageIcon("resource/success.png");
	ImageIcon failIcon 		= new ImageIcon("resource/failback.png"); //배경화면 로딩
	private boolean result;
	//JButton btn 	= new JButton("resource/retry.png");
	
	private int resultTimer;
	private int resultBurgerCount;
	
	ResultScene(boolean inputResult, int inputBurgerCount, int inputTimer){
		//this.setLayout(new GridBagLayout());
		result = inputResult;
		resultTimer = inputTimer;
		resultBurgerCount = inputBurgerCount;
	}
	
	/*
	void creatMenu() {
		JMenuBar mb = new JMenuBar();
		JMenuItem [] menuItem = new JMenuItem[2];
		String [] itemTitle = {"Retry", "Exit"};
		JMenu fileMenu = new JMenu("Menu");
		
		for(int i=0; i<menuItem.length; i++) 
		{
			menuItem[i] = new JMenuItem(itemTitle[i]);
			
		fileMenu.add(menuItem[i]);
		}		
		mb.add(fileMenu);
		setJMenuBar(mb);
	}
	*/
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		Font font1 = new Font("맑은 고딕", Font.PLAIN, 30);
		
		if(result) {
			
			g.drawImage(successIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
			
			g.setFont(font1);
			g.setColor(Color.red);
			
			int sec  = resultTimer % 60;
		    int min  = resultTimer / 60 % 60;
		    
			g.drawString(min + " : " + sec  + " 시간 남기고 성공", this.getWidth()/2 - 160 , 300);//string for price of items
			
		}else{
			g.setFont(font1);
			g.setColor(Color.red);
			
			g.drawImage(failIcon.getImage(), 0, 0, getWidth(), getHeight(), this);
			g.drawString( resultBurgerCount + " 개 만듬", this.getWidth()/2 - 260 , 100);
		}
	}
}
