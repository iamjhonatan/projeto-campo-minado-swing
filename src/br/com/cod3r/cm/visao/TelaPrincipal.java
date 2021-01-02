package br.com.cod3r.cm.visao;

import br.com.cod3r.cm.modelo.Tabuleiro;

import javax.swing.*;
import java.awt.*;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {
        Tabuleiro tabuleiro = new Tabuleiro(16, 30, 50);
        add(new PainelTabuleiro(tabuleiro));

        setTitle("Campo Minado"); // Título da janela
        setSize(690, 438); // Tamanho da janela
        setLocationRelativeTo(null); // Local onde a janela será iniciada
        setDefaultCloseOperation(DISPOSE_ON_CLOSE); // Encerrando o programa ao clicar no 'X'
        setVisible(true);
    }

    public static void main(String[] args) {
        new TelaPrincipal();
    }
}
