/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query.impl.validators;

import io.flybase.query.types.ContextParameter;
import java.util.List;

/**
 *
 * @author adantas
 */
public interface Validator {
    
    public boolean validate(List<ContextParameter> context);
}
