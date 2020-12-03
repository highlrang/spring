package com.myproject.myweb.domain;

public class MajorVO {
	private String dept_name;
	private String upper_dept;
	
	public MajorVO() {};
	
	public MajorVO(String dept_name) {
		this.dept_name = dept_name;
	};

	
	public String getDept_name() {
		return dept_name;
	}
	public void setMaj_name(String dept_name) {
		this.dept_name = dept_name;
	}

	public String getUpper_dept() {
		return upper_dept;
	}

	public void setUpper_dept(String upper_dept) {
		this.upper_dept = upper_dept;
	}

	
}
