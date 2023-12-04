package com.example.gateway.config;

public class ResponseDTO {

	private String jwtToken;
	
	private String jwtRefreshToken;
	
	private String error;

	public String getJwtToken() {
		return jwtToken;
	}

	public void setJwtToken(String jwtToken) {
		this.jwtToken = jwtToken;
	}

	public String getJwtRefreshToken() {
		return jwtRefreshToken;
	}

	public void setJwtRefreshToken(String jwtRefreshToken) {
		this.jwtRefreshToken = jwtRefreshToken;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
	
//	private 
	
	
}
