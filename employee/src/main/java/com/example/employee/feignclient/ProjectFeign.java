package com.example.employee.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.example.employee.dto.ProjectDTO;

@FeignClient(url="http://localhost:8082",value="project-feign-client",path="/project")
public interface ProjectFeign {

	@GetMapping("/getById/{id}")
	ProjectDTO getProject(@PathVariable(name="id") Long id);
	
//	@GetMapping("/getById/{id}")
//	feign.Response getProject(@PathVariable(name="id") Long id);
}
