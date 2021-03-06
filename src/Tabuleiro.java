import javax.imageio.ImageIO;
import javax.swing.*;

import java.io.*;
import java.awt.*;
@SuppressWarnings("serial")
public class Tabuleiro extends JPanel{
	//JLabel imageTab = new JLabel();
	

	Image tab;
	Jogador[] jogadores;
	Territorio cartaLugar[];
	static int[][] posJogadores = { {55, 10},
									{45, 15},
									{35, 20},
									{55, 25},
									{45, 30},
									{35, 35} };
	
	static String[] imgJogadores = {"img/black_pin.png",
									"img/blue_pin.png",
									"img/red_pin.png",
									"img/orange_pin.png",
									"img/yellow_pin.png",
									"img/purple_pin.png"};
	
	static String[] pointsJogadores = { "img/black_point.png",
										"img/blue_point.png",
										"img/red_point.png",
										"img/orange_point.png",
										"img/yellow_point.png",
										"img/purple_point.png"};
	
	int jogadorAtual;
	Territorio[] territorios;
	public Tabuleiro(int numJogadores, Territorio cartaLugarMesa[]){
		cartaLugar = cartaLugarMesa;
		Toolkit tk=Toolkit.getDefaultToolkit();
		Dimension screenSize=tk.getScreenSize();
		int sl = (screenSize.width)/2;
		int sa = (screenSize.height);
		jogadores = new Jogador[6];
		territorios = new Territorio[40];
		for(int i = 0; i < numJogadores; i++) {
			jogadores[i] = new Jogador(posJogadores[i][0], posJogadores[i][1]);
		}
		jogadorAtual = numJogadores-1;
		
		try
		{
			setBounds(0, 0, sl, sa);
			
			tab=ImageIO.read(new File("img/tabuleiro.png"));
			for(int i = 0; i < numJogadores; i++) {
				jogadores[i].img = ImageIO.read(new File(imgJogadores[i]));
				jogadores[i].point = ImageIO.read(new File(pointsJogadores[i]));
			}

		}
		catch(IOException e){
		};
	}
	public void paintComponent(Graphics g)
	{ 		
		super.paintComponent(g);
		g.drawImage(tab, 0, 0, 600, 600, null);
		for(int i = 0; i < jogadores.length; i++)
		{
			if(jogadores[i]!=null && jogadores[i].img!=null)
			{
				g.drawImage(jogadores[i].img, jogadores[i]._x, jogadores[i]._y, null);
			}
		}
		for(int i = 0; i < 40; i++) {
			if(cartaLugar[i].point!= null) {
				g.drawImage(cartaLugar[i].point, cartaLugar[i].x + 10, cartaLugar[i].y + 20, null);
			}
		}
	} 
	
	public void comprarTerreno(Jogador j, Territorio t) {
		t.x = j._x;
		t.y = j._y;
		j.dinheiro -= t.preco;
		t.dono = j;
		t.point = j.point;
		j.propiedades[j.numPropiedades] = t;
		j.numPropiedades++;
	}
}