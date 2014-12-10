import java.awt.event.*;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.*;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.net.URL;
import java.net.URLConnection;

import javazoom.jl.player.*;

public class MainManagment extends JFrame {
	
	private MainManagment rootFrame;
	private MainScene mainScene; 
	private GameScene gameScene;
	private ClothingScene clothingScene;
	private ResultScene resultScene;
	
	SoundPlayer backgroundSound;
	
	private CustomMouse mouseEventListener;
	
	public void clearFrame()
	{
		
		if(mainScene != null) {
			remove(mainScene);
		}
		
		if(gameScene != null) {
			removeKeyListener(gameScene);
			remove(gameScene);
		}
		
		if(clothingScene != null) {
			remove(clothingScene);
		}
		
		if(resultScene != null) {
			remove(resultScene);
		}
		
		repaint();
	}
	
	public void moveGameScene(int targetScore)
	{
		clearFrame();
		
		gameScene = new GameScene(mouseEventListener, rootFrame, targetScore, 60);
		setSize(1280, 720);
		addKeyListener(gameScene);
		add(gameScene);
		
		playBackgroundSound("resource/sound/main_back.mp3");

		System.out.println("∞‘¿”æ¿ ¿Ãµø");
		
		revalidate();
	}
	
	public void moveMainScene()
	{
		clearFrame();
		
		mainScene = new MainScene(1280, 720, mouseEventListener, rootFrame);
		setSize(1200, 720);
		add(mainScene);
		
		playBackgroundSound("resource/sound/main_back.mp3");
		
		System.out.println("∏ﬁ¿Œæ¿ ¿Ãµø");
		
		revalidate();
	}
	
	public void moveClothingScene()
	{
		clearFrame();
		
		clothingScene = new ClothingScene(mouseEventListener, rootFrame);
		setSize(1200, 720);
		add(clothingScene);
		
		playBackgroundSound("resource/sound/clothing_back.mp3");
		
		System.out.println("ø æ¿ ¿Ãµø");

		revalidate();
	}
	
	public void moveResultScene(boolean inputResult, int inputBurgerCount, int inputTimer)
	{
		clearFrame();
	
		resultScene = new ResultScene(inputResult, inputBurgerCount, inputTimer);
		setSize(1280, 720);
		//addKeyListener(gameScene);
		add(resultScene);
		
		if(inputResult) playBackgroundSound("resource/sound/mission_complete.mp3");
		else			playBackgroundSound("resource/sound/mission_fail.mp3");
		

		revalidate();
	}
	
	public void playEffectSound(String fileName)
	{
		SoundPlayer effectSound = new SoundPlayer(fileName);
	}
	
	public void playBackgroundSound(String fileName)
    {
        if(backgroundSound != null){
    		backgroundSound.stop();
    		backgroundSound = null;
    	}
        
    	backgroundSound = new SoundPlayer(fileName);
    	
        /*
        AudioInputStream sound = AudioSystem.getAudioInputStream(new File(fileName));
        Clip clip = AudioSystem.getClip();
        clip.open(sound);
        clip.start();
        */
    }


	public MainManagment()
	{	
		rootFrame = this;
		backgroundSound = null;
		
		setTitle("BURGERMON");
		setSize(640, 800);
		setResizable(false);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		mouseEventListener = new CustomMouse();
		addMouseListener(mouseEventListener);
	    addMouseMotionListener(mouseEventListener);
	    
	    moveMainScene();
	}
	
	public static void main(String[] args)
	{
		new MainManagment();
	}
}
