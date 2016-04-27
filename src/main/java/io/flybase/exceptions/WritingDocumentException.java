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
public class WritingDocumentException extends RuntimeException{

    public WritingDocumentException() {
    }

    public WritingDocumentException(String string) {
        super(string);
    }

    public WritingDocumentException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public WritingDocumentException(Throwable thrwbl) {
        super(thrwbl);
    }
    
    
}
