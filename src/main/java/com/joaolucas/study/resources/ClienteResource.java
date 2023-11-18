package com.joaolucas.study.resources;

import com.joaolucas.study.domain.Cliente;
import com.joaolucas.study.dto.ClienteDTO;
import com.joaolucas.study.dto.ClienteNewDTO;
import com.joaolucas.study.services.ClienteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping(value = "/clientes")
@RequiredArgsConstructor
public class ClienteResource {

    private final ClienteService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<Cliente> find(@PathVariable Integer id) {
        Cliente obj = service.find(id);
        return ResponseEntity.ok().body(obj);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping()
    public ResponseEntity<List<ClienteDTO>> findAll() {
        List<ClienteDTO> listDto = service.findAll()
                .stream()
                .map(ClienteDTO::new)
                .toList();
        return ResponseEntity.ok().body(listDto);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/page")
    public ResponseEntity<Page<ClienteDTO>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "nome") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        Page<ClienteDTO> listDto = service.findPage(page, linesPerPage, orderBy, direction)
                .map(ClienteDTO::new);
        return ResponseEntity.ok().body(listDto);
    }

    @GetMapping(value = "/email")
    public ResponseEntity<Cliente> find(@RequestParam(value = "value") String email) {
        Cliente obj = service.findByEmail(email);
        return ResponseEntity.ok().body(obj);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody ClienteNewDTO objDto) {
        Cliente obj = service.fromDTO(objDto);
        service.insert(obj);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(obj.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody ClienteDTO objDto, @PathVariable Integer id) {
        Cliente obj = service.fromDTO(objDto);
        service.update(obj);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/picture")
    public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file) {
        URI uri = service.uploadProfilePicture(file);
        return ResponseEntity.created(uri).build();
    }
}
