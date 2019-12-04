/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mutualCircle.repository;

import com.mutualCircle.model.ConfirmationToken;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author buffermedia
 */
public interface ConfirmationTokenRepository extends CrudRepository <ConfirmationToken, String> {
    
    ConfirmationToken findByConfirmationToken(String confirmationToken);
}

