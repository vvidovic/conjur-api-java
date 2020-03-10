package com.cyberark.conjur.api;

import com.cyberark.conjur.api.clients.ResourceClient;

public class Variables {

    private ResourceClient resourceClient;

    public Variables(Credentials credentials) {
        resourceClient = new ResourceClient(credentials, Endpoints.fromCredentials(credentials));
    }

    public Variables(Token token) {
        resourceClient = new ResourceClient(token, Endpoints.fromSystemProperties());
    }
    
    public String retrieveSecret(String variableId) {
        return resourceClient.retrieveSecret(variableId);
    }

    public void addSecret(String variableId, String secret){
        resourceClient.addSecret(variableId, secret);
    }
}
