package com.entity;

import java.io.Serializable;
import java.util.Objects;

/**
 * 
 * ClassName: Project.java
 * Description:项目负责人 
 * date: 2015年8月18日 上午11:24:09
 * 
 * @author sm12652
 * @version V1.0
 * @since JDK 1.7
 */
public class Presider implements Serializable {
    private static final long serialVersionUID = 1L;

    private int id;//id
    private String name;//姓名
    private String jobNumber;//工号
    private String deptName;//部门
	private String authorityId; //统一权限Id
	private String role;
	private String email;

    public Presider() {}

	public Presider(String name, String jobNumber) {
		this.name = name;
		this.jobNumber = jobNumber;
	}

	public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getJobNumber() {
		return jobNumber;
	}

	public void setJobNumber(String jobNumber) {
		this.jobNumber = jobNumber;
	}
	
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getAuthorityId() {
		return authorityId;
	}

	public void setAuthorityId(String authorityId) {
		this.authorityId = authorityId;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Presider presider = (Presider) o;
		return Objects.equals(jobNumber, presider.jobNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(jobNumber);
	}
}
