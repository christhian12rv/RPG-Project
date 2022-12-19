package enums;

public enum RaridadeArma {
	COMUM("Comum"),
	INCOMUM("Incomum"),
	RARO("Raro"),
	LENDARIO("Lendário"),
	MISTICO("Místico");

	private String valor;

	RaridadeArma(String valor) {
        this.valor = valor;
    }

	public String getValor() {
		return valor;
	}
}