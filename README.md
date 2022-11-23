<h1> 
    <a href="https://magician-io.com">Magician-Web3</a> ·
    <img src="https://img.shields.io/badge/licenes-MIT-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/jdk-8+-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/maven-3.5.4+-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/release-master-brightgreen.svg"/>
</h1>

Magician-Web3 is a blockchain development toolkit. 
It consists of two functions. One is to scan the blockchain and monitor the transactions according to the developer's needs. 
The other is some secondary packaging of web3j, which can reduce the workload of developers in some common scenarios. It is planned to support three chains, ETH (BSC, POLYGAN, etc.), SOL and TRON

## Running environment

JDK8+

## Documentation

[https://magician-io.com/chain](https://magician-io.com/chain)

## Example

### Importing dependencies
```xml
<dependency>
    <groupId>com.github.yuyenews</groupId>
    <artifactId>Magician-Web3</artifactId>
    <version>1.0.3</version>
</dependency>

<!-- This is the logging package, you must have it or the console will not see anything, any logging package that can bridge with slf4j is supported -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-jdk14</artifactId>
    <version>1.7.12</version>
</dependency>
```

### Create a scan task

Create a MonitorEvent

```java
import java.math.BigInteger;

/**
 * Create an implementation class for EthMonitorEvent
 */
public class EthMonitorEventImpl implements EthMonitorEvent {

    /**
     * set filters
     *
     * Filter the transaction records according to these conditions and trigger the call method
     * @return
     */
    @Override
    public EthMonitorFilter ethMonitorFilter() {
        return EthMonitorFilter.builder()
                .setToAddress("0xasdasdasdasdasdasdasdasdas")
                .setInputDataFilter(
                        InputDataFilter.create()
                                .setFunctionCode(ERC20.TRANSFER.getFunctionCode())
                                .setTypeReferences(
                                        new TypeReference<Address>(){},
                                        new TypeReference<Uint256>(){}
                                )
                                .setValue("0x552115849813d334C58f2757037F68E2963C4c5e", null)
                );
    }

    /**
     * This method is triggered when a transaction matching the above filter criteria is encountered
     * @param transactionModel
     */
    @Override
    public void call(TransactionModel transactionModel) {
        
    }
}
```

Start a monitoring task

```java
// Initialize the thread pool, the number of core threads must be >= the number of chains you want to scan, it is recommended to equal the number of chains to be scanned
EventThreadPool.init(1);

// Open a scan task, if you want to scan multiple chains, you can open multiple tasks, 
// by copying the following code and modifying the corresponding configuration you can open a new task
MagicianBlockchainScan.create()
        .setRpcUrl("https://data-seed-prebsc-1-s1.binance.org:8545/")
        .setChainType(ChainType.ETH)
        .setScanPeriod(5000)
        .setBeginBlockNumber(BigInteger.valueOf(24318610))
        .addEthMonitorEvent(new EventOne())
        .addEthMonitorEvent(new EventThree())
        .addEthMonitorEvent(new EventTwo())
        .start();
```

### Other utils

```java
// ABI codec
EthAbiCodec ethAbiCodec = MagicianWeb3.getEthBuilder().getEthAbiCodec();

// Encode the function as inputData
String inputData = ethAbiCodec.getInputData("mint",
    new Address("0xqwasdasd"),
    new Utf8String("https://asdasdasdsadasd.json")
);

// Get the function's signature
String funcCode = ethAbiCodec.getFunAbiCode("mint",
    new Address("0xqwasdasd"),
    new Utf8String("https://asdasdasdsadasd.json")
);

// Decode inputData into raw data
List<Type> result = ethAbiCodec.decoderInputData("0xasdasdas00000000adasd",
    new TypeReference<Address>(){},
    new TypeReference<Utf8String>(){}
);

// ------------------------ More, if you are interested, you can visit our official website for more information ----------------------

Web3j web3j = Web3j.build(new HttpService(""));
String privateKey = "";

EthHelper ethHelper = MagicianWeb3.getEthBuilder().getEth(web3j);

BigInteger amount = ethHelper.balanceOf(fromAddress);

ethHelper.transfer(
        toAddress,
        privateKey,
        BigDecimal.valueOf(1),
        Convert.Unit.ETHER
);

EthContract ethContract = MagicianWeb3.getEthBuilder().getEthContract(web3j);

List<Type> result =  ethContract.select(
        contractAddress,
        ethAbiCodec.getInputData(
        "balanceOf",
        new Address(toAddress)),
        new TypeReference<Uint256>() {}
);

System.out.println(result.get(0).getValue());

ethContract.sendRawTransaction(
        fromAddress,
        contractAddress,
        privateKey,
        ethAbiCodec.getInputData(
            "transfer",
            new Address(toAddress),
            new Uint256(new BigInteger("1000000000000000000"))
        )
);
```

## To explain this toolkit more intuitively, let me give you an example, for example.

I want the program to receive all the fields of a transaction message when a coin is received at a certain address. If I use this toolkit, I only need to write half a line of code by hand (not including the business operation after receiving the notification, because there is no escape from that, as with any tool).

If you add a scenario above where the program receives the appropriate transaction information when the number of coins received or sent to an address is within a certain range, then you don't need to change anything, just write another half line of code, and that half line of code is isolated and completely decoupled from the other half.

If you need to drop one of these two cases, just delete it or comment it out.

If there is a third case, then write another half line, one line at most.

Note: The half and one lines I mentioned above refer to the code that needs to be written by hand, not the total code, but the total code is not much, and each scenario corresponds to an implementation class that implements only two methods.

As a developer, you just need to pay attention to what kind of events to listen to, and nothing else needs to consume energy. For the transactions that call the contract, the developer may need to do secondary filtering, such as checking the logs to determine if the transaction is valid, parsing inputData to get more detailed information and making judgments etc.
