package org.example.javaeeweb.utils.mapper.impl;

import org.example.javaeeweb.dto.BookDto;
import org.example.javaeeweb.entity.Book;
import org.example.javaeeweb.utils.mapper.Mapper;

public class BookMappingImpl implements Mapper<Book, BookDto> {

    @Override
    public Book mapToEntity(BookDto bookDto) {
        Book entity = new Book();
        entity.setBooksID(bookDto.getBooksID());
        entity.setAuthor(bookDto.getAuthor());
        entity.setBookName(bookDto.getBookName());
        entity.setYearOfPublishing(bookDto.getYearOfPublishing());
        entity.setDepositPrice(bookDto.getDepositPrice());
        return entity;
    }

    @Override
    public BookDto mapToDto(Book entity) {
        BookDto bookDto = new BookDto();
        bookDto.setBooksID(entity.getBooksID());
        bookDto.setAuthor(entity.getAuthor());
        bookDto.setBookName(entity.getBookName());
        bookDto.setYearOfPublishing(entity.getYearOfPublishing());
        bookDto.setDepositPrice(entity.getDepositPrice());
        return bookDto;
    }
}
