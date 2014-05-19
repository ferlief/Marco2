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
	/* Variaveis de randomizacao */
	Random ranDado; /* Referente ao numero tirado nos dois dados */
	Random ranChance; /* Referente a carta de sorte que sera virada */

	public int qtdDados[]; /* Quantidade de dados */
	
	/* Variaveis booleanas */
	boolean mostraDados = false;
	boolean posCorrELugar = false;
	boolean posCorrEChance = false;
	boolean posCorrEAuto = false;
	
	/* Itens que serao carregados na interface */
	Image imagensDados[];
	Image cartaLugar[];
	Image cartaChance[];
	Tabuleiro tab;
	
	/* Variaveis de posicao e vetor de posicao corrente */
	int jogCorr = 0;
	int posCorr = 0;
	
	/* Vetores que informam a categoria das casas do tabuleiro */
	int vetPosLugar[] = {1,3,4,5,6,7,8,9,11,13,14,15,17,19,21,23,25,26,28,29,
			31,32,33,34,35,36,38,39}; /* Cartas de localizacao ou empresa */ 
	int vetPosChance[] = {2,12,16,22,27,37}; /* Cartas de sorte ou reves */
	int vetPosAuto[] = {0,10,18,20,24,30}; /* Cartas de acao automatica a ser executada */

	/* Cricao dos itens de titulos */
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

	/* Inicializador da classe, recebendo quantidade de jogadores */
	public Mesa(int numJogs)
	{
		setLayout(null);
		setBackground(Color.white);
		Container c = getContentPane();
		c.setBackground(Color.white);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		/* Itens da area direita ao tabuleiro */
		Turno = new JLabel("Turno");
		Turno.setBounds(750,50,100,50);
		add(Turno);
		
		JogadorNum = new JLabel("Jogador "+(jogCorr+1));
		JogadorNum.setBounds(750,75,100,50);
		add(JogadorNum);
		
		JogadorNumSaldo = new JLabel("R$5.000,00");
		JogadorNumSaldo.setBounds(750,100,100,50);
		add(JogadorNumSaldo);
		
		jogarDado = new JButton("Jogar Dados");
		jogarDado.setBounds(750,150,120,30);
		jogarDado.addActionListener(new jogarDadosButton_Click());
		add(jogarDado);
		
		/* Mostra para cada jogador o numero jogador e seu saldo, 
		 * itens da area inferior ao tabuleiro */
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
		
		/* Mostra jogador e saldo para demais jogadores se existirem*/
		if (numJogs >2)
		{			
			Jogador3 = new JLabel("Jogador 3");
			Jogador3.setBounds(300,700,100,50);
			add(Jogador3);
		
			SaldoJ3 = new JLabel("R$ 5.000,00");
			SaldoJ3.setBounds(300,725,100,50);
			add(SaldoJ3);
			
			if (numJogs >3)
			{
				Jogador4 = new JLabel("Jogador 4");
				Jogador4.setBounds(400,700,100,50);
				add(Jogador4);
			
				SaldoJ4 = new JLabel("R$ 5.000,00");
				SaldoJ4.setBounds(400,725,100,50);
				add(SaldoJ4);
				
				if (numJogs >4)
				{
					Jogador5 = new JLabel("Jogador 5");
					Jogador5.setBounds(500,700,100,50);
					add(Jogador5);
				
					SaldoJ5 = new JLabel("R$ 5.000,00");
					SaldoJ5.setBounds(500,725,100,50);
					add(SaldoJ5);
					
					if (numJogs >5){
						Jogador6 = new JLabel("Jogador 6");
						Jogador6.setBounds(600,700,100,50);
						add(Jogador6);
					
						SaldoJ6= new JLabel("R$ 5.000,00");
						SaldoJ6.setBounds(600,725,100,50);
						add(SaldoJ6);
					}
				}
			}
			
		} /* end if */
		
		/*Cria objetos de randomizacao*/
		ranDado = new Random();
		ranChance = new Random();
		
		/* cria imagens*/
		imagensDados = new Image[6];
		qtdDados = new int[2];
		cartaLugar = new Image[40];		
		cartaChance = new Image[30];
				
		/* Carrega imagens das cartas de sorte ou reves */
		for(int cont = 0; cont < 30; cont++)
		{
			String caminho3 = "img/chance"+(cont+1)+".png";
				try
				{
					cartaChance[cont] = ImageIO.read(new File(caminho3));
				}
				catch (IOException e)
				{
				}
		} /*end for*/
		
		/* Carrega imagens das cartas de localizacao ou empresa, com espacos vazios*/
		for(int m = 0; m < 40; m++)
		{
			if (m==0||m==10||m==18||m==20||m==24||m==30){
				continue;
			}
			if (m==2||m==12||m==16||m==22||m==27||m==37){
				continue;
			}
			String caminho1 = "img/Lugar"+(m)+".png";
				try
				{
					cartaLugar[m] = ImageIO.read(new File(caminho1));
				}
				catch (IOException e)
				{
				}
		} /* end for */
        
		/* Carrega imagens dos dados */
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
		} /* end for */
		
		/* Carrega imagem do tabuleiro com os pinos referentes aos jogadores */
		tab = new Tabuleiro(numJogs);
		tab.setBounds(100, 100, 600, 600);
		add(tab);
	} /* END public Mesa(int numJogs) */
	
	/* Desenha as imagens */
	public void paint(Graphics g)
	{
		super.paint(g);
		
		/*Quando mostraDados for verdade */
		if(mostraDados == true)
		{
			for(int n = 0; n < 2; n++)
				g.drawImage(imagensDados[qtdDados[n] - 1], 800+(150*n), 250, 75, 75, null);
			
			if (posCorrELugar == true){
				g.drawImage(cartaLugar[posCorr],800, 350, 150,200,null);
				posCorrELugar = false;
			}
			if(posCorrEChance == true){
				g.drawImage(cartaChance[VirarCarta()],800, 350, 150,200,null);
				posCorrEChance = false;
			}
			
		} /* end if */
		
	} /* END public void paint(Graphics g) */
	
	
	/* Randomizacao do valor do dado */
	public int JogarDados()
	{
		int res = 0;
		for(int n = 0; n < 2; n++){
			qtdDados[n] = ranDado.nextInt(6) + 1;
			res+=qtdDados[n];
			System.out.println(qtdDados[n]);
		}
		return res;
	} /*END public int JogarDados() */
	
	/* Randomizacao do valor da carta */
	public int VirarCarta()
	{
		int res = ranChance.nextInt(30); 
		System.out.println(res);
		return res;
	} /*END public int VirarCarta() */
	
	/* Classe do botao de jogar dado */
	public class jogarDadosButton_Click implements ActionListener
	{
		/* Implementa acao do jogar dado */
		public void actionPerformed(ActionEvent e)
		{
			int resDado = JogarDados();
			
			tab.jogadores[jogCorr].move(resDado);
			
			repaint();
			
			mostraDados=true;
			
			posCorr = tab.jogadores[jogCorr].retornaPos();
			
			if (posCorr==1||posCorr==3||posCorr==4||posCorr==5||posCorr==6||posCorr==7||posCorr==8||posCorr==9
					||posCorr==11||posCorr==13||posCorr==14||posCorr==15||posCorr==17||posCorr==19||posCorr==21
					||posCorr==23||posCorr==25||posCorr==26||posCorr==28||posCorr==29){
				posCorrELugar = true;
			}else if (posCorr==2||posCorr==12||posCorr==16||posCorr==22||posCorr==27||posCorr==37){
				posCorrEChance = true;
			}else posCorrEAuto = true;
			
			
			/*if (binSearch(posLugar, PosCorrente) == -1){
				correnteLugar = true;
			}
			else if (binSearch(this.posChance,PosCorrente)== -1){
				correnteChance = true;
			}
			else correnteAuto = true;
			*/
			
		} /* END public void actionPerformed(ActionEvent e) */
	} /* END jogarDadosButton_Click */
	
	/*
	public static int binSearch (int vet, int pos){
		int bottom = 0;
		int top = vet.lenght = 1;
		int middle;
		boolean found = false;
		int location = -1;
		
		while (bottom<=top&&!found){
			
			middle = (bottom+top)/2;
			
			if (vet[middle]==pos){
				found = true;
				location = middle;
			}else if (vet[middle]<pos){
				bottom = middle +1;
			}else {
				top = middle -1;
			}
		}
		return location;
		
	}
	*/
}