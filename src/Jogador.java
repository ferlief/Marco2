import java.awt.*;

public class Jogador {

	public static int cont = 0;
	
	public Image img;
	public String nome;
	public int _x, _y;
	public int dinheiro;
	public Territorio[] propiedades;
	public Territorio territorioAtual;
	public int numPropiedades = 0;
	
	public int pos;
	private int xi, yi;
	
	public Jogador() {
		pos = 0;
		_x = 0;
		_y = 0;
		xi = 0;
		yi = 0;
		this.move(0);
		dinheiro = 5000;
		cont++;
		propiedades = new Territorio[40];
	}
	
	public Jogador(int x, int y) {
		pos = 0;
		_x = x;
		_y = y;
		xi = x;
		yi = y;
		this.move(0);
		cont++;
		nome = "Jogador " + cont;
		propiedades = new Territorio[40];
		dinheiro = 5000;
	}
	
	public Jogador(int x, int y, String nome) {
		pos = 0;
		_x = x;
		_y = y;
		xi = x;
		yi = y;
		this.nome = nome;
		this.move(0);
		cont++;
		propiedades = new Territorio[40];
		dinheiro = 5000;
	}
	
	
	// Retorna true caso o jogador consiga pagar o aluguel, false caso ele não tenha fundos
	public boolean pagarAluguel(Jogador dono, int aluguel)
	{
		this.dinheiro -= aluguel;
		if(this.dinheiro < 0){
			dono.dinheiro = aluguel + this.dinheiro;
			return false;
		}
		dono.dinheiro += aluguel;
		return true;
	}
	
	public void move(int steps) {
		pos+=steps;
		
		if(pos > 39) {
			pos -= 40;
		}
		
		if(pos < 10) {
			_x = xi + 500;
			_y = yi + 50 * pos;
		} else if( pos < 20) {
			_x = xi + 500 - (50 * (pos - 10));
			_y = yi + 500;
		} else if( pos < 30) {
			_x = xi;
			_y = yi + 500 - (50 * (pos - 20));
		} else {
			_x = xi + 50 * (pos - 30);
			_y = yi;
		}
	}
}
