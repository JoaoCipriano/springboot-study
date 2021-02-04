package com.joaolucas.cursomc.domain.enums;

import java.util.stream.Stream;

public enum TipoCliente {
	
	PESSOAFISICA(1, "Pessoa Física"),
	PESSOAJURIDICA(2, "Pessoa Jurídica");
	
	private int cod;
	private String descricao;
	
	private TipoCliente(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	public String getDescricao() {
		return descricao;
	}
	
	public static TipoCliente toEnum(Integer cod) {
		return Stream.of(TipoCliente.values())
				.filter(e -> cod.equals(e.getCod()))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException("Id inválido: " + cod));					
	}
}
