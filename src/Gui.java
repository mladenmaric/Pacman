import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Gui extends JFrame
{
	private static final long serialVersionUID = 1L;
	private MyButton[][] dugmici = new MyButton[21][21];
	private Engine engine = new Engine();
	private int speed = 128;
	private JLabel level;
	private JLabel pojedeno;
	private JButton nova;
	
	public Gui(String title)
	{
		super(title);
		
		setCenter();
		setEast();
		setWindow();
		
		addListeners();
		setTimerAndPlay();
	}
	
	private void setTimerAndPlay()
	{
		Timer timer = new Timer();		
		
		TimerTask task1 = new TimerTask()
		{
			public void run()
			{
				engine.getPacman().move();		
				engine.pojediNovcic();
			}
		};
		
		TimerTask task2 = new TimerTask()
		{		
			public void run()
			{
				for (Enemy e : engine.getEnemies())	
					e.move();			
			}
		};
		
		TimerTask task3 = new TimerTask()
		{		
			public void run()
			{
				refreshGui();			
			}
		};
		
		Thread nit1 = new Thread(new Runnable()
		{
			public void run()
			{
				timer.schedule(task1, 0, speed);				
			}
		});
		
		Thread nit2 = new Thread(new Runnable()
		{
			public void run()
			{
				timer.schedule(task2, 0, speed * 2);
			}
		});
		
		Thread nit3 = new Thread(new Runnable()
		{
			public void run()
			{
				timer.schedule(task3, 0, 32);
			}
		});
		
		nit1.start();
		nit2.start();	
		nit3.start();
	}

	private void setCenter()
	{
		JPanel center = new JPanel(new GridLayout(21, 21));
		
		for (int i = 0; i < dugmici.length; i++)
			for (int j = 0; j < dugmici.length; j++)
			{
				dugmici[i][j] = new MyButton(i, j);
				dugmici[i][j].setPreferredSize(new Dimension(33, 33));
				dugmici[i][j].setBackground(Color.WHITE);
	
				center.add(dugmici[i][j]);
			}
		
		getContentPane().add(center, BorderLayout.CENTER);	
	}
	
	private void setEast()
	{
		JPanel east = new JPanel(new GridLayout(3, 1));
		east.setPreferredSize(new Dimension(250, 693));
		
		JPanel east1 = new JPanel();
		level = new JLabel("Level " + engine.getLevel());
		level.setFont(new Font("Arial", Font.BOLD, 32));
		east1.add(level);
		
		JPanel east2 = new JPanel();
		pojedeno = new JLabel("Sakupljeno novcica: " + engine.getPojedeno());
		pojedeno.setFont(new Font("Arial", Font.ITALIC, 20));
		east2.add(pojedeno);
		
		JPanel east3 = new JPanel();
		nova = new JButton("Nova");
		east3.add(nova);
		
		east.add(east1);
		east.add(east2);
		east.add(east3);
		
		getContentPane().add(east, BorderLayout.EAST);
	}
	
	private void setWindow()
	{
		setBounds(336, 5, 943, 693);
		setResizable(false);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setFocusable(true);
		setVisible(true);
		pack();
	}
	
	private void addListeners()
	{
		requestFocusInWindow();
		addKeyListener(new MyKeyListener(engine));
		
		nova.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				engine.initialize(4);
				speed = 256;
				refreshGui();
			}
		});
	}
	
	public void refreshGui()
	{
		for (int i = 0; i < 21; i++)
			for (int j = 0; j < 21; j++)
				if (!engine.isTherePacman(i, j) && !engine.isThereEnemy(i, j))
				{
					if (engine.getTablaIJ(i, j) != Polje.NISTA)
						dugmici[i][j].setIcon(new ImageIcon(getClass().getResource("/" + engine.getTablaIJ(i, j).toString() + ".jpg")));
					else
						dugmici[i][j].setIcon(null);
				}
		
		dugmici[engine.getPacman().getI()][engine.getPacman().getJ()].setIcon(new ImageIcon
				(getClass().getResource("/" + engine.getTrenutniSmer().toString() + ".gif")));
		
		for (Enemy e : engine.getEnemies())
			dugmici[e.getI()][e.getJ()].setIcon(new ImageIcon(getClass().getResource("/Enemy.png")));
		
		pojedeno.setText("Sakupljeno novcica: " + engine.getPojedeno());
		
		if (engine.end())
		{
			int kraj = JOptionPane.showConfirmDialog(this, "Igra je zavrsena. Sakupili ste " + engine.getPojedeno() + " novcica.\nNova igra?", 
					"Kraj igre", JOptionPane.YES_NO_OPTION);
			
			if (kraj == JOptionPane.YES_OPTION)
			{
				engine.setLevel(1);
				engine.initialize(4);
				speed = 256;
				refreshGui();
			}
			else
				System.exit(0);
		}
		else if (engine.nextLevel())
		{
			engine.incrementLevel();
			engine.initialize(engine.getBrojEnemies() + 1);
			speed /= 2;
			refreshGui();
			
			level.setText("Level " + engine.getLevel());
		}
			
	}
}
