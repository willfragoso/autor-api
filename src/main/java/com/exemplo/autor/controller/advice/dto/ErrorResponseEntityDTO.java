package com.exemplo.autor.controller.advice.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ErrorResponseEntityDTO {

    private LocalDateTime timestamp;

    private Integer status;

    private String error;

    private String exception;

    private String message;

    private String detailMessage;

    private String path;

}
