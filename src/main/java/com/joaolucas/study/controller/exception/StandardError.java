package com.joaolucas.study.controller.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StandardError implements Serializable {

    @Serial
    private static final long serialVersionUID = -3039268688976965827L;

    private Long timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
