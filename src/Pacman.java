
public class Pacman implements Moving
{
	private int i;
	private int j;
	private Engine engine;
	
	public Pacman(int i, int j, Engine engine)
	{
		super();
		this.i = i;
		this.j = j;
		this.engine = engine;
	}

	public void move()
	{		
		switch (engine.getTrenutniSmer())
		{
			case UP:
				if (i == 0 && j == 10) i = 20;
				else if (i > 0 && engine.getTablaIJ(i - 1, j) != Polje.ZID) i--;
	
				break;
				
			case DOWN:
				if (i == 20 && j == 10) i = 0;
				else if (i < 20 && engine.getTablaIJ(i + 1, j) != Polje.ZID) i++;

				break;
				
			case LEFT:
				if (i == 10 && j == 0) j = 20;
				else if (j > 0 && engine.getTablaIJ(i, j - 1) != Polje.ZID) j--;
				
				break;
				
			case RIGHT:
				if (i == 10 && j == 20) j = 0;
				else if (j < 20 && engine.getTablaIJ(i, j + 1) != Polje.ZID) j++;

				break;
			
			default:
				break;
		}
	}

	public int getI()
	{
		return i;
	}

	public int getJ()
	{
		return j;
	}
	
	
	
}
