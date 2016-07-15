package org.wuxb.beartific.batisUtils.SQLFactor;

import java.io.Serializable;

import org.dom4j.Element;

public interface Factor extends Serializable{
	
	String idType = "Integer";
	Element rootElement = null;;
	
	Element getRootElement();
	
	String getIdType();

}
