package com.joaolucas.cursomc.services;

import com.joaolucas.cursomc.domain.*;
import com.joaolucas.cursomc.domain.enums.EstadoPagamento;
import com.joaolucas.cursomc.dto.PedidoNewDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.joaolucas.cursomc.repositories.PedidoRepository;
import com.joaolucas.cursomc.services.exceptions.ObjectNotFoundException;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class PedidoService {
	
	@Autowired
	private PedidoRepository repo;

	@Autowired
	private BoletoService boletoService;

	@Autowired
	private PagamentoService pagamentoService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private ItemPedidoService itemPedidoService;

	@Autowired
	private ClienteService clienteService;

	@Autowired
	private EmailService emailService;
	
	public Pedido find(Integer id) {
		return repo.findById(id)
				.orElseThrow(() -> new ObjectNotFoundException(
						"Objeto não encontrado Id: " + id + ", Tipo: " + Pedido.class.getName()));
	}

	@Transactional
    public Pedido insert(Pedido obj) {
		obj.setId(null);
		obj.setInstante(new Date());
		obj.setCliente(clienteService.find(obj.getCliente().getId()));
		obj.getPagamento().setEstado(EstadoPagamento.PENDENTE);
		obj.getPagamento().setPedido(obj);
		if (obj.getPagamento() instanceof PagamentoComBoleto) {
			PagamentoComBoleto pagto = (PagamentoComBoleto) obj.getPagamento();
			boletoService.preencherPagamentoComBoleto(pagto, obj.getInstante());
		}
		obj = repo.save(obj);
		pagamentoService.insert(obj.getPagamento());
		for (ItemPedido ip : obj.getItens()) {
			ip.setDesconto(0.0);
			ip.setProduto(produtoService.find(ip.getProduto().getId()));
			ip.setPreco(ip.getProduto().getPreco());
			ip.setPedido(obj);
		}
		itemPedidoService.insertListPedidos(obj.getItens());
		emailService.sendOrderConfirmationHtmlEmail(obj);
		return obj;
    }

    public Pedido fromDTO(PedidoNewDTO pedidoNewDTO) {
		Pedido pedido = new Pedido();

		Cliente cliente = new Cliente(pedidoNewDTO.getCliente());

		Endereco endereco =  new Endereco(pedidoNewDTO.getEnderecoDeEntrega());

		Set<ItemPedido> itens = pedidoNewDTO.getItens().stream().map(ItemPedido::new).collect(Collectors.toSet());

		pedido.setCliente(cliente);
		pedido.setEnderecoDeEntrega(endereco);
		pedido.setItens(itens);
		pedido.setPagamento(pedidoNewDTO.getPagamento());
		return pedido;
	}
}
