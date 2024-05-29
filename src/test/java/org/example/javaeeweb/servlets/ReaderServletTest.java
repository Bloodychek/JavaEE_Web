package org.example.javaeeweb.servlets;

import com.google.gson.Gson;
import org.example.javaeeweb.dto.BookDto;
import org.example.javaeeweb.dto.ReaderDto;
import org.example.javaeeweb.dto.SubscriptionDto;
import org.example.javaeeweb.services.impl.BookServiceImpl;
import org.example.javaeeweb.services.impl.ReaderServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeast;

@ExtendWith(MockitoExtension.class)
class ReaderServletTest {
    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    ReaderServiceImpl readerService;

    @Mock
    PrintWriter printWriter;

    @InjectMocks
    ReaderServlet servlet;

    @Test
    void doGet() throws IOException {
        // Arrange
        List<ReaderDto> readerDtoList = new ArrayList<>() {{
            add(new ReaderDto(1, "Evgeniy", "Egorov", "Karl street", null, null));
        }};
        String json = new Gson().toJson(readerDtoList);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);
        when(readerService.getAll()).thenReturn(readerDtoList);

        // Act
        new ReaderServlet(readerService).doGet(httpServletRequest, httpServletResponse);

        // Assert
        verify(httpServletResponse).setContentType("application/json");
        verify(printWriter, atLeast(1)).write(json);
    }

    @Test
    void doPost() throws IOException {
        // Arrange
        ReaderDto readerDto = new ReaderDto(null, "Evgeniy", "Egorov", "Karl street",
                new ArrayList<>() {{
                    add(new BookDto(1, null, null, null, null, null, null));
                }},
                new ArrayList<>() {{
                    add(new SubscriptionDto(1, null, null, null, null));
                }});

        when(httpServletRequest.getParameter("first_name")).thenReturn(readerDto.getFirstName());
        when(httpServletRequest.getParameter("second_name")).thenReturn(readerDto.getSecondName());
        when(httpServletRequest.getParameter("address")).thenReturn(readerDto.getAddress());
        when(httpServletRequest.getParameter("book_id")).thenReturn(String.valueOf(1));
        when(httpServletRequest.getParameter("subscription_id")).thenReturn(String.valueOf(1));

        String json = new Gson().toJson(readerDto);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        // Act
        servlet.doPost(httpServletRequest, httpServletResponse);

        // Assert
        verify(readerService, atLeast(1)).add(readerDto);
        verify(printWriter, atLeast(1)).write(json);
    }

    @Test
    void doPut() throws IOException {
        // Arrange
        ReaderDto readerDto = new ReaderDto(1, "Evgeniy", "Egorov", "Karl street",
                new ArrayList<>() {{
                    add(new BookDto(1, null, null, null, null, null, null));
                }},
                new ArrayList<>() {{
                    add(new SubscriptionDto(1, null, null, null, null));
                }});

        when(httpServletRequest.getParameter("reader_id")).thenReturn(String.valueOf(readerDto.getReadersID()));
        when(httpServletRequest.getParameter("first_name")).thenReturn(readerDto.getFirstName());
        when(httpServletRequest.getParameter("second_name")).thenReturn(readerDto.getSecondName());
        when(httpServletRequest.getParameter("address")).thenReturn(readerDto.getAddress());
        when(httpServletRequest.getParameter("book_id")).thenReturn(String.valueOf(1));
        when(httpServletRequest.getParameter("subscription_id")).thenReturn(String.valueOf(1));

        String json = new Gson().toJson(readerDto);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        // Act
        servlet.doPut(httpServletRequest, httpServletResponse);

        // Assert
        verify(readerService, atLeast(1)).update(readerDto);
        verify(printWriter, atLeast(1)).write(json);
    }

    @Test
    void doDelete() throws IOException {
        // Arrange
        when(httpServletRequest.getParameter("reader_id")).thenReturn(String.valueOf(1));
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        // Act
        servlet.doDelete(httpServletRequest, httpServletResponse);

        // Assert
        verify(readerService, atLeast(1)).delete(1);
    }
}