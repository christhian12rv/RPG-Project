package enums;

public enum TipoItem {
	VIDA("Vida"),
	MANA("Mana"),
	HIBRIDA("Hibrida");

	private String valor;

	TipoItem(String valor) {
        this.valor = valor;
    }

	public String getValor() {
		return valor;
	}
}