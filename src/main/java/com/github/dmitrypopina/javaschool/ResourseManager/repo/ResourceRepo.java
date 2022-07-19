package com.github.dmitrypopina.javaschool.ResourseManager.repo;

import com.github.dmitrypopina.javaschool.ResourseManager.domain.Resource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepo extends JpaRepository<Resource, Long> {

}
