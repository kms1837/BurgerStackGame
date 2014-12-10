import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JPanel;


public class GameScene extends JPanel implements KeyListener,Runnable
{
	//UI���� ���
	private static final int maxUserBurgerNumber 		= 11;	//������ ������ִ� �ִ� ��������
	
	private static final int userBurgerBottomSide 		= 320; 	//������ ���� ������ �Ʒ��� ����
	private static final int userBurgerWidth			= 400;	//������ ���� ������ ��� ����
	private static final int userBurgerHeight		 	= 30;	//������ ���� ������ ��� ����
	
	private static final int exampleBurgerBottomSide 	= 160; 	//���� ������ �Ʒ��� ����
	private static final int exampleBurgerWidth			= 200;	//���� ������ ��� ����
	private static final int exampleBurgerHeight		= 10;	//���� ������ ��� ����
	
	private static final int burgerManuLeftSide		= 180; //�ܹ��� �޴� ���ʿ���
	private static final int burgerMenuTopSide 		= 540; //�ܹ��� �޴� ���ʿ���
	private static final int burgerMenuItemGap 		= 130; //�ܹ��� �޴� ������ ����
	private static final int burgerMenuItemWidth	= 120; //�ܹ��� �޴� ������ ����
	private static final int burgerMenuItemHeight	= 120; //�ܹ��� �޴� ������ ����
	
	private static final int timerGaugeLeftSide = 280; //Ÿ�̸� ������ ���ʿ���
	private static final int timerGaugeTopSide 	= 30;  //Ÿ�̸� ������ ���ʿ���
	private static final int timerGaugeWidth 	= 600; //Ÿ�̸� ������ ����
	private static final int timerGaugeHeight 	= 30;  //Ÿ�̸� ������ ����
	
	private ImageIcon background;
	
	//���� ����
	private int selectedBurgerMenuNumber; //������ ���� �޴�
	private int maxBurgerMenuNumber;	  //���� ���� ��� ����(�ּ� 2)
	
	private int score; 		 //��������
	private int targetScore; //���� ��ǥ����
	
	private int gameTimer; //Ÿ�̸�
	private int setTimer;  //������ �ð�
	
	/*
	 ���ε����� ���
	 
	 1 - �Ʒ� ��
	 2 - �� ��
	 3 - �����Ƽ
	 4 - ġ��
	 5 - �����
	 6 - �丶��
	 
	*/
	
	private Color burgerColor[] = {Color.WHITE, Color.pink, Color.orange, Color.DARK_GRAY, Color.YELLOW, Color.GREEN, Color.RED, Color.BLUE, Color.CYAN};
	private String burgerMenuItemImagePath[] = {"", "resource/menuItem/item1.png", "resource/menuItem/item2.png", "resource/menuItem/item3.png", "resource/menuItem/item4.png", "resource/menuItem/item5.png", "resource/menuItem/item6.png", "resource/menuItem/item7.png"};
	private String burgerLngredientName[] =   {"", "burgerbread_top", "burgerbread_bottom", "burgermeat", "burgercheese", "burgerlattuga", "burgertomato"};
	
	private burgerIngredient userBurger[];
	private int userBurgerCount;
	
	private int exampleBurger[];
	private int maxExampleBurgerNumber;
	
	private CustomMouse mouse;
	private MainManagment rootFrame;
	
	private Thread gameThread;
	
	private boolean flag;

	/*method*/
	public GameScene(CustomMouse inputMouseListener, MainManagment inputRootFrame, int inputTargetScore, int inputTimer)
	{
		
		background = new ImageIcon("resource/background3.png");
		
		mouse 	  = inputMouseListener;
		rootFrame = inputRootFrame;
		
		gameTimer = inputTimer * 60;
		setTimer  = inputTimer * 60;

		gameThread = new Thread(this);
		gameThread.start();
		//�ٸ� �����尣 ���ٰ� �ָŸ�ȣ�����°� ������ Ŭ���������� �����带 �����ϴ°��� ����
		
		selectedBurgerMenuNumber = 1;
		maxBurgerMenuNumber 	 = 7;
		
		userBurger = new burgerIngredient[maxUserBurgerNumber];
		userBurgerCount = 0;
		
		score = 0;
		
		flag = false;
		
		targetScore = inputTargetScore;
		
		createExampleBurger();
		
		this.setLayout(new GridBagLayout());
	}//construct
	
	//private int bug = 0 ;
	//TO-DO ������ �����ȵǴ� ���� ����
	
	public void clearExitScene()
	{
		if(gameThread!=null) {
//			gameThread.interrupt(); 
//			System.gc();
			flag = true;
		}
	}//method clearExitScene - ���� ���� ������ �����ش�.
	
	@Override
	public void run() 
	{	
		try{
			while(!flag){
				gameTimer--;
				
				mouseBurgerMenuEvent();
				
				repaint();
				revalidate();
				//1�ʿ� �ѹ� - 1000
				//1�ʿ� 60�� - 17		
				
				if(gameTimer<=0) {
					clearExitScene();
					rootFrame.moveResultScene(false, score, gameTimer);
				}
				
				gameThread.sleep(17);
			}
		}catch(InterruptedException ex){
			
		} finally {
			System.out.println("gameScene Thread dead");
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
			case KeyEvent.VK_ENTER : {
				clearExitScene();
			}
		}
		//revalidate();
		//repaint();
	}//method keyPressed - ���Ÿ޴��� ���� Ű�̺�Ʈ ó��
	
	private void mouseBurgerMenuEvent()
	{
		int clickPositionX = mouse.getMouseClickPositionX();
		int clickPositionY = mouse.getMouseClickPositionY();
		int positionX = mouse.getMousePositionX();
		int positionY = mouse.getMousePositionY();
		
		for(int i=1; i<=maxBurgerMenuNumber; i++){
			// && positionY
			if(positionX > burgerManuLeftSide + ((i-1)*burgerMenuItemGap) &&
			   positionX < burgerManuLeftSide + ((i-1)*burgerMenuItemGap)+burgerMenuItemWidth &&
			   positionY > burgerMenuTopSide &&
			   positionY < burgerMenuTopSide+burgerMenuItemHeight)
			{
			   selectedBurgerMenuNumber = i;
			   //revalidate();
			   //repaint();
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
		
		exampleBurger[0] = 2;
		exampleBurger[maxExampleBurgerNumber-1] = 1;
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
		
		if(userBurgerCount != maxExampleBurgerNumber || userBurger[0].ingredientNumber != 2 || userBurger[maxExampleBurgerNumber-1].ingredientNumber != 1){
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
		
		if(score >= targetScore) {
			clearExitScene();
			rootFrame.moveResultScene(true, score, gameTimer);
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
					exampleBurgerWidth + 60,
					260);
		
		if(maxExampleBurgerNumber > 0) {
			for(int i=0; i<maxExampleBurgerNumber; i++){
				//System.out.println("i�� ���� ���빰 : " + exampleBurger[i]);
				//int tempColor = exampleBurger[i]; 
				//g.setColor(burgerColor[tempColor]);
				
				ImageIcon burgerLngredientImage = new ImageIcon("resource/exampleburger/" + burgerLngredientName[exampleBurger[i]] + ".png");
				g.drawImage(burgerLngredientImage.getImage(), exampleBurgerPositionX, exampleBurgerBottomSide - (i*exampleBurgerHeight),this);
				
				/*
				g.fillRect(	exampleBurgerPositionX,
							exampleBurgerBottomSide - (i*exampleBurgerHeight),
							exampleBurgerWidth,
							exampleBurgerHeight);
				*/
				
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
				
				ingredientPositionY = ingredientPositionY + 10;
				
				if(ingredientPositionY + 10 > targetPositionY){
					ingredientPositionY = targetPositionY;
				}else{
//					repaint();
//					revalidate();
				}//���� �ִϸ��̼�
				
				userBurger[i].ingredientPositionY = ingredientPositionY;
			
				//g.setColor(burgerColor[ingredientNumber]);
				ImageIcon burgerLngredientImage = new ImageIcon("resource/" + burgerLngredientName[ingredientNumber] + ".png");
				g.drawImage(burgerLngredientImage.getImage(), userBurgerPositionX, ingredientPositionY,this);
				//g.fillRect(userBurgerPositionX, ingredientPositionY, userBurgerWidth, userBurgerHeight);
				
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
				
				//ImageIcon menuItemImage = new ImageIcon(burgerMenuItemImagePath[i] + ".png");
				//g.drawImage(menuItemImage.getImage(), burgerManuLeftSide + ((i-1)*burgerMenuItemGap), burgerMenuTopSide,this);
				g.fillRect(burgerManuLeftSide + ((i-1)*burgerMenuItemGap), burgerMenuTopSide, burgerMenuItemWidth, burgerMenuItemHeight);
			}
			
			ImageIcon menuItemImage = new ImageIcon(burgerMenuItemImagePath[i]); 
			//g.drawImage(menuItemImage.getImage(), this.getWidth()/2, burgerMenuTopSide, this);
			g.drawImage(menuItemImage.getImage(), burgerManuLeftSide + ((i-1)*burgerMenuItemGap), burgerMenuTopSide, this);
			
		}
	}//method displayMenu - �Ʒ��� �ܹ��� �޴��� ������ش�.
	
	private void displayUI(Graphics g)
	{
		Font font1 = new Font("���� ���", Font.PLAIN, 30);
		
		g.setFont(font1);
		g.setColor(Color.black);
		g.drawString("��ǥ : ", this.getWidth() - 200 , 50);//string for price of items
		g.drawString(""+targetScore, this.getWidth() - 80 , 50);
		g.drawString("�����ܹ��� ���� : ", this.getWidth() - 340 , 100);
		g.drawString(""+score, this.getWidth() - 80 , 100);
		int sec  = gameTimer % 60;
	    int min  = gameTimer / 60 % 60;
	    
		g.drawString("�����ð� " + min + " : " + sec , this.getWidth()/2 - 360 , 100);//string for price of items
		
		float test = ((float)gameTimer/setTimer) * 100;
		
		g.setColor(Color.red);
		g.fillRect(timerGaugeLeftSide, timerGaugeTopSide, (timerGaugeWidth*(int)test)/100, timerGaugeHeight);
		g.setColor(Color.black);
		g.drawRect(timerGaugeLeftSide, timerGaugeTopSide, timerGaugeWidth, timerGaugeHeight);
	}
	
	private void displayBackUI(Graphics g)
	{
		g.drawImage(background.getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
	}
	
	public void update(Graphics g)
	{
		displayBackUI(g);
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

