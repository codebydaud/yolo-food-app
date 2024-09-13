package com.yolo.customer.idea.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class IdeaDTO {
    private IdeaDetails idea;

    public IdeaDetails getIdea() {
        return (idea == null) ? null : new IdeaDetails(idea);
    }

    public void setIdea(IdeaDetails idea) {
        this.idea = (idea == null) ? null : new IdeaDetails(idea);
    }

    @Getter
    @Setter
    public static class IdeaDetails {
        private String customer_name;
        private String title;
        private String description;
        private String idea_code;
        private List<String> interests = new ArrayList<>();
        private List<String> dietary_restrictions = new ArrayList<>();

        public IdeaDetails(IdeaDetails other) {
            if (other != null) {
                this.customer_name = other.customer_name;
                this.title = other.title;
                this.description = other.description;
                this.idea_code = other.idea_code;
                this.interests = new ArrayList<>(other.interests);
                this.dietary_restrictions = new ArrayList<>(other.dietary_restrictions);
            }
        }

        public IdeaDetails() {}

        public List<String> getInterests() {
            return new ArrayList<>(interests);
        }

    }
}