package org.example.javaeeweb.services;

import org.example.javaeeweb.dto.BookDto;

import java.util.List;

public interface BookService {
    List<BookDto> getAll();

    void add(BookDto bookDto);

    void update(BookDto bookDto);

    void delete(int id);
}
