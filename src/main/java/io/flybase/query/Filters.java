/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query;

import io.flybase.query.Operators.Operator;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author adantas
 */
public class Filters {

    public static Filter eq(String path, Object value) {
        return new Filter(path, Arrays.asList(Operators.EQUAL.value(value)));
    }

    public static Filter not(String path, Object value) {
        return new Filter(path, Arrays.asList(Operators.NOT.value(value)));
    }

    public static Filter gt(String path, Object value) {
        return new Filter(path, Arrays.asList(Operators.GT.value(value)));
    }

    public static Filter gte(String path, Object value) {
        return new Filter(path, Arrays.asList(Operators.GTE.value(value)));
    }

    public static Filter lt(String path, Object value) {
        return new Filter(path, Arrays.asList(Operators.LT.value(value)));
    }

    public static Filter lte(String path, Object value) {
        return new Filter(path, Arrays.asList(Operators.LTE.value(value)));
    }

    public static Filter regex(String path, Object value) {
        return new Filter(path, Arrays.asList(Operators.REGEX.value(value)));
    }

    public static Filter in(String path, Object... values) {
        return new Filter(path, Arrays.asList(Operators.IN.value(values)));
    }

    public static Filter and(Map<String, List<Operator>> conditions) {
        return new Filter(Operators.AND.getName(), conditions.entrySet().parallelStream()
                .map(entry -> Operators.from(entry.getKey(), entry.getValue()
                        .parallelStream()
                        .collect(Collectors.toMap(Operator::getFieldName, Operator::getValue))))
                .collect(Collectors.toList()));
    }

    public static class Filter {

        private final String name;
        private final Collection<Map<String, Object>> operators;

        private Filter(String name, List<Operators.Operator> operators) {
            super();
            this.name = name;
            this.operators = operators.parallelStream().map((Operator operator) -> operator.build()).collect(Collectors.toList());
        }

        public String getName() {
            return name;
        }

        public Collection<Map<String, Object>> getOperators() {
            return operators;
        }
    }
}
