package jogo.lgj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class InterativoMouse extends JFrame{
	
	private JPanel tela;
	private  int px, py;
	private Point mouseClick = new Point();
	private boolean jogando= true;
	private final int FPS = 1000/20; // 50
	
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
		px = mouseClick.x;
		py = mouseClick.y;
	}
	
	public InterativoMouse() {
		
		tela = new JPanel() {
			@Override
			public void paintComponent(Graphics g) {
				g.setColor(Color.WHITE);
				g.fillRect(0, 0, tela.getWidth(), tela.getHeight());
				
				int x = px;
				int y = py;
				
				g.setColor(Color.BLUE);
				g.fillRect(x, y, 40, 40);
				g.drawString("Agora estou em " + x + "x" + y +"y", 5, 10);
			}
		};
		
		getContentPane().add(tela);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(640, 480);
		setVisible(true);
		
		tela.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent e) {
				//click do mouse
				mouseClick = e.getPoint();
				
			}

			@Override
			public void mousePressed(MouseEvent e) {
				// botao pressionado
				
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				// botão liberado
				
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				// mouse entrou na tela
				
			}

			@Override
			public void mouseExited(MouseEvent e) {
				//mouse saiu da tela
				
			}
			
		});
	}
	public static void main(String[] args) {
		InterativoMouse jogo = new InterativoMouse();
		jogo.inicia();
	}

}
