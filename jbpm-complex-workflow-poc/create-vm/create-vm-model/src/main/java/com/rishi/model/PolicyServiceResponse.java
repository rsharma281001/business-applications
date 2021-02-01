/**
 * 
 */
package com.rishi.model;

import java.io.Serializable;

/**
 * @author rishis
 *
 */
public class PolicyServiceResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private Boolean allow;

	public PolicyServiceResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public PolicyServiceResponse(Boolean allow) {
		super();
		this.allow = allow;
	}

	/**
	 * @return the allow
	 */
	public Boolean getAllow() {
		return allow;
	}

	/**
	 * @param allow the allow to set
	 */
	public void setAllow(Boolean allow) {
		this.allow = allow;
	}

	@Override
	public String toString() {
		return "PolicyAsCodeServiceResponse [allow=" + allow + "]";
	}

}
