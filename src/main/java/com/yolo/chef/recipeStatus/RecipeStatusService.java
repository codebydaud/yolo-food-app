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
        if(recipeStatus.isPresent())
        {
            return recipeStatus.get().getValue();
        }
        return "Unknown";
    }

    public Integer findStatusIdByName(String status) {
        return recipeStatusRepository.findByValue(status)
                .map(RecipeStatus::getId)
                .orElse(null);
    }
}
