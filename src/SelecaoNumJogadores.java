import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

@SuppressWarnings("serial")
public class SelecaoNumJogadores extends JFrame 
{
	JButton Selecione;
	JButton UmJogador;
	JButton DoisJogadores;
	JButton TresJogadores;
	JButton QuatroJogadores;
	JButton CincoJogadores;
	JButton SeisJogadores;

	public SelecaoNumJogadores(String s)
	{
		super(s);
		setLayout(null);
		setBackground(Color.white);
		Container c = getContentPane();
		c.setBackground(Color.white);
		this.setExtendedState(JFrame.MAXIMIZED_BOTH); 
		
		Selecione = new JButton("Selecione o número de jogadores abaixo:");
		Selecione.setBounds(500,50,500,30);
		add(Selecione);

		DoisJogadores = new JButton("Dois Jogadores");
		DoisJogadores.setBounds(500,100,200,30);
		DoisJogadores.addActionListener(new selecionaJogadorButton_Click(2));
		add(DoisJogadores);

		TresJogadores = new JButton("Tres Jogadores");
		TresJogadores.setBounds(500,125,200,30);
		TresJogadores.addActionListener(new selecionaJogadorButton_Click(3));
		add(TresJogadores);

		QuatroJogadores = new JButton("Quatro Jogadores");
		QuatroJogadores.setBounds(500,150,200,30);
		QuatroJogadores.addActionListener(new selecionaJogadorButton_Click(4));
		add(QuatroJogadores);

		CincoJogadores = new JButton("Cinco Jogadores");
		CincoJogadores.setBounds(500,175,200,30);
		CincoJogadores.addActionListener(new selecionaJogadorButton_Click(5));
		add(CincoJogadores);

		SeisJogadores = new JButton("Seis Jogadores");
		SeisJogadores.setBounds(500,200,200,30);
		SeisJogadores.addActionListener(new selecionaJogadorButton_Click(6));
		add(SeisJogadores);
		
		
		        
		
	}
	public class selecionaJogadorButton_Click implements ActionListener
	{
		int numJogadores;
		public selecionaJogadorButton_Click(int num){
			this.numJogadores=num;
		}
		public void actionPerformed(ActionEvent e)
		{
			Mesa f = new Mesa(this.numJogadores, "Banco Imobiliário");
			f.setVisible(true);
			setVisible(false);
		}
	}
	
}