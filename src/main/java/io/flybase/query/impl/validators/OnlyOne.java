/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query.impl.validators;

import io.flybase.query.QueryContext;
import io.flybase.query.types.ContextParameter;
import io.flybase.query.types.ParameterType;
import java.util.List;

/**
 *
 * @author adantas
 */
public class OnlyOne implements Validator {

    private final ParameterType type;

    public OnlyOne(ParameterType type) {
        this.type = type;
    }

    @Override
    public boolean validate(List<ContextParameter> context) {
        return context.parallelStream().filter(param
                -> this.type.equals(param.getType())).count() == 1;
    }
}
