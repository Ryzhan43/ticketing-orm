package com.mryzhan.service.impl;

import com.mryzhan.dto.ProjectDTO;
import com.mryzhan.entity.Project;
import com.mryzhan.enums.Status;
import com.mryzhan.mapper.ProjectMapper;
import com.mryzhan.repository.ProjectRepository;
import com.mryzhan.service.ProjectService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;


    public ProjectServiceImpl(ProjectRepository projectRepository, ProjectMapper projectMapper) {
        this.projectMapper = projectMapper;
        this.projectRepository = projectRepository;
    }

    @Override
    public List<ProjectDTO> listAllProjects() {
        return projectRepository.findAll().stream().map(projectMapper::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public void save(ProjectDTO projectDTO) {
        projectDTO.setProjectStatus(Status.OPEN);

        Project project =projectMapper.convertToEntity(projectDTO);
        projectRepository.save(project);
    }

    @Override
    public void update(ProjectDTO dto) {
        Project project = projectRepository.findByProjectCode(dto.getProjectCode());
        Project convertedProject = projectMapper.convertToEntity(dto);
        convertedProject.setId(project.getId());
        convertedProject.setProjectStatus(project.getProjectStatus());

        projectRepository.save(convertedProject);
    }

    @Override
    public void delete(String code) {
        Project project =  projectRepository.findByProjectCode(code);
        project.setIsDeleted(true);
        projectRepository.save(project);
    }

    @Override
    public void deleteById(String projectcode) {

    }

    @Override
    public void complete(ProjectDTO projectDTO) {
        Project project = projectRepository.findByProjectCode(projectDTO.getProjectCode());
        project.setProjectStatus(Status.COMPLETE);
        projectRepository.save(project);
    }

    @Override
    public ProjectDTO findByProjectCode(String source) {
        Project project = projectRepository.findByProjectCode(source);
        return projectMapper.convertToDTO(project);
    }


    @Override
    public ProjectDTO findById(Long source) {
        return projectMapper.convertToDTO(projectRepository.findById(source).get());
    }
}