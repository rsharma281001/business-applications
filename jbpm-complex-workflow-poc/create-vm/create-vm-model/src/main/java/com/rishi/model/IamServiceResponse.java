/**
 * 
 */
package com.rishi.model;

import java.io.Serializable;

/**
 * @author rishis
 *
 */
public class IamServiceResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String status;
	private IamResponse iamResponse;

	public IamServiceResponse() {
		super();
	}

	public IamServiceResponse(String status, IamResponse iamResponse) {
		super();
		this.status = status;
		this.iamResponse = iamResponse;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}

	/**
	 * @return the iamResponse
	 */
	public IamResponse getIamResponse() {
		return iamResponse;
	}

	/**
	 * @param iamResponse the iamResponse to set
	 */
	public void setIamResponse(IamResponse iamResponse) {
		this.iamResponse = iamResponse;
	}

	@Override
	public String toString() {
		return "IamServiceResponse [status=" + status + ", iamResponse=" + iamResponse + "]";
	}

}
