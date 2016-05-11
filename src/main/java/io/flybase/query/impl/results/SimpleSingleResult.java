/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query.impl.results;

import io.flybase.Document;
import io.flybase.query.QueryResult;
import java.util.Collections;
import java.util.List;
import org.json.JSONObject;

/**
 *
 * @author user
 */
public class SimpleSingleResult implements QueryResult {

    private final JSONObject raw;

    public SimpleSingleResult(JSONObject raw) {
        this.raw = raw;
    }

    @Override
    public Document single() {
        return Document.raw(this.raw);
    }

    @Override
    public List<Document> results() {
        return Collections.singletonList(Document.raw(this.raw));
    }
}
