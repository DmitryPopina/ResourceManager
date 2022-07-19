package com.github.dmitrypopina.javaschool.ResourseManager.controller;

import com.github.dmitrypopina.javaschool.ResourseManager.domain.Resource;
import com.github.dmitrypopina.javaschool.ResourseManager.domain.ResourceAllocation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("resource-allocations")
public class ResourceAllocationController {

    @GetMapping("{resource}")
    public List<ResourceAllocation> listAllocations(@PathVariable Resource resource){
        return resource.getAllocations();
    }
}
