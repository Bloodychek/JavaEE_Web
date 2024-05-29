package org.example.javaeeweb.servlets;

import com.google.gson.Gson;
import org.example.javaeeweb.dao.BookDao;
import org.example.javaeeweb.dao.ReaderBookDao;
import org.example.javaeeweb.dao.ReaderDao;
import org.example.javaeeweb.dao.SubscriptionDao;
import org.example.javaeeweb.dto.BookDto;
import org.example.javaeeweb.dto.ReaderDto;
import org.example.javaeeweb.dto.SubscriptionDto;
import org.example.javaeeweb.services.BookService;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/book")
public class BookServlet extends HttpServlet {
    private final BookService bookService;

    public BookServlet(BookService bookService) {
        this.bookService = bookService;
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        List<BookDto> bookDtoList = bookService.getAll();

        String json = new Gson().toJson(bookDtoList);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String author = httpServletRequest.getParameter("author");
        String bookName = httpServletRequest.getParameter("book_name");
        int yearOfPublishing = Integer.parseInt(httpServletRequest.getParameter("year_of_publishing"));
        int depositPrise = Integer.parseInt(httpServletRequest.getParameter("deposit_price"));

        List<ReaderDto> readerDtos = new ArrayList<>();
        ReaderDto readerDto = new ReaderDto();
        readerDto.setReadersID(Integer.valueOf(httpServletRequest.getParameter("reader_id")));
        readerDtos.add(readerDto);

        List<SubscriptionDto> subscriptionDtos = new ArrayList<>();
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setSubscriptionID(Integer.valueOf(httpServletRequest.getParameter("subscription_id")));
        subscriptionDtos.add(subscriptionDto);

        BookDto bookDto = new BookDto(null, author, bookName, yearOfPublishing, depositPrise, readerDtos, subscriptionDtos);
        bookService.add(bookDto);

        String json = new Gson().toJson(bookDto);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(json);
    }

    @Override
    protected void doPut(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        int id = Integer.parseInt(httpServletRequest.getParameter("book_id"));
        String author = httpServletRequest.getParameter("author");
        String bookName = httpServletRequest.getParameter("book_name");
        int yearOfPublishing = Integer.parseInt(httpServletRequest.getParameter("year_of_publishing"));
        int depositPrise = Integer.parseInt(httpServletRequest.getParameter("deposit_price"));

        List<ReaderDto> readerDtos = new ArrayList<>();
        ReaderDto readerDto = new ReaderDto();
        readerDto.setReadersID(Integer.valueOf(httpServletRequest.getParameter("reader_id")));
        readerDtos.add(readerDto);

        List<SubscriptionDto> subscriptionDtos = new ArrayList<>();
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setSubscriptionID(Integer.valueOf(httpServletRequest.getParameter("subscription_id")));
        subscriptionDtos.add(subscriptionDto);
        BookDto bookDto = new BookDto(id, author, bookName, yearOfPublishing, depositPrise, readerDtos, subscriptionDtos);
        bookService.update(bookDto);

        String json = new Gson().toJson(bookDto);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(json);
    }

    @Override
    protected void doDelete(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        int id = Integer.parseInt(httpServletRequest.getParameter("book_id"));
        bookService.delete(id);
        httpServletResponse.getWriter().write("Success");
    }
}
