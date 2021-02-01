/**
 * 
 */
package com.rishi.model;

import java.io.Serializable;

/**
 * @author rishis
 *
 */
public class CreateVMResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String instanceId;
	private String imageId;
	private String instanceType;
	private String instanceState;
	private String availabilityZone;

	public CreateVMResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CreateVMResponse(String instanceId, String imageId, String instanceType, String instanceState,
			String availabilityZone) {
		super();
		this.instanceId = instanceId;
		this.imageId = imageId;
		this.instanceType = instanceType;
		this.instanceState = instanceState;
		this.availabilityZone = availabilityZone;
	}

	/**
	 * @return the instanceId
	 */
	public String getInstanceId() {
		return instanceId;
	}

	/**
	 * @param instanceId the instanceId to set
	 */
	public void setInstanceId(String instanceId) {
		this.instanceId = instanceId;
	}

	/**
	 * @return the imageId
	 */
	public String getImageId() {
		return imageId;
	}

	/**
	 * @param imageId the imageId to set
	 */
	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

	/**
	 * @return the instanceType
	 */
	public String getInstanceType() {
		return instanceType;
	}

	/**
	 * @param instanceType the instanceType to set
	 */
	public void setInstanceType(String instanceType) {
		this.instanceType = instanceType;
	}

	/**
	 * @return the instanceState
	 */
	public String getInstanceState() {
		return instanceState;
	}

	/**
	 * @param instanceState the instanceState to set
	 */
	public void setInstanceState(String instanceState) {
		this.instanceState = instanceState;
	}

	/**
	 * @return the availabilityZone
	 */
	public String getAvailabilityZone() {
		return availabilityZone;
	}

	/**
	 * @param availabilityZone the availabilityZone to set
	 */
	public void setAvailabilityZone(String availabilityZone) {
		this.availabilityZone = availabilityZone;
	}

	@Override
	public String toString() {
		return "CreateVMResponse [instanceId=" + instanceId + ", imageId=" + imageId + ", instanceType=" + instanceType
				+ ", instanceState=" + instanceState + ", availabilityZone=" + availabilityZone + "]";
	}

}
