package org.example.javaeeweb.servlets;

import com.google.gson.Gson;
import org.example.javaeeweb.dto.BookDto;
import org.example.javaeeweb.dto.ReaderDto;
import org.example.javaeeweb.dto.SubscriptionDto;
import org.example.javaeeweb.services.impl.BookServiceImpl;
import org.example.javaeeweb.services.impl.SubscriptionServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.atLeast;

@ExtendWith(MockitoExtension.class)
class SubscriptionServletTest {
    @Mock
    HttpServletRequest httpServletRequest;

    @Mock
    HttpServletResponse httpServletResponse;

    @Mock
    SubscriptionServiceImpl subscriptionService;

    @Mock
    PrintWriter printWriter;

    @InjectMocks
    SubscriptionServlet servlet;

    @Test
    void doGet() throws IOException {
        // Arrange
        List<SubscriptionDto> subscriptionDtoList = new ArrayList<>() {{
            add(new SubscriptionDto(1, Date.valueOf("1999-01-01"), Date.valueOf("2000-02-02"), null, null));
        }};
        String json = new Gson().toJson(subscriptionDtoList);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);
        when(subscriptionService.getAll()).thenReturn(subscriptionDtoList);

        // Act
        new SubscriptionServlet(subscriptionService).doGet(httpServletRequest, httpServletResponse);

        // Assert
        verify(httpServletResponse).setContentType("application/json");
        verify(printWriter, atLeast(1)).write(json);
    }

    @Test
    void doPost() throws IOException {
        // Arrange
        SubscriptionDto subscriptionDto = new SubscriptionDto(null, Date.valueOf("1999-01-01"), Date.valueOf("2000-02-02"),
                new BookDto(1, null, null, null, null, null, null),
                new ReaderDto(1, null, null, null, null, null));

        when(httpServletRequest.getParameter("issue_date")).thenReturn(String.valueOf(subscriptionDto.getIssueDate()));
        when(httpServletRequest.getParameter("return_date")).thenReturn(String.valueOf(subscriptionDto.getReturnDate()));
        when(httpServletRequest.getParameter("book_id")).thenReturn(String.valueOf(1));
        when(httpServletRequest.getParameter("reader_id")).thenReturn(String.valueOf(1));

        String json = new Gson().toJson(subscriptionDto);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        // Act
        servlet.doPost(httpServletRequest, httpServletResponse);

        // Assert
        verify(subscriptionService, atLeast(1)).add(subscriptionDto);
        verify(printWriter, atLeast(1)).write(json);
    }

    @Test
    void doPut() throws IOException {
        // Arrange
        SubscriptionDto subscriptionDto = new SubscriptionDto(1, Date.valueOf("1999-01-01"), Date.valueOf("2000-02-02"),
                new BookDto(1, null, null, null, null, null, null),
                new ReaderDto(1, null, null, null, null, null));

        when(httpServletRequest.getParameter("subscription_id")).thenReturn(String.valueOf(subscriptionDto.getSubscriptionID()));
        when(httpServletRequest.getParameter("issue_date")).thenReturn(String.valueOf(subscriptionDto.getIssueDate()));
        when(httpServletRequest.getParameter("return_date")).thenReturn(String.valueOf(subscriptionDto.getReturnDate()));
        when(httpServletRequest.getParameter("book_id")).thenReturn(String.valueOf(1));
        when(httpServletRequest.getParameter("reader_id")).thenReturn(String.valueOf(1));

        String json = new Gson().toJson(subscriptionDto);
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        // Act
        servlet.doPut(httpServletRequest, httpServletResponse);

        // Assert
        verify(subscriptionService, atLeast(1)).update(subscriptionDto);
        verify(printWriter, atLeast(1)).write(json);
    }

    @Test
    void doDelete() throws IOException {
        // Arrange
        when(httpServletRequest.getParameter("subscription_id")).thenReturn(String.valueOf(1));
        when(httpServletResponse.getWriter()).thenReturn(printWriter);

        // Act
        servlet.doDelete(httpServletRequest, httpServletResponse);

        // Assert
        verify(subscriptionService, atLeast(1)).delete(1);
    }
}