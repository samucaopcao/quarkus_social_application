package br.com.quarkus.project.resources.exceptions;

import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolation;
import javax.ws.rs.core.Response;

public class ResponseError {

	public static final int UNPROCESSABLE_ENTITY_STATUS = 422;
	
	private String message;
	private Collection<FieldError> errors;

	public ResponseError(String message, Collection<FieldError> errors) {
		super();
		this.message = message;
		this.errors = errors;
	}
 
	// Criará um erro de responsta com a ConstraintViolation de qualquer objeto por isso usamos <T>
	public static <T> ResponseError createFromValidation(Set<ConstraintViolation<T>> violations) {

		// Capturará cada FieldError que foi gerado dessas violations e colocará em uma lista
		List<FieldError> errors = violations
		.stream()
		.map(x -> new FieldError(x.getPropertyPath().toString(), x.getMessage()))
		.collect(Collectors.toList());
		
		String message = "Validation Error";
		
		var responseError = new ResponseError(message, errors);
		return responseError;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public Collection<FieldError> getErrors() {
		return errors;
	}

	public void setErrors(Collection<FieldError> errors) {
		this.errors = errors;
	}
	
	public Response withStatusCode(int code) {
		return Response.status(code).entity(this).build();
	}

}
