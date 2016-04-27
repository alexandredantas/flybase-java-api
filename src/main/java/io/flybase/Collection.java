/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase;

import io.flybase.exceptions.DeletingDocumentException;
import io.flybase.exceptions.WritingDocumentException;
import io.flybase.query.QueryBuilder;
import java.util.Optional;

/**
 * A {@link Collection} is a bucket where your data is stored. It can be
 * compared to tables in relational databases
 *
 * @author adantas
 */
public interface Collection {

    /**
     * Retrieve a document using its unique identifier, represented by the field
     * _id
     *
     * @param id Document identifier (_id field)
     * @return An {@link Optional} that enable to check if the document was
     * found
     */
    Optional<Document> retrieve(String id);

    /**
     * Saves a document into this collection. If your document has a field named
     * _id, this method will perform an upsert operation.
     *
     * @param doc Document to be save
     * @return The saved document
     * @throws WritingDocumentException if save fails
     */
    Document save(Document doc);

    /**
     * Delete a document using its unique identifier, represented by the field
     * _id
     *
     * @param id Document identifier (_id field)
     * @throws DeletingDocumentException if save fails
     */
    void delete(String id);

    /**
     * Returns a {@link QueryBuilder} ready to query this collection
     * @return 
     */
    QueryBuilder query();
}
