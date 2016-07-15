package org.wuxb.beartific.batisUtils.IOFactory;

import java.util.Map;

public abstract class InterfaceCreateClass {
	
	
	private Map<String, String> collectionOfAbsolutePath;
	
	private Map<String, Map<String, String>> collectionOfSqlInfo;
	
	private String baseDaoKey;

	public Map<String, String> getCollectionOfAbsolutePath() {
		return collectionOfAbsolutePath;
	}

	public void setCollectionOfAbsolutePath(
			Map<String, String> collectionOfAbsolutePath) {
		this.collectionOfAbsolutePath = collectionOfAbsolutePath;
	}

	public Map<String, Map<String, String>> getCollectionOfSqlInfo() {
		return collectionOfSqlInfo;
	}

	public void setCollectionOfSqlInfo(
			Map<String, Map<String, String>> collectionOfSqlInfo) {
		this.collectionOfSqlInfo = collectionOfSqlInfo;
	}

	public String getBaseDaoKey() {
		return baseDaoKey;
	}

	public void setBaseDaoKey(String baseDaoKey) {
		this.baseDaoKey = baseDaoKey;
	}

	public InterfaceCreateClass(Map<String, String> collectionOfAbsolutePath,
			Map<String, Map<String, String>> collectionOfSqlInfo,
			String baseDaoKey) {
		super();
		this.collectionOfAbsolutePath = collectionOfAbsolutePath;
		this.collectionOfSqlInfo = collectionOfSqlInfo;
		this.baseDaoKey = baseDaoKey;
	}

	public InterfaceCreateClass() {
		super();
	}
	
	
	public abstract void InterfaceAutoCreate();

}
