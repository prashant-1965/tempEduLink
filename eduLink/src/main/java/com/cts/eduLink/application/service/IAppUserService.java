package com.cts.eduLink.application.service;

import com.cts.eduLink.application.entity.AppUser;
import org.jspecify.annotations.NonNull;
import org.springframework.security.core.userdetails.UserDetails;

public interface IAppUserService {
    void registerAppUser(AppUser appUser);
    UserDetails loadUserByUsername(@NonNull String userEmail);
}
