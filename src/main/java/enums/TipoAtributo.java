package enums;

public enum TipoAtributo {
    FORCA("Força"),
    DESTREZA("Destreza"),
    SABEDORIA("Sabedoria"),
    DEFESA("Defesa"),
    CURA("Cura");

    private String valor;

    TipoAtributo(String valor) {
        this.valor = valor;
    }

    public String getValor() {
        return valor;
    }
}