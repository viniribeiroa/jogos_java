package jogo.lgj.base;

public class Util {
	
	public static boolean colide(Elemento a, Elemento b) {
		if(!a.isAtivo() || !b.isAtivo())
			return false;
		//posição no eixo X + largura do elemento A e B
		final int plA = a.getLargura();
		final int plB = b.getLargura();
		
		//posicao no eixo Y + altura do elemento A e B
		final int paA = a.getPy() + a.getAltura();
		final int paB = b.getPy() + b.getAltura();
		
		if(plA > b.getPx() && a.getPx() < plB && paA > b.getPy() && a.getPy() < paB) {
			return true;
		}
		return false;
	}

}
