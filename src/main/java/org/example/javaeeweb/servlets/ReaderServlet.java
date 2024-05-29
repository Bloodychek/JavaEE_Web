package org.example.javaeeweb.servlets;

import com.google.gson.Gson;
import org.example.javaeeweb.dao.BookDao;
import org.example.javaeeweb.dao.ReaderBookDao;
import org.example.javaeeweb.dao.ReaderDao;
import org.example.javaeeweb.dao.SubscriptionDao;
import org.example.javaeeweb.dao.impl.BookDaoImpl;
import org.example.javaeeweb.dao.impl.ReaderBookDaoImpl;
import org.example.javaeeweb.dao.impl.ReaderDaoImpl;
import org.example.javaeeweb.dao.impl.SubscriptionDaoImpl;
import org.example.javaeeweb.dto.BookDto;
import org.example.javaeeweb.dto.ReaderDto;
import org.example.javaeeweb.dto.SubscriptionDto;
import org.example.javaeeweb.services.ReaderService;
import org.example.javaeeweb.services.impl.ReaderServiceImpl;
import org.example.javaeeweb.utils.DbConnectionProvider;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.example.javaeeweb.utils.ConnSettings.*;

@WebServlet("/reader")
public class ReaderServlet extends HttpServlet {
    private final ReaderService readerService;

    public ReaderServlet(ReaderService readerService) {
        this.readerService = readerService;
    }

    @Override
    protected void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        List<ReaderDto> readerDtoList = readerService.getAll();

        String json = new Gson().toJson(readerDtoList);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(json);
    }

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        String firstName = httpServletRequest.getParameter("first_name");
        String secondName = httpServletRequest.getParameter("second_name");
        String address = httpServletRequest.getParameter("address");

        List<BookDto> bookDtos = new ArrayList<>();
        BookDto bookDto = new BookDto();
        bookDto.setBooksID(Integer.valueOf(httpServletRequest.getParameter("book_id")));
        bookDtos.add(bookDto);

        List<SubscriptionDto> subscriptionDtos = new ArrayList<>();
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setSubscriptionID(Integer.valueOf(httpServletRequest.getParameter("subscription_id")));
        subscriptionDtos.add(subscriptionDto);

        ReaderDto readerDto = new ReaderDto(null, firstName, secondName, address, bookDtos, subscriptionDtos);
        readerService.add(readerDto);

        String json = new Gson().toJson(readerDto);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(json);
    }

    @Override
    protected void doPut(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        int id = Integer.parseInt(httpServletRequest.getParameter("reader_id"));
        String firstName = httpServletRequest.getParameter("first_name");
        String secondName = httpServletRequest.getParameter("second_name");
        String address = httpServletRequest.getParameter("address");

        List<BookDto> bookDtos = new ArrayList<>();
        BookDto bookDto = new BookDto();
        bookDto.setBooksID(Integer.valueOf(httpServletRequest.getParameter("book_id")));
        bookDtos.add(bookDto);

        List<SubscriptionDto> subscriptionDtos = new ArrayList<>();
        SubscriptionDto subscriptionDto = new SubscriptionDto();
        subscriptionDto.setSubscriptionID(Integer.valueOf(httpServletRequest.getParameter("subscription_id")));
        subscriptionDtos.add(subscriptionDto);

        ReaderDto readerDto = new ReaderDto(id, firstName, secondName, address, bookDtos, subscriptionDtos);
        readerService.update(readerDto);

        String json = new Gson().toJson(readerDto);
        httpServletResponse.setContentType("application/json");
        httpServletResponse.setCharacterEncoding("UTF-8");
        httpServletResponse.getWriter().write(json);
    }

    @Override
    protected void doDelete(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        int id = Integer.parseInt(httpServletRequest.getParameter("reader_id"));
        readerService.delete(id);
        httpServletResponse.getWriter().write("Success");
    }
}
