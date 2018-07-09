package com.ynet.xwfabric.client;

import java.io.Serializable;
import java.security.PrivateKey;

import org.hyperledger.fabric.sdk.Enrollment;

public class FabricEnrollment implements Enrollment, Serializable {
    private static final long serialVersionUID = 550416591376968096L;
    private PrivateKey key;
    private String cert;

    public FabricEnrollment(PrivateKey signingKeyPair, String signedPem) {
        key = signingKeyPair;
        this.cert = signedPem;
    }

    public PrivateKey getKey() {
        return key;
    }

    public String getCert() {
        return cert;
    }

}
