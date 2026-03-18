package com.cts.eduLink.application.projection;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FeedbackProjection {
    private String userName;
    private String message;
    private double rating;
}
