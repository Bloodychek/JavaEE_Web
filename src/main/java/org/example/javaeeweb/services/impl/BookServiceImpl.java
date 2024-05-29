package org.example.javaeeweb.services.impl;

import org.example.javaeeweb.dao.BookDao;
import org.example.javaeeweb.dao.ReaderBookDao;
import org.example.javaeeweb.dao.ReaderDao;
import org.example.javaeeweb.dao.SubscriptionDao;
import org.example.javaeeweb.dto.BookDto;
import org.example.javaeeweb.entity.Book;
import org.example.javaeeweb.entity.Reader;
import org.example.javaeeweb.entity.ReaderBook;
import org.example.javaeeweb.entity.Subscription;
import org.example.javaeeweb.services.BookService;
import org.example.javaeeweb.utils.mapper.impl.BookMappingImpl;
import org.example.javaeeweb.utils.mapper.impl.ReaderMappingImpl;
import org.example.javaeeweb.utils.mapper.impl.SubscriptionMappingImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BookServiceImpl implements BookService {
    private final BookDao bookDao;
    private final SubscriptionDao subscriptionDao;
    private final ReaderDao readerDao;
    private final ReaderBookDao readerBookDao;
    private final BookMappingImpl bookMappingImpl = new BookMappingImpl();
    private final ReaderMappingImpl readerMapping = new ReaderMappingImpl();
    private final SubscriptionMappingImpl subscriptionMappingImpl = new SubscriptionMappingImpl();

    public BookServiceImpl(BookDao bookDao, SubscriptionDao subscriptionDao, ReaderDao readerDao, ReaderBookDao readerBookDao) {
        this.bookDao = bookDao;
        this.subscriptionDao = subscriptionDao;
        this.readerDao = readerDao;
        this.readerBookDao = readerBookDao;
    }

    @Override
    public List<BookDto> getAll() {
        List<BookDto> bookDtos = new ArrayList<>();
        List<Book> books = bookDao.getAll();
        for (Book book : books) {
            List<Subscription> subscriptions = subscriptionDao.getAll().stream()
                    .filter(s -> s.getBookID().equals(book.getBooksID()))
                    .collect(Collectors.toList());

            List<ReaderBook> readerBooks = readerBookDao.getAll().stream()
                    .filter(rb -> rb.getBookID().equals(book.getBooksID()))
                    .collect(Collectors.toList());

            List<Reader> readers = readerDao.getAll().stream()
                    .filter(r -> readerBooks.stream().anyMatch(rb -> rb.getReaderID().equals(r.getReadersID())))
                    .collect(Collectors.toList());

            BookDto bookDto = bookMappingImpl.mapToDto(book);
            bookDto.setReaderList(readerMapping.entityListToDtoList(readers));
            bookDto.setSubscriptionList(subscriptionMappingImpl.entityListToDtoList(subscriptions));

            bookDtos.add(bookDto);
        }

        return bookDtos;
    }

    @Override
    public void add(BookDto bookDto) {
        bookDao.add(bookMappingImpl.mapToEntity(bookDto));
    }

    @Override
    public void update(BookDto bookDto) {
        bookDao.update(bookMappingImpl.mapToEntity(bookDto));
    }

    @Override
    public void delete(int id) {
        bookDao.delete(id);
    }
}
