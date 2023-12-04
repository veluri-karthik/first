 package com.example.project.serviceImpl;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.project.dto.ProjectDTO;
import com.example.project.entity.Project;
import com.example.project.repository.ProjectRepository;
import com.example.project.service.ProjectService;

@Transactional
@Service
public class ProjectServiceImpl implements ProjectService{

	@Autowired
	private ProjectRepository projectRepository;
	
	@Override
	public ProjectDTO saveProject(ProjectDTO projectDTO) {
		{
			if(projectDTO.getId() != 0 && projectDTO.getId() != null) {
				Project project=projectRepository.getById(projectDTO.getId());
				project.setName(projectDTO.getName());
				BeanUtils.copyProperties(project, projectDTO);
				return projectDTO;
			}else {
				Project project=new Project();
				project.setName(projectDTO.getName());
				projectRepository.save(project);
				BeanUtils.copyProperties(project, projectDTO);
				return projectDTO;
			}
			
		}
	}

	@Override
	public ProjectDTO getProject(Long projectId) {
		// TODO Auto-generated method stub
		
		System.out.println("projectController====>");
		System.out.println("project  service impl====>");
		ProjectDTO projectDTO=new ProjectDTO();
		if(projectId != 0 && projectId != null) {
			Project project=projectRepository.getById(projectId);
			BeanUtils.copyProperties(project, projectDTO);
			return projectDTO;
		}else
			return null;
	}

}
