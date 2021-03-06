/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.mashape.unirest.request.HttpRequest;
import com.mashape.unirest.request.HttpRequestWithBody;
import io.flybase.exceptions.QueryException;
import io.flybase.query.PreparedQuery;
import io.flybase.query.QueryResult;
import io.flybase.query.impl.results.SimpleArrayResult;
import io.flybase.query.impl.results.SimpleSingleResult;
import io.flybase.query.types.ContextParameter;
import io.flybase.query.types.ParameterType;
import java.util.List;
import java.util.stream.Collectors;

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
        this.request.queryString(this.parameters.stream()
                .filter(param -> ParameterType.QUERY.equals(param.getType()))
                .collect(Collectors.toMap(
                        ContextParameter::getName, ContextParameter::getContent)));

        if (request instanceof HttpRequestWithBody) {
            ((HttpRequestWithBody) request).body(this.parameters
                    .stream()
                    .filter(param -> ParameterType.BODY.equals(param.getType()))
                    .findFirst()
                    .orElse(new ContextParameter("", "", ParameterType.BODY)).getContent());
        }

        try {
            HttpResponse<JsonNode> response = this.request.asJson();

            if (response.getStatus() != 200) {
                throw new QueryException("Error retrieving data! Status: "
                        + response.getStatus());
            }

            if (response.getBody().isArray()) {
                return new SimpleArrayResult(response.getBody().getArray());
            } else {
                return new SimpleSingleResult(response.getBody().getObject());
            }
        } catch (UnirestException ex) {
            throw new QueryException("Error executing query", ex);
        }
    }
}
