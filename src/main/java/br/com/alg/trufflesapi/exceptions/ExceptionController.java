package br.com.alg.trufflesapi.exceptions;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;

@ControllerAdvice
public class ExceptionController {

	@ExceptionHandler(MultipartException.class)
	@ResponseBody
	public String handleException(HttpServletRequest request, Throwable ex) {
		System.out.println("FILE UPLOAD ERROR " + ex.getMessage());
		return "File upload error";
	}
	
	
	
}
