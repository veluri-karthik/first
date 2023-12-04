package com.example.employee.controller;

import java.util.concurrent.CompletableFuture;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.example.employee.dto.EmployeeDTO;
import com.example.employee.service.EmployeeService;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import io.github.resilience4j.timelimiter.annotation.TimeLimiter;

@RestController
@RequestMapping("/employee")
public class EmployeeController {

	@Autowired
	private EmployeeService employeeService;
	
	@PostMapping("/save")
	public EmployeeDTO saveEmployee(@RequestBody EmployeeDTO employeeDTO) {
		return employeeService.saveEmployee(employeeDTO);
	}
	
	
	
	@GetMapping("/getById/{id}")
//	@Retry(name="employeeservice")
//	@TimeLimiter(name="employeeservice")
	@CircuitBreaker(name="",fallbackMethod="fallbackMethod")
//	public CompletableFuture<String> saveEmployee(@PathVariable(name="id") Long id) {
//		
	public EmployeeDTO saveEmployee(@PathVariable(name="id") Long id) {
		
		EmployeeDTO employeeDTO= employeeService.getEmployee(id);
		System.out.println("employee object is"+employeeDTO.toString());
		return employeeDTO;
	}
	
	@GetMapping("/getByName")
	public EmployeeDTO getEmployeeCredentials(@RequestParam(name="username") String username,@RequestParam(name="password") String password) {
		
		EmployeeDTO employeeDTO= employeeService.getEmployeeByName(username,password);
		System.out.println("employee object is"+employeeDTO.toString());
		return employeeDTO;
	}
	
	
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public EmployeeDTO fallbackMethod(@PathVariable Long id, RuntimeException ex) {
		EmployeeDTO employeeDTO=new EmployeeDTO();
		employeeDTO.setError("Service is down. Please try after some time.");
		return employeeDTO;
	}
	
	
}
