package org.example.javaeeweb.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Subscription {
    private Integer subscriptionID;
    private Date issueDate;
    private Date returnDate;
    private Integer bookID;
    private Integer readerID;
}
