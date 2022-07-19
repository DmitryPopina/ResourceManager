package com.github.dmitrypopina.javaschool.ResourseManager.controller;

import com.github.dmitrypopina.javaschool.ResourseManager.domain.User;
import com.github.dmitrypopina.javaschool.ResourseManager.request.springsecurity.LoginRequest;
import com.github.dmitrypopina.javaschool.ResourseManager.service.ResourceService;
import com.github.dmitrypopina.javaschool.ResourseManager.domain.Resource;
import com.github.dmitrypopina.javaschool.ResourseManager.service.UserService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.AuthenticatedPrincipal;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("resource")
public class ResourceController {
    private final ResourceService resourceService;
    private final UserService userService;

    @Autowired
    public ResourceController(ResourceService resourceService, UserService userService) {
        this.resourceService = resourceService;
        this.userService = userService;
    }

    @GetMapping
    public List<Resource> listResource()
    {
        return resourceService.findAll();
    }

    @GetMapping("{id}")
    public Resource getResource(@PathVariable("id") Resource resource) {
        return resource;
    }

    @PostMapping
    public Resource create(@RequestBody Resource resource, @AuthenticationPrincipal User user){
        //resource.setOwner(user);
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
