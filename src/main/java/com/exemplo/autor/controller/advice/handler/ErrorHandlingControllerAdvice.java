package com.exemplo.autor.controller.advice.handler;

import com.exemplo.autor.controller.advice.dto.ErrorResponseEntityDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;

@Slf4j
@RestControllerAdvice
public class ErrorHandlingControllerAdvice {

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public final ResponseEntity<?> handleAllExceptions(Exception e, WebRequest webRequest) {

        log.error("Ocorreu um erro ao processar a requisição.", e);

        ErrorResponseEntityDTO errorResponseEntityDTO = ErrorResponseEntityDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .error(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .exception(e.getClass().getName())
                .message("Ocorreu um erro inesperado ao processar a requisição. Se o problema persistir, entre em contato com o administrador.")
                .detailMessage(null)
                .path(webRequest.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(errorResponseEntityDTO, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(com.exemplo.autor.exception.InvalidFieldSizeException.class)
    @ResponseBody
    public final ResponseEntity<?> handleInvalidFieldSizeException(
            com.exemplo.autor.exception.InvalidFieldSizeException e,
            WebRequest webRequest) {

        ErrorResponseEntityDTO errorResponseEntityDTO = ErrorResponseEntityDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .exception(e.getClass().getName())
                .message(e.getMessage())
                .detailMessage(null)
                .path(webRequest.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(errorResponseEntityDTO, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(com.exemplo.autor.exception.BusinessException.class)
    @ResponseBody
    public final ResponseEntity<?> handleBusinessException(
            com.exemplo.autor.exception.BusinessException e,
            WebRequest webRequest) {

        ErrorResponseEntityDTO errorResponseEntityDTO = ErrorResponseEntityDTO.builder()
                .timestamp(LocalDateTime.now())
                .status(HttpStatus.BAD_REQUEST.value())
                .error(HttpStatus.BAD_REQUEST.getReasonPhrase())
                .exception(e.getClass().getName())
                .message(e.getMessage())
                .detailMessage(null)
                .path(webRequest.getDescription(false).replace("uri=", ""))
                .build();

        return new ResponseEntity<>(errorResponseEntityDTO, HttpStatus.BAD_REQUEST);
    }

}
