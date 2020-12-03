package com.myproject.myweb.domain;

public class CourseVO {
	private String lec_name;
	private String lec_sem;
	private String lec_prof;
	private String lec_time;
	
	public CourseVO() {};
	public CourseVO(String lec_name) {
		this.lec_name = lec_name;
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
	public String getLec_prof() {
		return lec_prof;
	}
	public void setLec_prof(String lec_prof) {
		this.lec_prof = lec_prof;
	}
	public String getLec_time() {
		return lec_time;
	}
	public void setLec_time(String lec_time) {
		this.lec_time = lec_time;
	}
	
}
