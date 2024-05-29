package org.example.javaeeweb.services;

import org.example.javaeeweb.dto.ReaderDto;

import java.util.List;

public interface ReaderService {
    List<ReaderDto> getAll();

    void add(ReaderDto readerDto);

    void update(ReaderDto readerDto);

    void delete(int id);
}
