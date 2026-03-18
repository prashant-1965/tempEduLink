package com.cts.eduLink.application.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class FeedBack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private double rating;
    private String message;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "app_user_id",referencedColumnName = "id")
    private AppUser appUser;
}
