package entity;

public enum DificuldadeMonstro {
    INICIANTE("Iniciante"),
    FACIL("Fácil"),
    MEDIO("Médio"),
    DIFICIL("Difícil"),
    CHEFE("Chefe");

    private String valor;

    DificuldadeMonstro(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}