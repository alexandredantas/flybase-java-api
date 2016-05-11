/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query;

import io.flybase.query.types.SortType;

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

    public static class Sort {

        private final String field;
        private final SortType type;

        private Sort(String field, SortType type) {
            super();
            this.field = field;
            this.type = type;
        }

        public String getField() {
            return field;
        }

        public SortType getType() {
            return type;
        }
    }
}
