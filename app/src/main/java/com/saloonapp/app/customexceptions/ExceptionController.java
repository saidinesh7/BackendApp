package com.saloonapp.app.customexceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionController {

	@ExceptionHandler(NotFoundException.class)
	public ResponseEntity<ErrorClass> NotFoundExceptionHandler(NotFoundException ex) {
		String exception = ex.getMessage();

		ErrorClass errorClass = new ErrorClass(exception, HttpStatus.NOT_FOUND);
		return new ResponseEntity<ErrorClass>(errorClass, HttpStatus.NOT_FOUND);

	}

	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorClass> genericExceptionHandler(Exception ex) {
		String exception = ex.getMessage();

		ErrorClass errorClass = new ErrorClass(exception, HttpStatus.INTERNAL_SERVER_ERROR);
		return new ResponseEntity<ErrorClass>(errorClass, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
