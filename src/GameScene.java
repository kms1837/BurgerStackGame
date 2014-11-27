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
	//UI관련 변수들
	private static final int maxUserBurgerNumber 		= 14;	//유저가 만들수있는 최대 버거의층
	private static final int userBurgerBottomSide 		= 460; 	//유저가 만든 버거의 아래쪽 여백
	private static final int userBurgerWidth			= 400;	//유저가 만든 버거의 재료 넓이
	private static final int userBurgerHeight			= 30;	//유저가 만든 버거의 재료 높이
	private static final int exampleBurgerBottomSide 	= 160; 	//샘플 버거의 아래쪽 여백
	private static final int exampleBurgerWidth			= 200;	//샘플 버거의 재료 넓이
	private static final int exampleBurgerHeight		= 10;	//샘플 버거의 재료 높이
	
	private static final int burgerManuLeftSide		= 130; //햄버거 메뉴 왼쪽여백
	private static final int burgerMenuTopSide 		= 540; //햄버거 메뉴 위쪽여백
	private static final int burgerMenuItemGap 		= 170; //햄버거 메뉴 아이템 여백
	private static final int burgerMenuItemWidth	= 150; //햄버거 메뉴 아이템 넓이
	private static final int burgerMenuItemHeight	= 150; //햄버거 메뉴 아이템 높이
	
	private int selectedBurgerMenuNumber; //선택한 버거 메뉴
	private int maxBurgerMenuNumber;	  //현재 버거 재료 종류(최소 2)
	
	
	private int score; 		 //현재점수
	private int targetScore; //게임 목표점수
	
	//로직관련 변수들
	
	/*
	 토핑데이터 값 목록
	 
	 1 - 아래 빵
	 2 - 위 빵
	 3 - 고기패티
	 4 - 양상추
	 5 - 토마토
	 6 - 치즈
	 
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
	}//method clearExitScene - 현재 씬을 말끔히 지워준다.
	
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
	}//method keyPressed - 버거메뉴에 대한 키이벤트 처리
	
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
				System.out.println("클릭: " + selectedBurgerMenuNumber);
				if(selectedBurgerMenuNumber == maxBurgerMenuNumber) sendBurger();
				else												makeBurger();
			}
		}
		
		clickPositionX = -1;
		clickPositionY = -1;
	}//method mouseBurgerMenuEvent - 버거 메뉴의 마우스이벤트 처리

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
			//메뉴의 마지막은 제출메뉴가 되기때문에 -1을 해줌
		}
	}//method createExampleBurger - 샘플버거를 만든다.
	
	private void makeBurger()
	{
		if(userBurgerCount < maxUserBurgerNumber){
			//System.out.println("버거생성");
			
			if(userBurger[userBurgerCount]==null) userBurger[userBurgerCount] = new burgerIngredient();
			
			userBurger[userBurgerCount].createBurgerIngredient(selectedBurgerMenuNumber);
			userBurgerCount++;
			
			rootFrame.playEffectSound("resource/sound/burger_stack.mp3");
			
		}else{
			System.out.println("더이상 버거 쌓기 불가");
		}
	}//method makeBurger - 버거를 쌓는다.
	
	private void sendBurger()
	{
		boolean solutionCheck = true;
		
		if(userBurgerCount != maxExampleBurgerNumber || userBurger[0].ingredientNumber != 1 || userBurger[maxExampleBurgerNumber-1].ingredientNumber != 2){
			System.out.println("실패");
			rootFrame.playEffectSound("resource/sound/not_correct.mp3");
		}else{
			for(int i=0; i<userBurgerCount; i++) {
				if(userBurger[i].ingredientNumber != exampleBurger[i]){
					solutionCheck = false;
					break;
				}
			}
			
			if(solutionCheck) {
				System.out.println("정답");
				score += 1;
				rootFrame.playEffectSound("resource/sound/correct.mp3");
			}else{
				System.out.println("실패");
				rootFrame.playEffectSound("resource/sound/not_correct.mp3");
			}
			//검사
		}
		
		userBurgerCount = 0;
		
		createExampleBurger();
		
	}//method sendBurger - 현재 만든 버거를 제출하고 정답을 확인한다.
	
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
				//System.out.println("i층 버거 내용물 : " + exampleBurger[i]);
				int tempColor = exampleBurger[i]; 
				g.setColor(burgerColor[tempColor]);

				g.fillRect(	exampleBurgerPositionX,
							exampleBurgerBottomSide - (i*exampleBurgerHeight),
							exampleBurgerWidth,
							exampleBurgerHeight);
				
			}
		}
	}//method displayExampleBurger - 만들어아할 샘플 버거를 그려준다.
	
	private void displayUserBurger(Graphics g)
	{
		int userBurgerPositionX;
		
		userBurgerPositionX = (this.getWidth()/2) - (userBurgerWidth/2);
		
		if(userBurgerCount > 0) {
			for(int i=0; i<userBurgerCount; i++){
				//System.out.println("i층 버거 내용물 : " + exampleBurger[i]);
				int ingredientNumber = userBurger[i].ingredientNumber;			//재료넘버
				int ingredientPositionX = userBurger[i].ingredientPositionX;
				int ingredientPositionY = userBurger[i].ingredientPositionY;
				int targetPositionY = userBurgerBottomSide - (i*userBurgerHeight); //최종위치
				
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
	}//method displayUserBurger - 유저가만든 버거를 화면에 그려준다.
	
	private void displayMenu(Graphics g)
	{
		for(int i=1; i<=maxBurgerMenuNumber; i++) {
			g.setColor(Color.BLACK);
			g.drawRect(burgerManuLeftSide + ((i-1)*burgerMenuItemGap), burgerMenuTopSide, burgerMenuItemWidth, burgerMenuItemHeight);
			//drawRect(사각형선만 그림) - 시작x, 시작y, width, height
			
			if(i==selectedBurgerMenuNumber) {
				if(i==maxBurgerMenuNumber)	g.setColor(Color.WHITE); //제출메뉴
				else						g.setColor(burgerColor[selectedBurgerMenuNumber]);
				
				g.fillRect(burgerManuLeftSide + ((i-1)*burgerMenuItemGap), burgerMenuTopSide, burgerMenuItemWidth, burgerMenuItemHeight);
			}
		}
	}//method displayMenu - 아래의 햄버거 메뉴를 출력해준다.
	
	private void displayUI(Graphics g)
	{
		Font font1 = new Font("Eras Bold ITC", Font.PLAIN, 30);
		Font font2=new Font("Eras Bold ITC",Font.PLAIN,45);
		Font font3=new Font("자연Block",Font.BOLD,80);
		g.setFont(font1);
		g.setColor(Color.black);
		g.drawString("목표 : ", this.getWidth() - 200 , 50);//string for price of items
		g.drawString(""+targetScore, this.getWidth() - 80 , 50);
		g.drawString("만든햄버거 갯수 : ", this.getWidth() - 340 , 100);
		g.drawString(""+score, this.getWidth() - 80 , 100);
	}
	
	public void update(Graphics g)
	{
		displayExampleBurger(g);
		displayUserBurger(g);
		displayMenu(g);
		displayUI(g);
	}//method update - 리페인트시 화면을 그려준다.
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); 
		update(g);
	}//paint component

}

