package com.joaolucas.study.application.customer.mapper;

import com.joaolucas.model.CustomerRequest;
import com.joaolucas.model.CustomerResponse;
import com.joaolucas.model.PageableCustomerResponse;
import com.joaolucas.study.domain.customer.model.Customer;
import com.joaolucas.study.infrastructure.database.customer.CustomerEntity;
import com.joaolucas.study.infrastructure.database.customer.CustomerType;
import com.joaolucas.study.infrastructure.database.user.Role;
import com.joaolucas.study.infrastructure.database.user.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE,
        imports = {CustomerType.class})
public interface CustomerMapper {

    CustomerResponse toResponse(Customer customerModel);

    @Mapping(target = "type", expression = "java(CustomerType.toEnum(customerEntity.getType()))")
    Customer toModel(CustomerEntity customerEntity);

    List<Customer> toModels(List<CustomerEntity> customerEntities);

    List<CustomerResponse> toResponses(List<Customer> customerModels);

    default PageableCustomerResponse toPageableResponse(Page<Customer> pageableModels) {
        return new PageableCustomerResponse()
                .content(toResponses(pageableModels.toList()))
                .totalPages(pageableModels.getTotalPages())
                .totalElements(pageableModels.getTotalElements());
    }

    default Page<Customer> toPageableModel(Page<CustomerEntity> pageableEntity) {
        return new PageImpl<>(
                toModels(pageableEntity.toList()),
                pageableEntity.getPageable(),
                pageableEntity.getTotalElements()
        );
    }

    CustomerEntity toEntity(CustomerRequest customerRequest);

    @Mapping(target = "type", expression = "java(customerModel.type().getCode())")
    CustomerEntity toCustomerEntity(Customer customerModel);

    UserEntity toUserEntity(Customer customerModel);

    CustomerEntity toEntity(@MappingTarget CustomerEntity currentCustomerEntity, CustomerEntity customerEntity);

    @Mapping(target = "authorities", ignore = true)
    UserEntity toEntity(@MappingTarget UserEntity currentUserEntity, UserEntity customerEntity);

    @Mapping(target = "id", source = "customerEntity.id")
    @Mapping(target = "email", source = "userEntity.email")
    @Mapping(target = "type", expression = "java(CustomerType.toEnum(customerEntity.getType()))")
    Customer toModel(UserEntity userEntity, CustomerEntity customerEntity);

    @Mapping(target = "type", expression = "java(CustomerType.toEnum(customerRequest.getType()))")
    Customer toModel(CustomerRequest customerRequest);

    @Mapping(target = "firstName", source = "customerRequest.firstName")
    @Mapping(target = "lastName", source = "customerRequest.lastName")
    @Mapping(target = "email", source = "customerRequest.email")
    @Mapping(target = "password", source = "password")
    @Mapping(target = "roles", expression = "java(defaultRoles())")
    UserEntity toUserEntity(CustomerRequest customerRequest, String password);

    default Set<Integer> defaultRoles() {
        return Set.of(Role.USER.ordinal());
    }
}
