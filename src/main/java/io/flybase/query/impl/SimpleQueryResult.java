/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query.impl;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import io.flybase.Document;
import io.flybase.query.QueryResult;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
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

    private final Object raw;

    public SimpleQueryResult(Object raw) {
        this.raw = raw;
    }

    @Override
    public Document single() {
        if (this.raw instanceof JSONArray) {
            Iterator it = ((JSONArray) this.raw).iterator();
            while (it.hasNext()) {
                return Document.raw(
                        genson.deserialize(genson.serialize(it.next()), JSONObject.class));
            }
        }

        if (this.raw instanceof JSONObject) {
            return Document.raw((JSONObject) this.raw);
        }

        return null;
    }

    @Override
    public List<Document> results() {
        if (this.raw instanceof JSONObject) {
            return Collections.singletonList(Document.raw((JSONObject) this.raw));
        }

        if (this.raw instanceof JSONArray) {
            List<Document> results = new ArrayList<>();
            Iterator it = ((JSONArray) this.raw).iterator();
            while (it.hasNext()) {
                results.add(Document.raw(
                        genson.deserialize(genson.serialize(it.next()),
                                JSONObject.class)));
            }

            return results;
        }

        return null;
    }
}
