package com.github.dmitrypopina.javaschool.ResourseManager.service;

import com.github.dmitrypopina.javaschool.ResourseManager.domain.ResourceAllocation;
import com.github.dmitrypopina.javaschool.ResourseManager.repo.ResourceAllocationRepo;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class ResourceAllocationService {
    private final ResourceAllocationRepo resourceAllocationRepo;

    public List<ResourceAllocation> findAll() {
        return this.resourceAllocationRepo.findAll();
    }

    public ResourceAllocation save(ResourceAllocation resourceAllocation) {
        return this.resourceAllocationRepo.save(resourceAllocation);
    }

    public void delete(ResourceAllocation resourceAllocation) {
        this.resourceAllocationRepo.delete(resourceAllocation);
    }
}
