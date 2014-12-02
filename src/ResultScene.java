import javax.swing.*;
import java.awt.event.*;
import java.awt.*;


public class ResultScene extends JFrame {
	Container contentPane;
	ResultScene(){
		setTitle("BURGERMON");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		creatMenu();
		contentPane = getContentPane();
		MyPanel panel = new MyPanel(); 
		contentPane.add(panel,BorderLayout.CENTER);
		setSize(300, 400);
		setVisible(true);
	}
	
	void creatMenu(){
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



class MyPanel extends JPanel {
	ImageIcon icon = new ImageIcon("images/failback.png");//배경화면 로딩
	
	Image img = icon.getImage();//이미지 객체
	
	public void paintComponent(Graphics g){
		super.paintComponent(g);
		
		g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
	
	}
	ImageIcon retry = new ImageIcon("images/retry.png");
	
	JButton btn = new JButton("images/retry.png");
	

}
//public static void main(String [] args){
	//new ResultScene();
	
//	}
}
