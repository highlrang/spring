package com.myproject.myweb.domain;

public class RegisterVO {
	private int stu_num;
	private String lec_name;
	private String lec_sem;
	
	private Integer reg_count;
	private Integer lec_limit;
	
	public RegisterVO() {};
	
	public int getStu_num() {
		return stu_num;
	}
	public void setStu_num(int stu_num) {
		this.stu_num = stu_num;
	}
	public String getLec_name() {
		return lec_name;
	}
	public void setLec_name(String lec_name) {
		this.lec_name = lec_name;
	}
	public String getLec_sem() {
		return lec_sem;
	}
	public void setLec_sem(String lec_sem) {
		this.lec_sem = lec_sem;
	}
	
	
	public Integer getReg_count() {
		return reg_count;
	}
	public void setReg_count(Integer reg_count) {
		this.reg_count = reg_count;
	}
	public Integer getLec_limit() {
		return lec_limit;
	}
	public void setLec_limit(Integer lec_limit) {
		this.lec_limit = lec_limit;
	}
	
	
	
}
