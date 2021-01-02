package br.com.cod3r.cm.modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public class Tabuleiro {

    private int linhas;
    private int colunas;
    private int minas;

    private final List<Campo> campos = new ArrayList<>();

    public Tabuleiro(int linhas, int colunas, int minas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.minas = minas;

        gerarCampos();
        associarVizinhos();
        sortearMinas();
    }

    // Usnado a Stream paralela visando também mais velocidade no algoritmo
    public void abrir(int linha, int coluna){
        try {
            campos.parallelStream()
                    .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                    .findFirst() // Optional<Campo>
                    .ifPresent(c -> c.abrir());
        } catch (Exception e){
            // FIXME Ajustar a implementação do método abrir
            campos.forEach(c -> c.setAberto(true));
            throw e;
        }
    }

    public void alterarMarcacao(int linha, int coluna){
        campos.parallelStream()
                .filter(c -> c.getLinha() == linha && c.getColuna() == coluna)
                .findFirst() // Optional<Campo>
                .ifPresent(c -> c.alternarMarcacao());
    }

    private void gerarCampos() {
        for (int linha = 0; linha < linhas; linha++){
            for(int coluna = 0; coluna < colunas; coluna++){
                campos.add(new Campo(linha, coluna));
            }
        }
    }

    private void associarVizinhos() {
        for (Campo c1: campos){
            for (Campo c2: campos){
                c1.adicionarVizinho(c2);
            }
        }
    }

    // Quando as minhas armadas forem igual o número de minas determinado, o laço Do/While é encerrado
    private void sortearMinas() {
        long minasArmadas = 0;
        Predicate<Campo> minado =  c -> c.isMinado();

        do {
            int aleatorio = (int) (Math.random() * campos.size()); // Gerando um valor aleatório entre 0 e o tamamho da lista -1, fazendo o CAST para 'int'.
            campos.get(aleatorio).minar();
            minasArmadas = campos.stream().filter(minado).count();
        } while(minasArmadas < minas);
    }

    // Se todos os campos tem o objetivo alcançado (deram 'match'), o objetivo foi alcançado
    public boolean objetivoAlcancado(){
        return campos.stream().allMatch(c -> c.objetivoAlcancado());
    }

    public void reiniciar(){
        campos.stream().forEach(c -> c.reiniciar());
        sortearMinas();
    }
}
