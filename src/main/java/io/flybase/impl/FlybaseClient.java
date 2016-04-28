/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.impl;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import io.flybase.Collection;
import io.flybase.Flybase;
import io.flybase.exceptions.CollectionsRetrievingException;
import java.util.List;
import java.util.stream.Collectors;

/**
 *
 * @author adantas
 */
public class FlybaseClient implements Flybase {

    private static final String BASE_URL = "https://api.flybase.io/apps/%s";
    private static final String COLLECTIONS_URL = BASE_URL + "/collections";
    private static final String COLLECTION_URL = COLLECTIONS_URL + "/collections/%s";

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

    FlybaseClient(String apiKey, String slug) {
        this.requestParameters = new FlybaseRequestParameters(apiKey, slug);
        this.genson = new GensonBuilder()
                .failOnMissingProperty(false)
                .failOnNullPrimitive(false)
                .create();
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
}
