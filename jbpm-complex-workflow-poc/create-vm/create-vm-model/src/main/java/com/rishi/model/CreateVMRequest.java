/**
 * 
 */
package com.rishi.model;

import java.io.Serializable;

/**
 * @author rishis
 *
 */
public class CreateVMRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String appId;
	private String provider;
	private String size;
	private String userName;
	private String region;

	public CreateVMRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CreateVMRequest(String appId, String provider, String size, String userName, String region) {
		super();
		this.appId = appId;
		this.provider = provider;
		this.size = size;
		this.userName = userName;
		this.region = region;
	}

	/**
	 * @return the appId
	 */
	public String getAppId() {
		return appId;
	}

	/**
	 * @param appId the appId to set
	 */
	public void setAppId(String appId) {
		this.appId = appId;
	}

	/**
	 * @return the provider
	 */
	public String getProvider() {
		return provider;
	}

	/**
	 * @param provider the provider to set
	 */
	public void setProvider(String provider) {
		this.provider = provider;
	}

	/**
	 * @return the size
	 */
	public String getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(String size) {
		this.size = size;
	}

	/**
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * @return the region
	 */
	public String getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public String toString() {
		return "CreateVMRequest [appId=" + appId + ", provider=" + provider + ", size=" + size + ", userName="
				+ userName + ", region=" + region + "]";
	}

}
