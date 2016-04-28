/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query;

import io.flybase.query.types.Filter;

/**
 *
 * @author adantas
 */
public interface QueryBuilder {
    
    QueryBuilder filter(Filter filter);
    
    PreparedQuery prepare(QueryContext context);
}
