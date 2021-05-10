package com.topjava.graduation.web;

import com.topjava.graduation.util.exception.ErrorInfo;
import com.topjava.graduation.util.exception.NotFoundException;
import com.topjava.graduation.util.exception.TimeExpiredException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;

@RestControllerAdvice(annotations = RestController.class)
@Order(Ordered.HIGHEST_PRECEDENCE + 5)
public class ExceptionInfoHandler {
    private static final Logger log = LoggerFactory.getLogger(ExceptionInfoHandler.class);

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)  //422
    @ExceptionHandler({NotFoundException.class, IllegalArgumentException.class})
    public ErrorInfo handleErrors(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e);
    }

    @ResponseStatus(HttpStatus.NOT_ACCEPTABLE)  //406
    @ExceptionHandler(TimeExpiredException.class)
    public ErrorInfo handleError(HttpServletRequest req, TimeExpiredException e) {
        return logAndGetErrorInfo(req, e);
    }

    @ResponseStatus(HttpStatus.CONFLICT)  // 409
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ErrorInfo conflict(HttpServletRequest req, DataIntegrityViolationException e) {
        return logAndGetErrorInfo(req, e);
    }

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  //500
    @ExceptionHandler(Exception.class)
    public ErrorInfo handleError(HttpServletRequest req, Exception e) {
        return logAndGetErrorInfo(req, e);
    }

    @ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)  //422
    @ExceptionHandler(BindException.class)
    public ErrorInfo handleError(HttpServletRequest req, BindException e) {
        log.error("Validation error at request " + req.getRequestURL());
        return new ErrorInfo(req.getRequestURL(), e.getClass().getSimpleName(), getErrorMsg(e));
    }

    private static String getErrorMsg(BindException e) {
        return e.getBindingResult().getFieldErrors().stream()
                .map(fe -> String.format("[%s] %s", fe.getField(), fe.getDefaultMessage()))
                .collect(Collectors.joining("<br>"));
    }

    private static ErrorInfo logAndGetErrorInfo(HttpServletRequest req, Exception e) {
        String cause = e.getClass().getSimpleName();
        log.error(cause + " at request " + req.getRequestURL());
        return new ErrorInfo(req.getRequestURL(), cause, e.getMessage());
    }
}
