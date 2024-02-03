package ru.itgirl.libraryproject.model.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject.model.entity.User;
import ru.itgirl.libraryproject.repository.UserRepository;

import java.util.NoSuchElementException;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public User findByUsername(String username) {
        log.info("Try to find user by username {}", username);
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isPresent()) {
            return user.get();
        } else {
            log.error("User with username {} not found", username);
            throw new NoSuchElementException("No value present");
        }
    }
}

