package org.example.javaeeweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookDto {
    private Integer booksID;
    private String author;
    private String bookName;
    private Integer yearOfPublishing;
    private Integer depositPrice;
    private List<ReaderDto> readerList;
    private List<SubscriptionDto> subscriptionList;
}
