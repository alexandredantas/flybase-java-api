/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query.types;

import io.flybase.query.types.SortType;

/**
 *
 * @author adantas
 */
public class Sort {
    
    private final String field;
    private final SortType type;

    public Sort(String field, SortType type) {
        this.field = field;
        this.type = type;
    }

    public String getField() {
        return field;
    }

    public SortType getType() {
        return type;
    }
}
