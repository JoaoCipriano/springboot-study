package com.joaolucas.cursomc.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaolucas.cursomc.domain.Pedido;
import com.joaolucas.cursomc.repositories.PedidoRepository;
import com.joaolucas.cursomc.services.exceptions.ObjectNotFoundException;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;
	
	public Pedido find(Integer id) {
		return repo.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(
						"Objeto não encontrado Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}
}
