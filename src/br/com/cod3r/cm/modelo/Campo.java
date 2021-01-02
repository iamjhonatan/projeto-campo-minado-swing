package br.com.cod3r.cm.modelo;


import java.util.ArrayList;
import java.util.List;

public class Campo {

    private final int linha;
    private final int coluna;

    private boolean aberto;
    private boolean minado;
    private boolean marcado;

    private List<Campo> vizinhos = new ArrayList<>(); //Autorrelacionamento 1 para n

    Campo(int linha, int coluna){
        this.linha = linha;
        this.coluna = coluna;
    }

    boolean adicionarVizinho(Campo vizinho){
        // Caso linha e coluna sejam diferentes do seu, significa que seu vizinho está na sua diagonal.
        boolean linhaDiferente = linha != vizinho.linha;
        boolean colunaDiferente = coluna != vizinho.coluna;
        boolean diagonal = linhaDiferente && colunaDiferente;

        // Calculando as distancias com namero absoluto como retorno (positivo).
        int deltaLinha = Math.abs(linha - vizinho.linha);
        int deltaColuna = Math.abs(coluna - vizinho.coluna);
        int deltaGeral = deltaColuna + deltaLinha;

        // Cenarios onde tenho vizinho e adicionando na lista.
        if (deltaGeral == 1 && !diagonal){
            vizinhos.add(vizinho);
            return true;
        } else if (deltaGeral == 2 && diagonal){
            vizinhos.add(vizinho);
            return true;
        } else {
            return false;
        }
    }

    // Só posso alternar a marcacao de um campo que esteja fechado.
    void alternarMarcacao() {
        if (!aberto) {
            marcado = !marcado;
        }
    }

    // Funcao para abrir um campo
    boolean abrir(){
        if(!aberto && !marcado){
            aberto = true;

            // Interrompendo o processo de abertura e retornando para quem chamou, com a exceção
            if(minado){
                // TODO Implementar nova versão
            }
            if(vizinhancaSegura()){ // Usando uma função recursiva para continuar a abrir a vizinhanca, desde que seja segura
                vizinhos.forEach(v -> v.abrir());
            }
            return true;
        } else {
            return false;
        }
    }

    // Usando a Stream e a funcao 'noneMatch', comparando os 'vizinhos' com 'minado' e verificando se as variaveis não 'colidem'
    boolean vizinhancaSegura() {
        return vizinhos.stream().noneMatch(v -> v.minado);
    }

    void minar(){
        minado = true;
    }

    public boolean isMinado(){
        return minado;
    }

    public boolean isMarcado() {
        return marcado;
    }

    void setAberto(boolean aberto) {
        this.aberto = aberto;
    }

    public boolean isAberto() {
        return aberto;
    }

    public boolean isFechado() {
        return !isAberto();
    }

    public int getLinha() {
        return linha;
    }

    public int getColuna() {
        return coluna;
    }

    boolean objetivoAlcancado(){
        boolean desvendado = !minado && aberto;
        boolean protegido = minado && marcado;
        return desvendado || protegido;
    }

    // Filtrando apenas os campos que são minados, contando-os
    long minasNaVizinhanca(){
        return vizinhos.stream().filter(v -> v.minado).count();
    }

    void reiniciar(){
        aberto = false;
        minado = false;
        marcado = false;
    }
}
