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
public class DeletingDocumentException extends RuntimeException{

    public DeletingDocumentException() {
    }

    public DeletingDocumentException(String string) {
        super(string);
    }

    public DeletingDocumentException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public DeletingDocumentException(Throwable thrwbl) {
        super(thrwbl);
    }

    
}
