/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query.impl;

import com.mashape.unirest.request.HttpRequest;
import io.flybase.exceptions.QueryException;
import io.flybase.query.PreparedQuery;
import io.flybase.query.QueryBuilder;
import io.flybase.query.QueryContext;
import java.util.ArrayList;
import java.util.List;
import io.flybase.query.impl.validators.Validator;
import io.flybase.query.types.ContextParameter;
import io.flybase.query.types.Filter;
import io.flybase.query.types.OperatorsNames;
import io.flybase.query.types.ParameterType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;
import org.json.JSONObject;

/**
 *
 * @author adantas
 */
public class SimpleQueryBuilder implements QueryBuilder {

    private final HttpRequest request;
    private final List<Filter> filters;
    private final List<Validator> validators;

    public SimpleQueryBuilder(HttpRequest request, Validator... validators) {
        this.request = request;
        this.validators = Arrays.asList(validators);
        this.filters = new ArrayList<>();
    }

    @Override
    public QueryBuilder filter(Filter filter) {
        this.filters.add(filter);
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
        List<Filter> ands = this.filters.parallelStream()
                .filter(op -> op.getName().equals(OperatorsNames.AND.getName()))
                .collect(Collectors.toList());

        this.filters.removeAll(ands);

        Map<String, List<Filter>> groupedFilter = this.filters.parallelStream().collect(
                Collectors.groupingByConcurrent(Filter::getName));

        try {
            JSONObject jsonFilter = new JSONObject(
                    groupedFilter.entrySet().parallelStream()
                    .collect(Collectors.toMap(Map.Entry::getKey,
                            op -> op.getValue().stream()
                            .flatMap(filter -> filter.getOperators().stream())
                            .flatMap(map -> map.entrySet().stream())
                            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)))
                    ));

            ands.forEach((Filter filter) -> {
                jsonFilter.append(filter.getName(), filter.getOperators()
                        .parallelStream()
                        .flatMap(map -> map.entrySet().parallelStream())
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (s, a) -> {

                            HashMap<String, Object> merged = new HashMap<>();

                            merged.putAll((Map) s);
                            merged.putAll((Map) a);

                            return merged;
                        })));
            });

            return jsonFilter.toString();
        } catch (Exception e) {
            throw new RuntimeException("Invalid filter", e);
        }
    }

//    public static void main(String... args) {
//        SimpleQueryBuilder q = new SimpleQueryBuilder(null);
//
//        q.filter(new Filter("b",
//                new Operator(OperatorsNames.GT.getName(), "a"),
//                new Operator(OperatorsNames.GTE.getName(), "b")))
//                .filter(new Filter("$and",
//                        new Operator("field", Collections.singletonMap(OperatorsNames.GTE.getName(), "testing"))));
//        System.out.println(((SimpleQueryBuilder) q).appendFilters());
//    }
}
