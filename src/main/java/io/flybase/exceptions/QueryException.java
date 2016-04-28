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
public class QueryException extends RuntimeException {

    public QueryException() {
    }

    public QueryException(String string) {
        super(string);
    }

    public QueryException(String string, Throwable thrwbl) {
        super(string, thrwbl);
    }

    public QueryException(Throwable thrwbl) {
        super(thrwbl);
    }
}
