package com.cts.eduLink.application.service;

import com.cts.eduLink.application.dto.FeedbackDto;
import com.cts.eduLink.application.projection.FeedbackProjection;

import java.util.List;

public interface FeedbackService {
    String registerFeedback(FeedbackDto feedbackDto);
    List<FeedbackProjection> findFeedBackList();
}
