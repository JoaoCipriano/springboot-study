package com.joaolucas.study.dto;

public record EnderecoDTO(
        Integer id,
        String logradouro,
        String numero,
        String complemento,
        String bairro,
        String cep) {
}
