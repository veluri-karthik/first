package com.example.employee.dto;

public class EmployeeDTO {

	private Long id;
	private String name;
	private String mail;
	private Long projectId;
	private String error;
	
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Long getProjectId() {
		return projectId;
	}
	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMail() {
		return mail;
	}
	public void setMail(String mail) {
		this.mail = mail;
	}
	public EmployeeDTO(Long id, String name, String mail,Long projectId) {
		super();
		this.id = id;
		this.name = name;
		this.mail = mail;
		this.projectId = projectId;
	}
	public EmployeeDTO() {
		
	}
	
}
