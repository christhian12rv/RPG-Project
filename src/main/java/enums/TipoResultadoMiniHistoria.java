package enums;

public enum TipoResultadoMiniHistoria {
	HABILIDADE("Habilidade"),
	ITEM("Item"),
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