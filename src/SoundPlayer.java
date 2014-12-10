import java.io.File;
import java.io.FileInputStream;

import javazoom.jl.player.Player;


public class SoundPlayer implements Runnable{
	
	private Thread playThread;
	private Player sound;
	private String filePath;
	
	public SoundPlayer(String inputFilePath)
	{
		filePath = inputFilePath;
		playThread = new Thread(this);
		playThread.start();
		//System.out.println("asd");
	}
	
	public void stop()
	{
		sound.close();
	}
	
	@Override
	public void run()
	{
		try
        {
			FileInputStream fis = new FileInputStream(new File(filePath));
			sound = new Player(fis);
			sound.play();
        }catch (Exception ex)
        {
        }
		// TODO Auto-generated method stub
	}

}
