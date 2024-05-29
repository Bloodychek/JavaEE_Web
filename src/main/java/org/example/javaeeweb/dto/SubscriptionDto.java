package org.example.javaeeweb.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class SubscriptionDto {
    private Integer subscriptionID;
    private Date issueDate;
    private Date returnDate;
    private BookDto bookDto;
    private ReaderDto readerDto;

    public SubscriptionDto(Date issueDate, Date returnDate, BookDto bookDto, ReaderDto readerDto) {
        this.issueDate = issueDate;
        this.returnDate = returnDate;
        this.bookDto = bookDto;
        this.readerDto = readerDto;
    }
}
