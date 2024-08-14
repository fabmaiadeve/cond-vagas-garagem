package com.example.teste.cond_vagas_garagem.exceptions;

import java.sql.SQLException;

public class JdbcSQLIntegrityConstraintViolationException extends SQLException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public JdbcSQLIntegrityConstraintViolationException(String message) {
		super(message);
	}
}
