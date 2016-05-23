import java.util.Random;

public class Engine
{
	private Pacman pacman;
	private Enemy[] enemies;
	private Smer trenutniSmer;
	private int pojedeno;
	private Polje[][] tabla = new Polje[21][21];
	private int brojEnemies;
	public static final int UKUPNO_ZA_POJESTI_PO_NIVOU = 312; 
	private int level = 1;
	
	public Engine()
	{		
		initialize(4);
	}
	
	public void initialize(int brojEnemies)
	{
		enemies = new Enemy[brojEnemies];
		
		setBrojEnemies(brojEnemies);
		setRandomSmerAndPojedeno();
		setNovcice();
		setZid();
				
		setRandomPackman();
		setRandomEnemies();
	}
	
	private void setRandomEnemies()
	{
		int i, v, k;
		Random rand = new Random();
		
		i = 0;
		while (i < enemies.length)
		{
			v = rand.nextInt(21);
			k = rand.nextInt(21);
			
			if (tabla[v][k] == Polje.NOVCIC)
			{
				boolean flag = true;
				
				for (int j = i - 1; j >= 0; j--)
					if (enemies[j].getI() == v && enemies[j].getJ() == k)
					{
						flag = false;
						break;
					}
				
				if (flag) 
				{
					enemies[i] = new Enemy(v, k, this);
					i++;
				}
			}
		}
	}
	
	private void setRandomSmerAndPojedeno()
	{
		Random rand = new Random();
		trenutniSmer = Smer.values()[rand.nextInt(4)];
		pojedeno = (level - 1) * UKUPNO_ZA_POJESTI_PO_NIVOU + 1;
	}
	
	private void setRandomPackman()
	{
		int v, k;
		Random rand = new Random();
		
		while (true)
		{
			v = rand.nextInt(21);
			k = rand.nextInt(21);
			
			if (tabla[v][k] == Polje.NOVCIC)
			{
				tabla[v][k] = Polje.NISTA;
				pacman = new Pacman(v, k, this);
				break;
			}
		}
	}
	
	private void setNovcice()
	{
		for (int i = 0; i < 21; i++)
			for (int j = 0; j < 21; j++)
				tabla[i][j] = Polje.NOVCIC;
	}
	
	private void setZid()
	{
		for (int i = 0; i < 21; i++)
			if (i != 10) 
			{
				tabla[0][i] = Polje.ZID;
				tabla[i][0] = Polje.ZID;
				tabla[20][i] = Polje.ZID;
				tabla[i][20] = Polje.ZID;
			}		
		
		for (int i = 6; i < 15; i++)
		{
			tabla[10][i] = Polje.ZID;
			tabla[i][10] = Polje.ZID;
		}
		
		for (int i = 3; i < 8; i++)
		{
			// UP - LEFT
			tabla[3][i] = Polje.ZID;
			tabla[i][3] = Polje.ZID;
			
			// UP - RIGHT
			tabla[3][i + 10] = Polje.ZID;
			tabla[i][17] = Polje.ZID;
			
			// DOWN - LEFT
			tabla[17][i] = Polje.ZID;
			tabla[i + 10][3] = Polje.ZID;
			
			// DOWN - RIGHT
			tabla[17][i + 10] = Polje.ZID;
			tabla[i + 10][17] = Polje.ZID;
		}
	}

	public Smer getTrenutniSmer()
	{
		return trenutniSmer;
	}

	public void setTrenutniSmer(Smer trenutniSmer)
	{
		this.trenutniSmer = trenutniSmer;
	}
	
	public Polje getTablaIJ(int i, int j)
	{
		return tabla[i][j];
	}
	
	public int getPojedeno()
	{
		return pojedeno;
	}
	
	public void setTablaIJ(int i, int j, Polje polje)
	{
		tabla[i][j] = polje;
	}

	public Pacman getPacman()
	{
		return pacman;
	}

	public Enemy[] getEnemies()
	{
		return enemies;
	}
	
	public boolean end()
	{
		for (Enemy enemy : enemies)
		{
			if (enemy.getI() == pacman.getI() && enemy.getJ() == pacman.getJ())
				return true;
		}
		
		return false;
	}

	public int getBrojEnemies()
	{
		return brojEnemies;
	}

	public void setBrojEnemies(int brojEnemies)
	{
		this.brojEnemies = brojEnemies;
	}
	
	public void pojediNovcic()
	{
		if (tabla[pacman.getI()][pacman.getJ()] == Polje.NOVCIC)
		{
			tabla[pacman.getI()][pacman.getJ()] = Polje.NISTA;
			pojedeno++;
		}
		
	}
	
	public boolean nextLevel()
	{
		if (pojedeno * level == UKUPNO_ZA_POJESTI_PO_NIVOU)
			return true;
		else
			return false;
	}

	public boolean isThereEnemy(int i, int j)
	{
		for (Enemy enemy : enemies)
		{
			if (enemy.getI() == i && enemy.getJ() == j)
				return true;
		}
		
		return false;
	}
	
	public boolean isTherePacman(int i, int j)
	{
		if (pacman.getI() == i && pacman.getJ() == j)
			return true;
		
		return false;
	}
	
	public void incrementLevel()
	{
		level++;
	}

	
	public int getLevel()
	{
		return level;
	}

	public void setLevel(int level)
	{
		this.level = level;
	}
}
