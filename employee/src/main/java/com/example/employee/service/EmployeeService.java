package com.example.employee.service;

import com.example.employee.dto.EmployeeDTO;


public interface EmployeeService {

	public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO);
	
	public EmployeeDTO getEmployee(Long employeeId);

	public EmployeeDTO getEmployeeByName(String username,String password);
}
