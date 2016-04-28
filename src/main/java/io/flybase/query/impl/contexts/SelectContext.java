/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query.impl.contexts;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import io.flybase.query.QueryContext;
import io.flybase.query.types.ContextParameter;
import io.flybase.query.types.ParameterType;
import io.flybase.query.types.Sort;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author adantas
 */
public class SelectContext implements QueryContext {

    private boolean count;
    private List<String> includedFields;
    private List<String> excludedFields;
    private boolean single;
    private final List<Sort> sorting;
    private long skip;
    private long limit;
    private final Genson genson;

    public SelectContext() {
        this.sorting = new ArrayList<>();
        this.genson = new GensonBuilder().create();
        this.includedFields = Collections.emptyList();
        this.excludedFields = Collections.emptyList();
    }

    public SelectContext count() {
        this.count = true;
        return this;
    }

    public SelectContext nonCount() {
        this.count = false;
        return this;
    }

    public SelectContext includedFields(String... fields) {
        this.includedFields = Arrays.asList(fields);
        return this;
    }

    public SelectContext excludedFields(String... fields) {
        this.excludedFields = Arrays.asList(fields);
        return this;
    }

    public SelectContext single() {
        this.single = true;
        return this;
    }

    public SelectContext nonSingle() {
        this.single = false;
        return this;
    }

    public SelectContext sort(Sort sort) {
        this.sorting.add(sort);
        return this;
    }

    public SelectContext skip(long skip) {
        this.skip = skip;
        return this;
    }

    public SelectContext limit(long limit) {
        this.limit = limit;
        return this;
    }

    @Override
    public List<ContextParameter> create() {
        List<ContextParameter> params = new ArrayList<>();

        if (this.count) {
            params.add(new ContextParameter("c", String.valueOf(this.count),
                    ParameterType.QUERY));
        }

        if (!this.includedFields.isEmpty() || !this.excludedFields.isEmpty()) {
            params.add(new ContextParameter("f", this.getQueriedFields(),
                    ParameterType.QUERY));
        }

        if (this.single) {
            params.add(new ContextParameter("fo", "true",
                    ParameterType.QUERY));
        }

        if (!this.sorting.isEmpty()) {
            params.add(new ContextParameter("s", this.getSorting(),
                    ParameterType.QUERY));
        }

        if (this.skip > 0) {
            params.add(new ContextParameter("sk", String.valueOf(this.skip),
                    ParameterType.QUERY));
        }

        if (this.limit > 0) {
            params.add(new ContextParameter("l", String.valueOf(this.limit),
                    ParameterType.QUERY));
        }

        return params;
    }

    private String getQueriedFields() {
        Map<String, Integer> fields = new HashMap<>();

        fields.putAll(this.includedFields.parallelStream().collect(
                Collectors.toMap(fieldName -> fieldName, fieldName -> 1)));

        fields.putAll(this.excludedFields.parallelStream().collect(
                Collectors.toMap(fieldName -> fieldName, fieldName -> 0)));

        return this.genson.serialize(fields);
    }

    private String getSorting() {
        return this.genson.serialize(this.sorting.parallelStream().collect(
                Collectors.toMap(sort -> sort.getField(),
                        sort -> sort.getType().getValue())));
    }

}
