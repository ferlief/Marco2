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
	
	Territorio cartaLugar[];
	Territorio cartaChance[];
	
	Image cartaAtual;

	Image imagensDados[];
	Tabuleiro tab;
	DadosPanel dp;
	
	public int cartasTiradas[];
	public int cartasTiradasContador;

	public int valores[];
	int VJogador;
	int JogCorrente = 0;

	JLabel Mensagem;
	JLabel JogadorNum;
	JLabel JogadorNumSaldo;
	
	JLabel jogadoresLabel[];
	JLabel saldoLabel[];

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
		
		jogadoresLabel = new JLabel[6];
		saldoLabel = new JLabel[6];
		
		jogarDado = new JButton("Jogar Dados");
		jogarDado.setBounds(750,50,120,30);
		jogarDado.addActionListener(new jogarDadosButton_Click());
		add(jogarDado);
		
		comprarPropiedade = new JButton("Comprar Propiedade");
		comprarPropiedade.setBounds(950,50,220,30);
		comprarPropiedade.addActionListener(new comprarPropiedadeButton_Click());
		add(comprarPropiedade);

		Mensagem = new JLabel("");
		Mensagem.setBounds(800,650,500,100);
		add(Mensagem);
		
		JogadorNum = new JLabel("Jogador Atual");
		JogadorNum.setBounds(750,75,100,50);
		add(JogadorNum);
		
		JogadorNumSaldo = new JLabel(String.format("R$%d", tab.jogadores[tab.jogadorAtual].dinheiro));
		JogadorNumSaldo.setBounds(750,100,100,50);
		add(JogadorNumSaldo);
		
		
		for(int i = 0; i < numJogadores; i++) {
			jogadoresLabel[i] = new JLabel("Jogador " +(i+1));
			jogadoresLabel[i].setBounds(100+(i*100),700,100,50);
			add(jogadoresLabel[i]);
			saldoLabel[i] = new JLabel(String.format("R$%d", tab.jogadores[i].dinheiro));
			saldoLabel[i].setBounds(100+(i*100),725,100,50);
			add(saldoLabel[i]);
		}
	
		random = new Random();
		imagensDados = new Image[6];
		dadosAtuais = new int[2];
		cartasTiradas = new int[30];
		for(int i = 0; i < 30; i++)
		{
			cartasTiradas[i] = 0;
		}
		cartasTiradasContador = 0;

		cartaLugar = new Territorio[40];
		cartaChance = new Territorio[30];
		
		/* Carrega imagens das cartas de sorte ou reves */
		for(int cont = 0; cont < 30; cont++)
		{
			cartaChance[cont] = new Territorio();
			String caminho3 = "img/chance"+(cont+1)+".png";
				try
				{
					cartaChance[cont].img = ImageIO.read(new File(caminho3));
				}
				catch (IOException e)
				{
				}
		} /*end for*/
		
		
		for(int m = 0; m < 40; m++)
		{
			
			cartaLugar[m] = new Territorio(1000, 100, Territorio.Tipo.propriedade);
			
			if (m==24){
				cartaLugar[m].tipo = Territorio.Tipo.imposto;
				continue;
			}
			if (m==30){
				cartaLugar[m].tipo = Territorio.Tipo.vaParaPrisao;
				continue;
			}
			if (m==10||m==20){
				cartaLugar[m].tipo = Territorio.Tipo.neutro;
				continue;
			}
			if (m==0||m==18){
				cartaLugar[m].tipo = Territorio.Tipo.bonus;
				continue;
			}
			if (m==2||m==12||m==16||m==22||m==27||m==37){
				cartaLugar[m].tipo = Territorio.Tipo.cartaSorte;
				continue;
			}
			
			cartaLugar[m] = new Territorio(1000, 100, Territorio.Tipo.propriedade);
			
			String caminho = "img/Lugar"+m+".png";
			try
			{
				cartaLugar[m].img = ImageIO.read(new File(caminho));
			}
			catch (IOException e)
			{
				System.out.println(caminho);
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
	}
	
	public void paint(Graphics g)
	{
		super.paint(g);
		if(mostraDados == true)
		{
			for(int n = 0; n < 2; n++)
				g.drawImage(imagensDados[dadosAtuais[n] - 1], 800+(150*n), 200, 100, 100, null);
			g.drawImage(cartaAtual,800, 350, 150,200,null);
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
		this.Mensagem.setText(s);
	}
	
	/* Randomizacao do valor da carta */
	public int VirarCarta()
	{
		int res = random.nextInt(30);
		while(Contem(res))
		{
			res = random.nextInt(30);
		}
		
		cartasTiradas[cartasTiradasContador] = res;
		cartasTiradasContador++;
		
		if(cartasTiradasContador == 30)
		{
			cartasTiradasContador = 0;
		}
		
		return res;
	} /*END public int VirarCarta() */
	
	public boolean Contem( int x ) 
	{
		for(int i = 0; i < cartasTiradasContador; i++)
		{
			if(cartasTiradas[i] == x)
			{
				return true;
			}
		}
		return false;
	}
	
	public void MudaSaldo()
	{
		for(int i = 0; i < numJogadores; i++)
		{
			this.saldoLabel[i].setText(String.format("R$%d", tab.jogadores[i].dinheiro));
		}
	}

	public class comprarPropiedadeButton_Click implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			if(tab.jogadores[tab.jogadorAtual].territorioAtual.tipo == Territorio.Tipo.propriedade &&
					tab.jogadores[tab.jogadorAtual].territorioAtual.preco < tab.jogadores[tab.jogadorAtual].dinheiro &&
					tab.jogadores[tab.jogadorAtual].territorioAtual.dono == null) {
				tab.comprarTerreno(tab.jogadores[tab.jogadorAtual], tab.jogadores[tab.jogadorAtual].territorioAtual);
			}
			MudaSaldo();
		}
	}
	
	
	
	public class jogarDadosButton_Click implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			
			int r = jogarDados();
			tab.jogadores[tab.jogadorAtual].move(r);
			tab.jogadores[tab.jogadorAtual].territorioAtual = cartaLugar[tab.jogadores[tab.jogadorAtual].pos];
			cartaAtual = null;

			switch(tab.jogadores[tab.jogadorAtual].territorioAtual.tipo)
			{
			case cartaSorte: 
				cartaAtual = cartaChance[VirarCarta()].img;
				mostrarCartaSorte();
				mostrarMensagem("");
				break;
			case propriedade:
				cartaAtual = tab.jogadores[tab.jogadorAtual].territorioAtual.img;
				mostrarCartaPropiedade();
				mostrarMensagem("");
				break;
			case vaParaPrisao:
				mostrarMensagem("Voce visitou a prisao");
				break;
			case bonus:
				mostrarMensagem("Voce passou pelo comeco.");
				break;
			case imposto:
				mostrarMensagem("Imposto.");
				break;
			case neutro:
				mostrarMensagem("Neutro.");
				break;
			}
			
			if( tab.jogadores[tab.jogadorAtual].territorioAtual.dono != null &&
					tab.jogadores[tab.jogadorAtual].territorioAtual.dono != tab.jogadores[tab.jogadorAtual] )
			{
				
			}
			
			repaint();
			mostraDados=true;
		}
	}
}