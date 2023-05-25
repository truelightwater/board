package com.example.board.servlet;

import com.example.board.dto.BoardRequest;
import com.example.board.dto.BoardResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StreamUtils;

import javax.servlet.ServletInputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

@WebServlet(name = "BoardServlet", urlPatterns = "/ss")
public class BoardServlet extends HttpServlet {
    private BoardRequest boardRequest;
    private BoardResponse boardResponse;
    private ObjectMapper objectMapper;

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        System.out.println("messageBody = " + messageBody);

        response.getWriter().write("OK");

    }
}
