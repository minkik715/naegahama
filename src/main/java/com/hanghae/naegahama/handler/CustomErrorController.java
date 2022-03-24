package com.hanghae.naegahama.handler;

import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.autoconfigure.web.servlet.error.BasicErrorController;
import org.springframework.boot.autoconfigure.web.servlet.error.ErrorViewResolver;
import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@RestController
public class CustomErrorController extends BasicErrorController {

    public CustomErrorController(ErrorAttributes errorAttributes,
                                 ServerProperties serverProperties,
                                 List<ErrorViewResolver> errorViewResolvers) {
        super(errorAttributes, serverProperties.getError(), errorViewResolvers);
    }




    @Override
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {

        HttpStatus hs = getStatus(request);
        ModelAndView mv = new ModelAndView("500.html");

        switch (hs){
            case UNAUTHORIZED:
                mv.setViewName("400.html");
                break;
            case NOT_FOUND:
                mv.setViewName("401.html");
                break;
            case INTERNAL_SERVER_ERROR:
                mv.setViewName("404.html");
                break;
            case SERVICE_UNAVAILABLE:
                mv.setViewName("500.html");
                break;
        }
        try {
            response.sendRedirect("/api/error");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return mv;
    }

    @Override
    public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
        HttpStatus status = getStatus(request);
        if (status == HttpStatus.NO_CONTENT) {
            return new ResponseEntity<>(status);
        }
        Map<String, Object> body = getErrorAttributes(request, ErrorAttributeOptions.defaults());
        return new ResponseEntity<>(body, status);
    }
}