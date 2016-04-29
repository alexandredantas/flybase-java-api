/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase;

import com.owlike.genson.GenericType;
import com.owlike.genson.Genson;
import com.owlike.genson.GensonBuilder;
import org.json.JSONObject;

/**
 * Document is a utility class that enables converting your own POJOs to/from
 * JSON representations to be written to Flybase
 *
 * @author adantas
 */
public abstract class Document {

    /**
     * Get the raw document
     *
     * @return A raw {@link JSONObject}
     */
    public abstract JSONObject getRaw();

    /**
     * Convert this document to your own POJO class
     *
     * @param <T>
     * @param convertTo The destination class type
     * @return An object filled with the properties of this document
     */
    public abstract <T> T to(GenericType<T> convertTo);

    /**
     * Convert this document to your own POJO class
     *
     * @param <T>
     * @param convertTo The destination class type
     * @return An object filled with the properties of this document
     */
    public abstract <T> T to(Class<T> convertTo);

    /**
     * Convert your POJO to a JSON representation
     *
     * @param source Your POJO instance
     * @return The produced document
     */
    public static Document from(Object source) {
        Genson genson = new GensonBuilder().create();
        return new DocumentImpl(new JSONObject(genson.serialize(source)));
    }

    public static Document raw(JSONObject source) {
        return new DocumentImpl(source);
    }

    private static class DocumentImpl extends Document {

        private final JSONObject raw;
        private final Genson genson;

        private DocumentImpl(JSONObject raw) {
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
        public <T> T to(Class<T> convertTo) {
            return this.genson.deserialize(this.raw.toString(), convertTo);
        }
    }
}
