/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query;

import io.flybase.query.types.Filter;
import io.flybase.query.types.Operator;
import io.flybase.query.types.OperatorsNames;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author adantas
 */
public class Filters {

    public static Filter eq(String path, Object value) {
        return new Filter(path, Arrays.asList(new Operator(
                OperatorsNames.EQUAL.getName(), value)));
    }

    public static Filter not(String path, Object value) {
        return new Filter(path, Arrays.asList(new Operator(
                OperatorsNames.NOT.getName(), value)));
    }

    public static Filter gt(String path, Object value) {
        return new Filter(path, Arrays.asList(new Operator(
                OperatorsNames.GT.getName(), value)));
    }

    public static Filter gte(String path, Object value) {
        return new Filter(path, Arrays.asList(new Operator(
                OperatorsNames.GTE.getName(), value)));
    }

    public static Filter lt(String path, Object value) {
        return new Filter(path, Arrays.asList(new Operator(
                OperatorsNames.LT.getName(), value)));
    }

    public static Filter lte(String path, Object value) {
        return new Filter(path, Arrays.asList(new Operator(
                OperatorsNames.LTE.getName(), value)));
    }

    public static Filter regex(String path, Object value) {
        return new Filter(path, Arrays.asList(new Operator(
                OperatorsNames.REGEX.getName(), value)));
    }

    public static Filter in(String path, Object... values) {
        return new Filter(path, Arrays.asList(new Operator(
                OperatorsNames.IN.getName(), values)));
    }

    public static Filter and(Map<String, List<Operator>> conditions) {
        return new Filter(OperatorsNames.AND.getName(), conditions.entrySet().parallelStream()
                .map(entry -> new Operator(entry.getKey(), entry.getValue()
                        .parallelStream()
                        .collect(Collectors.toMap(Operator::getFieldName, Operator::getValue))))
                .collect(Collectors.toList()));
    }
}
