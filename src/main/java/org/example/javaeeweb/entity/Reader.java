package org.example.javaeeweb.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Reader {
    private Integer readersID;
    private String firstName;
    private String secondName;
    private String address;
}
