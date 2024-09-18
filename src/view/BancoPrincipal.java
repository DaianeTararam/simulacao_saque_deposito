package view;
import controller.Banco;
public class BancoPrincipal {

	public static void main(String[] args) {
		
		
		// haverá 30 transações 
		for (int i = 1; i <= 30; i++) {
			int codigo = (int)(Math.random() * (50001 - 10000))+ 10000;
			int valor= (int)(Math.random() * 100) + 1;
			int saldo = (int)(Math.random() * (5001 - 500))+ 500;
			boolean sacando  = Math.random() < 0.5;
			Banco banco = new Banco(codigo, saldo, valor, sacando);
			banco.start();
		}
		
		
	}

}
