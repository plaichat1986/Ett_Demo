package com.laurel.eth.abi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.ethereum.solidity.Abi;
import org.ethereum.solidity.SolidityType.AddressType;
import org.ethereum.util.ByteUtil;
import org.spongycastle.util.encoders.Hex;

public class AbiDecoder {
	private List<Abi.Entry> _savedAbis = new ArrayList<>();
	private HashMap<String, Abi.Entry> _methodIDs = new HashMap<>();
	
	class DecodedMethod{
		public DecodedMethod(String name, List params) {
			this.name = name;
			this.params = params;
		}
		String name;
		List<Param> params;
	}
	
	class Log{
		String data;
		List<String> topics;
		String address;
	}
	
	class DecodedLog{
		String name;
		String address;
		List<Param> events;
	}
	
	class Param{
		public Param(String name, String type, Object value) {
			this.name = name;
			this.type = type;
			this.value = value;
		}
		String name;
		String type;
		Object value;
	}
	
	public void addAbi(String json) {
		Abi abi = Abi.fromJson(json);
		for(Abi.Entry entry : abi) {
			if(entry == null) {
				continue;
			}
			if(entry.name != null) {
				byte[] methodSignature = entry.encodeSignature();
				_methodIDs.put(Hex.toHexString(methodSignature) ,entry);
			}
			_savedAbis.add(entry);
		}
	}
	
	private List<Abi.Entry> getAbis() {
        return _savedAbis;
    }
	
	private Map<String, Abi.Entry> getMethodIDs(){
        return _methodIDs;    
    }
	
	private void removeAbi(String json) {
		Abi abi = Abi.fromJson(json);
        for (Abi.Entry entry : abi) {
            if (entry == null) {
                continue;
            }
            if (entry.name != null) {
                byte[] methodSignature = entry.encodeSignature();
                _methodIDs.remove(Hex.toHexString(methodSignature));
            }
            _savedAbis.remove(entry);
        }
    }
	
	public DecodedMethod decodeMethod(String data){
        String noPrefix = data.substring("0x".length());
        byte[] bytes = Hex.decode(noPrefix.toUpperCase());
        byte[] methodBytes = new byte[4];
        System.arraycopy(bytes, 0, methodBytes, 0, 4);
        System.out.println("methodBytes = " + Hex.toHexString(methodBytes));
        Abi.Entry entry = _methodIDs.get(Hex.toHexString(methodBytes));
        if (entry instanceof Abi.Function) {
        	System.out.println("method.name = " + ((Abi.Function)entry).name);
            List decoded = ((Abi.Function)entry).decode(bytes);
            List params = new ArrayList<Param>();
            Abi.Entry.Param etnryParam = null;
            for (int i = 0; i < decoded.size(); i++) {
            	System.out.println("   ");
            	etnryParam = entry.inputs.get(i);
                String name = etnryParam.name;
                String type = etnryParam.type.toString();
                Object value = decoded.get(i);
                System.out.println("param.name = " + name);
                System.out.println("param.type = " + type);
                
                
                if(type.equals("address")) {
                	byte[] address = (byte[]) value;
                	System.out.println("address = " +  ByteUtil.toHexString(address));
                } else {
                	System.out.println("value = " + value);
                }
                Param param = new Param(name, type, value);
                params.add(param);
            }
            return new DecodedMethod(entry.name, params);
        }
        return null;
	}
}
