package com.joaolucas.study.services;

import com.joaolucas.study.domain.Cliente;
import com.joaolucas.study.domain.Endereco;
import com.joaolucas.study.domain.ItemPedido;
import com.joaolucas.study.domain.PagamentoComBoleto;
import com.joaolucas.study.domain.Pedido;
import com.joaolucas.study.domain.enums.EstadoPagamento;
import com.joaolucas.study.dto.PedidoNewDTO;
import com.joaolucas.study.repositories.PedidoRepository;
import com.joaolucas.study.security.User;
import com.joaolucas.study.services.exceptions.AuthorizationException;
import com.joaolucas.study.services.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PedidoService {

    private final PedidoRepository repo;

    private final BoletoService boletoService;

    private final PagamentoService pagamentoService;

    private final ProdutoService produtoService;

    private final ItemPedidoService itemPedidoService;

    private final ClienteService clienteService;

    private final EmailService emailService;

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
        if (obj.getPagamento() instanceof PagamentoComBoleto pagto) {
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
        var pedido = new Pedido();

        var cliente = new Cliente(pedidoNewDTO.cliente());

        var endereco = new Endereco(pedidoNewDTO.enderecoDeEntrega());

        Set<ItemPedido> itens = pedidoNewDTO.itens().stream().map(ItemPedido::new).collect(Collectors.toSet());

        pedido.setCliente(cliente);
        pedido.setEnderecoDeEntrega(endereco);
        pedido.setItens(itens);
        pedido.setPagamento(pedidoNewDTO.pagamento());
        return pedido;
    }

    public Page<Pedido> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        User user = UserService.authenticated();
        if (user == null) {
            throw new AuthorizationException("Acesso Negado");
        }
        var pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        var cliente = clienteService.find(user.getId());
        return repo.findByCliente(cliente, pageRequest);
    }
}
