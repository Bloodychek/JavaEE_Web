package org.example.javaeeweb.utils.mapper.impl;

import org.example.javaeeweb.dto.SubscriptionDto;
import org.example.javaeeweb.entity.Subscription;
import org.example.javaeeweb.utils.mapper.Mapper;

public class SubscriptionMappingImpl implements Mapper<Subscription, SubscriptionDto> {

    @Override
    public Subscription mapToEntity(SubscriptionDto subscriptionDTO) {
        Subscription entity = new Subscription();
        entity.setSubscriptionID(subscriptionDTO.getSubscriptionID());
        entity.setIssueDate(subscriptionDTO.getIssueDate());
        entity.setReturnDate(subscriptionDTO.getReturnDate());
        entity.setReaderID(subscriptionDTO.getReaderDto().getReadersID());
        entity.setBookID(subscriptionDTO.getBookDto().getBooksID());
        return entity;
    }

    @Override
    public SubscriptionDto mapToDto(Subscription entity) {
        SubscriptionDto subscriptionDTO = new SubscriptionDto();
        subscriptionDTO.setSubscriptionID(entity.getSubscriptionID());
        subscriptionDTO.setIssueDate(entity.getIssueDate());
        subscriptionDTO.setReturnDate(entity.getReturnDate());
        return subscriptionDTO;
    }
}
