package jogo.lgj;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class UmJogo extends JFrame {
    private final int FPS = 1000 / 20; // 50 FPS

    class Elemento {
        public int x, y, largura, altura;
        public float velocidade;

        public Elemento(int x, int y, int largura, int altura) {
            this.x = x;
            this.y = y;
            this.largura = largura;
            this.altura = altura;
        }
    }

    private JPanel tela;
    private boolean jogando = true;
    private boolean fimDeJogo = false;

    private Elemento tiro;
    private Elemento jogador;
    private Elemento[] blocos;

    private int pontos;
    private int larg = 50; // largura padrão
    private int linhaLimite = 350;
    private java.util.Random r = new java.util.Random();

    private boolean[] controleTecla = new boolean[4];

    public UmJogo() {
        tela = new JPanel() {
            private static final long serialVersionUID = 1L;

            @Override
            public void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.WHITE);
                g.fillRect(0, 0, getWidth(), getHeight());

                // Desenha o tiro
                g.setColor(Color.RED);
                g.fillRect(tiro.x, tiro.y, tiro.largura, tiro.altura);

                // Desenha o jogador
                g.setColor(Color.GREEN);
                g.fillRect(jogador.x, jogador.y, jogador.largura, jogador.altura);

                // Desenha os blocos
                g.setColor(Color.BLUE);
                for (Elemento bloco : blocos) {
                    g.fillRect(bloco.x, bloco.y, bloco.largura, bloco.altura);
                }

                // Linha de limite
                g.setColor(Color.GRAY);
                g.drawLine(0, linhaLimite, getWidth(), linhaLimite);

                // Exibe os pontos
                g.setColor(Color.BLACK);
                g.drawString("Pontos: " + pontos, 10, 20);

                // Mensagem de fim de jogo
                if (fimDeJogo) {
                    g.setColor(Color.RED);
                    g.drawString("FIM DE JOGO", getWidth() / 2 - 50, getHeight() / 2);
                }
            }
        };

        getContentPane().add(tela);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(640, 480);
        setVisible(true);
        setResizable(false);

        // Inicializa os elementos do jogo
        tiro = new Elemento(0, 0, 5, 20);
        jogador = new Elemento(0, 0, larg, larg);
        jogador.velocidade = 5;

        jogador.x = tela.getWidth() / 2 - jogador.largura / 2;
        jogador.y = tela.getHeight() - jogador.altura - 10;

        blocos = new Elemento[5];
        for (int i = 0; i < blocos.length; i++) {
            int espaco = i * larg + 10 * (i + 1);
            blocos[i] = new Elemento(espaco, 0, larg, larg);
            blocos[i].velocidade = 1;
        }

        // Adiciona KeyListener
        this.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
            }

            @Override
            public void keyPressed(KeyEvent e) {
                setaTecla(e.getKeyCode(), true);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                setaTecla(e.getKeyCode(), false);
            }
        });
    }

    public void inicia() {
        long proximaAtualizacao = 0;

        while (jogando) {
            if (System.currentTimeMillis() >= proximaAtualizacao) {
                atualizaJogo();
                tela.repaint();
                proximaAtualizacao = System.currentTimeMillis() + FPS;
            }
        }
    }

    private void atualizaJogo() {
        if (fimDeJogo)
            return;

        // Movimento do jogador
        if (controleTecla[2]) // Esquerda
            jogador.x -= jogador.velocidade;
        else if (controleTecla[3]) // Direita
            jogador.x += jogador.velocidade;

        // Restringe o jogador à tela
        if (jogador.x < 0)
            jogador.x = tela.getWidth() - jogador.largura;
        if (jogador.x + jogador.largura > tela.getWidth())
            jogador.x = 0;

        // Atualiza o tiro
        tiro.y = 0;
        tiro.x = jogador.x + jogador.largura / 2;

        // Atualiza blocos
        for (Elemento bloco : blocos) {
            if (bloco.y > linhaLimite) {
                fimDeJogo = true;
                break;
            }

            if (colide(bloco, tiro)) {
                bloco.y = -bloco.altura; // Remove o bloco da tela
                pontos += 10; // Adiciona pontos
            } else {
                bloco.y += bloco.velocidade;
            }
        }
    }

    private boolean colide(Elemento a, Elemento b) {
        return a.x < b.x + b.largura && a.x + a.largura > b.x && a.y < b.y + b.altura && a.y + a.altura > b.y;
    }

    private void setaTecla(int tecla, boolean pressionada) {
        switch (tecla) {
            case KeyEvent.VK_ESCAPE:
                jogando = false;
                dispose();
                break;
            case KeyEvent.VK_LEFT:
                controleTecla[2] = pressionada;
                break;
            case KeyEvent.VK_RIGHT:
                controleTecla[3] = pressionada;
                break;
        }
    }

    public static void main(String[] args) {
        UmJogo jogo = new UmJogo();
        jogo.inicia();
    }
}
