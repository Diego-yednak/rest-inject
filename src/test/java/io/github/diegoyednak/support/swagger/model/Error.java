package io.github.diegoyednak.support.swagger.model;

import jakarta.annotation.Generated;

@Generated(value = "org.openapitools.codegen.languages.SpringCodegen", date = "2025-03-26T16:27:38.171017100-03:00[America/Sao_Paulo]", comments = "Generator version: 7.8.0")
public class Error {
    private Integer code;

    private Integer reason;

    private String message;

    private Integer status;

    private String referenceError;

    private String atType;

    private String atSchemaLocation;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public Integer getReason() {
        return reason;
    }

    public void setReason(Integer reason) {
        this.reason = reason;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getReferenceError() {
        return referenceError;
    }

    public void setReferenceError(String referenceError) {
        this.referenceError = referenceError;
    }

    public String getAtType() {
        return atType;
    }

    public void setAtType(String atType) {
        this.atType = atType;
    }

    public String getAtSchemaLocation() {
        return atSchemaLocation;
    }

    public void setAtSchemaLocation(String atSchemaLocation) {
        this.atSchemaLocation = atSchemaLocation;
    }
}
