package jogo.lgj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Interativo2 extends JFrame{
	
	private boolean[] controleTecla = new boolean[4];
	
	private JPanel tela;
	private int px;
	private int py;
	private boolean jogando = true;
	
	private final int FPS = 1000/20;
	
	public void inicia() {
		long prxAtualizacao = 0;
		
		while(jogando) {
			if(System.currentTimeMillis() >= prxAtualizacao) {
				atualizaJogo();
				tela.repaint();
				prxAtualizacao = System.currentTimeMillis() + FPS;
			}
		}
	}
	private void atualizaJogo() {
		if(controleTecla[0])
			py--;
		else if(controleTecla[1])
			py++;
		if(controleTecla[2])
			px--;
		else if(controleTecla[3])
			px++;
	}
	private void setaTecla(int tecla, boolean pressionada) {
		switch(tecla) {
		case KeyEvent.VK_ESCAPE:
			//tecla ESC
			jogando = false;
			dispose();
			break;
		case KeyEvent.VK_UP:
			//seta pra cima
			controleTecla[0] = pressionada;
			break;
		case KeyEvent.VK_DOWN:
			//seta pra baixo
			controleTecla[1] = pressionada;
			break;
		case KeyEvent.VK_LEFT:
			//seta pra esquerda
			controleTecla[2] = pressionada;
			break;
		case KeyEvent.VK_RIGHT:
			//seta pra direita
			controleTecla[3] = pressionada;
			break;
		}
	}
	
	public Interativo2() {
		
		tela = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, tela.getWidth(), tela.getHeight());
				
				int x = tela.getWidth()/2 - 20 + px;
				int y = tela.getHeight()/2 - 20 + py;
				
				g.setColor(Color.BLUE);
				g.fillRect(x, y, 40, 40);
				g.drawString("Agora estou em " + x + "x" + y +"y", 5, 10);
			}
		};
		
		getContentPane().add(tela);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setVisible(true);
		
		this.addKeyListener(new KeyListener() {
		
			@Override
			public void keyTyped(KeyEvent e) {
				
			}
			@Override
			public void keyReleased(KeyEvent e) {
				setaTecla(e.getKeyCode(), false);
			}
			@Override
			public void keyPressed(KeyEvent e) {
				setaTecla(e.getKeyCode(), true);
			}
			
		});
		
	}
	public static void main(String[] args) {
		Interativo2 jogo = new Interativo2();
		jogo.inicia();
	}

}
