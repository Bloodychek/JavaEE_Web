package org.example.javaeeweb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Integer booksID;
    private String author;
    private String bookName;
    private Integer yearOfPublishing;
    private Integer depositPrice;
}
