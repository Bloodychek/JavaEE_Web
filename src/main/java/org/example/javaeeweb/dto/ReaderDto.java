package org.example.javaeeweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReaderDto {
    private Integer readersID;
    private String firstName;
    private String secondName;
    private String address;
    private List<BookDto> bookList;
    private List<SubscriptionDto> subscriptionList;
}
