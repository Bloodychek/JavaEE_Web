package org.example.javaeeweb.services.impl;

import org.example.javaeeweb.dao.BookDao;
import org.example.javaeeweb.dao.ReaderBookDao;
import org.example.javaeeweb.dao.ReaderDao;
import org.example.javaeeweb.dao.SubscriptionDao;
import org.example.javaeeweb.dto.ReaderDto;
import org.example.javaeeweb.entity.Book;
import org.example.javaeeweb.entity.Reader;
import org.example.javaeeweb.entity.ReaderBook;
import org.example.javaeeweb.entity.Subscription;
import org.example.javaeeweb.services.ReaderService;
import org.example.javaeeweb.utils.mapper.impl.BookMappingImpl;
import org.example.javaeeweb.utils.mapper.impl.ReaderMappingImpl;
import org.example.javaeeweb.utils.mapper.impl.SubscriptionMappingImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ReaderServiceImpl implements ReaderService {
    private final ReaderDao readerDao;
    private final SubscriptionDao subscriptionDao;
    private final BookDao bookDao;
    private final ReaderBookDao readerBookDao;
    private final ReaderMappingImpl readerMappingImpl = new ReaderMappingImpl();
    private final BookMappingImpl bookMappingImpl = new BookMappingImpl();
    private final SubscriptionMappingImpl subscriptionMappingImpl = new SubscriptionMappingImpl();

    public ReaderServiceImpl(ReaderDao readerDao, SubscriptionDao subscriptionDao, BookDao bookDao, ReaderBookDao readerBookDao) {
        this.readerDao = readerDao;
        this.subscriptionDao = subscriptionDao;
        this.bookDao = bookDao;
        this.readerBookDao = readerBookDao;
    }

    @Override
    public List<ReaderDto> getAll() {
        List<ReaderDto> readerDtos = new ArrayList<>();
        List<Reader> readers = readerDao.getAll();
        for (Reader reader : readers) {
            List<Subscription> subscriptions = subscriptionDao.getAll().stream()
                    .filter(s -> s.getReaderID().equals(reader.getReadersID()))
                    .collect(Collectors.toList());

            List<ReaderBook> readerBooks = readerBookDao.getAll().stream()
                    .filter(rb -> rb.getReaderID().equals(reader.getReadersID()))
                    .collect(Collectors.toList());

            List<Book> books = bookDao.getAll().stream()
                    .filter(b -> readerBooks.stream().anyMatch(rb -> rb.getBookID().equals(b.getBooksID())))
                    .collect(Collectors.toList());

            ReaderDto readerDto = readerMappingImpl.mapToDto(reader);
            readerDto.setBookList(bookMappingImpl.entityListToDtoList(books));
            readerDto.setSubscriptionList(subscriptionMappingImpl.entityListToDtoList(subscriptions));

            readerDtos.add(readerDto);
        }
        return readerDtos;
    }

    @Override
    public void add(ReaderDto readerDto) {
        readerDao.add(readerMappingImpl.mapToEntity(readerDto));
    }

    @Override
    public void update(ReaderDto readerDto) {
        readerDao.update(readerMappingImpl.mapToEntity(readerDto));
    }

    @Override
    public void delete(int id) {
        readerDao.delete(id);
    }
}
