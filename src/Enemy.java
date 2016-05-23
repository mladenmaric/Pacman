
public class Enemy implements Moving
{
	private int i;
	private int j;
	private Engine engine;
	
	public Enemy(int i, int j, Engine engine)
	{
		super();
		this.i = i;
		this.j = j;
		this.engine = engine;
	}

	public int getI()
	{
		return i;
	}

	public int getJ()
	{
		return j;
	}
	
	public boolean canYouMove(Smer smer)
	{
		switch (smer)
		{
			case UP:
				if (engine.getTablaIJ(i - 1, j) == Polje.ZID)
					return false;
				
				for (Enemy e : engine.getEnemies())
					if (i - 1 == e.getI() && j == e.getJ())
						return false;
				
				i--;
				return true;
				
			case DOWN:
				if (engine.getTablaIJ(i + 1, j) == Polje.ZID)
					return false;
				
				for (Enemy e : engine.getEnemies())
					if (i + 1 == e.getI() && j == e.getJ())
						return false;
				
				i++;
				return true;
				
			case LEFT:
				if (engine.getTablaIJ(i, j - 1) == Polje.ZID)
					return false;
				
				for (Enemy e : engine.getEnemies())
					if (i == e.getI() && j - 1 == e.getJ())
						return false;
				
				j--;
				return true;
				
			case RIGHT:
				if (engine.getTablaIJ(i, j + 1) == Polje.ZID)
					return false;
				
				for (Enemy e : engine.getEnemies())
					if (i == e.getI() && j + 1 == e.getJ())
						return false;
				
				j++;
				return true;
			
			default:
				return false;
		}
	}
	
	public void move()
	{
		Pacman pacman = engine.getPacman();
		Smer[] prior = new Smer[4];
		int x, y;
		
		x = pacman.getI() - i;
		y = pacman.getJ() - j;
		
		if (Math.abs(x) >= Math.abs(y))
		{
			if (x >= 0)
			{
				prior[0] = Smer.DOWN;
				prior[3] = Smer.UP;
			}
			else 
			{
				prior[0] = Smer.UP;
				prior[3] = Smer.DOWN;
			}
			
			if (y >= 0)
			{
				prior[1] = Smer.RIGHT;
				prior[2] = Smer.LEFT;
			}			
			else
			{
				prior[1] = Smer.LEFT;
				prior[2] = Smer.RIGHT;
			}
		}
		else
		{
			if (x >= 0)
			{
				prior[1] = Smer.DOWN;
				prior[2] = Smer.UP;
			}			
			else
			{
				prior[1] = Smer.UP;
				prior[2] = Smer.DOWN;
			}
			
			if (y >= 0)
			{
				prior[0] = Smer.RIGHT;
				prior[3] = Smer.LEFT;
			}			
			else
			{
				prior[0] = Smer.LEFT;
				prior[3] = Smer.RIGHT;
			}	
		}
		
		for (int p = 0; p < 4; p++)
			if (canYouMove(prior[p])) break;
	}

}
