/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query;

/**
 *
 * @author adantas
 */
public interface QueryBuilder {
    
    QueryBuilder filter(io.flybase.query.Filters.Filter filter);
    
    PreparedQuery prepare(QueryContext context);
}
