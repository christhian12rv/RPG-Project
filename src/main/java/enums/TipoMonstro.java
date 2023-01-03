package enums;

public enum TipoMonstro {
	MORTO_VIVO("Morto vivo");

	private String valor;

	TipoMonstro(String valor) {
        this.valor = valor;
    }

	public String getValor() {
		return valor;
	}
}