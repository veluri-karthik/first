package com.example.gateway.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(url="http://localhost:8081",value="project-feign-client",path="/employee")
public interface EmployeeFeign {

	@GetMapping("/getByName")
	public EmployeeDTO getEmployeeCredentials(@RequestParam(name="username") String username,@RequestParam(name="password") String password);
}
