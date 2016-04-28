/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query;

import com.owlike.genson.GenericType;

/**
 *
 * @author adantas
 */
public interface QueryResult {

    <T> T as(GenericType<T> convertTo);
    
    <T> T as(Class<T> convertTo);
    
    String asString();
}
