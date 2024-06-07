package com.glossy.evolchat.service;

import com.glossy.evolchat.model.PointUsage;
import com.glossy.evolchat.repository.PointUsageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class PointUsageService {

    @Autowired
    private PointUsageRepository pointUsageRepository;

    public List<PointUsage> getAllPointUsages() {
        return pointUsageRepository.findAll();
    }

    public PointUsage getPointUsageById(int id) {
        return pointUsageRepository.findById(id).orElse(null);
    }

    public PointUsage savePointUsage(PointUsage pointUsage) {
        return pointUsageRepository.save(pointUsage);
    }

    public void deletePointUsage(int id) {
        pointUsageRepository.deleteById(id);
    }
}
