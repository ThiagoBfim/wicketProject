package n3m6.entity;

public enum Tracao {

	COMBUSTAO("Combustão"), ELETRICO("Elétrico");

	private String descricao;

	Tracao(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

}
