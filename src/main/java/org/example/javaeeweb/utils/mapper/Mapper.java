package org.example.javaeeweb.utils.mapper;

import java.util.List;
import java.util.stream.Collectors;

public interface Mapper<T, TDto> {
    T mapToEntity(TDto list);

    TDto mapToDto(T list);

    default List<TDto> entityListToDtoList(List<T> list) {
        return list.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    default List<T> dtoListToEntityList(List<TDto> list) {
        return list.stream().map(this::mapToEntity).collect(Collectors.toList());
    }
}
