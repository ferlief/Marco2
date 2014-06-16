import java.awt.Image;


public class Territorio {
	public int preco;
	public int aluguel;
	public Tipo tipo;
	public Jogador dono;
	public Image img;
	public Image point;
	public int x, y;
	
	public enum Tipo { propriedade, cartaSorte, vaParaPrisao, bonus, imposto, neutro }
	
	Territorio() {}
	
	Territorio(int preco, int aluguel, Tipo tipo) {
		this.tipo = tipo;
		this.preco = preco;
		this.aluguel = aluguel;
	}
	
}
