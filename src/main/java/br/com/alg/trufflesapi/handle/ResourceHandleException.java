package br.com.alg.trufflesapi.handle;

import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import br.com.alg.trufflesapi.exceptions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.amazonaws.AmazonClientException;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.s3.model.AmazonS3Exception;

@ControllerAdvice
public class ResourceHandleException {

	
	@ExceptionHandler(BadCredentialsException.class)
	public ResponseEntity<Erro> handleInvalidLoginException(BadCredentialsException e,
			HttpServletRequest request) {
		Erro erro = new Erro();
		erro.setMessage(e.getMessage()).setStatus(HttpStatus.UNAUTHORIZED.value()).setTimestamp(new Date().getTime());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(AuthenticationException.class)
	public ResponseEntity<Erro> handleAuthenticationException(AuthenticationException e, HttpServletRequest request) {
		Erro erro = new Erro();
		erro.setMessage(e.getMessage()).setStatus(HttpStatus.UNAUTHORIZED.value()).setTimestamp(new Date().getTime());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(CredentiaisInvalidException.class)
	public ResponseEntity<Erro> handleInvalidLoginException(CredentiaisInvalidException e,
			HttpServletRequest request) {
		Erro erro = new Erro();
		erro.setMessage(e.getMessage()).setStatus(HttpStatus.BAD_REQUEST.value()).setTimestamp(new Date().getTime());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(UserException.class)
	public ResponseEntity<Erro> handleAccountNotFoudException(UserException e,
			HttpServletRequest request) {
		Erro erro = new Erro();
		erro.setMessage(e.getMessage()).setStatus(HttpStatus.BAD_REQUEST.value()).setTimestamp(new Date().getTime());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(AccountNotFoundException.class)
	public ResponseEntity<Erro> handleAccountNotFoudException(AccountNotFoundException e,
			HttpServletRequest request) {
		Erro erro = new Erro();
		erro.setMessage(e.getMessage()).setStatus(HttpStatus.BAD_REQUEST.value()).setTimestamp(new Date().getTime());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(AccountEmailExistException.class)
	public ResponseEntity<Erro> handleAccountNotFoudException(AccountEmailExistException e,
			HttpServletRequest request) {
		Erro erro = new Erro();
		erro.setMessage(e.getMessage()).setStatus(HttpStatus.BAD_REQUEST.value()).setTimestamp(new Date().getTime());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(CategoryNotFoundException.class)
	public ResponseEntity<Erro> handleCategoryNotFoudException(CategoryNotFoundException e,
			HttpServletRequest request) {
		Erro erro = new Erro();
		erro.setMessage(e.getMessage()).setStatus(HttpStatus.BAD_REQUEST.value()).setTimestamp(new Date().getTime());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(ItemNotFoundException.class)
	public ResponseEntity<Erro> handleItemNotFoundException(ItemNotFoundException e,
			HttpServletRequest request) {
		Erro erro = new Erro();
		erro.setMessage(e.getMessage()).setStatus(HttpStatus.BAD_REQUEST.value()).setTimestamp(new Date().getTime());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(PriceNotFoudException.class)
	public ResponseEntity<Erro> handlePriceNotFoundException(PriceNotFoudException e,
			HttpServletRequest request) {
		Erro erro = new Erro();
		erro.setMessage(e.getMessage()).setStatus(HttpStatus.BAD_REQUEST.value()).setTimestamp(new Date().getTime());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(OrderNotFoundException.class)
	public ResponseEntity<Erro> handleOrderNotFoundException(OrderNotFoundException e,
			HttpServletRequest request) {
		Erro erro = new Erro();
		erro.setMessage(e.getMessage()).setStatus(HttpStatus.BAD_REQUEST.value()).setTimestamp(new Date().getTime());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(PaymentInvalidException.class)
	public ResponseEntity<Erro> handlePaymentInvalidException(PaymentInvalidException e,
			HttpServletRequest request) {
		Erro erro = new Erro();
		erro.setMessage(e.getMessage()).setStatus(HttpStatus.BAD_REQUEST.value()).setTimestamp(new Date().getTime());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(CityNotFoundException.class)
	public ResponseEntity<Erro> handleCityNotFoudException(CityNotFoundException e,
			HttpServletRequest request) {
		Erro erro = new Erro();
		erro.setMessage(e.getMessage()).setStatus(HttpStatus.BAD_REQUEST.value()).setTimestamp(new Date().getTime());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(FileException.class)
	public ResponseEntity<Erro> file(FileException e,
			HttpServletRequest request) {
		Erro erro = new Erro();
		erro.setMessage(e.getMessage()).setStatus(HttpStatus.BAD_REQUEST.value()).setTimestamp(new Date().getTime());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(AmazonServiceException.class)
	public ResponseEntity<Erro> amazonService(AmazonServiceException e,
			HttpServletRequest request) {
		HttpStatus code = HttpStatus.valueOf(e.getErrorCode());
		Erro erro = new Erro();
		erro.setMessage(e.getMessage()).setStatus(code.value()).setTimestamp(new Date().getTime());
		return ResponseEntity.status(code).body(erro);
	}
	
	@ExceptionHandler(AmazonClientException.class)
	public ResponseEntity<Erro> amazonClient(AmazonClientException e,
			HttpServletRequest request) {
		Erro erro = new Erro();
		erro.setMessage(e.getMessage()).setStatus(HttpStatus.BAD_REQUEST.value()).setTimestamp(new Date().getTime());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(AmazonS3Exception.class)
	public ResponseEntity<Erro> amazonS3(AmazonS3Exception e,
			HttpServletRequest request) {
		Erro erro = new Erro();
		erro.setMessage(e.getMessage()).setStatus(HttpStatus.BAD_REQUEST.value()).setTimestamp(new Date().getTime());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(TaxDeliveryNotFoundException.class)
	public ResponseEntity<Erro> taxDelivery(TaxDeliveryNotFoundException e,
										 HttpServletRequest request) {
		Erro erro = new Erro();
		erro.setMessage(e.getMessage()).setStatus(HttpStatus.BAD_REQUEST.value()).setTimestamp(new Date().getTime());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}
	
	@ExceptionHandler(ImageNotFoundException.class)
	public ResponseEntity<Erro> imageNotFound(ImageNotFoundException e,
										 HttpServletRequest request) {
		Erro erro = new Erro();
		erro.setMessage(e.getMessage()).setStatus(HttpStatus.BAD_REQUEST.value()).setTimestamp(new Date().getTime());
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(erro);
	}

	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseEntity<Erro> dataIntegrity(DataIntegrityViolationException e, HttpServletRequest request) {
		Erro erro = new Erro();
		erro.setMessage(e.getMessage()).setStatus(HttpStatus.NOT_ACCEPTABLE.value()).setTimestamp(new Date().getTime());
		return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(erro);
	}


	
}
