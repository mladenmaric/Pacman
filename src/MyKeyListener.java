import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class MyKeyListener implements KeyListener
{
	private Engine engine;
	
	public MyKeyListener(Engine engine)
	{
		super();
		this.engine = engine;
	}

	public void keyTyped(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	
	public void keyPressed(KeyEvent e)
	{
		int smer = e.getKeyCode();
		
		switch (smer)
		{
			case KeyEvent.VK_UP:
				engine.setTrenutniSmer(Smer.UP);
				break;
				
			case KeyEvent.VK_DOWN:
				engine.setTrenutniSmer(Smer.DOWN);
				break;
				
			case KeyEvent.VK_LEFT:
				engine.setTrenutniSmer(Smer.LEFT);
				break;
				
			case KeyEvent.VK_RIGHT:
				engine.setTrenutniSmer(Smer.RIGHT);
				break;
			
			default:
				break;
		}
		
	}
	
	public void keyReleased(KeyEvent e)
	{
		// TODO Auto-generated method stub
		
	}
	
}
