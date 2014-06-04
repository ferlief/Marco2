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
	JButton jogarDado;
	JButton comprarPropiedade;
	public int dadosAtuais[];
	boolean mostraDados = false;
	public int numJogadores;
	Territorio CartaLugar[];
	Image CartaChance;
	Image imagensDados[];
	Tabuleiro tab;
	DadosPanel dp;

	public int valores[];
	int VJogador;
	int JogCorrente = 0;

	JLabel Turno;
	JLabel JogadorNum;
	JLabel JogadorNumSaldo;
	
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

	public Mesa(int numJogadores, String s)
	{
		super(s);
		this.numJogadores = numJogadores;
		setLayout(null);
		setBackground(Color.white);
		Container c = getContentPane();
		c.setBackground(Color.white);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		tab = new Tabuleiro();
		dp = new DadosPanel(tab.jogadores);
		
		jogarDado = new JButton("Jogar Dados");
		jogarDado.setBounds(750,50,120,30);
		jogarDado.addActionListener(new jogarDadosButton_Click());
		add(jogarDado);
		
		comprarPropiedade = new JButton("Comprar Propiedade");
		comprarPropiedade.setBounds(950,50,220,30);
		comprarPropiedade.addActionListener(new comprarPropiedadeButton_Click());
		add(comprarPropiedade);

		Turno = new JLabel("Turno");
		Turno.setBounds(750,50,100,50);
		add(Turno);
		
		JogadorNum = new JLabel("Jogador Atual");
		JogadorNum.setBounds(750,75,100,50);
		add(JogadorNum);
		
		JogadorNumSaldo = new JLabel(String.format("R$%d", tab.jogadores[tab.jogadorAtual].dinheiro));
		JogadorNumSaldo.setBounds(750,100,100,50);
		add(JogadorNumSaldo);
		
		Jogador1 = new JLabel("Jogador 1");
		Jogador1.setBounds(100,700,100,50);
	
		SaldoJ1 = new JLabel(String.format("R$%d", tab.jogadores[0].dinheiro));
		SaldoJ1.setBounds(100,725,100,50);
		
		Jogador2 = new JLabel("Jogador 2");
		Jogador2.setBounds(200,700,100,50);
	
		SaldoJ2 = new JLabel(String.format("R$%d", tab.jogadores[1].dinheiro));
		SaldoJ2.setBounds(200,725,100,50);
		
		Jogador3 = new JLabel("Jogador 3");
		Jogador3.setBounds(300,700,100,50);
	
		SaldoJ3 = new JLabel(String.format("R$%d", tab.jogadores[2].dinheiro));
		SaldoJ3.setBounds(300,725,100,50);
		
		Jogador4 = new JLabel("Jogador 4");
		Jogador4.setBounds(400,700,100,50);
	
		SaldoJ4 = new JLabel(String.format("R$%d", tab.jogadores[3].dinheiro));
		SaldoJ4.setBounds(400,725,100,50);
		
		Jogador5 = new JLabel("Jogador 5");
		Jogador5.setBounds(500,700,100,50);
	
		SaldoJ5 = new JLabel(String.format("R$%d", tab.jogadores[4].dinheiro));
		SaldoJ5.setBounds(500,725,100,50);
		
		Jogador6 = new JLabel("Jogador 6");
		Jogador6.setBounds(600,700,100,50);
	
		SaldoJ6= new JLabel(String.format("R$%d", tab.jogadores[5].dinheiro));
		SaldoJ6.setBounds(600,725,100,50);
		
		random = new Random();
		imagensDados = new Image[6];
		dadosAtuais = new int[2];

		CartaLugar = new Territorio[40];
		
		for(int m = 0; m < 40; m++)
		{
			CartaLugar[m] = new Territorio(1000, 100, Territorio.Tipo.propiedade);
			if (m+1==2||m+1==10||m+1==12||m+1==16||m+1==18||m+1==20||m+1==22||m+1==24||m+1==27||m+1==30||m+1==37||m==39){
				CartaLugar[m].tipo = Territorio.Tipo.comeco;
				continue;
			}
			
			
			String caminho1 = "img/Lugar"+(m+1)+".png";
			try
			{
				CartaLugar[m].img = ImageIO.read(new File(caminho1));
			}
			catch (IOException e)
			{
				System.out.println(caminho1);
			}
		}
		
		JMenuBar MenuBar = new JMenuBar();
        setJMenuBar(MenuBar);
        
		for(int n = 0; n < 6; n++)
		{
			String caminho = "img/Dice"+(n+1)+".png";
			try
			{
				imagensDados[n] = ImageIO.read(new File(caminho));
			}
			catch (IOException e)
			{
			}
		}

		tab.setBounds(100, 100, 600, 600);
		dp.setBounds(20, 700, 150, 100);
		add(tab);
		//add(dp);
		switch(this.numJogadores)
		{
		case 6:
			add(Jogador6);
			add(SaldoJ6);
			add(Jogador5);
			add(SaldoJ5);
			add(Jogador4);
			add(SaldoJ4);
			add(Jogador3);
			add(SaldoJ3);
			add(Jogador1);
			add(SaldoJ1);
			add(Jogador2);
			add(SaldoJ2);
			break;
		case 5:
			add(Jogador5);
			add(SaldoJ5);
			add(Jogador4);
			add(SaldoJ4);
			add(Jogador3);
			add(SaldoJ3);
			add(Jogador1);
			add(SaldoJ1);
			add(Jogador2);
			add(SaldoJ2);
			break;
		case 4:
			add(Jogador4);
			add(SaldoJ4);
			add(Jogador3);
			add(SaldoJ3);
			add(Jogador1);
			add(SaldoJ1);
			add(Jogador2);
			add(SaldoJ2);
			break;
		case 3:
			add(Jogador3);
			add(SaldoJ3);
			add(Jogador1);
			add(SaldoJ1);
			add(Jogador2);
			add(SaldoJ2);
			break;
		case 2:
			add(Jogador1);
			add(SaldoJ1);
			add(Jogador2);
			add(SaldoJ2);
			break;
		}
	}
	public void paint(Graphics g)
	{
		super.paint(g);
		if(mostraDados == true)
		{
			for(int n = 0; n < 2; n++)
				g.drawImage(imagensDados[dadosAtuais[n] - 1], 800+(150*n), 200, 100, 100, null);
			g.drawImage(CartaLugar[tab.jogadores[tab.jogadorAtual].pos-1].img,800, 350, 150,200,null);
			g.drawImage(tab.jogadores[tab.jogadorAtual].img, 750, 175, null );
		}

	}
	
	public int jogarDados()
	{
		int r = 0;
		for(int n = 0; n < 2; n++){
			dadosAtuais[n] = random.nextInt(6) + 1;
			r+=dadosAtuais[n];
		}
		tab.jogadorAtual++;
		if(tab.jogadorAtual > this.numJogadores-1) 
			tab.jogadorAtual = 0;

		return r;
	}
	
	public void mostrarCartaSorte()
	{
		
	}
	
	public void mostrarCartaPropiedade()
	{
		
	}
	
	public void mostrarMensagem(String s)
	{
		
	}

	public class comprarPropiedadeButton_Click implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(tab.jogadores[tab.jogadorAtual].territorioAtual.tipo == Territorio.Tipo.propiedade &&
					tab.jogadores[tab.jogadorAtual].territorioAtual.preco < tab.jogadores[tab.jogadorAtual].dinheiro &&
					tab.jogadores[tab.jogadorAtual].territorioAtual.dono == null) {
				tab.comprarTerreno(tab.jogadores[tab.jogadorAtual], tab.jogadores[tab.jogadorAtual].territorioAtual);
			}
		}
	}
	
	
	
	public class jogarDadosButton_Click implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			int r = jogarDados();
			tab.jogadores[tab.jogadorAtual].move(r);
			tab.jogadores[tab.jogadorAtual].territorioAtual = CartaLugar[tab.jogadores[tab.jogadorAtual].pos-1];
			switch(tab.jogadores[tab.jogadorAtual].territorioAtual.tipo)
			{
			case cartaSorte: 
				mostrarCartaSorte();
				break;
			case propiedade: 
				mostrarCartaPropiedade();
				break;
			case prisao:
				mostrarMensagem("Voce visitou a prisao");
				break;
			case comeco:
				mostrarMensagem("Voce passou pelo comeco.");
				break;
			}
			repaint();
			mostraDados=true;
		}
	}
	
}