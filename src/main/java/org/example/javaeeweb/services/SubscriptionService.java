package org.example.javaeeweb.services;

import org.example.javaeeweb.dto.SubscriptionDto;

import java.util.List;

public interface SubscriptionService {
    List<SubscriptionDto> getAll();

    void add(SubscriptionDto subscriptionDto);

    void update(SubscriptionDto subscriptionDto);

    void delete(int id);
}
