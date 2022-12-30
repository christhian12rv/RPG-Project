package enums;

public enum TipoDanoHabilidade {
    FIXO("Fixo"),
    PORCENTAGEM("Porcentagem"),
    INTERVALO("Intervalo");

    private String valor;

    TipoDanoHabilidade(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}