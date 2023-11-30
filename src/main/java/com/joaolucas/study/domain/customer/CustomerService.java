package com.joaolucas.study.domain.customer;

import com.joaolucas.study.infrastructure.image.ImageService;
import com.joaolucas.study.domain.user.UserService;
import com.joaolucas.study.infrastructure.database.city.CityEntity;
import com.joaolucas.study.infrastructure.database.customer.CustomerEntity;
import com.joaolucas.study.infrastructure.database.address.AddressEntity;
import com.joaolucas.study.infrastructure.database.customer.CustomerType;
import com.joaolucas.study.domain.user.Customer;
import com.joaolucas.study.domain.user.NewCustomer;
import com.joaolucas.study.infrastructure.storage.S3Service;
import com.joaolucas.study.infrastructure.database.customer.CustomerRepository;
import com.joaolucas.study.infrastructure.database.address.AddressRepository;
import com.joaolucas.study.infrastructure.database.user.Role;
import com.joaolucas.study.infrastructure.database.user.UserEntity;
import com.joaolucas.study.domain.exceptions.AuthorizationException;
import com.joaolucas.study.domain.exceptions.DataIntegrityException;
import com.joaolucas.study.domain.exceptions.ObjectNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.awt.image.BufferedImage;
import java.net.URI;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private static final String ACESSO_NEGADO = "Acesso negado";

    private final CustomerRepository repo;

    private final AddressRepository addressRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final S3Service s3Service;

    private final ImageService imageService;

    @Value("${img.prefix.client.profile}")
    private String prefix;

    @Value("${img.profile.size}")
    private Integer size;

    public CustomerEntity find(Integer id) {
        UserEntity user = UserService.authenticated();
        if (user == null || !user.hasRole(Role.ADMIN) && !id.equals(user.getId()))
            throw new AuthorizationException(ACESSO_NEGADO);

        return repo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Objeto não encontrado Id: " + id + ", Tipo: " + CustomerEntity.class.getName()));
    }

    @Transactional
    public CustomerEntity insert(CustomerEntity obj) {
        obj = repo.save(obj);
        addressRepository.saveAll(obj.getAddressEntities());
        return obj;
    }

    public CustomerEntity insertOrUpdate(CustomerEntity obj) {
        return repo.save(obj);
    }

    public CustomerEntity update(CustomerEntity obj) {
        CustomerEntity newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    private void updateData(CustomerEntity newObj, CustomerEntity obj) {
        //TODO: improve update method with MapStruct
//        newObj.set(obj.getNome());
//        newObj.setEmail(obj.getEmail());
    }

    public void delete(Integer id) {
        find(id);
        try {
            repo.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new DataIntegrityException("Não é possível excluir porque há pedidos relacionadas");
        }
    }

    public Page<CustomerEntity> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        var pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public List<CustomerEntity> findAll() {
        return repo.findAll();
    }

    public CustomerEntity fromDTO(NewCustomer objDto) {
        var cli = new CustomerEntity(null, objDto.primeiroNome(), objDto.ultimoNome(), objDto.email(), objDto.cpfOuCnpj(), CustomerType.toEnum(objDto.tipo()), passwordEncoder.encode(objDto.senha()));
        var cid = new CityEntity(objDto.cidadeId(), null, null);
        var end = new AddressEntity(null, objDto.logradouro(), objDto.numero(), objDto.complemento(), objDto.bairro(), objDto.cep(), cli, cid);
        cli.getAddressEntities().add(end);
        cli.getPhones().add(objDto.telefone1());
        if (objDto.telefone2() != null) {
            cli.getPhones().add(objDto.telefone2());
        }
        if (objDto.telefone3() != null) {
            cli.getPhones().add(objDto.telefone3());
        }
        return cli;
    }

    public CustomerEntity fromDTO(Customer objDto) {
        return new CustomerEntity(objDto);
    }

    public CustomerEntity findByEmail(String email) {

        UserEntity user = UserService.authenticated();
        if (user == null || !user.hasRole(Role.ADMIN) && !email.equals(user.getFirstName())) {
            throw new AuthorizationException(ACESSO_NEGADO);
        }

        return repo.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + CustomerEntity.class.getName()));
    }

    public URI uploadProfilePicture(MultipartFile multipartFile) {

        UserEntity user = UserService.authenticated();
        if (user == null)
            throw new AuthorizationException(ACESSO_NEGADO);

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
        jpgImage = imageService.cropSquare(jpgImage);
        jpgImage = imageService.resize(jpgImage, size);

        String fileName = prefix + user.getId() + ".jpg";

        return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
    }
}
