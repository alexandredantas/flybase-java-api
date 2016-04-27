/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase;

import io.flybase.exceptions.CollectionsRetrievingException;
import java.util.List;

/**
 * Flybase client api.
 * @author adantas
 */
public interface Flybase {
    
    String TIMESTAMP = "Flybase.ServerValue.TIMESTAMP";
    String UTC = "Flybase.ServerValue.UTC";
    String UNIQUE = "Flybase.ServerValue.UNIQUE";
    String UNIQUE_UUID = "Flybase.ServerValue.UUID";
    
    /**
     * Retrieve all collections on a given application
     * @return 
     * @throws CollectionsRetrievingException If retrieving collections fail
     */
    List<Collection> collections();
}
