package com.github.dmitrypopina.javaschool.ResourseManager.repo;

import com.github.dmitrypopina.javaschool.ResourseManager.domain.ResourceAllocation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceAllocationRepo extends JpaRepository<ResourceAllocation, Long> {
}
