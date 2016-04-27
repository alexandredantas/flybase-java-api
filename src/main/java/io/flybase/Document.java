/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase;

import com.owlike.genson.GenericType;
import org.json.JSONObject;

/**
 * Document is a utility class that enables converting your own POJOs to/from JSON
 * representations to be written to Flybase
 * @author adantas
 */
public interface Document {
    
    /**
     * Get the raw document
     * @return A raw {@link JSONObject} 
     */
    JSONObject getRaw();
    
    /**
     * Convert this document to your own POJO class
     * @param <T>
     * @param convertTo The destination class type
     * @return An object filled with the properties of this document
     */
    <T> T to(GenericType<T> convertTo);
    
    /**
     * Convert your POJO to a JSON representation
     * @param source Your POJO instance
     * @return The produced document
     */
    Document from(Object source);
}
