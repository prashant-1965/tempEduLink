package com.cts.eduLink.application.dto;

import lombok.Getter;

@Getter
public class FeedbackDto {
    private Long userId; // this can we app_user_id or faculty_id
    private String reviewerType; // either student or faculty
    private double rating;
    private String comment;
}
