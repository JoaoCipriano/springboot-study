package com.joaolucas.cursomc.services;

import com.joaolucas.cursomc.security.UserSS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private ClienteService clienteService;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        var cli = clienteService.findByEmail(email);
        if (cli == null)
            throw new UsernameNotFoundException(email);

        return new UserSS(cli.getId(), cli.getEmail(), cli.getSenha(), cli.getPerfis());
    }
}
