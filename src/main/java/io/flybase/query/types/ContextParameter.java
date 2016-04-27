/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query.types;

import io.flybase.query.types.ParameterType;

/**
 *
 * @author adantas
 */
public class ContextParameter {
    
    private final String name;
    private final String content;
    private final ParameterType type;

    public ContextParameter(String name, String content, ParameterType type) {
        this.name = name;
        this.content = content;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public String getContent() {
        return content;
    }

    public ParameterType getType() {
        return type;
    }
}
