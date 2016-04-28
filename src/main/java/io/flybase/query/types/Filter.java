/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query.types;

import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author adantas
 */
public class Filter {
    private final String name;
    private final Collection<Map<String, Object>> operators;

    public Filter(String name, Operator... operators) {
        this.name = name;
        this.operators = Arrays.stream(operators)
                .map(operator -> operator.build())
                .collect(Collectors.toList());
    }

    public String getName() {
        return name;
    }

    public Collection<Map<String, Object>> getOperators() {
        return operators;
    }
}
