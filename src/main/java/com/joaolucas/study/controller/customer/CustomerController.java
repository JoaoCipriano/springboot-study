package com.joaolucas.study.controller.customer;

import com.joaolucas.study.application.customer.CustomerApplicationService;
import com.joaolucas.study.controller.customer.model.CustomerRequest;
import com.joaolucas.study.controller.customer.model.CustomerResponse;
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

@RestController
@RequestMapping(value = "/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerApplicationService customerApplicationService;

    @GetMapping(value = "/{id}")
    public ResponseEntity<CustomerResponse> findById(@PathVariable Integer id) {
        var customerResponse = customerApplicationService.findById(id);
        return ResponseEntity.ok(customerResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping(value = "/page")
    public ResponseEntity<Page<CustomerResponse>> findPage(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        var pageableCustomerResponses = customerApplicationService.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok(pageableCustomerResponses);
    }

    @GetMapping(value = "/email")
    public ResponseEntity<CustomerResponse> find(@RequestParam(value = "value") String email) {
        var customerResponse = customerApplicationService.findByEmail(email);
        return ResponseEntity.ok(customerResponse);
    }

    @PostMapping
    public ResponseEntity<Void> insert(@Valid @RequestBody CustomerRequest customerRequest) {
        var customerResponse = customerApplicationService.insert(customerRequest);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(customerResponse.id()).toUri();
        return ResponseEntity.created(uri).build();
    }

    @PutMapping(value = "/{id}")
    public ResponseEntity<Void> update(@Valid @RequestBody CustomerRequest customerRequest, @PathVariable Integer id) {
        customerApplicationService.update(customerRequest, id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        customerApplicationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping(value = "/picture")
    public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file) {
        var uri = customerApplicationService.uploadProfilePicture(file);
        return ResponseEntity.created(uri).build();
    }
}
