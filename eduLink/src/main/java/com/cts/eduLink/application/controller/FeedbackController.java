package com.cts.eduLink.application.controller;

import com.cts.eduLink.application.dto.FeedbackDto;
import com.cts.eduLink.application.projection.FeedbackProjection;
import com.cts.eduLink.application.service.IFeedbackService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/feedback")
@AllArgsConstructor
@Slf4j
public class FeedbackController {

    private final IFeedbackService feedbackService;

    @PostMapping("/register")
    public ResponseEntity<String> registerFeedback(@RequestBody FeedbackDto feedbackDto){
        log.info("Received POST request to register feedback for User ID: {}", feedbackDto.getUserId());
        return ResponseEntity.status(200).body(feedbackService.registerFeedback(feedbackDto));
    }

    @GetMapping("/getFeedbackList")
    private  ResponseEntity<List<FeedbackProjection>> findFeedBackList(){
        return ResponseEntity.status(200).body(feedbackService.findFeedBackList());
    }

}
