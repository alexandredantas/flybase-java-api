/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.query;

import org.json.JSONObject;

/**
 *
 * @author adantas
 */
public interface Operator {
    
    JSONObject build();
}
