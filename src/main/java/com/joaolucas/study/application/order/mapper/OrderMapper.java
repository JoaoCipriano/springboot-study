package com.joaolucas.study.application.order.mapper;

import com.joaolucas.study.controller.order.model.OrderRequest;
import com.joaolucas.study.controller.order.model.OrderResponse;
import com.joaolucas.study.controller.order.model.PaymentRequest;
import com.joaolucas.study.controller.order.model.PaymentResponse;
import com.joaolucas.study.controller.order.model.PaymentStatus;
import com.joaolucas.study.infrastructure.database.order.OrderEntity;
import com.joaolucas.study.infrastructure.database.payment.PaymentEntity;
import com.joaolucas.study.infrastructure.database.payment.PaymentWithCardEntity;
import com.joaolucas.study.infrastructure.database.payment.PaymentWithSlipEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.Objects;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "customer.type", expression = "java(CustomerType.toEnum(customerEntity.getType()))")
    @Mapping(source = "payment", target = "payment", qualifiedByName = "toPaymentResponse")
    OrderResponse toResponse(OrderEntity entity);

    List<OrderResponse> toResponses(List<OrderEntity> entities);

    default Page<OrderResponse> toPageableResponse(Page<OrderEntity> pageableEntities) {
        return new PageImpl<>(
                toResponses(pageableEntities.toList()),
                pageableEntities.getPageable(),
                pageableEntities.getTotalElements()
        );
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(source = "payment", target = "payment", qualifiedByName = "toPaymentEntity")
    @Mapping(target = "instant", constant = "java(LocalDateTime.now())")
    OrderEntity toEntity(OrderRequest request);

    @Named(value = "toPaymentEntity")
    default PaymentEntity toPaymentEntity(PaymentRequest request) {
        if (Objects.nonNull(request.installmentsNumber())) {
            return new PaymentWithCardEntity(null, request.status().getCode(), null, request.installmentsNumber());
        } else {
            return new PaymentWithSlipEntity(null, request.status().getCode(), null, request.dueDate(), request.paidAt());
        }
    }

    @Named(value = "toPaymentResponse")
    default PaymentResponse toPaymentResponse(PaymentEntity entity) {
        var paymentResponseBuilder = PaymentResponse.builder();
        if (entity instanceof PaymentWithCardEntity paymentWithCardEntity) {
            paymentResponseBuilder.installmentsNumber(paymentWithCardEntity.getInstallmentsNumber());
        }
        if (entity instanceof PaymentWithSlipEntity paymentWithSlipEntity) {
            paymentResponseBuilder.dueDate(paymentWithSlipEntity.getDueDate());
            paymentResponseBuilder.paidAt(paymentWithSlipEntity.getPaidAt());
        }
        return paymentResponseBuilder
                .status(PaymentStatus.toEnum(entity.getStatus()))
                .build();
    }
}
