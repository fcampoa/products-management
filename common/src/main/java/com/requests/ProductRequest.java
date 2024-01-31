package com.requests;

import java.io.Serializable;

public class ProductRequest implements Serializable {
    private String requestId;
    private String content;
    private String method;
    public ProductRequest() {}

    public ProductRequest(String requestId, String content, String method) {
        this.requestId = requestId;
        this.content = content;
        this.method = method;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}

