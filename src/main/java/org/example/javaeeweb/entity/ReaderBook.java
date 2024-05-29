package org.example.javaeeweb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReaderBook {
    private Integer readersBooksID;
    private Integer bookID;
    private Integer readerID;
}
