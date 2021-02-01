package com.rishi.model;

import java.io.Serializable;

public class EmailResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String response;

	public EmailResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public EmailResponse(String response) {
		super();
		this.response = response;
	}

	/**
	 * @return the response
	 */
	public String getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(String response) {
		this.response = response;
	}

	@Override
	public String toString() {
		return "EmailResponse [response=" + response + "]";
	}

}