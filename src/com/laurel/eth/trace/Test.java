package com.laurel.eth.trace;

import java.io.IOException;
import java.util.Map;


public class Test {
	//private static final String NET_URL = "https://kovan.infura.io/nbiFqa6vmBlr2Te4BRro:8545"; //no use
	private static final String NET_URL = "http://192.168.1.167:8545";
	
	public static void main(String[] args) {
		String nodeUrl = NET_URL;
		String address = "0xd5486850fac55f39bd06194148647a731dbd8780";
		String txHash="0xecb8ba9d125cc7bdec81d2a5d46d3ea50ecb854e52ef81a559e5375de187ab7a";
		NodeService nodeService = new NodeService(nodeUrl);
		Map<String, Map<Integer, DebugTraceTransactionLog>> traceData = null;

        try {
            nodeService.populateTraceDataResponse(address, txHash);
            traceData = nodeService.getAddressTrace();
        } catch (IOException e) {
        	e.printStackTrace();
            System.out.println("Failed getting debug trace for hash: " + txHash);
        }
	}

}
