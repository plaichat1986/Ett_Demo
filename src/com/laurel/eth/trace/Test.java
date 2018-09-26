package com.laurel.eth.trace;

import java.io.IOException;
import java.util.Map;


public class Test {
	//private static final String NET_URL = "https://kovan.infura.io/nbiFqa6vmBlr2Te4BRro:8545"; //no use
	private static final String NET_URL = "http://192.168.1.167:8545";
	
	public static void main(String[] args) {
		String nodeUrl = NET_URL;
		String address = "0x6eafdfde5aeead59b1245f6a88ff9512ab7352ec";
		String txHash="0x3ff04a7ba6b54698ad631296a369b0e1c13f4d87a0da1ebbd1f80423e0e31465";
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
