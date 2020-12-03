package com.myproject.myweb.domain;

public class RegisteredVO {
	private String lec_name;
	private String lec_sem;
	private String lec_maj;
	
	public RegisteredVO() {};
	public RegisteredVO(String cour_name) {
		this.lec_name = cour_name;
	}
	public String getLec_name() {
		return lec_name;
	}
	public void setLec_name(String cour_name) {
		this.lec_name = cour_name;
	}
	public String getLec_sem() {
		return lec_sem;
	}
	public void setLec_sem(String lec_sem) {
		this.lec_sem = lec_sem;
	}
	public String getLec_maj() {
		return lec_maj;
	}
	public void setLec_maj(String lec_maj) {
		this.lec_maj = lec_maj;
	}
	
	
}
