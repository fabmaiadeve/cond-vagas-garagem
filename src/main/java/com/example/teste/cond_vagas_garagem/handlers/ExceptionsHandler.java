package com.example.teste.cond_vagas_garagem.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.example.teste.cond_vagas_garagem.dtos.ErrorDto;
import com.example.teste.cond_vagas_garagem.exceptions.ConstraintViolationException;
import com.example.teste.cond_vagas_garagem.exceptions.NotFoundObjectException;
import com.example.teste.cond_vagas_garagem.exceptions.NotNullableFieldsException;

@ControllerAdvice
public class ExceptionsHandler {

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ResponseBody
	@ExceptionHandler(NotFoundObjectException.class)
	public ErrorDto handler(NotFoundObjectException ex) {
		return new ErrorDto(ex.getMessage(), HttpStatus.NOT_FOUND.value());
	}
	
	@ResponseStatus(HttpStatus.NOT_ACCEPTABLE)
	@ResponseBody
	@ExceptionHandler(NotNullableFieldsException.class)
	public ErrorDto handler(NotNullableFieldsException ex) {
		return new ErrorDto(ex.getMessage(), HttpStatus.NOT_ACCEPTABLE.value());
	}
	
	@ResponseStatus(HttpStatus.CONFLICT)
	@ResponseBody
	@ExceptionHandler(ConstraintViolationException.class)
	public ErrorDto handler(ConstraintViolationException ex) {
		return new ErrorDto(ex.getMessage(), HttpStatus.CONFLICT.value());
	}
}
