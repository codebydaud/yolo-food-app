package com.yolo.customer.idea.ideaStatus;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class IdeaStatusService {

    @Autowired
    private IdeaStatusRepository ideaStatusRepository;

    public Integer findStatusIdByName(String status) {
        IdeaStatus ideaStatus = ideaStatusRepository.findByValue(status)
                .orElseThrow(() -> new RuntimeException("Idea status '" + status + "' not found"));
        return ideaStatus.getId();
    }
}