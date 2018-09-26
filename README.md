# Eth_Demo
1.com.laurel.eth.trace
debug_tracetransaction
https://github.com/ethereum/go-ethereum/wiki/Management-APIs#debug_tracetransaction

The class is copy from "https://github.com/fergarrui/ethereum-disassembler" and "https://github.com/fergarrui/ethereum-graph-debugger"

1.1.initial
run local geth in "./build/bin/geth --datadir dev_data/node1 --rpc --rpcaddr "your ip address"  --rpcport 8545 --rpcapi "eth,net,web3,debug" console"

1.2.run
run the Test.java's main method

1.3.The result
 ![image](https://github.com/plaichat1986/Ett_Demo/blob/master/pic/result.png)

2. com.laurel.eth.abi
decode contract transaction's input data.
translate the code into java (https://github.com/prettymuchbryce/abidecoder)
Such like https://etherscan.io/address/0x3839416bd0095d97be9b354cbfb0f6807d4d609e#code, If Abi has fallback, the Abi.fromJson() will run with NullPointerException.
In Abi.java, its create method only handle "constructor","function","event" type, don't handle "fallback" type. 
But the "https://etherscan.io/tx/0xda5332582d170ef114d9e717c09aaf954b66b51942fcd8585e377093843cbb72 " also has "fallback", it run success.
