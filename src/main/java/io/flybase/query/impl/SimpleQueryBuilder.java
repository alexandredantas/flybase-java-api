/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query.impl;

import com.mashape.unirest.request.HttpRequest;
import com.owlike.genson.GensonBuilder;
import io.flybase.exceptions.QueryException;
import io.flybase.query.types.Operator;
import io.flybase.query.PreparedQuery;
import io.flybase.query.QueryBuilder;
import io.flybase.query.QueryContext;
import java.util.ArrayList;
import java.util.List;
import io.flybase.query.impl.validators.Validator;
import io.flybase.query.types.ContextParameter;
import io.flybase.query.types.ParameterType;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

/**
 *
 * @author adantas
 */
public class SimpleQueryBuilder implements QueryBuilder {

    private final HttpRequest request;
    private final List<Operator> operators;
    private final List<Validator> validators;

    public SimpleQueryBuilder(HttpRequest request, Validator... validators) {
        this.request = request;
        this.validators = Arrays.asList(validators);
        this.operators = new ArrayList<>();
    }

    @Override
    public QueryBuilder filter(Operator operator) {
        this.operators.add(operator);
        return this;
    }

    @Override
    public PreparedQuery prepare(QueryContext context) {
        List<ContextParameter> params = context.create();

        params.add(new ContextParameter("q", this.appendFilters(),
                ParameterType.QUERY));

        if (this.validators.parallelStream().filter(validator
                -> validator.validate(params)).count() != this.validators.size()) {
            throw new QueryException("Invalid query context!");
        }

        return new RestPreparedQuery(params, this.request);
    }

    private String appendFilters() {
        Map<String, List<String>> a = this.operators.parallelStream().collect(
                Collectors.groupingByConcurrent(Operator::getFieldName,
                        Collectors.mapping(Operator::getValue, Collectors.toList())));

        System.out.println(new GensonBuilder().create().serialize(a));

        return null;
    }
}
