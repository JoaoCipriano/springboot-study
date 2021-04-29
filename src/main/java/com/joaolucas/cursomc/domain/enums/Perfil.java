package com.joaolucas.cursomc.domain.enums;

import java.util.stream.Stream;

public enum Perfil {

	ADMIN(1, "ROLE_ADMIN"),
	CLIENTE(2, "ROLE_CLIENTE");

	private int cod;
	private String descricao;

	private Perfil(int cod, String descricao) {
		this.cod = cod;
		this.descricao = descricao;
	}
	
	public int getCod() {
		return cod;
	}
	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer cod) {
		return Stream.of(Perfil.values())
				.filter(e -> cod.equals(e.getCod()))
				.findAny()
				.orElseThrow(() -> new IllegalArgumentException("Id inválido: " + cod));					
	}
}
