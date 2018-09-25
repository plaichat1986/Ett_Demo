package com.laurel.eth.trace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DebugTraceTransactionRequest {

    private String jsonrpc = "2.0";
    private String method = "debug_traceTransaction";
    private String id = "67";
    private List<String> params;

    public String getJsonrpc() {
        return jsonrpc;
    }

    public void setJsonrpc(String jsonrpc) {
        this.jsonrpc = jsonrpc;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<String> getParams() {
        return params;
    }

    public void setParams(List<String> params) {
        this.params = params;
    }

    public void addParams(String... params) {
        this.params = new ArrayList<>(Arrays.asList(params));
    }

    public void addTransactionHashAsParam(String txHash) {
        //this.params = new ArrayList<>(Arrays.asList(txHash, "latest"));
        this.params = new ArrayList<>(Arrays.asList(txHash));
    }
}
