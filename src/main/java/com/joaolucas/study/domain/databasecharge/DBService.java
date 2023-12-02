package com.joaolucas.study.domain.databasecharge;

import com.joaolucas.study.infrastructure.database.address.AddressEntity;
import com.joaolucas.study.infrastructure.database.address.AddressRepository;
import com.joaolucas.study.infrastructure.database.category.CategoryEntity;
import com.joaolucas.study.infrastructure.database.category.CategoryRepository;
import com.joaolucas.study.infrastructure.database.city.CityEntity;
import com.joaolucas.study.infrastructure.database.city.CityRepository;
import com.joaolucas.study.infrastructure.database.customer.CustomerEntity;
import com.joaolucas.study.infrastructure.database.customer.CustomerRepository;
import com.joaolucas.study.infrastructure.database.customer.CustomerType;
import com.joaolucas.study.infrastructure.database.order.OrderEntity;
import com.joaolucas.study.infrastructure.database.order.OrderRepository;
import com.joaolucas.study.infrastructure.database.orderitem.OrderItemEntity;
import com.joaolucas.study.infrastructure.database.orderitem.OrderItemRepository;
import com.joaolucas.study.infrastructure.database.payment.PaymentRepository;
import com.joaolucas.study.infrastructure.database.payment.PaymentStatus;
import com.joaolucas.study.infrastructure.database.payment.PaymentWithCardEntity;
import com.joaolucas.study.infrastructure.database.payment.PaymentWithSlipEntity;
import com.joaolucas.study.infrastructure.database.product.ProductEntity;
import com.joaolucas.study.infrastructure.database.product.ProductRepository;
import com.joaolucas.study.infrastructure.database.state.StateEntity;
import com.joaolucas.study.infrastructure.database.state.StateRepository;
import com.joaolucas.study.infrastructure.database.user.Role;
import com.joaolucas.study.infrastructure.database.user.UserEntity;
import com.joaolucas.study.infrastructure.database.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class DBService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final StateRepository stateRepository;
    private final CityRepository cityRepository;
    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final AddressRepository addressRepository;
    private final OrderRepository orderRepository;
    private final PaymentRepository paymentRepository;
    private final OrderItemRepository orderItemRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public void instantiateTestDatabase() throws ParseException {
        var cat1 = new CategoryEntity(null, "Informática");
        var cat2 = new CategoryEntity(null, "Escritório");
        var cat3 = new CategoryEntity(null, "Cama mesa e banho");
        var cat4 = new CategoryEntity(null, "Eletrônicos");
        var cat5 = new CategoryEntity(null, "Jardinagem");
        var cat6 = new CategoryEntity(null, "Decoração");
        var cat7 = new CategoryEntity(null, "Perfumaria");

        var p1 = new ProductEntity(null, "Computador", 2000.00);
        var p2 = new ProductEntity(null, "Impressora", 800.00);
        var p3 = new ProductEntity(null, "Mouse", 80.00);
        var p4 = new ProductEntity(null, "Mesa de escritório", 300.00);
        var p5 = new ProductEntity(null, "Toalha", 50.00);
        var p6 = new ProductEntity(null, "Colcha", 200.00);
        var p7 = new ProductEntity(null, "TV true color", 1200.00);
        var p8 = new ProductEntity(null, "Roçadeira", 800.00);
        var p9 = new ProductEntity(null, "Abajour", 100.00);
        var p10 = new ProductEntity(null, "Pendente", 180.00);
        var p11 = new ProductEntity(null, "Shampoo", 90.00);

        cat1.getProducts().addAll(Arrays.asList(p1, p3));
        cat2.getProducts().addAll(Arrays.asList(p2, p4));
        cat3.getProducts().addAll(Arrays.asList(p5, p6));
        cat4.getProducts().addAll(Arrays.asList(p1, p2, p3, p7));
        cat5.getProducts().add(p8);
        cat6.getProducts().addAll(Arrays.asList(p9, p10));
        cat7.getProducts().add(p11);

        p1.getCategories().addAll(Arrays.asList(cat1, cat4));
        p2.getCategories().addAll(Arrays.asList(cat1, cat2, cat4));
        p3.getCategories().addAll(Arrays.asList(cat1, cat4));
        p4.getCategories().add(cat2);
        p5.getCategories().add(cat3);
        p6.getCategories().add(cat3);
        p7.getCategories().add(cat4);
        p8.getCategories().add(cat5);
        p9.getCategories().add(cat6);
        p10.getCategories().add(cat6);
        p11.getCategories().add(cat7);

        categoryRepository.saveAll(Arrays.asList(cat1, cat2, cat3, cat4, cat5, cat6, cat7));
        productRepository.saveAll(Arrays.asList(p1, p2, p3, p4, p5, p6, p7, p8, p9, p10, p11));

        var est1 = new StateEntity(null, "Minas Gerais");
        var est2 = new StateEntity(null, "São Paulo");

        var c1 = new CityEntity(null, "Uberlândia", est1);
        var c2 = new CityEntity(null, "São Paulo", est2);
        var c3 = new CityEntity(null, "Campinas", est2);

        est1.getCityEntities().add(c1);
        est2.getCityEntities().addAll(Arrays.asList(c2, c3));

        stateRepository.saveAll(Arrays.asList(est1, est2));
        cityRepository.saveAll(Arrays.asList(c1, c2, c3));

        var user1 = UserEntity.builder()
                .firstName("Maria")
                .lastName("Silva")
                .email("maria@gmail.com")
                .password(passwordEncoder.encode("123"))
                .build();
        var cli1 = new CustomerEntity("maria@gmail.com", "36378912377", CustomerType.PESSOA_FISICA.getCode());
        cli1.getPhones().addAll(Arrays.asList("27363323", "93838393"));

        var user2 = UserEntity.builder()
                .firstName("Ana")
                .lastName("Costa")
                .email("ana@gmail.com")
                .password(passwordEncoder.encode("123"))
                .build();
        var cli2 = new CustomerEntity("ana@gmail.com", "88071871087", CustomerType.PESSOA_FISICA.getCode());
        user2.addRole(Role.ADMIN);
        cli2.getPhones().addAll(Arrays.asList("93883321", "34292625"));

        var e1 = new AddressEntity(null, "Rua Flores", "300", "Apto 303", "Jardim", "38220834", "Uberlândia", cli1);
        var e2 = new AddressEntity(null, "Avenida Matos", "105", "Sala 800", "Centro", "38777012", "Uberlândia", cli1);
        var e3 = new AddressEntity(null, "Avenida Floriano", "2106", null, "Centro", "38777012", "São Paulo", cli2);

        cli1.getAddresses().addAll(Arrays.asList(e1, e2));
        cli2.getAddresses().add(e3);

        userRepository.saveAll(Arrays.asList(user1, user2));
        customerRepository.saveAll(Arrays.asList(cli1, cli2));
        addressRepository.saveAll(Arrays.asList(e1, e2, e3));

        var sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");

        var ped1 = new OrderEntity(null, sdf.parse("30/09/2020 10:32"), cli1, e1);
        var ped2 = new OrderEntity(null, sdf.parse("10/10/2020 19:35"), cli1, e2);

        var pagto1 = new PaymentWithCardEntity(null, PaymentStatus.PAID, ped1, 6);
        ped1.setPayment(pagto1);

        var pagto2 = new PaymentWithSlipEntity(null, PaymentStatus.PENDING, ped2, sdf.parse("20/10/2020 00:00"), null);
        ped2.setPayment(pagto2);

        cli1.getOrders().addAll(Arrays.asList(ped1, ped2));

        orderRepository.saveAll(Arrays.asList(ped1, ped2));
        paymentRepository.saveAll(Arrays.asList(pagto1, pagto2));

        var ip1 = new OrderItemEntity(ped1, p1, 0.00, 1, 2000.00);
        var ip2 = new OrderItemEntity(ped1, p3, 0.00, 2, 80.00);
        var ip3 = new OrderItemEntity(ped2, p2, 100.00, 1, 800.00);

        ped1.getItens().addAll(Arrays.asList(ip1, ip2));
        ped2.getItens().add(ip3);

        p1.getItems().add(ip1);
        p2.getItems().add(ip3);
        p3.getItems().add(ip2);

        orderItemRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
    }
}
