package com.yolo.customer.idea;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.yolo.customer.idea.dietaryRestriction.DietaryRestriction;
import com.yolo.customer.idea.dietaryRestriction.DietaryRestrictionRepository;
import com.yolo.customer.idea.dto.IdeaDTO;
import com.yolo.customer.idea.dto.IdeaRequest;
import com.yolo.customer.idea.dto.IdeaResponse;
import com.yolo.customer.idea.ideaStatus.IdeaStatus;
import com.yolo.customer.idea.ideaStatus.IdeaStatusRepository;
import com.yolo.customer.idea.ideaStatus.IdeaStatusService;
import com.yolo.customer.idea.interest.Interest;
import com.yolo.customer.idea.interest.InterestRepository;
import com.yolo.customer.user.User;
import com.yolo.customer.user.UserRepository;
import com.yolo.customer.utils.ApiMessages;
import com.yolo.customer.utils.GetContextHolder;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class IdeaService {

    @Autowired
    private IdeaRepository ideaRepository;

    @Autowired
    private IdeaStatusRepository ideaStatusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IdeaStatusService ideaStatusService;

    @Autowired
    private InterestRepository interestRepository;

    @Autowired
    private DietaryRestrictionRepository dietaryRestrictionRepository;

    public ResponseEntity<Map<String, String>> submitIdeaToVendor(Integer ideaId, String status) {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = GetContextHolder.getUsernameFromAuthentication(authentication);
        User loggedInUser = userRepository.findByUsername(username).orElseThrow(() ->
                new EntityNotFoundException("User with given username does not exist"));

        if (status == null || status.isEmpty()) {
            throw new EntityNotFoundException("Status cannot be empty.");
        }

        Idea idea = ideaRepository.findById(ideaId)
                .orElseThrow(() -> new EntityNotFoundException("Ideas not found"));

        if (!idea.getUserId().equals(loggedInUser.getId())) {
            throw new RuntimeException("Unauthorized to update idea.");
        }

        boolean vendorApiSuccess = callVendorApi(idea, username);

        if (vendorApiSuccess) {
            Integer statusId = ideaStatusService.findStatusIdByName(status);

            IdeaStatus ideaStatus = new IdeaStatus();
            ideaStatus.setId(statusId);
            idea.setIdeaStatusId(ideaStatus);
            ideaRepository.save(idea);

            Map<String, String> response = new HashMap<>();
            response.put("status", "success");
            response.put("message", "Idea submitted and status updated successfully.");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        } else {
            throw new RuntimeException("Failed to submit idea to the vendor.");
        }
    }


    private boolean callVendorApi(Idea idea, String username) {
        IdeaDTO.IdeaDetails ideaDetails = new IdeaDTO.IdeaDetails();
        ideaDetails.setCustomer_name(username);
        ideaDetails.setTitle(idea.getTitle());
        ideaDetails.setDescription(idea.getDescription());
        ideaDetails.setIdea_code(idea.getCode());

        List<String> interests = interestRepository.findByIdeaId(idea.getId())
                .stream()
                .map(Interest::getDescription)
                .collect(Collectors.toList());
        ideaDetails.setInterests(interests);

        List<String> dietaryRestrictions = dietaryRestrictionRepository.findByIdeaId(idea.getId())
                .stream()
                .map(DietaryRestriction::getDescription)
                .collect(Collectors.toList());
        ideaDetails.setDietary_restrictions(dietaryRestrictions);

        IdeaDTO ideaDTO = new IdeaDTO();
        ideaDTO.setIdea(ideaDetails);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<IdeaDTO> requestEntity = new HttpEntity<>(ideaDTO, headers);

        String vendorApiUrl = "http://localhost:8081/api/v1/ideas";

        RestTemplate restTemplate = new RestTemplate();

        return true;
    }

    @Transactional
    public Idea createDraftIdea(IdeaRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = GetContextHolder.getUsernameFromAuthentication(authentication);

        User loggedInUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new EntityNotFoundException("User with given username does not exists: " + username));

        Integer userId = loggedInUser.getId();

        List<String> interestsList = request.getInterests();
        if (interestsList == null || interestsList.isEmpty()) {
            throw new IllegalArgumentException(ApiMessages.INTERESTS_REQUIRED.getMessage());
        }

        Idea idea = new Idea();
        idea.setTitle(request.getTitle());
        idea.setDescription(request.getDescription());
        idea.setUserId(userId);
        idea.setCode(generateUniqueCode());

        IdeaStatus draftStatus = ideaStatusRepository.findByValue("Draft")
                .orElseThrow(() -> new RuntimeException(ApiMessages.DEFAULT_IDEA_STATUS_NOT_FOUND.getMessage()));
        idea.setIdeaStatusId(draftStatus);

        idea = ideaRepository.save(idea);

        List<String> dietaryRestrictions = request.getDietaryRestrictions();
        if (dietaryRestrictions != null && !dietaryRestrictions.isEmpty()) {
            Idea finalIdea = idea;
            List<DietaryRestriction> restrictions = dietaryRestrictions.stream()
                    .filter(desc -> desc != null && !desc.trim().isEmpty())
                    .limit(3)
                    .map(desc -> {
                        DietaryRestriction restriction = new DietaryRestriction();
                        restriction.setDescription(desc);
                        restriction.setIdea(finalIdea);
                        return restriction;
                    })
                    .collect(Collectors.toList());

            if (!restrictions.isEmpty()) {
                dietaryRestrictionRepository.saveAll(restrictions);
            }
        }

        Idea finalIdea1 = idea;
        List<Interest> interests = interestsList.stream()
                .filter(desc -> desc != null && !desc.trim().isEmpty())
                .limit(3)
                .map(desc -> {
                    Interest interest = new Interest();
                    interest.setDescription(desc);
                    interest.setIdea(finalIdea1);
                    return interest;
                })
                .collect(Collectors.toList());

        interestRepository.saveAll(interests);

        return idea;
    }

    @Transactional
    public Page<IdeaResponse> getIdeas(Optional<Integer> statusId, String search, int page, int size, String sortOrder) {
        if (page < 1) {
            throw new IllegalArgumentException(ApiMessages.PAGE_INDEX_NEGATIVE.getMessage());
        }
        if (size <= 0) {
            throw new IllegalArgumentException(ApiMessages.PAGE_SIZE_INVALID.getMessage());
        }
        if (size > 1000) {
            size = 1000;
        }

        Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by("createdAt").ascending() : Sort.by("createdAt").descending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        Page<Idea> ideas;

        if (statusId.isPresent() && ideaStatusRepository.existsById(statusId.get())) {
            ideas = (search != null && !search.isEmpty()) ?
                    ideaRepository.findByIdeaStatusIdAndTitleContainingIgnoreCase(statusId.get(), search, pageable) :
                    ideaRepository.findByIdeaStatusId(statusId.get(), pageable);
        } else if (search != null && !search.isEmpty()) {
            ideas = ideaRepository.findByTitleContainingIgnoreCase(search, pageable);
        } else {
            ideas = ideaRepository.findAll(pageable);
        }

        return ideas.map(this::mapToIdeaResponse);
    }


    private IdeaResponse mapToIdeaResponse(Idea idea) {
        List<String> interests = interestRepository.findByIdeaId(idea.getId())
                .stream()
                .map(Interest::getDescription)
                .collect(Collectors.toList());

        List<String> dietaryRestrictions = dietaryRestrictionRepository.findByIdeaId(idea.getId())
                .stream()
                .map(DietaryRestriction::getDescription)
                .collect(Collectors.toList());

        IdeaResponse response = new IdeaResponse();
        response.setIdeaId(idea.getId());
        response.setTitle(idea.getTitle());
        response.setDescription(idea.getDescription());
        response.setInterests(interests);
        response.setDietaryRestrictions(dietaryRestrictions);
        response.setIdeaStatus(ideaStatusRepository.findById(idea.getIdeaStatusId().getId())
                .map(IdeaStatus::getCode)
                .orElse(null));
        response.setCreatedAt(idea.getCreatedAt());

        return response;
    }

    private String generateUniqueCode() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
}
