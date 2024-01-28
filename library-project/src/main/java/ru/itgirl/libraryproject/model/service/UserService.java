package ru.itgirl.libraryproject.model.service;

import ru.itgirl.libraryproject.model.entity.User;

public interface UserService {
    User findByUsername (String username);
}
