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
	MainScene mainScene; 
	GameScene gameScene;
	ClothingScene clothingScene;
	
	//Player backgroundSound;
	SoundPlayer backgroundSound;
	
	private CustomMouse mouseEventListener;
	
	public void clearFrame()
	{
		
		if(mainScene!=null) {
			mainScene.clearExitScene();
			remove(mainScene);
		}
		
		if(gameScene!=null) {
			gameScene.clearExitScene();
			removeKeyListener(gameScene);
			remove(gameScene);
		}
		
		if(clothingScene!=null) {
			clothingScene.clearExitScene();
			remove(clothingScene);
		}
		
		mainScene = null;
		
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

		revalidate();
	}
	
	public void moveMainScene()
	{
		clearFrame();
		
		mainScene = new MainScene(1280, 720, mouseEventListener, rootFrame);
		setSize(1200, 720);
		add(mainScene);
		
		playBackgroundSound("resource/sound/main_back.mp3");
		
		revalidate();
	}
	
	public void moveClothingScene()
	{
		clearFrame();
		
		clothingScene = new ClothingScene(mouseEventListener, rootFrame);
		setSize(1200, 720);
		add(clothingScene);
		
		playBackgroundSound("resource/sound/clothing_back.mp3");

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
		
		setTitle("햄버거 게임 ^_^");
		setLayout(new GridLayout(1,2));//dualmode를 위한 layout
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
