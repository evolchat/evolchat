package com.glossy.evolchat.controller;

import com.glossy.evolchat.model.PointUsage;
import com.glossy.evolchat.service.PointUsageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/point-usage")
public class PointUsageController {

    @Autowired
    private PointUsageService pointUsageService;

    @GetMapping
    public List<PointUsage> getAllPointUsages() {
        return pointUsageService.getAllPointUsages();
    }

    @GetMapping("/{id}")
    public PointUsage getPointUsageById(@PathVariable int id) {
        return pointUsageService.getPointUsageById(id);
    }

    @PostMapping
    public PointUsage createPointUsage(@RequestBody PointUsage pointUsage) {
        return pointUsageService.savePointUsage(pointUsage);
    }

    @DeleteMapping("/{id}")
    public void deletePointUsage(@PathVariable int id) {
        pointUsageService.deletePointUsage(id);
    }
}
