package com.jwxt.controller;

import com.jwxt.response.Result;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class ErrorController {

    @RequestMapping(value = "/error",method = RequestMethod.GET)
    public ResponseEntity<Result> error(HttpServletRequest request){
        String message = request.getAttribute("javax.servlet.error.message").toString();
        Result result = new Result(false,101101,message);
        return new ResponseEntity<>(result, HttpStatus.BAD_GATEWAY);
    }
}
