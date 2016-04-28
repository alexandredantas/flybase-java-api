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
import org.json.JSONObject;

/**
 *
 * @author adantas
 */
public class SimpleQueryResult implements QueryResult {

    private final Genson genson = new GensonBuilder()
            .failOnMissingProperty(false)
            .failOnNullPrimitive(false)
            .create();

    private final JSONObject raw;

    public SimpleQueryResult(JSONObject raw) {
        this.raw = raw;
    }

    @Override
    public <T> T as(GenericType<T> convertTo) {
        return this.genson.deserialize(this.raw.toString(), convertTo);
    }
}
