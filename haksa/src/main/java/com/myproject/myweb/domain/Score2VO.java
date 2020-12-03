package com.myproject.myweb.domain;

//scoreVO만으로 가능한지 알아보기

public class Score2VO {
	private String stu_name;
	private String lec_name;
	private int mid_exam;
	private int final_exam;
	private int assignment;
	private int attendence;
	
	public Score2VO() {};

	
	public String getStu_name() {
		return stu_name;
	}
	public void setStu_name(String stu_name) {
		this.stu_name = stu_name;
	}
	public String getLec_name() {
		return lec_name;
	}
	public void setLec_name(String cour_name) {
		this.lec_name = cour_name;
	}


	public int getMid_exam() {
		return mid_exam;
	}


	public void setMid_exam(int mid_exam) {
		this.mid_exam = mid_exam;
	}


	public int getFinal_exam() {
		return final_exam;
	}


	public void setFinal_exam(int final_exam) {
		this.final_exam = final_exam;
	}


	public int getAssignment() {
		return assignment;
	}


	public void setAssignment(int assignment) {
		this.assignment = assignment;
	}


	public int getAttendence() {
		return attendence;
	}


	public void setAttendence(int attendence) {
		this.attendence = attendence;
	}
	
	
	
}
