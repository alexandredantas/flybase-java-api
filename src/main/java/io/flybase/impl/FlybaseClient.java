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
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import io.flybase.Collection;
import io.flybase.Document;
import io.flybase.Flybase;
import io.flybase.exceptions.CollectionsRetrievingException;
import io.flybase.exceptions.DeletingDocumentException;
import io.flybase.exceptions.WritingDocumentException;
import io.flybase.query.QueryBuilder;
import io.flybase.query.impl.SimpleQueryBuilder;
import io.flybase.query.impl.validators.AtLeastOne;
import io.flybase.query.impl.validators.NoOne;
import io.flybase.query.impl.validators.OnlyOne;
import io.flybase.query.types.ParameterType;
import java.util.List;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import org.json.JSONObject;

/**
 *
 * @author adantas
 */
public class FlybaseClient implements Flybase {

    private static final String BASE_URL = "https://api.flybase.io/apps/%s";
    private static final String COLLECTIONS_URL = BASE_URL + "/collections";
    private static final String COLLECTION_URL = COLLECTIONS_URL + "/%s";

    static final String API_KEY_HEADER_NAME = "X-Flybase-API-Key";

    private final FlybaseRequestParameters requestParameters;
    private final Genson genson;

    class FlybaseRequestParameters {

        private final String apiKey;
        private final String slug;

        public FlybaseRequestParameters(String apiKey, String slug) {
            this.apiKey = apiKey;
            this.slug = slug;
        }

        public String getApiKey() {
            return apiKey;
        }

        public String getSlug() {
            return slug;
        }
    }

    public static class FlybaseClientBuilder {

        private String apiKey;
        private String slug;

        private FlybaseClientBuilder() {
        }

        public FlybaseClientBuilder apiKey(String api) {
            this.apiKey = api;
            return this;
        }

        public FlybaseClientBuilder slug(String slug) {
            this.slug = slug;
            return this;
        }

        public Flybase build() {
            return new FlybaseClient(this.apiKey, this.slug);
        }
    }

    private FlybaseClient(String apiKey, String slug) {
        this.requestParameters = new FlybaseRequestParameters(apiKey, slug);
        this.genson = new GensonBuilder()
                .failOnMissingProperty(false)
                .failOnNullPrimitive(false)
                .create();
    }

    public static FlybaseClientBuilder builder(){
        return new FlybaseClientBuilder();
    }

    @Override

    public List<Collection> collections() {
        try {
            return this.genson.deserialize(Unirest.get(getCollectionsUrl())
                    .header(API_KEY_HEADER_NAME, this.requestParameters.getApiKey())
                    .asJson().getBody().getArray().toString(),
                    new GenericType<List<String>>() {
                    }).parallelStream()
                    .map(name -> (Collection) new CollectionImpl(getCollectionUrl(name),
                                    this.requestParameters))
                    .collect(Collectors.toList());
        } catch (UnirestException ex) {
            throw new CollectionsRetrievingException(ex);
        }
    }

    @Override
    public Collection collection(String collectionName) {
        return new CollectionImpl(this.getCollectionUrl(collectionName),
                this.requestParameters);
    }

    private String getCollectionsUrl() {
        return String.format(COLLECTIONS_URL, this.requestParameters.getSlug());
    }

    private String getCollectionUrl(String collectionName) {
        return String.format(COLLECTION_URL, new Object[]{this.requestParameters.getSlug(),
            collectionName});
    }

    private class CollectionImpl implements Collection {

        private final Logger LOGGER = Logger.getLogger(CollectionImpl.class.
                getName());

        private final String collectionUrl;
        private final FlybaseClient.FlybaseRequestParameters requestParameters;

        private CollectionImpl(String url, FlybaseClient.FlybaseRequestParameters requestParamters) {
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
                    return Optional.of(Document.raw(response));
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
                    return Document.raw(response.getBody().getObject());
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
            return new SimpleQueryBuilder(Unirest.get(this.collectionUrl)
                    .header(FlybaseClient.API_KEY_HEADER_NAME, this.requestParameters.getApiKey()),
                    new NoOne(ParameterType.BODY));
        }

        @Override
        public QueryBuilder batchUpdate() {
            return new SimpleQueryBuilder(Unirest.put(this.collectionUrl)
                    .header(FlybaseClient.API_KEY_HEADER_NAME, this.requestParameters.getApiKey()),
                    new AtLeastOne(ParameterType.QUERY),
                    new OnlyOne(ParameterType.BODY));
        }
    }
}
