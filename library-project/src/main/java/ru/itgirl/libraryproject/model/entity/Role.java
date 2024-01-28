package ru.itgirl.libraryproject.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Getter
@Table (name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Setter
    @ManyToMany(mappedBy = "roles")
    private Set <User> users;
}
