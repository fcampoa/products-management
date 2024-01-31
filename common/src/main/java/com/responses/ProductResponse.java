package com.responses;

import java.io.Serializable;

public class ProductResponse implements Serializable {
    private String requestId;
    private String content;
    private int responseStatus;
    public ProductResponse() {}

    public ProductResponse(String requestId, String content, int responseStatus) {
        this.requestId = requestId;
        this.content = content;
        this.responseStatus = responseStatus;
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

    public int getResponseStatus() {
        return responseStatus;
    }

    public void setResponseStatus(int responseStatus) {
        this.responseStatus = responseStatus;
    }
}
