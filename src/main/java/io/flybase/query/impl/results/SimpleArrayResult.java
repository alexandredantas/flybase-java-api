/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query.impl.results;

import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import io.flybase.Document;
import io.flybase.query.QueryResult;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author user
 */
public class SimpleArrayResult implements QueryResult {

    private static final Genson genson = new GensonBuilder()
            .failOnMissingProperty(false)
            .failOnNullPrimitive(false)
            .create();

    private final JSONArray raw;

    public SimpleArrayResult(JSONArray raw) {
        this.raw = raw;
    }

    @Override
    public Document single() {
        Iterator it = this.raw.iterator();
        while (it.hasNext()) {
            return Document.raw(
                    genson.deserialize(genson.serialize(it.next()), JSONObject.class));
        }

        return Document.from(new Object());
    }

    @Override
    public List<Document> results() {
        List<Document> results = new ArrayList<>();
        Iterator it = this.raw.iterator();
        while (it.hasNext()) {
            results.add(Document.raw(
                    genson.deserialize(genson.serialize(it.next()),
                            JSONObject.class)));
        }

        return results;
    }

}
