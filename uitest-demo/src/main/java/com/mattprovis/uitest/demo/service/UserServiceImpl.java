package com.mattprovis.uitest.demo.service;

import com.mattprovis.uitest.demo.entity.User;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private User currentUser = new User();

    @Override
    public User getCurrentUser() {
        return currentUser;
    }
}
