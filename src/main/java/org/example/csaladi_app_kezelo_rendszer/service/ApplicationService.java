package org.example.csaladi_app_kezelo_rendszer.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.example.csaladi_app_kezelo_rendszer.dto.ApplicationDto;
import org.example.csaladi_app_kezelo_rendszer.entity.ApplicationEntity;
import org.example.csaladi_app_kezelo_rendszer.mapper.ApplicationMapper;
import org.example.csaladi_app_kezelo_rendszer.repository.ApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final ApplicationMapper applicationMapper;

    @Transactional
    public ApplicationDto createApplication(ApplicationDto application) {
        ApplicationEntity applicationToCreate = ApplicationEntity.builder().name(application.getName()).build();
        ApplicationEntity applicationToReturn = applicationRepository.save(applicationToCreate);
        return applicationMapper.toDto(applicationToReturn);
    }

    @Transactional
    public ApplicationDto updateApplication(String id, ApplicationDto application) {
        ApplicationEntity applicationToUpdate = applicationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Application not found!"));
        applicationToUpdate.setName(application.getName());
        ApplicationEntity applicationToReturn = applicationRepository.save(applicationToUpdate);
        return applicationMapper.toDto(applicationToReturn);
    }

    @Transactional
    public void deleteApplication(String id) {
        ApplicationEntity applicationToDelete = applicationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Application not found!"));
        applicationRepository.delete(applicationToDelete);
    }

    @Transactional(readOnly = true)
    public ApplicationDto getApplicationById(String id) {
        ApplicationEntity applicationToReturn = applicationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Application not found!"));
        return applicationMapper.toDto(applicationToReturn);
    }

    @Transactional(readOnly = true)
    public List<ApplicationDto> getAllApplication() {
        List<ApplicationEntity> listEntities = applicationRepository.findAll();
        return listEntities.stream().map(applicationMapper::toDto).toList();
    }
}
