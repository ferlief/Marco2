import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.Random;
import javax.imageio.ImageIO;
import javax.swing.*;

@SuppressWarnings("serial")
public class Mesa extends JFrame 
{
	Random random;

	public int valores[];
	int VJogador;
	boolean mostraDados = false;
	Image imagensDados[];
	Image CartaLugar[];
	Image CartaChance;
	Tabuleiro tab;
	int JogCorrente = 0;
	int PosCorrente = 0;
	//DadosPanel dp;
	int x;

	JLabel Turno;
	JLabel JogadorNum;
	JLabel JogadorNumSaldo;
	JButton jogarDado;
	
	JLabel Jogador1;
	JLabel SaldoJ1;

	JLabel Jogador2;
	JLabel SaldoJ2;
	
	JLabel Jogador3;
	JLabel SaldoJ3;
	
	JLabel Jogador4;
	JLabel SaldoJ4;
	
	JLabel Jogador5;
	JLabel SaldoJ5;
	
	JLabel Jogador6;
	JLabel SaldoJ6;

	public Mesa(int numJogadores)
	{
		this.x=numJogadores;
		setLayout(null);
		setBackground(Color.white);
		Container c = getContentPane();
		c.setBackground(Color.white);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		Turno = new JLabel("Turno");
		Turno.setBounds(750,50,100,50);
		add(Turno);
		
		JogadorNum = new JLabel("Jogador Num");
		JogadorNum.setBounds(750,75,100,50);
		add(JogadorNum);
		
		JogadorNumSaldo = new JLabel("R$5.000,00");
		JogadorNumSaldo.setBounds(750,100,100,50);
		add(JogadorNumSaldo);
		
		jogarDado = new JButton("Jogar Dados");
		jogarDado.setBounds(750,150,120,30);
		jogarDado.addActionListener(new jogarDadosButton_Click());
		add(jogarDado);
		
		
		Jogador1 = new JLabel("Jogador 1");
		Jogador1.setBounds(100,700,100,50);
		add(Jogador1);
	
		SaldoJ1 = new JLabel("R$ 5.000,00");
		SaldoJ1.setBounds(100,725,100,50);
		add(SaldoJ1);
		
		Jogador2 = new JLabel("Jogador 2");
		Jogador2.setBounds(200,700,100,50);
		add(Jogador2);
	
		SaldoJ2 = new JLabel("R$ 5.000,00");
		SaldoJ2.setBounds(200,725,100,50);
		add(SaldoJ2);
		
		Jogador3 = new JLabel("Jogador 3");
		Jogador3.setBounds(300,700,100,50);
		add(Jogador3);
	
		SaldoJ3 = new JLabel("R$ 5.000,00");
		SaldoJ3.setBounds(300,725,100,50);
		add(SaldoJ3);
		
		Jogador4 = new JLabel("Jogador 4");
		Jogador4.setBounds(400,700,100,50);
		add(Jogador4);
	
		SaldoJ4 = new JLabel("R$ 5.000,00");
		SaldoJ4.setBounds(400,725,100,50);
		add(SaldoJ4);
		
		Jogador5 = new JLabel("Jogador 5");
		Jogador5.setBounds(500,700,100,50);
		add(Jogador5);
	
		SaldoJ5 = new JLabel("R$ 5.000,00");
		SaldoJ5.setBounds(500,725,100,50);
		add(SaldoJ5);
		
		Jogador6 = new JLabel("Jogador 6");
		Jogador6.setBounds(600,700,100,50);
		add(Jogador6);
	
		SaldoJ6= new JLabel("R$ 5.000,00");
		SaldoJ6.setBounds(600,725,100,50);
		add(SaldoJ6);
		
		random = new Random();
		imagensDados = new Image[6];
		valores = new int[2];
		
		CartaLugar = new Image[40];
		
		for(int m = 0; m < 40; m++)
		{
			if (m+1==2||m+1==10||m+1==12||m+1==16||m+1==18||m+1==20||m+1==22||m+1==24||m+1==27||m+1==30||m+1==37){
				continue;
			}
			String caminho1 = "img/Lugar"+(m+1)+".png";
				try
				{
					CartaLugar[m] = ImageIO.read(new File(caminho1));
				}
				catch (IOException e)
				{
				}
		}
        
		for(int n = 0; n < 6; n++)
		{
			String caminho2 = "img/Dice"+(n+1)+".png";
				try
				{
					imagensDados[n] = ImageIO.read(new File(caminho2));
				}
				catch (IOException e)
				{
				}
		}
	
			tab = new Tabuleiro(numJogadores);
			//dp = new DadosPanel(tab.jogadores);
			tab.setBounds(100, 100, 600, 600);
			//dp.setBounds(20, 700, 150, 100);
			add(tab);
			//add(dp);
		}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		if(mostraDados == true)
			{
			for(int n = 0; n < 2; n++){
				g.drawImage(imagensDados[valores[n] - 1], 800+(150*n), 250, 75, 75, null);
			}
			g.drawImage(CartaLugar[PosCorrente-1],800, 350, 150,200,null);
			}
		
	}
	
	
	public int JogarDados()
	{
		int r = 0;
		for(int n = 0; n < 2; n++){
			valores[n] = random.nextInt(6) + 1;
			r+=valores[n];
			System.out.println(valores[n]);
		}

		return r;
	}
	
	
	public class jogarDadosButton_Click implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int r = JogarDados();
			tab.jogadores[JogCorrente].move(r);
			repaint();
			mostraDados=true;
			PosCorrente = tab.jogadores[JogCorrente].retornaPos();
		}
	}
	
}