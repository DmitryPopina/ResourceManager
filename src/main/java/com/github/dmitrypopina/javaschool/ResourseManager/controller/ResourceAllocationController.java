package com.github.dmitrypopina.javaschool.ResourseManager.controller;

import com.github.dmitrypopina.javaschool.ResourseManager.domain.Resource;
import com.github.dmitrypopina.javaschool.ResourseManager.domain.ResourceAllocation;
import com.github.dmitrypopina.javaschool.ResourseManager.domain.User;
import com.github.dmitrypopina.javaschool.ResourseManager.service.ResourceAllocationService;
import lombok.Data;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("resource-allocations")
public class ResourceAllocationController {

    private final ResourceAllocationService resourceAllocationService;
    @GetMapping("{resource}")
    public List<ResourceAllocation> listAllocations(@PathVariable Resource resource,
                                                    @AuthenticationPrincipal User user){
        if (user.isAdmin() || user.equals(resource.getOwner())) {
            return resource.getAllocations();
        }
        else {
            //TODO: add exception raise
            return null;
        }
    }

    @PostMapping("{resource}")
    public ResourceAllocation create(@RequestBody ResourceAllocation allocation,
                                     @PathVariable Resource resource,
                                     @AuthenticationPrincipal User user){
        if (user.isAdmin() || user.equals(resource.getOwner())) {
            allocation.setUser(user);
            allocation.setResource(resource);
            return resourceAllocationService.save(allocation);
        }else {
            //TODO: add exception raise
            return null;

        }
    }


}
