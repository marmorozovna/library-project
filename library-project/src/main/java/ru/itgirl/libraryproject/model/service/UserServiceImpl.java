package ru.itgirl.libraryproject.model.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject.model.entity.User;
import ru.itgirl.libraryproject.repository.UserRepository;

import java.util.HashSet;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }
}
