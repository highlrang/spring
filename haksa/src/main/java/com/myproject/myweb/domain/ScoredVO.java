package com.myproject.myweb.domain;

public class ScoredVO {
	private int stu_num;
	private String stu_password;
	
	private String lec_name;
	private String lec_sem;
	private String lec_maj;
	
	private int mid_exam;
	private int final_exam;
	private int assignment;
	private int attendence;
	private int res_score;
	private String res_grade;
	
	private Integer reg_stu;
	
	public ScoredVO() {}
	
	public int getStu_num() {
		return stu_num;
	}

	public void setStu_num(int stu_number) {
		this.stu_num = stu_number;
	}

	
	public String getStu_password() {
		return stu_password;
	}
	public void setStu_password(String stu_password) {
		this.stu_password = stu_password;
	}
	public String getLec_name() {
		return lec_name;
	}

	public void setLec_name(String cour_name) {
		this.lec_name = cour_name;
	}
	public String getLec_maj() {
		return lec_maj;
	}
	public void setLec_maj(String lec_maj) {
		this.lec_maj = lec_maj;
	}
	public String getLec_sem() {
		return lec_sem;
	}
	public void setLec_sem(String lec_sem) {
		this.lec_sem = lec_sem;
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
	public int getRes_score() {
		return res_score;
	}
	
	public void setRes_score(int score) {
		this.res_score = score;
	}

	public String getRes_grade() {
		return res_grade;
	}

	public void setRes_grade(String grade) {
		this.res_grade = grade;
	}

	
	
	
	
	public Integer getReg_stu() {
		return reg_stu;
	}

	public void setReg_stu(Integer reg_stu) {
		this.reg_stu = reg_stu;
	}
	
	
	
	
}
