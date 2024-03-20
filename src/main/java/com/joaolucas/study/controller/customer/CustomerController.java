package com.joaolucas.study.controller.customer;

import com.joaolucas.api.CustomerApi;
import com.joaolucas.model.CustomerRequest;
import com.joaolucas.model.CustomerResponse;
import com.joaolucas.model.PageableCustomerResponse;
import com.joaolucas.study.application.customer.CustomerApplicationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
@RequiredArgsConstructor
public class CustomerController implements CustomerApi {

    private final CustomerApplicationService customerApplicationService;

    @Override
    public ResponseEntity<CustomerResponse> findById(@PathVariable Integer id) {
        var customerResponse = customerApplicationService.findById(id);
        return ResponseEntity.ok(customerResponse);
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Override
    public ResponseEntity<PageableCustomerResponse> findPageableCustomer(
            @RequestParam(value = "page", defaultValue = "0") Integer page,
            @RequestParam(value = "linesPerPage", defaultValue = "24") Integer linesPerPage,
            @RequestParam(value = "orderBy", defaultValue = "name") String orderBy,
            @RequestParam(value = "direction", defaultValue = "ASC") String direction) {
        var pageableCustomerResponses = customerApplicationService.findPage(page, linesPerPage, orderBy, direction);
        return ResponseEntity.ok(pageableCustomerResponses);
    }

    @Override
    public ResponseEntity<CustomerResponse> findByEmail(String email) {
        var customerResponse = customerApplicationService.findByEmail(email);
        return ResponseEntity.ok(customerResponse);
    }

    @Override
    public ResponseEntity<Void> save(CustomerRequest customerRequest) {
        var customerResponse = customerApplicationService.insert(customerRequest);
        var uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(customerResponse.getId())
                .toUri();
        return ResponseEntity.created(uri).build();
    }

    @Override
    public ResponseEntity<Void> updateById(Integer id, CustomerRequest customerRequest) {
        customerApplicationService.update(customerRequest, id);
        return ResponseEntity.noContent().build();
    }

    @PreAuthorize("hasAnyRole('ADMIN')")
    @Override
    public ResponseEntity<Void> deleteById(@PathVariable Integer id) {
        customerApplicationService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Void> uploadProfilePicture(@RequestParam(name = "file") MultipartFile file) {
        var uri = customerApplicationService.uploadProfilePicture(file);
        return ResponseEntity.created(uri).build();
    }
}
