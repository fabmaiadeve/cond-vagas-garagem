package com.example.teste.cond_vagas_garagem.dtos;

public class ErrorDto {

	private String message;
	private Integer codeHttpError;
	
	public ErrorDto() {
	}
	
	public ErrorDto(String message, Integer codeHttpError) {
		this.message = message;
		this.codeHttpError = codeHttpError;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Integer getCodeHttpError() {
		return codeHttpError;
	}

	public void setCodeHttpError(Integer codeHttpError) {
		this.codeHttpError = codeHttpError;
	}
}