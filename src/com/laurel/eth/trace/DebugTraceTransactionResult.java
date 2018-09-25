package com.laurel.eth.trace;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties
public class DebugTraceTransactionResult {

    private int gas;
    private boolean failed;
    private String returnValue;
    private List<DebugTraceTransactionLog> structLogs;

    public int getGas() {
        return gas;
    }

    public void setGas(int gas) {
        this.gas = gas;
    }

    public boolean isFailed() {
		return failed;
	}

	public void setFailed(boolean failed) {
		this.failed = failed;
	}

	public String getReturnValue() {
        return returnValue;
    }

    public void setReturnValue(String returnValue) {
        this.returnValue = returnValue;
    }

    public List<DebugTraceTransactionLog> getStructLogs() {
        return structLogs;
    }

    public void setStructLogs(List<DebugTraceTransactionLog> structLogs) {
        this.structLogs = structLogs;
    }

    @Override
    public String toString() {
        return "DebugTraceTransactionResult{" +
                "gas=" + gas +
                ", returnValue='" + returnValue + '\'' +
                ", structLogs=" + structLogs +
                '}';
    }
}
