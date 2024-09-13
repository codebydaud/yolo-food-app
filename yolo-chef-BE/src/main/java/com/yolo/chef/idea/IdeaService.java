package com.yolo.chef.idea;


import com.yolo.chef.dietaryRestriction.DietaryRestriction;
import com.yolo.chef.dietaryRestriction.DietaryRestrictionRepository;
import com.yolo.chef.dto.IdeaDetailResponse;
import com.yolo.chef.dto.IdeaGetResponse;
import com.yolo.chef.dto.IdeaPostRequest;
import com.yolo.chef.exception.NotFoundException;
import com.yolo.chef.interest.Interest;
import com.yolo.chef.interest.InterestRepository;
import com.yolo.chef.recipe.Recipe;
import com.yolo.chef.recipe.RecipeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor

public class IdeaService {
    @Autowired
    private IdeaRepository ideaRepository;
    @Autowired
    private RecipeRepository recipeRepository;
    @Autowired
    private InterestRepository interestRepository;
    @Autowired
    private DietaryRestrictionRepository dietaryRestrictionRepository;

    public IdeaDetailResponse findById(Integer Id){
        Optional<Idea> optionalIdea= ideaRepository.findById(Id);

         if(optionalIdea.isPresent()){
                Idea idea = optionalIdea.get();
                IdeaDetailResponse ideaResponseObject= new IdeaDetailResponse();
                IdeaDetailResponse.Idea ideaResponse= new IdeaDetailResponse.Idea();
                ideaResponse.setTitle(idea.getTitle());
                ideaResponse.setDescription(idea.getDescription());
                ideaResponse.setCustomerName(idea.getCustomerName());
                ideaResponse.setCreatedAt(idea.getCreatedAt());

                List<Recipe> recipesList=recipeRepository.findByIdeaId(Id);
                Integer recipeCount=recipesList.size();
                ideaResponse.setRecipeCount(recipeCount);

                List<Interest> interests = interestRepository.findByIdeaId(Id);
                List<String> interestTexts = interests.stream()
                     .map(Interest::getDescription)
                     .collect(Collectors.toList());
                ideaResponse.setInterests(interestTexts);

                List<DietaryRestriction> dietaryRestrictions = dietaryRestrictionRepository.findByIdeaId(Id);
                List<String> dietaryRestrictionTexts = dietaryRestrictions.stream()
                     .map(DietaryRestriction::getDescription)
                     .collect(Collectors.toList());
                ideaResponse.setDietaryRestrictions(dietaryRestrictionTexts);
                ideaResponseObject.setIdea(ideaResponse);


                return ideaResponseObject;

         }
         else{
                throw new NotFoundException("Idea not found ","The idea with the provided ID does not exist.");
         }

    }

    public IdeaGetResponse getAllIdeas(Optional<String> title, Integer page, Integer size) {
        if (page < 0) {
            page = 0;
        }
        if (size > 100) {
            size = 100;
        }

        Page<Idea> ideasPage;

        if (title.isPresent()) {
            Optional<Page<Idea>> Page = ideaRepository.findByTitle(title.get(), PageRequest.of(page, size));
            if(Page.isPresent()){
               ideasPage=Page.get();
            }
            else{
                throw new NotFoundException("Idea not found", "The idea with the provided title does not exist.");
            }
        } else {
            ideasPage = ideaRepository.findAll(PageRequest.of(page, size));
        }

        if (!ideasPage.hasContent()) {
            throw new NotFoundException("Idea not found", "The idea with the provided title does not exist.");
        }

        IdeaGetResponse response = new IdeaGetResponse();
        response.setCurrentPage(ideasPage.getNumber());
        response.setPageSize(ideasPage.getSize());
        response.setTotalItems(ideasPage.getTotalElements());
        response.setTotalPages(ideasPage.getTotalPages());

        List<Idea> ideaDtos = ideasPage.getContent().stream()
                .map(idea -> {
                    Idea dto = new Idea();
                    dto.setId(idea.getId());
                    dto.setCustomerName(idea.getCustomerName());
                    dto.setTitle(idea.getTitle());
                    dto.setCode(idea.getCode());
                    dto.setDescription(idea.getDescription());
                    dto.setCreatedAt(idea.getCreatedAt());
                    return dto;
                })
                .collect(Collectors.toList());

        response.setIdeas(ideaDtos);

        return response;
    }

    public void createIdea(IdeaPostRequest request) {
        IdeaPostRequest.Idea ideaRequest = request.getIdea();
        Idea idea = new Idea();
        idea.setCustomerName(ideaRequest.getCustomerName());
        idea.setTitle(ideaRequest.getTitle());
        idea.setDescription(ideaRequest.getDescription());
        System.out.println("Code "+ideaRequest.getIdeaCode());
        idea.setCode(ideaRequest.getIdeaCode());
        System.out.println("Code "+idea.getCode());

        idea.setCreatedAt(LocalDateTime.now());
        ideaRepository.save(idea);

        // Save interests
        for (String interest : ideaRequest.getInterests()) {
            Interest newInterest = new Interest();
            newInterest.setDescription(interest);
            newInterest.setIdeaId(idea.getId());
            interestRepository.save(newInterest);
        }

        // Save dietary restrictions
        for (String restriction : ideaRequest.getDietaryRestrictions()) {
            DietaryRestriction newRestriction = new DietaryRestriction();
            newRestriction.setDescription(restriction);
            newRestriction.setIdeaId(idea.getId());
            dietaryRestrictionRepository.save(newRestriction);
        }
    }
    public String findIdeaTitleById(Integer ideaId) {
        Optional<Idea> idea=ideaRepository.findById(ideaId);
        if(idea.isPresent())
        {
            return idea.get().getTitle();
        }
        return "Idea Title Not Found";
    }

    public String findIdeaCodeById(Integer ideaId) {
        Optional<Idea> idea=ideaRepository.findById(ideaId);
        if(idea.isPresent())
        {
            return idea.get().getCode();
        }
        return "Idea Code Not Found";
    }
}

