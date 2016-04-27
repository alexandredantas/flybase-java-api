/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query.impl;

import io.flybase.query.Operator;
import io.flybase.query.PreparedQuery;
import io.flybase.query.QueryBuilder;
import io.flybase.query.QueryContext;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author adantas
 */
public class SimpleQueryBuilder implements QueryBuilder {

    private final String collectionUrl;
    private final List<Operator> operators;

    public SimpleQueryBuilder(String collectionUrl) {
        this.collectionUrl = collectionUrl;
        this.operators = new ArrayList<>();
    }

    @Override
    public QueryBuilder filter(Operator operator) {
        this.operators.add(operator);
        return this;
    }

    @Override
    public PreparedQuery prepare(QueryContext context) {
        
        return null;
    }
}
