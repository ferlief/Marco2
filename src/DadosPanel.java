import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

@SuppressWarnings("serial")
public class DadosPanel extends JPanel{
	JLabel jogadoresLabel;
	JComboBox<String> jogadorComboBox;
	DadosPanel(Jogador[] jogadores)
	{
		String[] nomeJogadores = new String[jogadores.length]; 
		for(int i = 0; i < jogadores.length; i++)
		{
			if(jogadores[i] != null) {
				nomeJogadores[i] = jogadores[i].nome;
			}
		}
		jogadoresLabel = new JLabel("Selecione Jogador");
		jogadorComboBox = new JComboBox<String>(nomeJogadores);
		jogadorComboBox.addActionListener(new NumJogador());
		add(jogadoresLabel);
		add(jogadorComboBox);
		setBackground(Color.white);
	}
	
	
	public class NumJogador implements ActionListener
	{
		public void actionPerformed(ActionEvent e)
		{
			jogadoresLabel.setText(jogadorComboBox.getSelectedItem().toString() + " Selecionado\n");
		}
	}
}
