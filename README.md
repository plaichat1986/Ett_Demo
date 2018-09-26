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
Some Abicode failed in Abi.fromJson(), I don't know the reason
