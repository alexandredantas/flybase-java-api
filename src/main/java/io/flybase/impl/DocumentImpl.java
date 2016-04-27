/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.impl;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import io.flybase.Document;
import org.json.JSONObject;

/**
 *
 * @author adantas
 */
public class DocumentImpl implements Document {

    private final JSONObject raw;
    private final Genson genson;

    DocumentImpl(JSONObject raw) {
        this.raw = raw;
        this.genson = new GensonBuilder()
                .failOnMissingProperty(false)
                .failOnNullPrimitive(false)
                .create();
    }

    public static Document empty() {
        return new DocumentImpl(new JSONObject());
    }

    @Override
    public JSONObject getRaw() {
        return this.raw;
    }

    @Override
    public <T> T to(GenericType<T> convertTo) {
        return this.genson.deserialize(this.raw.toString(), convertTo);
    }

    @Override
    public Document from(Object source) {
        return new DocumentImpl(new JSONObject(this.genson.serialize(source)));
    }

}
