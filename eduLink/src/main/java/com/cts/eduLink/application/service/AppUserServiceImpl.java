package com.cts.eduLink.application.service;

import com.cts.eduLink.application.classexception.AppUserException;
import com.cts.eduLink.application.entity.AppUser;
import com.cts.eduLink.application.repository.AppUserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class AppUserServiceImpl implements IAppUserService{
    private final AppUserRepository appUserRepository;

    @Override
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
}
