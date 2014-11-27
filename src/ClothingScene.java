import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.Font;

import javax.swing.*;

public class ClothingScene extends JPanel implements MouseListener,MouseMotionListener{

	private static final long serialVersionUID = 1L;
	
	int price;//the price of items
	int total=0;//the total numbers of hamburger
	int cnt_top=0;int cnt_bot=0;int cnt_shoe=0;
	ImageIcon back=new ImageIcon("resource/background2.png");
	ImageIcon img1=new ImageIcon("resource/logo-shoe.png");
	ImageIcon img2=new ImageIcon("resource/heels.png");
	ImageIcon img3=new ImageIcon("resource/slipper.png");
	
	ImageIcon img4= new ImageIcon("resource/tshirt.png");
	ImageIcon img5= new ImageIcon("resource/long.png");
	ImageIcon img6= new ImageIcon("resource/hoodie.png");
	
	ImageIcon img7= new ImageIcon("resource/skirt.png");
	ImageIcon img8= new ImageIcon("resource/shortpants.png");
	ImageIcon img9= new ImageIcon("resource/long-pants.png");
	
	ImageIcon img10=new ImageIcon("resource/empty_ring.png");
	
	ImageIcon top1,top2,top3;
	ImageIcon bottom1,bottom2,bottom3;
	ImageIcon shoe1,shoe2,shoe3;
	ImageIcon check;
	Image before,after;
	
	int col1=45;int row1=400;
	int col2=260;int row2=670;
	int col3=500;int row3=940;
	int check_col1=-300;int check_row1=-300;//draw a check ring for top
	int check_col2=-300;int check_row2=-300;//draw a check ring for bottom
	int check_col3=-300;int check_row3=-300;//draw a check ring for shoe
	int startW=-300;int startH=-300;
	
	private CustomMouse mouse;
	private MainManagment rootFrame;
	
	public ClothingScene(CustomMouse inputMouseListener, MainManagment inputRootFrame)
	{
		//mouse = inputMouseListener;
		rootFrame = inputRootFrame;
		addMouseListener(this);
		addMouseMotionListener(this);
		
	}
	
	public void clearExitScene()
	{
		
	}
	
	public void paintComponent(Graphics g)
	{	
		Graphics g2=(Graphics )g;
		g2.drawImage(back.getImage(),0,0,this.getWidth(),this.getHeight(),null);
		
		before=img4.getImage();
		after=before.getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH);
		top1=new ImageIcon(after);
		before=img5.getImage();
		after=before.getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH);
		top2=new ImageIcon(after);
		before=img6.getImage();
		after=before.getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH);
		top3=new ImageIcon(after);
		
		before=img7.getImage();
		after=before.getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH);
		bottom1=new ImageIcon(after);
		before=img8.getImage();
		after=before.getScaledInstance(180, 150, java.awt.Image.SCALE_SMOOTH);
		bottom2=new ImageIcon(after);
		before=img9.getImage();
		after=before.getScaledInstance(180, 150, java.awt.Image.SCALE_SMOOTH);
		bottom3=new ImageIcon(after);
		
		before=img1.getImage();
		after=before.getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH);
		shoe1=new ImageIcon(after);
		before=img2.getImage();
		after=before.getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH);
		shoe2=new ImageIcon(after);
		before=img3.getImage();
		after=before.getScaledInstance(200, 150, java.awt.Image.SCALE_SMOOTH);
		shoe3=new ImageIcon(after);
		
		before=img10.getImage();
		after=before.getScaledInstance(180, 180, java.awt.Image.SCALE_SMOOTH);
		check=new ImageIcon(after);
		
		g2.drawImage(top1.getImage(),row1,col1,this);
		g2.drawImage(top2.getImage(), row2,col1,this);
		g2.drawImage(top3.getImage(),row3,col1,this);

		g2.drawImage(bottom1.getImage(),row1,col2,this);
		g2.drawImage(bottom2.getImage(), row2,col2,this);
		g2.drawImage(bottom3.getImage(),row3,col2,this);
		
		g2.drawImage(shoe1.getImage(),row1,col3,this);
		g2.drawImage(shoe2.getImage(), row2,col3,this);
		g2.drawImage(shoe3.getImage(),row3,col3,this);
		
		g2.drawImage(check.getImage(), check_row1,check_col1,this);
		g2.drawImage(check.getImage(), check_row2,check_col2,this);
		g2.drawImage(check.getImage(), check_row3,check_col3,this);
		
		Font font1 = new Font("Eras Bold ITC", Font.PLAIN, 30);//font for price of item
		Font font2=new Font("Eras Bold ITC",Font.PLAIN,45);//font for total hamburgers
		Font font3=new Font("ÀÚ¿¬Block",Font.BOLD,80);//font for start button
		g2.setFont(font1);
		g2.setColor(Color.black);
		g2.drawString("price "+price,50,200);//string for price of items
		g2.setFont(font2);
		g2.drawString("total: "+total,50,250);//string for total hamburgers
		g2.setFont(font3);
		g2.setColor(Color.white);
		g2.drawString("START",50, 550);
		g2.setColor(Color.orange);
		g2.drawString("START",startW,startH);
		
		 
	}
	@Override
	public void mouseClicked(MouseEvent e) {

	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		int xx=e.getX();
		int yy=e.getY();
				
		boolean topflag=false;
		boolean bottomflag=false;
		boolean shoeflag=false;
		
		boolean top1=((xx>=row1-100&&xx<=row1+100)&&(yy>=col1-100&&yy<=col1+100));
		boolean top2=((xx>=row2-100&&xx<=row2+100)&&(yy>=col1-100&&yy<=col1+100));
		boolean top3=((xx>=row3-100&&xx<=row3+100)&&(yy>=col1-100&&yy<=col1+100));
		
		boolean bottom1=((xx>=row1-100&&xx<=row1+100)&&(yy>=col2-100&&yy<=col2+100));
		boolean bottom2=((xx>=row2-100&&xx<=row2+100)&&(yy>=col2-100&&yy<=col2+100));
		boolean bottom3=((xx>=row3-100&&xx<=row3+100)&&(yy>=col2-100&&yy<=col2+100));
		
		boolean shoe1=((xx>=row1-100&&xx<=row1+100)&&(yy>=col3-100&&yy<=col3+100));
		boolean shoe2=((xx>=row2-100&&xx<=row2+100)&&(yy>=col3-100&&yy<=col3+100));
		boolean shoe3=((xx>=row3-100&&xx<=row3+100)&&(yy>=col3-100&&yy<=col3+100));
		
		boolean start=((xx>=30&&xx<=350)&&(yy>=450&&yy<=600));
		
		
		if(topflag)
		{
			cnt_top=0;
			check_col1=col1-5;
			if(top1){
				cnt_top=10;
				check_row1=row1;
			}
			if(top2){
				cnt_top=15;
				check_row1=row2;
			}
			if(top3){
				cnt_top=20;
				check_row1=row3;
			}
				
		}
		else{
			check_col1=col1-5;
			if(top1){
				cnt_top=10;
				check_row1=row1;
			}
			if(top2){
				cnt_top=15;
				check_row1=row2;
			}
			if(top3){
				cnt_top=20;
				check_row1=row3;
			}
		}
		
		if(bottomflag){
			cnt_bot=0;
			check_col2=col2-5;
			
			if(bottom1){
				cnt_bot=20;
				check_row2=row1;
			}
			if(bottom2){
				cnt_bot=15;
				check_row2=row2;
			}
			if(bottom3){
				cnt_bot=10;
				check_row2=row3;
			}
			
		}
		
		else{
			bottomflag=true;
			check_col2=col2-5;
			
			if(bottom1){
				cnt_bot=20;
				check_row2=row1;
			}
			if(bottom2){
				cnt_bot=15;
				check_row2=row2;
			}
			if(bottom3){
				cnt_bot=10;
				check_row2=row3;
			}
		}
		
		if(shoeflag){
			cnt_shoe=0;
			check_col3=col3-5;
			
			if(shoe1){
				cnt_shoe=10;
				check_row3=row1;
			}
			if(shoe2){
				cnt_shoe=15;
				check_row3=row2;
			}
			if(shoe3){
				cnt_shoe=5;
				check_row3=row3;
			}
		}
		else{
			shoeflag=true;
			check_col3=col3-5;
			
			if(shoe1){
				cnt_shoe=10;
				check_row3=row1;
			}
			if(shoe2){
				cnt_shoe=15;
				check_row3=row2;
			}
			if(shoe3){
				cnt_shoe=5;
				check_row3=row3;
			}
		}
		
		if(start){
			rootFrame.moveGameScene(total);
		}
		
		total=cnt_top+cnt_bot+cnt_shoe;
		repaint();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseMoved(MouseEvent e) {
		// TODO Auto-generated method stub
		int xx=e.getX();
		int yy=e.getY();
		boolean top1=((xx>=row1-100&&xx<=row1+100)&&(yy>=col1-100&&yy<=col1+100));
		boolean top2=((xx>=row2-100&&xx<=row2+100)&&(yy>=col1-100&&yy<=col1+100));
		boolean top3=((xx>=row3-100&&xx<=row3+100)&&(yy>=col1-100&&yy<=col1+100));
		
		boolean bottom1=((xx>=row1-100&&xx<=row1+100)&&(yy>=col2-100&&yy<=col2+100));
		boolean bottom2=((xx>=row2-100&&xx<=row2+100)&&(yy>=col2-100&&yy<=col2+100));
		boolean bottom3=((xx>=row3-100&&xx<=row3+100)&&(yy>=col2-100&&yy<=col2+100));
		
		boolean shoe1=((xx>=row1-100&&xx<=row1+100)&&(yy>=col3-100&&yy<=col3+100));
		boolean shoe2=((xx>=row2-100&&xx<=row2+100)&&(yy>=col3-100&&yy<=col3+100));
		boolean shoe3=((xx>=row3-100&&xx<=row3+100)&&(yy>=col3-100&&yy<=col3+100));
		
		boolean start=((xx>=30&&xx<=350)&&(yy>=450&&yy<=600));//check for the start
		
		if(top1)
			price=10;
		if(top2)
			price=15;
		if(top3)
			price=20;
		
		if(bottom1)
			price=15;
		if(bottom2)
			price=20;
		if(bottom3)
			price=25;
		
		if(shoe1)
			price=10;
		if(shoe2)
			price=15;
		if(shoe3)
			price=5;
			
		//if the mouse is on the 'start', 'start' color is change to orange 
		if(start){
			startW=50;
			startH=550;
		}
		else{
			startW=-300;
			startH=-300;
		}
			
		repaint();
	}
	
}
