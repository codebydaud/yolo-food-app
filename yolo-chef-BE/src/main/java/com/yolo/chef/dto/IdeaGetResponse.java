package com.yolo.chef.dto;

import com.yolo.chef.idea.Idea;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class IdeaGetResponse {

    private int currentPage;
    private int pageSize;
    private long totalItems;
    private int totalPages;
    private List<Idea> ideas;

}
