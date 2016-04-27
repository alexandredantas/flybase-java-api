/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.exceptions;

/**
 *
 * @author adantas
 */
public class CollectionsRetrievingException extends RuntimeException{

    public CollectionsRetrievingException() {
    }

    public CollectionsRetrievingException(String string) {
        super(string);
    }

    public CollectionsRetrievingException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public CollectionsRetrievingException(Throwable thrwbl) {
        super(thrwbl);
    }
    
    
    
}
