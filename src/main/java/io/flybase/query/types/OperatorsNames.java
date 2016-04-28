/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query.types;

/**
 *
 * @author adantas
 */
public enum OperatorsNames {
    
    AND("$and"), EQUAL("$eq"), NOT("$not"), GT("$gt"), GTE("$gte"), LT("$lt"), 
    LTE("$lte"), REGEX("$regex"), IN("$in");
    
    private final String name;

    private OperatorsNames(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
