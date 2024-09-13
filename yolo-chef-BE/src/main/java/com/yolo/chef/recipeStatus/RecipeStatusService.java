package com.yolo.chef.recipeStatus;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RecipeStatusService {
    private final RecipeStatusRepository recipeStatusRepository;

    public String findStatusById(Integer recipeStatusId)
    {
        Optional <RecipeStatus> recipeStatus = recipeStatusRepository.findById(recipeStatusId);
        return recipeStatus.map(status -> status.getValue().toLowerCase()).orElse("Unknown");
    }

    public Integer findStatusIdByName(String status) {
        return recipeStatusRepository.findByValueAndIsActive(status,true)
                .map(RecipeStatus::getId)
                .orElse(null);
    }
}
