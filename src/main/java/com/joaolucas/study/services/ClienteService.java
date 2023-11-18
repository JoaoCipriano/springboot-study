package com.joaolucas.study.services;

import com.joaolucas.study.domain.Cidade;
import com.joaolucas.study.domain.Cliente;
import com.joaolucas.study.domain.Endereco;
import com.joaolucas.study.domain.enums.CustomerType;
import com.joaolucas.study.dto.ClienteDTO;
import com.joaolucas.study.dto.ClienteNewDTO;
import com.joaolucas.study.repositories.ClienteRepository;
import com.joaolucas.study.repositories.EnderecoRepository;
import com.joaolucas.study.security.Role;
import com.joaolucas.study.security.User;
import com.joaolucas.study.services.exceptions.AuthorizationException;
import com.joaolucas.study.services.exceptions.DataIntegrityException;
import com.joaolucas.study.services.exceptions.ObjectNotFoundException;
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
public class ClienteService {

    private static final String ACESSO_NEGADO = "Acesso negado";

    private final ClienteRepository repo;

    private final EnderecoRepository enderecoRepository;

    private final BCryptPasswordEncoder passwordEncoder;

    private final S3Service s3Service;

    private final ImageService imageService;

    @Value("${img.prefix.client.profile}")
    private String prefix;

    @Value("${img.profile.size}")
    private Integer size;

    public Cliente find(Integer id) {
        User user = UserService.authenticated();
        if (user == null || !user.hasRole(Role.ADMIN) && !id.equals(user.getId()))
            throw new AuthorizationException(ACESSO_NEGADO);

        return repo.findById(id)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Objeto não encontrado Id: " + id + ", Tipo: " + Cliente.class.getName()));
    }

    @Transactional
    public Cliente insert(Cliente obj) {
        obj = repo.save(obj);
        enderecoRepository.saveAll(obj.getEnderecos());
        return obj;
    }

    public Cliente insertOrUpdate(Cliente obj) {
        return repo.save(obj);
    }

    public Cliente update(Cliente obj) {
        Cliente newObj = find(obj.getId());
        updateData(newObj, obj);
        return repo.save(newObj);
    }

    private void updateData(Cliente newObj, Cliente obj) {
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

    public Page<Cliente> findPage(Integer page, Integer linesPerPage, String orderBy, String direction) {
        var pageRequest = PageRequest.of(page, linesPerPage, Sort.Direction.valueOf(direction), orderBy);
        return repo.findAll(pageRequest);
    }

    public List<Cliente> findAll() {
        return repo.findAll();
    }

    public Cliente fromDTO(ClienteNewDTO objDto) {
        var cli = new Cliente(null, objDto.primeiroNome(), objDto.ultimoNome(), objDto.email(), objDto.cpfOuCnpj(), CustomerType.toEnum(objDto.tipo()), passwordEncoder.encode(objDto.senha()));
        var cid = new Cidade(objDto.cidadeId(), null, null);
        var end = new Endereco(null, objDto.logradouro(), objDto.numero(), objDto.complemento(), objDto.bairro(), objDto.cep(), cli, cid);
        cli.getEnderecos().add(end);
        cli.getTelefones().add(objDto.telefone1());
        if (objDto.telefone2() != null) {
            cli.getTelefones().add(objDto.telefone2());
        }
        if (objDto.telefone3() != null) {
            cli.getTelefones().add(objDto.telefone3());
        }
        return cli;
    }

    public Cliente fromDTO(ClienteDTO objDto) {
        return new Cliente(objDto);
    }

    public Cliente findByEmail(String email) {

        User user = UserService.authenticated();
        if (user == null || !user.hasRole(Role.ADMIN) && !email.equals(user.getFirstName())) {
            throw new AuthorizationException(ACESSO_NEGADO);
        }

        return repo.findByEmail(email)
                .orElseThrow(() -> new ObjectNotFoundException(
                        "Objeto não encontrado! Id: " + user.getId() + ", Tipo: " + Cliente.class.getName()));
    }

    public URI uploadProfilePicture(MultipartFile multipartFile) {

        User user = UserService.authenticated();
        if (user == null)
            throw new AuthorizationException(ACESSO_NEGADO);

        BufferedImage jpgImage = imageService.getJpgImageFromFile(multipartFile);
        jpgImage = imageService.cropSquare(jpgImage);
        jpgImage = imageService.resize(jpgImage, size);

        String fileName = prefix + user.getId() + ".jpg";

        return s3Service.uploadFile(imageService.getInputStream(jpgImage, "jpg"), fileName, "image");
    }
}
