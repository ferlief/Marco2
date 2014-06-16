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

	Tabuleiro tab;
	private JPanel topPanel;
	private JPanel bottomPanel;
	private JSplitPane split;
	//private JLayeredPane lp;	
	Territorio cartaLugar[];
	Territorio cartaChance[];
	
	Image cartaAtual;

	Image imagensDados[];
	DadosPanel dp;
	
	private JTextArea outputArea;
	private JScrollPane outputPane;
	
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
		instantiate();
		initTokens(); 
		loadCards();
		
		//Generate and add stuff to the panels
		bottomPanel.add(jogarDado);
		bottomPanel.add(comprarPropiedade);
		bottomPanel.add(Mensagem);
		bottomPanel.add(JogadorNum);
		bottomPanel.add(JogadorNumSaldo);
		for(int i = 0; i < numJogadores; i++) {
			topPanel.add(jogadoresLabel[i]);
			topPanel.add(saldoLabel[i]);
		}
		
		setTitle("Monopoly");
		setSize(1200, 850);
		setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		add(split, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);	
		//Sets a minimum size for the the board, which 
		//is the size of the image of the board.
		Dimension d = new Dimension(600,600);
		tab.setMinimumSize(d);
		//Sets a minimum size for the output area,
		//based on the board.
		Dimension d1 = new Dimension(214, 800);
		outputPane.setMinimumSize(d1);
		
		setBackground(Color.white);
		Container c = getContentPane();
		c.setBackground(Color.white);
		//this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		JMenuBar MenuBar = new JMenuBar();
        setJMenuBar(MenuBar);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		tab.setBounds(100, 100, 600, 600);
//		dp.setBounds(20, 700, 150, 100);
//		add(tab);
//		add(dp);
	}
	
	private void initTokens() {
		jogarDado = new JButton("Jogar Dados");
		jogarDado.setBounds(750,50,120,30);
		jogarDado.addActionListener(new jogarDadosButton_Click());
		
		comprarPropiedade = new JButton("Comprar Propiedade");
		comprarPropiedade.setBounds(950,50,220,30);
		comprarPropiedade.addActionListener(new comprarPropiedadeButton_Click());

		Mensagem = new JLabel("");
		Mensagem.setBounds(800,650,500,100);
		
		JogadorNum = new JLabel("Jogador Atual");
		JogadorNum.setBounds(750,75,100,50);
		
		JogadorNumSaldo = new JLabel(String.format("R$%d", tab.jogadores[tab.jogadorAtual].dinheiro));
		JogadorNumSaldo.setBounds(750,100,100,50);
		
		for(int i = 0; i < numJogadores; i++) {
			jogadoresLabel[i] = new JLabel("Jogador " +(i+1));
			jogadoresLabel[i].setBounds(100+(i*100),700,100,50);
			saldoLabel[i] = new JLabel(String.format("R$%d", tab.jogadores[i].dinheiro));
			saldoLabel[i].setBounds(100+(i*100),725,100,50);
		}
	}
	
	private void instantiate() {
		tab = new Tabuleiro(numJogadores);
		dp = new DadosPanel(tab.jogadores);
		jogadoresLabel = new JLabel[6];
		saldoLabel = new JLabel[6];
		topPanel = new JPanel(new FlowLayout());
		bottomPanel = new JPanel(new FlowLayout());
	//	lp = getLayeredPane();
		outputArea = new JTextArea("Welcome to Monopoly\n");
		outputArea.setEditable(false);
		outputArea.setLineWrap(true);
		outputPane = new JScrollPane(outputArea);
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, tab, outputPane);
	}
	
	private void loadCards() {
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
				if ( m % 2 == 0 )
					cartaLugar[m].preco = 50;
				else
					cartaLugar[m].preco = -50;
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
		Jogador jogadorAtual = tab.jogadores[tab.jogadorAtual];
		Territorio terAtual = jogadorAtual.territorioAtual;
		if(terAtual.preco > 0 ) {
			mostrarMensagem(String.format("Sorte! Ganhou R$%d", terAtual.preco));
		} else {
			mostrarMensagem(String.format("Revez. Perdeu R$%d", terAtual.preco));
		}
		jogadorAtual.dinheiro += terAtual.preco;
		if(jogadorAtual.dinheiro < 0 ) {
			falencia();
		}
		MudaSaldo();
	}
	
	public void falencia(){
		mostrarMensagem("Foi a falencia!");
	}
	
	public void mostrarCartaPropiedade()
	{
		Jogador jogadorAtual = tab.jogadores[tab.jogadorAtual];
		Territorio terAtual = jogadorAtual.territorioAtual;
		if( jogadorAtual != terAtual.dono && terAtual.dono != null )
		{
			mostrarMensagem(String.format("Territorio do jogador %s, aluguel de R$%d", terAtual.dono.nome, terAtual.aluguel));
			boolean resultado = jogadorAtual.pagarAluguel(terAtual.dono, terAtual.aluguel);
			if (!resultado)
			{
				falencia();
			}
			MudaSaldo();
		}
	}
	
	public void mostrarMensagem(String text) {
		outputArea.append(String.format("[%s] - %s \n", tab.jogadores[tab.jogadorAtual].nome, text));
		outputArea.setCaretPosition(outputArea.getDocument().getLength());
	}

/*	public void mostrarMensagem(String s)
	{
		this.Mensagem.setText(s);
	}*/
	
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
			Jogador jogadorAtual = tab.jogadores[tab.jogadorAtual];
			Territorio terAtual = jogadorAtual.territorioAtual;
			if(terAtual.tipo == Territorio.Tipo.propriedade) {
				if( terAtual.preco <= jogadorAtual.dinheiro &&
						terAtual.dono == null) {
					tab.comprarTerreno(jogadorAtual, terAtual);
					MudaSaldo();
				}
				else
				{
					if(terAtual.dono == jogadorAtual) {
						mostrarMensagem(String.format("Propriedade já é sua"));
					} else if (terAtual.dono != null) {
						mostrarMensagem(String.format("Propriedade já é do jogador %s", terAtual.dono.nome));
					} else if (terAtual.preco > jogadorAtual.dinheiro) {
						mostrarMensagem(String.format("Propriedade custa R$%d, você tem apenas R$%d", terAtual.preco, jogadorAtual.dinheiro));
					}
				}
			}
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
				break;
			case propriedade:
				cartaAtual = tab.jogadores[tab.jogadorAtual].territorioAtual.img;
				mostrarCartaPropiedade();
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