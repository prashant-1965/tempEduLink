package com.cts.eduLink.application.service;

import com.cts.eduLink.application.classexception.AppUserException;
import com.cts.eduLink.application.entity.AppUser;
import com.cts.eduLink.application.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NonNull;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserServiceImpl implements IAppUserService, UserDetailsService {
    private final AppUserRepository appUserRepository;

    @Override
    @Transactional
    public void registerAppUser(AppUser appUser) throws AppUserException {
        log.info("AppUser registration intercepted ");
        log.debug("AppUserRepo initiated searching user by email");
        Optional<AppUser> appUserOptional = appUserRepository.findAppUserByUserEmail(appUser.getUserEmail());
        if(appUserOptional.isPresent()){
            log.error("{}'s email is Already present into database with email {}",appUser.getUserName(),appUser.getUserEmail());
            throw new AppUserException("Your "+appUser.getUserEmail()+" is Already registered", HttpStatus.CONFLICT);
        }
        log.debug("AppUserRepo initiated searching user by phone number");
        Optional<AppUser> appUserOptional1 = appUserRepository.findAppUserByUserPhoneNumber(appUser.getPhoneNumber());
        if(appUserOptional1.isPresent()){
            log.error("{}'s phone number is Already present into database with phone number {}",appUser.getUserName(),appUser.getPhoneNumber());
            throw new AppUserException("Your "+appUser.getPhoneNumber()+" is Already registered", HttpStatus.CONFLICT);
        }
        appUserRepository.save(appUser);
        log.info("{} information saved into database successFully",appUser.getUserEmail());
//        return appUser.getUserName()+" added SuccessFully"; // for testing
    }

    @Override
    public UserDetails loadUserByUsername(@NonNull String userEmail) throws AppUserException {
        log.debug("Attempting to load user by email: {}", userEmail);
        Optional<AppUser> appUser = appUserRepository.findByUserEmail(userEmail);
        if(appUser.isEmpty()){
            log.warn("Login failure: User with email {} not found in database", userEmail);
            throw new AppUserException("Invalid login Details",HttpStatus.NOT_FOUND);
        }
        return new User(
                appUser.get().getUserEmail(),
                appUser.get().getUserPassword(),
                List.of(new SimpleGrantedAuthority("ROLE_"+appUser.get().getRole().getRoleName()))
        );
    }
}
