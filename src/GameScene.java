import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.JPanel;


public class GameScene extends JPanel implements KeyListener,Runnable
{
	//UI���� ������
	private static final int maxUserBurgerNumber 		= 14;	//������ ������ִ� �ִ� ��������
	private static final int userBurgerBottomSide 		= 460; 	//������ ���� ������ �Ʒ��� ����
	private static final int userBurgerWidth			= 400;	//������ ���� ������ ��� ����
	private static final int userBurgerHeight			= 30;	//������ ���� ������ ��� ����
	private static final int exampleBurgerBottomSide 	= 160; 	//���� ������ �Ʒ��� ����
	private static final int exampleBurgerWidth			= 200;	//���� ������ ��� ����
	private static final int exampleBurgerHeight		= 10;	//���� ������ ��� ����
	
	private static final int burgerManuLeftSide		= 130; //�ܹ��� �޴� ���ʿ���
	private static final int burgerMenuTopSide 		= 540; //�ܹ��� �޴� ���ʿ���
	private static final int burgerMenuItemGap 		= 170; //�ܹ��� �޴� ������ ����
	private static final int burgerMenuItemWidth	= 150; //�ܹ��� �޴� ������ ����
	private static final int burgerMenuItemHeight	= 150; //�ܹ��� �޴� ������ ����
	
	private int selectedBurgerMenuNumber; //������ ���� �޴�
	private int maxBurgerMenuNumber;	  //���� ���� ��� ����(�ּ� 2)
	
	
	private int score; 		 //��������
	private int targetScore; //���� ��ǥ����
	
	//�������� ������
	
	/*
	 ���ε����� �� ���
	 
	 1 - �Ʒ� ��
	 2 - �� ��
	 3 - �����Ƽ
	 4 - �����
	 5 - �丶��
	 6 - ġ��
	 
	 */
	
	private Color burgerColor[] = {Color.WHITE, Color.ORANGE, Color.YELLOW, Color.DARK_GRAY, Color.GREEN, Color.RED, Color.BLUE, Color.CYAN};
	
	private burgerIngredient userBurger[];
	private int userBurgerCount;
	
	private int exampleBurger[];
	private int maxExampleBurgerNumber;
	
	private CustomMouse mouse;
	private MainManagment rootFrame;
	
	private Thread gameThread;
	
	
	/*method*/
	public GameScene(CustomMouse inputMouseListener, MainManagment inputRootFrame, int inputTargetScore)
	{
		mouse 	  = inputMouseListener;
		rootFrame = inputRootFrame;

		gameThread = new Thread(this);
		gameThread.start();
		
		selectedBurgerMenuNumber = 1;
		maxBurgerMenuNumber 	 = 6;
		
		userBurger = new burgerIngredient[maxUserBurgerNumber];
		userBurgerCount = 0;
		
		score = 0;
		
		targetScore = inputTargetScore;
		
		createExampleBurger();
		
		this.setLayout(new GridBagLayout());
	}//construct
	
	public void clearExitScene()
	{
		if(gameThread!=null) {
			gameThread.interrupt();
			gameThread = null;
			System.gc();
		}
	}//method clearExitScene - ���� ���� ������ �����ش�.
	
	@Override
	public void run() 
	{
		try{
			while(!gameThread.currentThread().isInterrupted()){
				mouseBurgerMenuEvent();				
				gameThread.sleep(0);
			}
		}catch(InterruptedException ex){
			
		}
	}//thread

	@Override
	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		// TODO Auto-generated method stub
		switch(e.getKeyCode()) {
			case KeyEvent.VK_LEFT : {
				if(selectedBurgerMenuNumber <= 1)	selectedBurgerMenuNumber = maxBurgerMenuNumber;
				else								selectedBurgerMenuNumber--;
				break;
			}
			case KeyEvent.VK_RIGHT : {
				if(selectedBurgerMenuNumber >= maxBurgerMenuNumber)	selectedBurgerMenuNumber = 1;
				else						 				 		selectedBurgerMenuNumber++;
				break;
			}
			case KeyEvent.VK_SPACE :{
				if(selectedBurgerMenuNumber == maxBurgerMenuNumber) sendBurger();
				else												makeBurger();
				break;
			}
			case KeyEvent.VK_UP : {
				break;
			}
			case KeyEvent.VK_DOWN : {
				break;
			}
		}
		revalidate();
		repaint();
	}//method keyPressed - ���Ÿ޴��� ���� Ű�̺�Ʈ ó��
	
	private void mouseBurgerMenuEvent()
	{
		int clickPositionX = mouse.getMouseClickPositionX();
		int clickPositionY = mouse.getMouseClickPositionY();
		int positionX = mouse.getMousePositionX();
		int positionY = mouse.getMousePositionY();
		
		for(int i=1; i<=5; i++){
			// && positionY
			if(positionX > burgerManuLeftSide + ((i-1)*burgerMenuItemGap) &&
			   positionX < burgerManuLeftSide + ((i-1)*burgerMenuItemGap)+burgerMenuItemWidth &&
			   positionY > burgerMenuTopSide &&
			   positionY < burgerMenuTopSide+burgerMenuItemHeight)
			{
			   selectedBurgerMenuNumber = i;
			   revalidate();
			   repaint();
			}
			
			if(clickPositionX > burgerManuLeftSide + ((i-1)*burgerMenuItemGap) &&
			   clickPositionX < burgerManuLeftSide + ((i-1)*burgerMenuItemGap)+burgerMenuItemWidth &&
			   clickPositionY > burgerMenuTopSide &&
			   clickPositionY < burgerMenuTopSide+burgerMenuItemHeight)
			{
				System.out.println("Ŭ��: " + selectedBurgerMenuNumber);
				if(selectedBurgerMenuNumber == maxBurgerMenuNumber) sendBurger();
				else												makeBurger();
			}
		}
		
		clickPositionX = -1;
		clickPositionY = -1;
	}//method mouseBurgerMenuEvent - ���� �޴��� ���콺�̺�Ʈ ó��

	@Override
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	
	private void createExampleBurger()
	{
		Random random = new Random();
		
		maxExampleBurgerNumber = 3 + random.nextInt(10); 
		
		exampleBurger = new int[maxExampleBurgerNumber];
		
		exampleBurger[0] = 1;
		exampleBurger[maxExampleBurgerNumber-1] = 2;
		for(int i=1; i<=maxExampleBurgerNumber-2; i++){
			exampleBurger[i] = 2 + random.nextInt(maxBurgerMenuNumber-2);
			//�޴��� �������� ����޴��� �Ǳ⶧���� -1�� ����
		}
	}//method createExampleBurger - ���ù��Ÿ� �����.
	
	private void makeBurger()
	{
		if(userBurgerCount < maxUserBurgerNumber){
			//System.out.println("���Ż���");
			
			if(userBurger[userBurgerCount]==null) userBurger[userBurgerCount] = new burgerIngredient();
			
			userBurger[userBurgerCount].createBurgerIngredient(selectedBurgerMenuNumber);
			userBurgerCount++;
			
			rootFrame.playEffectSound("resource/sound/burger_stack.mp3");
			
		}else{
			System.out.println("���̻� ���� �ױ� �Ұ�");
		}
	}//method makeBurger - ���Ÿ� �״´�.
	
	private void sendBurger()
	{
		boolean solutionCheck = true;
		
		if(userBurgerCount != maxExampleBurgerNumber || userBurger[0].ingredientNumber != 1 || userBurger[maxExampleBurgerNumber-1].ingredientNumber != 2){
			System.out.println("����");
			rootFrame.playEffectSound("resource/sound/not_correct.mp3");
		}else{
			for(int i=0; i<userBurgerCount; i++) {
				if(userBurger[i].ingredientNumber != exampleBurger[i]){
					solutionCheck = false;
					break;
				}
			}
			
			if(solutionCheck) {
				System.out.println("����");
				score += 1;
				rootFrame.playEffectSound("resource/sound/correct.mp3");
			}else{
				System.out.println("����");
				rootFrame.playEffectSound("resource/sound/not_correct.mp3");
			}
			//�˻�
		}
		
		userBurgerCount = 0;
		
		createExampleBurger();
		
	}//method sendBurger - ���� ���� ���Ÿ� �����ϰ� ������ Ȯ���Ѵ�.
	
	private void displayExampleBurger(Graphics g)
	{
		int exampleBurgerPositionX;
		exampleBurgerPositionX = (this.getWidth()/10) - (exampleBurgerWidth/2);
		
		g.setColor(Color.BLACK);
		
		g.drawRect(	exampleBurgerPositionX-20,
					10,
					exampleBurgerWidth + 40,
					170);
		
		if(maxExampleBurgerNumber > 0) {
			for(int i=0; i<maxExampleBurgerNumber; i++){
				//System.out.println("i�� ���� ���빰 : " + exampleBurger[i]);
				int tempColor = exampleBurger[i]; 
				g.setColor(burgerColor[tempColor]);

				g.fillRect(	exampleBurgerPositionX,
							exampleBurgerBottomSide - (i*exampleBurgerHeight),
							exampleBurgerWidth,
							exampleBurgerHeight);
				
			}
		}
	}//method displayExampleBurger - �������� ���� ���Ÿ� �׷��ش�.
	
	private void displayUserBurger(Graphics g)
	{
		int userBurgerPositionX;
		
		userBurgerPositionX = (this.getWidth()/2) - (userBurgerWidth/2);
		
		if(userBurgerCount > 0) {
			for(int i=0; i<userBurgerCount; i++){
				//System.out.println("i�� ���� ���빰 : " + exampleBurger[i]);
				int ingredientNumber = userBurger[i].ingredientNumber;			//���ѹ�
				int ingredientPositionX = userBurger[i].ingredientPositionX;
				int ingredientPositionY = userBurger[i].ingredientPositionY;
				int targetPositionY = userBurgerBottomSide - (i*userBurgerHeight); //������ġ
				
				g.setColor(burgerColor[ingredientNumber]);
				
				ingredientPositionY = ingredientPositionY + 10;
				
				if(ingredientPositionY + 10 > targetPositionY){
					ingredientPositionY = targetPositionY;
				}else{
					repaint();
					revalidate();
				}
				
				userBurger[i].ingredientPositionY = ingredientPositionY;
				
				//userBurgerBottomSide - (i*userBurgerHeight)
				
				g.fillRect(userBurgerPositionX, ingredientPositionY, userBurgerWidth, userBurgerHeight);
				
			}
		}
	}//method displayUserBurger - ���������� ���Ÿ� ȭ�鿡 �׷��ش�.
	
	private void displayMenu(Graphics g)
	{
		for(int i=1; i<=maxBurgerMenuNumber; i++) {
			g.setColor(Color.BLACK);
			g.drawRect(burgerManuLeftSide + ((i-1)*burgerMenuItemGap), burgerMenuTopSide, burgerMenuItemWidth, burgerMenuItemHeight);
			//drawRect(�簢������ �׸�) - ����x, ����y, width, height
			
			if(i==selectedBurgerMenuNumber) {
				if(i==maxBurgerMenuNumber)	g.setColor(Color.WHITE); //����޴�
				else						g.setColor(burgerColor[selectedBurgerMenuNumber]);
				
				g.fillRect(burgerManuLeftSide + ((i-1)*burgerMenuItemGap), burgerMenuTopSide, burgerMenuItemWidth, burgerMenuItemHeight);
			}
		}
	}//method displayMenu - �Ʒ��� �ܹ��� �޴��� ������ش�.
	
	private void displayUI(Graphics g)
	{
		Font font1 = new Font("Eras Bold ITC", Font.PLAIN, 30);
		Font font2=new Font("Eras Bold ITC",Font.PLAIN,45);
		Font font3=new Font("�ڿ�Block",Font.BOLD,80);
		g.setFont(font1);
		g.setColor(Color.black);
		g.drawString("��ǥ : ", this.getWidth() - 200 , 50);//string for price of items
		g.drawString(""+targetScore, this.getWidth() - 80 , 50);
		g.drawString("�����ܹ��� ���� : ", this.getWidth() - 340 , 100);
		g.drawString(""+score, this.getWidth() - 80 , 100);
	}
	
	public void update(Graphics g)
	{
		displayExampleBurger(g);
		displayUserBurger(g);
		displayMenu(g);
		displayUI(g);
	}//method update - ������Ʈ�� ȭ���� �׷��ش�.
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); 
		update(g);
	}//paint component

}

