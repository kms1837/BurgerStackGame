import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;


public class CustomMouse implements MouseListener, MouseMotionListener
{
	private int mousePositionX, mousePositionY;
	private int mouseClickPositionX, mouseClickPositionY;

	public int getMousePositionX() { return mousePositionX; }
	public int getMousePositionY() { return mousePositionY; }
	
	public int getMouseClickPositionX()
	{
		int returnNumber = mouseClickPositionX; 
		mouseClickPositionX = -1;

		return returnNumber;
	} 
	
	public int getMouseClickPositionY()
	{
		int returnNumber = mouseClickPositionY; 
		mouseClickPositionY = -1;

		return returnNumber;
	}
	
	public void mouseUp()
	{
		
	}
	
	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
		mouseClickPositionX = e.getX();
		mouseClickPositionY = e.getY();
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
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
		mousePositionX = e.getX();
		mousePositionY = e.getY();
	}

}
