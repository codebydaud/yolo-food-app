package com.yolo.chef.idea;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class IdeaService {

    private final IdeaRepository ideaRepository;

    @Autowired
    public IdeaService(IdeaRepository ideaRepository) {
        this.ideaRepository = ideaRepository;
    }

    public String findIdeaTitleById(Integer ideaId) {
        Optional<Idea> idea=ideaRepository.findById(ideaId);
        if(idea.isPresent())
        {
            return idea.get().getTitle();
        }
        return "Idea Title Not Found";
    }
}
