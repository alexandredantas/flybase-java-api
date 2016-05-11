/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query;

import java.util.Collections;
import java.util.Map;

/**
 *
 * @author user
 */
public enum Operators {

    AND("$and"), EQUAL("$eq"), NOT("$not"), GT("$gt"), GTE("$gte"), LT("$lt"),
    LTE("$lte"), REGEX("$regex"), IN("$in");

    private final String name;

    private Operators(String name) {
        this.name = name;
    }

    public Operator value(Object value) {
        return new Operator(this.name, value);
    }

    public String getName() {
        return name;
    }
    
    public static Operator from(String name, Object value){
        return new Operator(name, value);
    }

    public static class Operator {

        private final String name;
        private final Object value;

        private Operator(String name, Object value) {
            this.name = name;
            this.value = value;
        }

        public String getFieldName() {
            return name;
        }

        public Object getValue() {
            return value;
        }

        public Map<String, Object> build() {
            return Collections.singletonMap(this.name, this.value);
        }
    }
}
