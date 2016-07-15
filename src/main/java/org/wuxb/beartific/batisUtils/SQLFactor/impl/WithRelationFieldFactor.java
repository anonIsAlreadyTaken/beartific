package org.wuxb.beartific.batisUtils.SQLFactor.impl;

import org.dom4j.Element;
import org.wuxb.beartific.batisUtils.SQLFactor.Factor;

public class WithRelationFieldFactor implements Factor{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3762185632332011815L;
	private String idType;
	private Element rootElement;
	private String RelationField;
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
	
	public String getRelationField() {
		return RelationField;
	}
	public void setRelationField(String relationField) {
		RelationField = relationField;
	}
	public WithRelationFieldFactor() {
		super();
		// TODO Auto-generated constructor stub
	}
	public WithRelationFieldFactor(String idType, Element rootElement,
			String relationField) {
		super();
		this.idType = idType;
		this.rootElement = rootElement;
		RelationField = relationField;
	}
	
	
	

}
