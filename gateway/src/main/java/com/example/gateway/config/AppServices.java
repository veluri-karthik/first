package com.example.gateway.config;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="appservices")
public class AppServices {

	@Id
	@Column(name="id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(name="end_point_method")
	private String endPointMethod;
	
	@Column(name="end_point_url")
	private String endPointURL;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEndPointMethod() {
		return endPointMethod;
	}

	public void setEndPointMethod(String endPointMethod) {
		this.endPointMethod = endPointMethod;
	}

	public String getEndPointURL() {
		return endPointURL;
	}

	public void setEndPointURL(String endPointURL) {
		this.endPointURL = endPointURL;
	}
	
	
}
