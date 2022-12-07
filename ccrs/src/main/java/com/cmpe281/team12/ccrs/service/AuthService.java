package com.cmpe281.team12.ccrs.service;

import com.cmpe281.team12.ccrs.model.User;
import com.cmpe281.team12.ccrs.model.payload.SignupRequest;

public interface AuthService {
    User registerUser(SignupRequest signUpRequest) throws Exception;
}