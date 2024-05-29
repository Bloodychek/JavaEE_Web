package org.example.javaeeweb.services.impl;

import org.example.javaeeweb.dao.BookDao;
import org.example.javaeeweb.dao.ReaderDao;
import org.example.javaeeweb.dao.SubscriptionDao;
import org.example.javaeeweb.dto.SubscriptionDto;
import org.example.javaeeweb.entity.Book;
import org.example.javaeeweb.entity.Reader;
import org.example.javaeeweb.entity.Subscription;
import org.example.javaeeweb.services.SubscriptionService;
import org.example.javaeeweb.utils.mapper.impl.BookMappingImpl;
import org.example.javaeeweb.utils.mapper.impl.ReaderMappingImpl;
import org.example.javaeeweb.utils.mapper.impl.SubscriptionMappingImpl;

import java.util.ArrayList;
import java.util.List;

public class SubscriptionServiceImpl implements SubscriptionService {
    private final SubscriptionDao subscriptionDao;
    private final ReaderDao readerDao;
    private final BookDao bookDao;
    private final SubscriptionMappingImpl subscriptionMappingImpl = new SubscriptionMappingImpl();
    private final ReaderMappingImpl readerMappingImpl = new ReaderMappingImpl();
    private final BookMappingImpl bookMappingImpl = new BookMappingImpl();

    public SubscriptionServiceImpl(SubscriptionDao subscriptionDao, ReaderDao readerDao, BookDao bookDao) {
        this.subscriptionDao = subscriptionDao;
        this.readerDao = readerDao;
        this.bookDao = bookDao;
    }

    @Override
    public List<SubscriptionDto> getAll() {
        List<SubscriptionDto> subscriptionDtos = new ArrayList<>();
        List<Subscription> subscriptions = subscriptionDao.getAll();
        for (Subscription subscription : subscriptions) {
            Reader readers = readerDao.getAll().stream()
                    .filter(r -> r.getReadersID().equals(subscription.getReaderID()))
                    .findFirst().get();

            Book books = bookDao.getAll().stream()
                    .filter(b -> b.getBooksID().equals(subscription.getBookID()))
                    .findFirst().get();

            SubscriptionDto subscriptionDto = subscriptionMappingImpl.mapToDto(subscription);
            subscriptionDto.setBookDto(bookMappingImpl.mapToDto(books));
            subscriptionDto.setReaderDto(readerMappingImpl.mapToDto(readers));

            subscriptionDtos.add(subscriptionDto);
        }
        return subscriptionDtos;
    }

    @Override
    public void add(SubscriptionDto subscriptionDto) {
        subscriptionDao.add(subscriptionMappingImpl.mapToEntity(subscriptionDto));
    }

    @Override
    public void update(SubscriptionDto subscriptionDto) {
        subscriptionDao.update(subscriptionMappingImpl.mapToEntity(subscriptionDto));
    }

    @Override
    public void delete(int id) {
        subscriptionDao.delete(id);
    }
}
