package org.example.javaeeweb.servlets;

import com.google.gson.Gson;
import org.example.javaeeweb.dto.BookDto;
import org.example.javaeeweb.dto.ReaderDto;
import org.example.javaeeweb.dto.SubscriptionDto;
import org.example.javaeeweb.services.impl.BookServiceImpl;
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

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BookServletTest {
    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    BookServiceImpl bookService;

    @Mock
    PrintWriter printWriter;

    @InjectMocks
    BookServlet servlet;

    @Test
    void doGet() throws IOException {
        // Arrange
        List<BookDto> bookDtoList = new ArrayList<>() {{
            add(new BookDto(1, "Tolstoy", "Peace of War", 1999, 12, null, null));
        }};
        String json = new Gson().toJson(bookDtoList);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);
        when(bookService.getAll()).thenReturn(bookDtoList);

        // Act
        new BookServlet(bookService).doGet(httpServletRequest, httpServletResponse);

        // Assert
        verify(httpServletResponse).setContentType("application/json");
        verify(printWriter, atLeast(1)).write(json);
    }

    @Test
    void doPost() throws IOException {
        // Arrange
        BookDto bookDto = new BookDto(null, "Tolstoy", "Peace of War", 1900, 12,
                new ArrayList<>() {{
                    add(new ReaderDto(1, null, null, null, null, null));
                }},
                new ArrayList<>() {{
                    add(new SubscriptionDto(1, null, null, null, null));
                }});

        when(httpServletRequest.getParameter("author")).thenReturn(bookDto.getAuthor());
        when(httpServletRequest.getParameter("book_name")).thenReturn(bookDto.getBookName());
        when(httpServletRequest.getParameter("year_of_publishing")).thenReturn(String.valueOf(bookDto.getYearOfPublishing()));
        when(httpServletRequest.getParameter("deposit_price")).thenReturn(String.valueOf(bookDto.getDepositPrice()));
        when(httpServletRequest.getParameter("reader_id")).thenReturn(String.valueOf(1));
        when(httpServletRequest.getParameter("subscription_id")).thenReturn(String.valueOf(1));

        String json = new Gson().toJson(bookDto);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        // Act
        servlet.doPost(httpServletRequest, httpServletResponse);

        // Assert
        verify(bookService, atLeast(1)).add(bookDto);
        verify(printWriter, atLeast(1)).write(json);
    }

    @Test
    void doPut() throws IOException {
        // Arrange
        BookDto bookDto = new BookDto(1, "Tolstoy", "Peace of War", 1900, 12,
                new ArrayList<>() {{
                    add(new ReaderDto(1, null, null, null, null, null));
                }},
                new ArrayList<>() {{
                    add(new SubscriptionDto(1, null, null, null, null));
                }});

        when(httpServletRequest.getParameter("book_id")).thenReturn(String.valueOf(bookDto.getBooksID()));
        when(httpServletRequest.getParameter("author")).thenReturn(bookDto.getAuthor());
        when(httpServletRequest.getParameter("book_name")).thenReturn(bookDto.getBookName());
        when(httpServletRequest.getParameter("year_of_publishing")).thenReturn(String.valueOf(bookDto.getYearOfPublishing()));
        when(httpServletRequest.getParameter("deposit_price")).thenReturn(String.valueOf(bookDto.getDepositPrice()));
        when(httpServletRequest.getParameter("reader_id")).thenReturn(String.valueOf(1));
        when(httpServletRequest.getParameter("subscription_id")).thenReturn(String.valueOf(1));

        String json = new Gson().toJson(bookDto);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        // Act
        servlet.doPut(httpServletRequest, httpServletResponse);

        // Assert
        verify(bookService, atLeast(1)).update(bookDto);
        verify(printWriter, atLeast(1)).write(json);
    }

    @Test
    void doDelete() throws IOException {
        // Arrange
        when(httpServletRequest.getParameter("book_id")).thenReturn(String.valueOf(1));
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        // Act
        servlet.doDelete(httpServletRequest, httpServletResponse);

        // Assert
        verify(bookService, atLeast(1)).delete(1);
    }
}