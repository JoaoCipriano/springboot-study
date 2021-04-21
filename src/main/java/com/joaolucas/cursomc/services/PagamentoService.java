package com.joaolucas.cursomc.services;

import com.joaolucas.cursomc.domain.Pagamento;
import com.joaolucas.cursomc.repositories.PagamentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PagamentoService {

    @Autowired
    private PagamentoRepository repo;

    public Pagamento insert(Pagamento pagamento) {
        return repo.save(pagamento);
    }
}
