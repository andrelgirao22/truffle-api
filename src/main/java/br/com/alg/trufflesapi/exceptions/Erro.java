package br.com.alg.trufflesapi.exceptions;

public class Erro {

	private Long timestamp = 0L;
	private Integer status = 0;
	private String error = "";
	private String exception = "";
	private String message = "";
	private String path = "";

	public Long getTimestamp() {
		return timestamp;
	}

	public Integer getStatus() {
		return status;
	}

	public String getError() {
		return error;
	}

	public String getException() {
		return exception;
	}

	public String getMessage() {
		return message;
	}

	public String getPath() {
		return path;
	}

	public Erro setTimestamp(Long timestamp) {
		this.timestamp = timestamp;
		return this;
	}

	public Erro setStatus(Integer status) {
		this.status = status;
		return this;
	}

	public Erro setError(String error) {
		this.error = error;
		return this;
	}
	

	public Erro setMessage(String message) {
		this.message = message;
		return this;
	}

	public Erro setPath(String path) {
		this.path = path;
		return this;
	}

	public Erro setException(String exception) {
		this.exception = exception;
		return this;
	}

}
