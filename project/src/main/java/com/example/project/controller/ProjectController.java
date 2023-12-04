package com.example.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.project.dto.ProjectDTO;
import com.example.project.service.ProjectService;

@RestController
@RequestMapping("/project")
public class ProjectController {

	@Autowired
	private ProjectService projectService;
	
	@PostMapping("/save")
	public ProjectDTO saveproject(@RequestBody ProjectDTO projectDTO) {
		return projectService.saveProject(projectDTO);
	}
	
	@GetMapping("/getById/{id}")
	public ProjectDTO getProject(@PathVariable(name="id") Long id) {
		System.out.println("projectController====>"+id);
		return projectService.getProject(id);
	}
	
	
}
