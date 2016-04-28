/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query.impl;

import com.mashape.unirest.request.HttpRequest;
import io.flybase.query.PreparedQuery;
import io.flybase.query.QueryResult;
import io.flybase.query.types.ContextParameter;
import java.util.List;

/**
 *
 * @author adantas
 */
public class RestPreparedQuery implements PreparedQuery {

    private final List<ContextParameter> parameters;
    private final HttpRequest request;

    public RestPreparedQuery(List<ContextParameter> parameters, HttpRequest request) {
        this.parameters = parameters;
        this.request = request;
    }

    @Override
    public QueryResult execute() {
        return null;
    }
}
