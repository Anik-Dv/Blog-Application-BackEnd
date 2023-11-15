package com.anikdv.blog.app.payloads;

/**
 * @author AnikDV
 */
public class ApiResponse {

	private String message;
	private boolean status;

	/**
	 * super constructor
	 */
	public ApiResponse() {
		super();
	}

	/**
	 * parameterize constructor
	 * 
	 * @param message
	 * @param status
	 */
	public ApiResponse(String message, boolean status) {
		super();
		this.message = message;
		this.status = status;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "ApiResponse [message=" + message + ", status=" + status + "]";
	}
}
