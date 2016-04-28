/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query.types;

import java.util.Collections;
import java.util.Map;

/**
 *
 * @author adantas
 */
public class Operator {

    private final String name;
    private final Object value;

    public Operator(String name, Object value) {
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
