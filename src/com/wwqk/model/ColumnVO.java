package com.wwqk.model;

public class ColumnVO {
	
	private String colomnName;
	
	private String colomnType;
	
	private String colomnComment;
	
	//0、可编辑   1、不可编辑
	private int isInForm;
	
	//0、不展示  1、展示
	private int isInList;
	
	//0、不可排序       1、可以排序
	private int isOrderable;
	
	//0、不可搜索 	1、可以搜索 
	private int isSearchable;
	
	//0、文本模式   1、富文本  2、图片模式
	private int finalType;
	
	//0、非必填  1、必填
	private int isRequired;
	
	public String getColomnName() {
		return colomnName;
	}

	public void setColomnName(String colomnName) {
		this.colomnName = colomnName;
	}

	public String getColomnType() {
		return colomnType;
	}

	public void setColomnType(String colomnType) {
		this.colomnType = colomnType;
	}

	public String getColomnComment() {
		return colomnComment;
	}

	public void setColomnComment(String colomnComment) {
		this.colomnComment = colomnComment;
	}

	public int getIsInForm() {
		return isInForm;
	}

	public void setIsInForm(int isInForm) {
		this.isInForm = isInForm;
	}

	public int getIsInList() {
		return isInList;
	}

	public void setIsInList(int isInList) {
		this.isInList = isInList;
	}

	public int getIsOrderable() {
		return isOrderable;
	}

	public void setIsOrderable(int isOrderable) {
		this.isOrderable = isOrderable;
	}

	public int getIsSearchable() {
		return isSearchable;
	}

	public void setIsSearchable(int isSearchable) {
		this.isSearchable = isSearchable;
	}

	public int getFinalType() {
		return finalType;
	}

	public void setFinalType(int finalType) {
		this.finalType = finalType;
	}

	public int getIsRequired() {
		return isRequired;
	}

	public void setIsRequired(int isRequired) {
		this.isRequired = isRequired;
	}
	
}
