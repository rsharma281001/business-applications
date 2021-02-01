/**
 * 
 */
package com.rishi.model;

import java.io.Serializable;

/**
 * @author rishis
 *
 */
public class BluePrintServiceResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private String blueprint;

	public BluePrintServiceResponse() {
		super();
		// TODO Auto-generated constructor stub
	}

	public BluePrintServiceResponse(String blueprint) {
		super();
		this.blueprint = blueprint;
	}

	/**
	 * @return the blueprint
	 */
	public String getBlueprint() {
		return blueprint;
	}

	/**
	 * @param blueprint the blueprint to set
	 */
	public void setBlueprint(String blueprint) {
		this.blueprint = blueprint;
	}

	@Override
	public String toString() {
		return "BluePrint [blueprint=" + blueprint + "]";
	}

}