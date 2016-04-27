/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.impl;

import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.JsonNode;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import io.flybase.Collection;
import io.flybase.Document;
import io.flybase.exceptions.DeletingDocumentException;
import io.flybase.exceptions.WritingDocumentException;
import io.flybase.query.QueryBuilder;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONObject;
import io.flybase.query.impl.SimpleQueryBuilder;

/**
 *
 * @author adantas
 */
public class CollectionImpl implements Collection {

    private static final Logger LOGGER = Logger.getLogger(CollectionImpl.class.getName());
    private static final String ID_FIELD = "_id";

    private final String collectionUrl;
    private final FlybaseClient.FlybaseRequestParameters requestParameters;

    CollectionImpl(String url, FlybaseClient.FlybaseRequestParameters requestParamters) {
        this.collectionUrl = url;
        this.requestParameters = requestParamters;
    }

    @Override
    public Optional<Document> retrieve(String id) {
        try {
            JSONObject response = Unirest.get(collectionUrl + "/" + id)
                    .header(FlybaseClient.API_KEY_HEADER_NAME, this.requestParameters.getApiKey())
                    .asJson()
                    .getBody()
                    .getObject();

            if (response.length() > 0) {
                return Optional.of((Document) new DocumentImpl(response));
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            LOGGER.log(Level.WARNING, "Error retrieving document by id", e);
            return Optional.empty();
        }
    }

    @Override
    public Document save(Document doc) {
        try {
            HttpResponse<JsonNode> response = Unirest.post(this.collectionUrl)
                    .header(FlybaseClient.API_KEY_HEADER_NAME, this.requestParameters.getApiKey())
                    .body(doc.getRaw())
                    .asJson();

            if (response.getStatus() > 201) {
                throw new WritingDocumentException(
                        String.format("Error writing document. Status: %d", response.getStatus()));

            } else {
                return new DocumentImpl(response.getBody().getObject());
            }
        } catch (UnirestException e) {
            throw new WritingDocumentException(e);
        }
    }

    @Override
    public void delete(String id) {
        try {
            HttpResponse<JsonNode> response = Unirest.delete(collectionUrl + "/" + id)
                    .header(FlybaseClient.API_KEY_HEADER_NAME, this.requestParameters.getApiKey())
                    .asJson();

            if (response.getStatus() != 200) {
                throw new DeletingDocumentException(String.format("Error deleting document!\nError:%s",
                        response.getBody().getObject().toString()));
            }
        } catch (UnirestException e) {
            LOGGER.log(Level.WARNING, "Error deleting document", e);
            throw new DeletingDocumentException(e);
        }
        
    }

    @Override
    public QueryBuilder query() {
        return new SimpleQueryBuilder(this.collectionUrl);
    }
}
