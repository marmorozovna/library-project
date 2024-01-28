package ru.itgirl.libraryproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.itgirl.libraryproject.model.entity.Role;

public interface RoleRepository extends JpaRepository <Role,Long> {
}
