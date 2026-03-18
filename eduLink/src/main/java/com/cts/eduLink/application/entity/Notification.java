package com.cts.eduLink.application.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String notificationMessage;

    @ManyToOne
    @JoinColumn(name = "app_user_id", referencedColumnName = "id")
    private AppUser appUser;
}
