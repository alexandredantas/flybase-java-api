/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package io.flybase.impl;

import io.flybase.Flybase;

/**
 *
 * @author adantas
 */
public class FlybaseClientBuilder {
    
    private String apiKey;
    private String slug;

    private FlybaseClientBuilder() {
    }
    
    public static FlybaseClientBuilder instance(){
        return new FlybaseClientBuilder();
    }
    
    FlybaseClientBuilder apiKey(String api){
        this.apiKey = api;
        return this;
    }
    
    FlybaseClientBuilder slug(String slug){
        this.slug = slug;
        return this;
    }
    
    Flybase build(){
        return new FlybaseClient(this.apiKey, this.slug);
    }
}
