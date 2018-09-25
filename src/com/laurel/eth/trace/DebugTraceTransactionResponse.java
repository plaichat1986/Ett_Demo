package com.laurel.eth.trace;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties
public class DebugTraceTransactionResponse {

    private String jsonrpc;
    private String id;
    private DebugTraceTransactionResult result;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public DebugTraceTransactionResult getResult() {
        return result;
    }

    public void setResult(DebugTraceTransactionResult result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "DebugTraceTransactionResponse{" +
                "jsonrpc='" + jsonrpc + '\'' +
                ", result=" + result +
                '}';
    }
}
