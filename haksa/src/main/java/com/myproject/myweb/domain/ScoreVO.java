package com.myproject.myweb.domain;

public class ScoreVO {
	private int prof_num;
	private String prof_password;
	private String lec_name;
	private String stu_name;
	private Integer stu_num;
	private String stu_major;
	
	public ScoreVO() {};
	public ScoreVO(String cour_name, String stu_name) {
		this.lec_name = cour_name;
		this.stu_name = stu_name;
	}
	
	
	
	public int getProf_num() {
		return prof_num;
	}
	public void setProf_num(int prof_num) {
		this.prof_num = prof_num;
	}
	public String getProf_password() {
		return prof_password;
	}
	public void setProf_password(String prof_password) {
		this.prof_password = prof_password;
	}
	public String getLec_name() {
		return lec_name;
	}
	public void setLec_name(String cour_name) {
		this.lec_name = cour_name;
	}
	public String getStu_name() {
		return stu_name;
	}
	public void setStu_name(String stu_name) {
		this.stu_name = stu_name;
	}
	public Integer getStu_num() {
		return stu_num;
	}
	public void setStu_num(Integer stu_num) {
		this.stu_num = stu_num;
	}
	public String getStu_major() {
		return stu_major;
	}
	public void setStu_major(String stu_major) {
		this.stu_major = stu_major;
	}
	
	
	
}
