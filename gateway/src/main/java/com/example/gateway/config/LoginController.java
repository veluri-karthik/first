package com.example.gateway.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

	@Autowired
	EmployeeFeign employeeFeign;
	
	@Autowired
	JWTTokenProvider jwtTokenProvider;
	
	@PostMapping("/login")
	public ResponseDTO getLoginCredentials(@RequestParam(name="username") String username,@RequestParam("password") String password) {
		ResponseDTO responseDTO=new ResponseDTO();
		EmployeeDTO employeeDTO=employeeFeign.getEmployeeCredentials(username, password);
		if(null != employeeDTO) {
			String token = jwtTokenProvider.generateToken(username);
//			String refreshToken = jwtTokenProvider.generateRefreshToken(username);
			
			responseDTO.setJwtToken(token);
			return responseDTO;
		}else {
			responseDTO.setError("please check credentials.");
			return responseDTO;
		}
		
	}
	
}
