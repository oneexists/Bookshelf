package com.bujo.bookshelf.response;

import org.springframework.http.HttpStatus;

/**
 * This class represents an HTTP response. It contains the status code, the result status, the reason, and the message
 * of the response.
 *
 * @author skylar
 */
public class HttpResponse {
	private int status;
	private HttpStatus result;
	private String reason;
	private String message;
	
	public HttpResponse(int status, HttpStatus result, String reason, String message) {
		this.status = status;
		this.result = result;
		this.reason = reason;
		this.message = message;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public HttpStatus getResult() {
		return result;
	}

	public void setResult(HttpStatus result) {
		this.result = result;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
