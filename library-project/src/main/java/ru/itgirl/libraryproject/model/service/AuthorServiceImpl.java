package ru.itgirl.libraryproject.model.service;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import ru.itgirl.libraryproject.model.dto.AuthorCreateDTO;
import ru.itgirl.libraryproject.model.dto.AuthorDTO;
import ru.itgirl.libraryproject.model.dto.AuthorUpdateDTO;
import ru.itgirl.libraryproject.model.dto.BookDTO;
import ru.itgirl.libraryproject.model.entity.Author;
import ru.itgirl.libraryproject.repository.AuthorRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AuthorServiceImpl implements AuthorService {

    private final AuthorRepository authorRepository;

    @Override
    public AuthorDTO getAuthorById(Long id) {
        Author author = authorRepository.findById(id).orElseThrow();
        return convertEntityToDto(author);
    }

    @Override
    public AuthorDTO getByNameV1(String name) {
        Author author = authorRepository.findAuthorByName(name).orElseThrow();
        return convertEntityToDto(author);
    }

    @Override
    public AuthorDTO getByNameV2(String name) {
        Author author = authorRepository.findAuthorByNameBySql(name).orElseThrow();
        return convertEntityToDto(author);
    }

    @Override
    public AuthorDTO getByNameV3(String name) {
        Specification<Author> specification = Specification.where(new Specification<Author>() {
            @Override
            public Predicate toPredicate(Root<Author> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                return cb.equal(root.get("name"), name);
            }
        });
        Author author = authorRepository.findOne(specification).orElseThrow();
        return convertEntityToDto(author);
    }

    @Override
    public AuthorDTO createAuthor(AuthorCreateDTO authorCreateDTO) {
        Author author = authorRepository.save(convertDtoToEntity(authorCreateDTO));
        AuthorDTO authorDTO = convertEntityToDto(author);
        return authorDTO;
    }

    @Override
    public AuthorDTO updateAuthor(AuthorUpdateDTO authorUpdateDTO) {
        Author author = authorRepository.findById(authorUpdateDTO.getId()).orElseThrow();
        author.setName(authorUpdateDTO.getName());
        author.setSurname(authorUpdateDTO.getSurname());
        Author savedAuthor = authorRepository.save(author);
        AuthorDTO authorDTO = convertEntityToDto(savedAuthor);
        return authorDTO;
    }

    @Override
    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }

    @Override
    public List <AuthorDTO> getAllAuthors (){
        List <Author> authors= authorRepository.findAll();
        return authors.stream().map(this::convertEntityToDto).collect(Collectors.toList());
    }

    private Author convertDtoToEntity(AuthorCreateDTO authorCreateDTO) {
        return Author.builder()
                .name(authorCreateDTO.getName())
                .surname(authorCreateDTO.getSurname())
                .build();
    }

    private AuthorDTO convertEntityToDto(Author author) {
        List<BookDTO> bookDTOList = null;
        if (author.getBooks() != null) {
            bookDTOList = author.getBooks()
                    .stream()
                    .map(book -> BookDTO.builder().
                            genre(book.getGenre().getName()).
                            name(book.getName()).
                            id(book.getId())
                            .build()
                    ).toList();
        }
        return AuthorDTO.builder()
                .books(bookDTOList)
                .id(author.getId())
                .name(author.getName())
                .surname(author.getSurname())
                .build();
    }
}
