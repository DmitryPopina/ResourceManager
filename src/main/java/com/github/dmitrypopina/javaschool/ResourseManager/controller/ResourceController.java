package com.github.dmitrypopina.javaschool.ResourseManager.controller;

import com.github.dmitrypopina.javaschool.ResourseManager.domain.Resource;
import com.github.dmitrypopina.javaschool.ResourseManager.domain.User;
import com.github.dmitrypopina.javaschool.ResourseManager.domain.UserRole;
import com.github.dmitrypopina.javaschool.ResourseManager.service.ResourceService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("resource")
public class ResourceController {
    private final ResourceService resourceService;

    @Autowired
    public ResourceController(ResourceService resourceService) {
        this.resourceService = resourceService;
    }

    @GetMapping
    public List<Resource> listResource()
    {
        return resourceService.findAll();
    }

    @GetMapping("{id}")
    public Resource getResource(@PathVariable("id") Resource resource,
                                @AuthenticationPrincipal User user) {
        if (user.isAdmin() || (resource.getOwner() == user)) {
            return resource;
        }
        else {
            return null;
        }
    }

    @PostMapping
    public Resource create(@RequestBody Resource resource,
                           @AuthenticationPrincipal User user){
        if (resource.getOwner() == null || (!user.isAdmin() && resource.getOwner() != user)){
            resource.setOwner(user);
        }
        return resourceService.save(resource);
    }

    @PutMapping("{id}")
    public Resource update(
            @PathVariable("id") Resource resourceFromDb,
            @RequestBody Resource resource
    ){
        BeanUtils.copyProperties(resource, resourceFromDb, "id");
        return resourceService.save(resourceFromDb);
    }

    @DeleteMapping("{id}")
    public void delete(@PathVariable("id") Resource resource){
        resourceService.delete(resource);
    }
}
