package com.github.dmitrypopina.javaschool.ResourseManager.service;

import com.github.dmitrypopina.javaschool.ResourseManager.domain.Resource;
import com.github.dmitrypopina.javaschool.ResourseManager.repo.ResourceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ResourceService {
    private final ResourceRepo resourceRepo;

    @Autowired
    public ResourceService(ResourceRepo resourceRepo) {
        this.resourceRepo = resourceRepo;
    }

    public List<Resource> findAll() {
        return this.resourceRepo.findAll();
    }

    public Resource save(Resource resource) {
        return this.resourceRepo.save(resource);
    }

    public void delete(Resource resource) {
        this.resourceRepo.delete(resource);
    }
}
