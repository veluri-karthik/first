package com.example.project.service;

import com.example.project.dto.ProjectDTO;


public interface ProjectService {

	public ProjectDTO saveProject(ProjectDTO projectDTO);
	
	public ProjectDTO getProject(Long projectId);
}
