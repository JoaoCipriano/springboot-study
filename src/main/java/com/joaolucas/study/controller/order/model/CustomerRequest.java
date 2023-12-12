package com.joaolucas.study.controller.order.model;

import jakarta.validation.constraints.NotNull;

public record CustomerRequest(@NotNull Integer id) {
}
