/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mutualCircle.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author Dayo
 */
@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class UserServiceException extends RuntimeException {	

   public UserServiceException() {
         super();
    }
  
    public UserServiceException(String message) {
           super(message);	
   }

}
