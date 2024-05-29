package org.example.javaeeweb.servlets;

import com.google.gson.Gson;
import lombok.SneakyThrows;
import org.example.javaeeweb.dao.BookDao;
import org.example.javaeeweb.dao.ReaderDao;
import org.example.javaeeweb.dao.SubscriptionDao;
import org.example.javaeeweb.dao.impl.BookDaoImpl;
import org.example.javaeeweb.dao.impl.ReaderDaoImpl;
import org.example.javaeeweb.dao.impl.SubscriptionDaoImpl;
import org.example.javaeeweb.dto.BookDto;
import org.example.javaeeweb.dto.ReaderDto;
import org.example.javaeeweb.dto.SubscriptionDto;
import org.example.javaeeweb.services.SubscriptionService;
import org.example.javaeeweb.services.impl.SubscriptionServiceImpl;
import org.example.javaeeweb.utils.DbConnectionProvider;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.Date;
import java.util.List;

import static org.example.javaeeweb.utils.ConnSettings.*;

@WebServlet("/subscription")
public class SubscriptionServlet extends HttpServlet {
    private final SubscriptionService subscriptionService;

    public SubscriptionServlet(SubscriptionService subscriptionService) {
        this.subscriptionService = subscriptionService;
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        List<SubscriptionDto> subscriptionDtoList = subscriptionService.getAll();

        String json = new Gson().toJson(subscriptionDtoList);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(json);
    }

    @SneakyThrows
    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        Date issueDate = Date.valueOf(httpServletRequest.getParameter("issue_date"));
        Date returnDate = Date.valueOf(httpServletRequest.getParameter("return_date"));
        int bookId = Integer.parseInt(httpServletRequest.getParameter("book_id"));
        int readerId = Integer.parseInt(httpServletRequest.getParameter("reader_id"));

        BookDto bookDto = new BookDto();
        bookDto.setBooksID(bookId);

        ReaderDto readerDto = new ReaderDto();
        readerDto.setReadersID(readerId);

        SubscriptionDto subscriptionDto = new SubscriptionDto(issueDate, returnDate, bookDto, readerDto);
        subscriptionService.add(subscriptionDto);

        String json = new Gson().toJson(subscriptionDto);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(json);
    }

    @SneakyThrows
    @Override
    protected void doPut(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        int id = Integer.parseInt(httpServletRequest.getParameter("subscription_id"));
        Date issueDate = Date.valueOf(httpServletRequest.getParameter("issue_date"));
        Date returnDate = Date.valueOf(httpServletRequest.getParameter("return_date"));
        int bookId = Integer.parseInt(httpServletRequest.getParameter("book_id"));
        int readerId = Integer.parseInt(httpServletRequest.getParameter("reader_id"));

        BookDto bookDto = new BookDto();
        bookDto.setBooksID(bookId);

        ReaderDto readerDto = new ReaderDto();
        readerDto.setReadersID(readerId);

        SubscriptionDto subscriptionDto = new SubscriptionDto(id, issueDate, returnDate, bookDto, readerDto);
        subscriptionService.update(subscriptionDto);

        String json = new Gson().toJson(subscriptionDto);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(json);
    }

    @Override
    protected void doDelete(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        int id = Integer.parseInt(httpServletRequest.getParameter("subscription_id"));
        subscriptionService.delete(id);
        httpServletResponse.getWriter().write("Success");
    }
}
