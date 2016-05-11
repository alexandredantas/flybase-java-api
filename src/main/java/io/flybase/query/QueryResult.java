/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query;

import io.flybase.Document;
import java.util.List;

/**
 *
 * @author adantas
 */
public interface QueryResult {

    Document single();
    
    List<Document> results();
}
