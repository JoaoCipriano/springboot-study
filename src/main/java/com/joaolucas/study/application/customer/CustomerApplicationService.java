package com.joaolucas.study.application.customer;

import com.joaolucas.study.application.customer.mapper.CustomerMapper;
import com.joaolucas.study.controller.customer.model.CustomerRequest;
import com.joaolucas.study.controller.customer.model.CustomerResponse;
import com.joaolucas.study.domain.customer.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@Service
@RequiredArgsConstructor
public class CustomerApplicationService {

    private final CustomerService customerService;
    private final CustomerMapper customerMapper;

    public CustomerResponse findById(Integer id) {
        return customerMapper.toResponse(customerService.findById(id));
    }

    public Page<CustomerResponse> findPage(Integer page,
                                           Integer linesPerPage,
                                           String orderBy,
                                           String direction) {
        return customerMapper.toPageableResponse(customerService.findPage(page, linesPerPage, orderBy, direction));
    }

    public CustomerResponse findByEmail(String email) {
        return customerMapper.toResponse(customerService.findByEmail(email));
    }

    public CustomerResponse insert(CustomerRequest customerRequest) {
        return customerMapper.toResponse(customerService.insert(customerRequest));
    }

    public void update(CustomerRequest customerRequest, Integer id) {
        var customerModel = customerMapper.toModel(customerRequest);
        customerService.update(customerModel, id);
    }

    public void delete(Integer id) {
        customerService.delete(id);
    }

    public URI uploadProfilePicture(MultipartFile file) {
        return customerService.uploadProfilePicture(file);
    }
}
