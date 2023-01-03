package enums;

public enum TipoResultadoMiniHistoria {
	HABILIDADE("Habilidade"),
	ITEM_MANA("Item Mana"),
	ITEM_VIDA("Item Vida"),
	ITEM_HIBRIDA("Item Hibrido"),
	ARMA("Arma"),
	DANO("Dano"),
	CURA("Cura"),
	NENHUM("Nenhum");

	private String valor;

	TipoResultadoMiniHistoria(String valor) {
        this.valor = valor;
    }

	public String getValor() {
		return valor;
	}
}