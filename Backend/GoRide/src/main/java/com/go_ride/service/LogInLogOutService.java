package com.go_ride.service;

import com.go_ride.model.LogInDTO;
import com.go_ride.model.UserSession;

public interface LogInLogOutService {
    UserSession logInUser(LogInDTO logInDTO);

    String logOutUser(String uuid);
}
