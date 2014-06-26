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
	JButton comprarPropriedade;
	public int dadosAtuais[];
	boolean mostraDados = false;
	public int numJogadores;

	Tabuleiro tab;
	private JPanel topPanel;
	private JPanel bottomPanel;
	private JSplitPane split;	
	Territorio cartaLugar[];
	Territorio cartaChance[];
	
	Image cartaAtual;

	Image imagensDados[];
	
	private JTextArea outputArea;
	private JScrollPane outputPane;
	
	public int cartasTiradas[];
	public int cartasTiradasContador;

	public int valores[];
	int VJogador;
	int JogCorrente = 0;
	
	JLabel jogadoresLabel[];
	JLabel saldoLabel[];

	public Mesa(int numJogadores, String s)
	{
		super(s);
		this.numJogadores = numJogadores;
		loadCards();
		instantiate();
		initTokens(); 
		
		bottomPanel.add(jogarDado);
		bottomPanel.add(comprarPropriedade);
		for(int i = 0; i < numJogadores; i++) {
			topPanel.add(jogadoresLabel[i]);
			topPanel.add(saldoLabel[i]);
		}
		
		setTitle("Banco Imobiliario");
		setSize(1200, 850);
		setLayout(new BorderLayout());
		add(topPanel, BorderLayout.NORTH);
		add(split, BorderLayout.CENTER);
		add(bottomPanel, BorderLayout.SOUTH);	
		Dimension d = new Dimension(600,600);
		tab.setMinimumSize(d);
		Dimension d1 = new Dimension(214, 800);
		outputPane.setMinimumSize(d1);
		
		setBackground(Color.white);
		Container c = getContentPane();
		c.setBackground(Color.white);
		
		
		JMenuBar MenuBar = new JMenuBar();
        setJMenuBar(MenuBar);

		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	private void initTokens() {
		jogarDado = new JButton("Jogar Dados");
		jogarDado.setBounds(750,50,120,30);
		jogarDado.addActionListener(new jogarDadosButton_Click());
		
		comprarPropriedade = new JButton("Comprar Propriedade");
		comprarPropriedade.setBounds(950,50,220,30);
		comprarPropriedade.addActionListener(new comprarPropriedadeButton_Click());

		for(int i = 0; i < numJogadores; i++) {
			jogadoresLabel[i] = new JLabel("Jogador " +(i+1));
			jogadoresLabel[i].setBounds(100+(i*100),700,100,50);
			saldoLabel[i] = new JLabel(String.format("R$%d", tab.jogadores[i].dinheiro));
			saldoLabel[i].setBounds(100+(i*100),725,100,50);
		}
	}
	
	private void instantiate() {
		tab = new Tabuleiro(numJogadores, cartaLugar);
		jogadoresLabel = new JLabel[6];
		saldoLabel = new JLabel[6];
		topPanel = new JPanel(new FlowLayout());
		bottomPanel = new JPanel(new FlowLayout());
		outputArea = new JTextArea("Banco Imobiliario\n");
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
				g.drawImage(imagensDados[dadosAtuais[n] - 1], 1025+(75*n), 100, 50, 50, null);
			g.drawImage(cartaAtual, 985, 175, 150,200,null);
			g.drawImage(tab.jogadores[tab.jogadorAtual].img, 975, 100, null );
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
	
	public void mostrarCartaSorte(int cartaNum)
	{
		Jogador jogadorAtual = tab.jogadores[tab.jogadorAtual];
		int sorteValor = calculaValor( cartaNum+1 );
		if(sorteValor > 0 ) {
			mostrarMensagem(String.format("Sorte. Ganhou %d.", sorteValor));
		} else {
			mostrarMensagem(String.format("Reves. Perdeu %d.", sorteValor));
		}
		jogadorAtual.dinheiro += sorteValor;
		if(jogadorAtual.dinheiro < 0 ) {
			falencia();
		}
		MudaSaldo();
	}
	
	public void falencia(){
		mostrarMensagem("Foi a falencia!");
	}
	
	public void mostrarCartaPropriedade()
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
	
	public int calculaValor (int pos){
				
		if( pos < 16 ){
			if ( pos == 1){
				return 25;			
			}
			else if ( pos == 2 ){
				return 150;			
			}
			else if ( pos == 3 ){
				return 80;			
			}
			else if ( pos == 4 ){
				return 200;			
			}
			else if ( pos == 5 || pos == 6 ){
				return 50;
			}
			else if ( pos == 7 || pos == 8 || pos == 13 ||  pos == 14 ){
				return 100;
			}
			else if ( pos == 9 || pos == 10 || pos == 11 ){
				return 0;
			}
			else if ( pos == 12 ){
				return 45;
			}
			else if ( pos == 15 ){
				return 20;
			}
		}
		else {
			if ( pos == 16){
				return -15;			
			}
			else if ( pos == 17 || pos == 26 ){
				return -25;			
			}
			else if ( pos == 18 || pos == 28 ){
				return -45;			
			}
			else if ( pos == 19 || pos == 27 ){
				return -30;			
			}
			else if ( pos == 20 || pos == 21 ){
				return -100;
			}
			else if ( pos == 22 ){
				return -40;
			}
			else if ( pos == 23 ){
				return 0;
			}
			else if ( pos == 24 ){
				return -30;
			}
			else if ( pos == 25 || pos == 29 || pos == 30 ){
				return -50;
			}
		}		
		
		return -1;
	}
	
	public int calculaCompraTerr (int pos){
				
		if ( pos == 1 || pos == 23){
			return -100;			
		}
		else if ( pos == 3 || pos == 4 ){
			return -60;			
		}
		else if ( pos == 5 || pos == 7 || pos == 11 || pos == 35){
			return -200;			
		}
		else if ( pos == 6 ){
			return -240;			
		}
		else if ( pos == 8 || pos == 9 ){
			return -220;
		}
		else if ( pos == 13 || pos == 14 ){
			return -180;
		}
		else if ( pos == 15 ||  pos == 25 ){
			return -150;
		}
		else if ( pos == 17 ){
			return -350;
		}
		else if ( pos == 19 ){
			return -400;
		}
		else if ( pos == 21 ){
			return -120;
		}
		else if ( pos == 26 ){
			return -160;
		}
		else if ( pos == 28 || pos == 29 ){
			return -140;
		}
		else if ( pos == 31 || pos == 38 || pos == 39){
			return -260;
		}
		else if ( pos == 33 ){
			return -320;
		}
		else if ( pos == 34 || pos == 36 ){
			return -300;
		}
		
		return -1;
	}

	public class comprarPropriedadeButton_Click implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			Jogador jogadorAtual = tab.jogadores[tab.jogadorAtual];
			if(jogadorAtual.territorioAtual!= null) {
				Territorio terAtual = jogadorAtual.territorioAtual;
				if(terAtual.tipo == Territorio.Tipo.propriedade) {
					if( terAtual.preco <= jogadorAtual.dinheiro &&
							terAtual.dono == null) {
						tab.comprarTerreno(jogadorAtual, terAtual);
						mostrarMensagem(String.format("Comprou propriedade por R$%d!", terAtual.preco));
						MudaSaldo();
					}
					else
					{
						if(terAtual.dono == jogadorAtual) {
							mostrarMensagem(String.format("Propriedade ja e sua"));
						} else if (terAtual.dono != null) {
							mostrarMensagem(String.format("Propriedade ja e do jogador %s", terAtual.dono.nome));
						} else if (terAtual.preco > jogadorAtual.dinheiro) {
							mostrarMensagem(String.format("Propriedade custa R$%d, voce tem apenas R$%d", terAtual.preco, jogadorAtual.dinheiro));
						}
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
			mostrarMensagem(String.format("Jogou os dados."));

			switch(tab.jogadores[tab.jogadorAtual].territorioAtual.tipo)
			{
			case cartaSorte: 
				int cartaNum = VirarCarta();
				cartaAtual = cartaChance[cartaNum].img;
				mostrarCartaSorte(cartaNum);
				break;
			case propriedade:
				cartaAtual = tab.jogadores[tab.jogadorAtual].territorioAtual.img;
				mostrarCartaPropriedade();
				break;
			case vaParaPrisao:
				mostrarMensagem("Voce visitou a prisao.");
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