/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query.impl;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import io.flybase.query.QueryResult;

/**
 *
 * @author adantas
 */
public class SimpleQueryResult implements QueryResult {

    private final Genson genson = new GensonBuilder()
            .failOnMissingProperty(false)
            .failOnNullPrimitive(false)
            .create();

    private final Object raw;

    public SimpleQueryResult(Object raw) {
        this.raw = raw;
    }

    @Override
    public <T> T as(GenericType<T> convertTo) {
        if (this.raw == null) {
            return null;
        }

        return this.genson.deserialize(this.raw.toString(), convertTo);
    }

    @Override
    public <T> T as(Class<T> convertTo) {
        if (this.raw == null) {
            return null;
        }

        return this.genson.deserialize(this.raw.toString(), convertTo);
    }

    @Override
    public String asString() {
        if (this.raw == null) {
            return null;
        }

        return this.raw.toString();
    }
}
