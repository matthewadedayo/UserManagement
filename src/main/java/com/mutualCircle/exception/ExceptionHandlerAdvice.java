/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mutualCircle.exception;

import java.sql.SQLException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Dayo
 */
public class ExceptionHandlerAdvice {
    
    @ControllerAdvice
   public class UserServiceErrorAdvice {
    @ResponseStatus(HttpStatus.NOT_FOUND)
       @ExceptionHandler({UserNotFoundException.class})
        public void handle(UserNotFoundException e) {}
    
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
       @ExceptionHandler({UserServiceException.class, SQLException.class, NullPointerException.class})
         public void handle() {}
    
    //@ResponseStatus(HttpStatus.BAD_REQUEST)
      //  @ExceptionHandler({DogsServiceValidationException.class})
       //  public void handle(DogsServiceValidationException e) {}
}
}
