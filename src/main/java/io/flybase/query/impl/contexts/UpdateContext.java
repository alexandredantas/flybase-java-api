/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query.impl.contexts;

import io.flybase.Document;
import io.flybase.query.QueryContext;
import io.flybase.query.types.ContextParameter;
import io.flybase.query.types.ParameterType;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author adantas
 */
public class UpdateContext implements QueryContext {

    private final Document changes;

    public UpdateContext(Document changes) {
        this.changes = changes;
    }

    @Override
    public List<ContextParameter> create() {
        return Collections.singletonList(new ContextParameter("body",
                this.changes.getRaw().toString(), ParameterType.BODY));
    }
}
