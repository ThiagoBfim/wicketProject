package n3m6.entity.enuns;

public enum Categoria {


	PARTICULAR("Particular"), ALUGUEL("Aluguel"), OFICIAL("Oficial");

	private String descricao;

	Categoria(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}
}
