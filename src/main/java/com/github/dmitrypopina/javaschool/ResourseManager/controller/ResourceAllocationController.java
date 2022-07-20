package com.github.dmitrypopina.javaschool.ResourseManager.controller;

import com.github.dmitrypopina.javaschool.ResourseManager.domain.Resource;
import com.github.dmitrypopina.javaschool.ResourseManager.domain.ResourceAllocation;
import com.github.dmitrypopina.javaschool.ResourseManager.domain.User;
import com.github.dmitrypopina.javaschool.ResourseManager.exception.CustomException;
import com.github.dmitrypopina.javaschool.ResourseManager.service.ResourceAllocationService;
import lombok.Data;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@RequestMapping("resource-allocations")
public class ResourceAllocationController {

    private final ResourceAllocationService resourceAllocationService;
    @GetMapping("{resource}")
    public List<ResourceAllocation> listAllocations(@PathVariable Resource resource){
        return resource.getAllocations();
    }

    @PostMapping("{resource}")
    public ResourceAllocation create(@RequestBody ResourceAllocation allocation,
                                     @PathVariable Resource resource,
                                     @AuthenticationPrincipal User user){
        if (user.isAdmin() || user.equals(resource.getOwner())) {
            allocation.setResource(resource);
            return resourceAllocationService.save(allocation);
        }else {
            throw new CustomException("You can't add allocation to not your resource", HttpStatus.FORBIDDEN);
        }
    }
    @DeleteMapping("{resource}/{id}")
    public void delete(@PathVariable Resource resource,
                                     @PathVariable("id") ResourceAllocation allocation,
                                     @AuthenticationPrincipal User user){
        if (user.isAdmin() || user.equals(resource.getOwner())) {
            resourceAllocationService.delete(allocation);
        }else {
            throw new CustomException("You can't delete allocation from not your resource", HttpStatus.FORBIDDEN);
        }
    }
    @PutMapping("{resource}/{id}")
    public ResourceAllocation edit(@RequestBody ResourceAllocation allocation,
                                   @PathVariable Resource resource,
                                   @PathVariable("id") ResourceAllocation allocationFromDb,
                                   @AuthenticationPrincipal User user){
        if (user.isAdmin() || user.equals(resource.getOwner())) {
            BeanUtils.copyProperties(allocation, allocationFromDb, "id");
        }else {
            allocationFromDb.setUser(user);
        }
        return resourceAllocationService.save(allocationFromDb);
    }
    /*
    Админ может назначить кого угодно.
    Владелец ресурса может назначить кого угодно
    Просто пользователь может только назначить себя
     */
    @PutMapping("{resource}/allocate/{id}/{userid}")
    public ResourceAllocation allocate(@PathVariable Resource resource,
                                       @PathVariable("id") ResourceAllocation allocation,
                                       @AuthenticationPrincipal User user,
                                       @PathVariable(name = "userid") User userToAllocate){
        if (user.isAdmin() || user.equals(resource.getOwner())) {
            allocation.setUser(userToAllocate);
        }else {
            throw new CustomException("You can't modify not your allocation", HttpStatus.FORBIDDEN);
        }
        return resourceAllocationService.save(allocation);
    }
    /*
    Админ может освободить любую аллокацию.
    Владелец ресурса тоже
    Просто пользователь освободить только свою бронь
     */
    @PutMapping("{resource}/deallocate/{id}")
    public ResourceAllocation deallocate(@PathVariable Resource resource,
                                       @PathVariable("id") ResourceAllocation allocation,
                                       @AuthenticationPrincipal User user){
        if (user.isAdmin() || user.equals(resource.getOwner()) || user.equals(allocation.getUser())) {
            allocation.setUser(null);
        }else {
            throw new CustomException("You can't modify not your allocation", HttpStatus.FORBIDDEN);
        }
        return resourceAllocationService.save(allocation);
    }
}
