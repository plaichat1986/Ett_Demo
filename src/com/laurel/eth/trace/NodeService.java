package com.laurel.eth.trace;


import com.fasterxml.jackson.databind.ObjectMapper;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import java.io.IOException;
import java.util.*;

public class NodeService {

    private static final List<String> callingOps = new ArrayList<>(Arrays.asList(Opcodes.CALL.toString(), Opcodes.DELEGATECALL.toString()));
    public static final String EMPTY_ADDRESS = "0x0000000000000000000000000000000000000000";

    private final String nodeUrl;
    private final Map<String, Map<Integer, DebugTraceTransactionLog>> addressTrace = new HashMap<>();

    public NodeService(String nodeUrl) {
        this.nodeUrl = nodeUrl;
    }

    public void populateTraceDataResponse(String address, String txHash) throws IOException {
        DebugTraceTransactionResponse debugTraceTransactionResponse = getDebugTraceTransactionResponse(txHash);
        populateAddressDebugTraceMap(address, debugTraceTransactionResponse.getResult().getStructLogs());
    }

    private void populateAddressDebugTraceMap(String address, List<DebugTraceTransactionLog> debugTrace) {
        address = "0x" + address.substring(address.length() - 40, address.length());
        boolean isNested = debugTrace.stream().anyMatch(trace -> callingOps.contains(trace.getOp()));
        if (!isNested) {
            Map<Integer, DebugTraceTransactionLog> pcTraceMap = getPcTraceMap(debugTrace);
            addressTrace.put(address, pcTraceMap);
        } else {
            int currentDepth = debugTrace.get(0).getDepth();
            for (DebugTraceTransactionLog debugTraceTransactionLog : debugTrace) {
                if (callingOps.contains(debugTraceTransactionLog.getOp())) {
                    List<String> stack = debugTraceTransactionLog.getStack();
                    String calledAddress = stack.get(stack.size() - 2);
                    int currentIndex = debugTrace.indexOf(debugTraceTransactionLog);
                    int nextIndex = currentIndex + 1;
                    DebugTraceTransactionLog nextLog = debugTrace.get(nextIndex);
                    int nextDepth = nextLog.getDepth();
                    int endIndex = 0;
                    for (int i = nextIndex; i < debugTrace.size(); i++) {
                        DebugTraceTransactionLog insider = debugTrace.get(i);
                        if (insider.getDepth() == nextDepth-1) {
                            endIndex = i;
                            break;
                        }
                    }
                    List<DebugTraceTransactionLog> debugTraceTransactionLogs = debugTrace.subList(nextIndex, endIndex);
                    populateAddressDebugTraceMap(calledAddress, debugTraceTransactionLogs);
                }
                if (debugTraceTransactionLog.getDepth() == currentDepth) {
                    if (addressTrace.containsKey(address)) {
                        addressTrace.get(address).put(debugTraceTransactionLog.getPc(), debugTraceTransactionLog);
                    } else {
                        Map<Integer, DebugTraceTransactionLog> map = new HashMap<>();
                        map.put(debugTraceTransactionLog.getPc(), debugTraceTransactionLog);
                        addressTrace.put(address, map);
                    }
                }
            }
        }
    }

    private static Map<Integer, DebugTraceTransactionLog> getPcTraceMap(List<DebugTraceTransactionLog> debugTrace) {
        Map<Integer, DebugTraceTransactionLog> mapResult = new HashMap<>();
        for (DebugTraceTransactionLog structLog : debugTrace) {
            int pc = structLog.getPc();
            mapResult.put(pc, structLog);
        }
        return mapResult;
    }

    private DebugTraceTransactionResponse getDebugTraceTransactionResponse(String txHash) throws IOException {
        DebugTraceTransactionRequest requestJson = new DebugTraceTransactionRequest();
        requestJson.addTransactionHashAsParam(txHash);
        ObjectMapper objectMapper = new ObjectMapper();
        String json = objectMapper.writeValueAsString(requestJson);

        String responseJson = doRequest(json);
        System.out.println("test responseJson = " + responseJson);
        return objectMapper.readValue(responseJson, DebugTraceTransactionResponse.class);
    }

    private String doRequest(String json) throws IOException {
        HttpClient httpClient = HttpClientBuilder.create().build();
        HttpPost httpPost = new HttpPost(nodeUrl);
        StringEntity stringEntity = new StringEntity(json);
        //httpPost.addHeader("encoding", "UTF-8");
        //httpPost.addHeader("content-type", "application/json; charset=utf-8");
        stringEntity.setContentEncoding("UTF-8");    
        stringEntity.setContentType("application/json"); 
        httpPost.setEntity(stringEntity);
        HttpResponse response = httpClient.execute(httpPost);
        System.out.println("test doRequest json = " + json);
        System.out.println("test doRequest response = " + response);
        HttpEntity responseEntity = response.getEntity();
        return EntityUtils.toString(responseEntity);
    }
    
    public Map<String, Map<Integer, DebugTraceTransactionLog>> getAddressTrace() {
        return addressTrace;
    }
}
