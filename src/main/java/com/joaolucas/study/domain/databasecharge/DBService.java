package com.joaolucas.study.domain.databasecharge;

import com.joaolucas.study.infrastructure.database.address.AddressEntity;
import com.joaolucas.study.infrastructure.database.address.AddressRepository;
import com.joaolucas.study.infrastructure.database.category.CategoryEntity;
import com.joaolucas.study.infrastructure.database.category.CategoryRepository;
import com.joaolucas.study.infrastructure.database.customer.CustomerEntity;
import com.joaolucas.study.infrastructure.database.customer.CustomerRepository;
import com.joaolucas.study.infrastructure.database.customer.CustomerType;
import com.joaolucas.study.infrastructure.database.order.OrderEntity;
import com.joaolucas.study.infrastructure.database.order.OrderRepository;
import com.joaolucas.study.infrastructure.database.orderitem.OrderItemEntity;
import com.joaolucas.study.infrastructure.database.orderitem.OrderItemRepository;
import com.joaolucas.study.infrastructure.database.payment.PaymentRepository;
import com.joaolucas.study.controller.order.model.PaymentStatus;
import com.joaolucas.study.infrastructure.database.payment.PaymentWithCardEntity;
import com.joaolucas.study.infrastructure.database.payment.PaymentWithSlipEntity;
import com.joaolucas.study.infrastructure.database.product.ProductEntity;
import com.joaolucas.study.infrastructure.database.product.ProductRepository;
import com.joaolucas.study.infrastructure.database.user.Role;
import com.joaolucas.study.infrastructure.database.user.UserEntity;
import com.joaolucas.study.infrastructure.database.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class DBService {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
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

        var p1 = new ProductEntity(null, "Computador", BigDecimal.valueOf(2000.00));
        var p2 = new ProductEntity(null, "Impressora", BigDecimal.valueOf(800.00));
        var p3 = new ProductEntity(null, "Mouse", BigDecimal.valueOf(80.00));
        var p4 = new ProductEntity(null, "Mesa de escritório", BigDecimal.valueOf(300.00));
        var p5 = new ProductEntity(null, "Toalha", BigDecimal.valueOf(50.00));
        var p6 = new ProductEntity(null, "Colcha", BigDecimal.valueOf(200.00));
        var p7 = new ProductEntity(null, "TV true color", BigDecimal.valueOf(1200.00));
        var p8 = new ProductEntity(null, "Roçadeira", BigDecimal.valueOf(800.00));
        var p9 = new ProductEntity(null, "Abajour", BigDecimal.valueOf(100.00));
        var p10 = new ProductEntity(null, "Pendente", BigDecimal.valueOf(180.00));
        var p11 = new ProductEntity(null, "Shampoo", BigDecimal.valueOf(90.00));

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

        var ped1 = new OrderEntity(null, LocalDateTime.ofInstant(sdf.parse("30/09/2020 10:32").toInstant(), ZoneId.of("GTM-3")), cli1, e1);
        var ped2 = new OrderEntity(null, LocalDateTime.ofInstant(sdf.parse("10/10/2020 19:35").toInstant(), ZoneId.of("GTM-3")), cli1, e2);

        var payment1 = new PaymentWithCardEntity(null, PaymentStatus.PAID.getCode(), ped1, 6);
        ped1.setPayment(payment1);

        var payment2 = new PaymentWithSlipEntity(null, PaymentStatus.PENDING.getCode(), ped2, LocalDateTime.ofInstant(sdf.parse("20/10/2020 00:00").toInstant(), ZoneId.of("GMT-3")).toLocalDate(), null);
        ped2.setPayment(payment2);

        cli1.getOrders().addAll(Arrays.asList(ped1, ped2));

        orderRepository.saveAll(Arrays.asList(ped1, ped2));
        paymentRepository.saveAll(Arrays.asList(payment1, payment2));

        var ip1 = new OrderItemEntity(ped1, p1, BigDecimal.ZERO, 1, BigDecimal.valueOf(2000.00));
        var ip2 = new OrderItemEntity(ped1, p3, BigDecimal.ZERO, 2, BigDecimal.valueOf(80.00));
        var ip3 = new OrderItemEntity(ped2, p2, BigDecimal.ZERO, 1, BigDecimal.valueOf(800.00));

        ped1.getItems().addAll(Arrays.asList(ip1, ip2));
        ped2.getItems().add(ip3);

        p1.getItems().add(ip1);
        p2.getItems().add(ip3);
        p3.getItems().add(ip2);

        orderItemRepository.saveAll(Arrays.asList(ip1, ip2, ip3));
    }
}
