package org.example.javaeeweb.utils.mapper.impl;

import org.example.javaeeweb.dto.ReaderDto;
import org.example.javaeeweb.entity.Reader;
import org.example.javaeeweb.utils.mapper.Mapper;

public class ReaderMappingImpl implements Mapper<Reader, ReaderDto> {

    @Override
    public Reader mapToEntity(ReaderDto readerDTO) {
        Reader entity = new Reader();
        entity.setReadersID(readerDTO.getReadersID());
        entity.setFirstName(readerDTO.getFirstName());
        entity.setSecondName(readerDTO.getSecondName());
        entity.setAddress(readerDTO.getAddress());
        return entity;
    }

    @Override
    public ReaderDto mapToDto(Reader entity) {
        ReaderDto readerDTO = new ReaderDto();
        readerDTO.setReadersID(entity.getReadersID());
        readerDTO.setFirstName(entity.getFirstName());
        readerDTO.setSecondName(entity.getSecondName());
        readerDTO.setAddress(entity.getAddress());
        return readerDTO;
    }
}
