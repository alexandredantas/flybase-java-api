/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query.impl;

import io.flybase.query.Operator;
import org.json.JSONObject;

/**
 *
 * @author adantas
 */
public class Operators {
    
    private class OperatorImpl implements Operator{

        @Override
        public JSONObject build() {
            throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    }
    
    
    public Operator eq(String path, String value){
        return null;
    }
    
    public Operator not(String path, String value){
        return null;
    }
    
    public Operator gt(String path, String value){
        return null;
    }
    
    public Operator gte(String path, String value){
        return null;
    }
    
    public Operator lt(String path, String value){
        return null;
    }
    
    public Operator lte(String path, String value){
        return null;
    }
    
    public Operator regex(String path, String value){
        return null;
    }
    
    public Operator in(String path, String ... values){
        return null;
    }
    
    public Operator and(Operator ... and){
        return null;
    }
    
    public Operator or(Operator ... and){
        return null;
    }
}