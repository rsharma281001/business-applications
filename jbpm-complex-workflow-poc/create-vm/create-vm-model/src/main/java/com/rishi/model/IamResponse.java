/**
 * 
 */
package com.rishi.model;

import java.io.Serializable;

/**
 * @author rishis
 *
 */
public class IamResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private float id;
	private String role;
	private String firstName;
	private String lastName;
	private String userName;

	public IamResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public IamResponse(float id, String role, String firstName, String lastName, String userName) {
		super();
		this.id = id;
		this.role = role;
		this.firstName = firstName;
		this.lastName = lastName;
		this.userName = userName;
	}

	/**
	 * @return the firstName
	 */
	public String getFirstName() {
		return firstName;
	}

	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	/**
	 * @return the id
	 */
	public float getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(float id) {
		this.id = id;
	}

	/**
	 * @return the role
	 */
	public String getRole() {
		return role;
	}

	/**
	 * @param role the role to set
	 */
	public void setRole(String role) {
		this.role = role;
	}

	/**
	 * @return the lastName
	 */
	public String getLastName() {
		return lastName;
	}

	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName(String lastName) {
		this.lastName = lastName;
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

	@Override
	public String toString() {
		return "IamResponse [id=" + id + ", role=" + role + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", userName=" + userName + "]";
	}

}
