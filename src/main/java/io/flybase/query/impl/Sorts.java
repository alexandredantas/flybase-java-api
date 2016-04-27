/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query.impl;

import io.flybase.query.types.Sort;
import io.flybase.query.types.SortType;
import java.util.Collections;
import org.json.JSONObject;

/**
 *
 * @author adantas
 */
public class Sorts {

    public Sort asc(String field) {
        return new Sort(field, SortType.ASCENDING);
    }

    public Sort desc(String field) {
        return new Sort(field, SortType.DESCENDING);
    }
}
