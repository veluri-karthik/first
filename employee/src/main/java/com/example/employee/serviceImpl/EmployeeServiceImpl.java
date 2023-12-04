 package com.example.employee.serviceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.employee.dto.EmployeeDTO;
import com.example.employee.dto.ProjectDTO;
import com.example.employee.entity.Employee;
import com.example.employee.feignclient.ProjectFeign;
import com.example.employee.repository.EmployeeRepository;
import com.example.employee.service.EmployeeService;

@Transactional
@Service
public class EmployeeServiceImpl implements EmployeeService{

	@Autowired
	private EmployeeRepository employeeRepository;
	
	@Autowired
	private ProjectFeign projectFeign;
	
	@Override
	public EmployeeDTO saveEmployee(EmployeeDTO employeeDTO) {
		{
			if(employeeDTO.getId() != 0 && employeeDTO.getId() != null) {
				Employee employee=employeeRepository.getById(employeeDTO.getId());
				ProjectDTO projectDTO= projectFeign.getProject(employeeDTO.getProjectId());
				employee.setProjectId(projectDTO.getId());
				employee.setMail(employeeDTO.getMail());
				employee.setName(employeeDTO.getName());
				BeanUtils.copyProperties(employee, employeeDTO);
				return employeeDTO;
			}else {
				Employee employee=new Employee();
				ProjectDTO projectDTO= projectFeign.getProject(employeeDTO.getProjectId());
				
				employee.setProjectId(projectDTO.getId());
				employee.setMail(employeeDTO.getMail());
				employee.setName(employeeDTO.getName());
				employeeRepository.save(employee);
				BeanUtils.copyProperties(employee, employeeDTO);
				return employeeDTO;
			}
			
		}
		}

	@Override
	public EmployeeDTO getEmployee(Long employeeId) {
		// TODO Auto-generated method stub
		EmployeeDTO employeeDTO=new EmployeeDTO();
		if(employeeId != 0 && employeeId != null) {
			Employee employee=employeeRepository.getEmployeeById(employeeId);
			ProjectDTO projectDTO= projectFeign.getProject(employee.getProjectId());
			BeanUtils.copyProperties(employee, employeeDTO);
			employeeDTO.setProjectId(projectDTO.getId());
			return employeeDTO;
		}else
			return null;
	}

	@Override
	public EmployeeDTO getEmployeeByName(String username,String password) {
		try {
			EmployeeDTO employeeDTO=new EmployeeDTO();
			Employee employee=employeeRepository.getEmployeeByName(username,password);
			if(employee != null) {
			BeanUtils.copyProperties(employee, employeeDTO);
			return employeeDTO;
			}else {
				return null;
			}
		}catch(Exception e) {
			return null;
		}
		
	}

}
