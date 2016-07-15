package org.wuxb.beartific.batisUtils.SQLFactor.impl;

import org.dom4j.Element;
import org.wuxb.beartific.batisUtils.SQLFactor.Factor;

public class IdTypeAndRootEleFactor implements Factor{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2041151420853344190L;
	
	private String idType;
	private Element rootElement;
	public String getIdType() {
		return idType;
	}
	public void setIdType(String idType) {
		this.idType = idType;
	}
	public Element getRootElement() {
		return rootElement;
	}
	public void setRootElement(Element rootElement) {
		this.rootElement = rootElement;
	}
	public IdTypeAndRootEleFactor(String idType, Element rootElement) {
		super();
		this.idType = idType;
		this.rootElement = rootElement;
	}
	public IdTypeAndRootEleFactor() {
		super();
	}
	
	
	

}
