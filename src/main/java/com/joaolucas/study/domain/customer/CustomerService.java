package com.joaolucas.study.domain.customer;

import com.joaolucas.study.application.customer.mapper.CustomerMapper;
import com.joaolucas.study.controller.customer.model.CustomerRequest;
import com.joaolucas.study.domain.customer.model.Customer;
import com.joaolucas.study.domain.exceptions.AuthorizationException;
import com.joaolucas.study.domain.exceptions.DataIntegrityException;
import com.joaolucas.study.domain.exceptions.ObjectNotFoundException;
import com.joaolucas.study.domain.user.UserService;
import com.joaolucas.study.infrastructure.database.address.AddressRepository;
import com.joaolucas.study.infrastructure.database.customer.CustomerEntity;
import com.joaolucas.study.infrastructure.database.customer.CustomerRepository;
import com.joaolucas.study.infrastructure.database.user.Role;
import com.joaolucas.study.infrastructure.database.user.UserEntity;
import com.joaolucas.study.infrastructure.image.ImageService;
import com.joaolucas.study.infrastructure.storage.S3Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.net.URI;

@Service
public class CustomerService {

    private static final String ACCESS_DENIED = "Access denied";

    private final CustomerRepository customerRepository;
    private final UserService userService;
    private final AddressRepository addressRepository;
    private final CustomerMapper customerMapper;
    private final PasswordEncoder passwordEncoder;
    private final S3Service s3Service;
    private final ImageService imageService;
    private final String prefix;
    private final Integer size;

    public CustomerService(CustomerRepository customerRepository,
                           UserService useruserService,
                           AddressRepository addressRepository,
                           CustomerMapper customerMapper,
                           PasswordEncoder passwordEncoder,
                           S3Service s3Service,
                           ImageService imageService,
                           @Value("${img.prefix.client.profile}") String prefix,
                           @Value("${img.profile.size}") Integer size) {
        this.customerRepository = customerRepository;
        this.userService = useruserService;
        this.addressRepository = addressRepository;
        this.customerMapper = customerMapper;
        this.passwordEncoder = passwordEncoder;
        this.s3Service = s3Service;
        this.imageService = imageService;
        this.prefix = prefix;
        this.size = size;
    }

    public Customer findById(Integer id) {
        var userEntity = retrieveAuthorizedUser(id);
        var customerEntity = customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Object not found Id: " + id));
        return customerMapper.toModel(userEntity, customerEntity);
    }

    public CustomerEntity findEntityById(Integer id) {
        retrieveAuthorizedUser(id);
        return customerRepository.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException("Object not found Id: " + id));
    }

    @Transactional
    public Customer insert(CustomerRequest customerRequest) {
        var customerEntity = customerMapper.toEntity(customerRequest);
        var userEntity = customerMapper.toUserEntity(customerRequest, passwordEncoder.encode(customerRequest.password()));
        userEntity = userService.save(userEntity);
        customerEntity = customerRepository.save(customerEntity);
        addressRepository.saveAll(customerEntity.getAddresses());
        return customerMapper.toModel(userEntity, customerEntity);
    }

    public void update(Customer customer, Integer id) {
        var currentCustomerEntity = findEntityById(id);
        var customerEntityWithNewInformation = customerMapper.toCustomerEntity(customer);
        var currentUserEntity = userService.findByEmail(currentCustomerEntity.getEmail());
        var userEntityWithNewInformation = customerMapper.toUserEntity(customer);
        var updatedCustomerEntity = mergeData(currentCustomerEntity, customerEntityWithNewInformation);
        var updatedUserEntity = mergeData(currentUserEntity, userEntityWithNewInformation);
        customerRepository.save(updatedCustomerEntity);
        userService.save(updatedUserEntity);
    }

    private CustomerEntity mergeData(CustomerEntity currentCustomerEntity, CustomerEntity customerEntityWithNewInformation) {
        return customerMapper.toEntity(currentCustomerEntity, customerEntityWithNewInformation);
    }

    private UserEntity mergeData(UserEntity currentUserEntity, UserEntity userEntityWithNewInformation) {
        return customerMapper.toEntity(currentUserEntity, userEntityWithNewInformation);
    }

    public void delete(Integer id) {
        findEntityById(id);
        try {
            customerRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Unable to delete because there are related orders.");
        }
    }

    public Page<Customer> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        var pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return customerMapper.toPageableModel(customerRepository.findAll(pageRequest));
    }

    public Customer findByEmail(String email) {
        var userEntity = retrieveAuthorizedUser(email);
        var customerEntity = customerRepository.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Object not found! Id: " + userEntity.getId() + ", Type: " + CustomerEntity.class.getName()));
        return customerMapper.toModel(userEntity, customerEntity);
    }

    public URI uploadProfilePicture(MultipartFile multipartFile) {
        var userEntity = UserService.authenticated();
        if (userEntity == null)
            throw new AuthorizationException(ACCESS_DENIED);

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
        jpgImage = imageService.cropSquare(jpgImage);
        jpgImage = imageService.resize(jpgImage, size);

        var fileName = prefix + userEntity.getId() + ".jpg";

        return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
    }

    private UserEntity retrieveAuthorizedUser(Integer id) {
        var userEntity = UserService.authenticated();
        if (userEntity == null || userEntity.hasNotRole(Role.ADMIN) && !id.equals(userEntity.getId()))
            throw new AuthorizationException(ACCESS_DENIED);
        return userEntity;
    }

    private UserEntity retrieveAuthorizedUser(String email) {
        var userEntity = UserService.authenticated();
        if (userEntity == null || userEntity.hasNotRole(Role.ADMIN) && !email.equals(userEntity.getEmail()))
            throw new AuthorizationException(ACCESS_DENIED);
        return userEntity;
    }
}
