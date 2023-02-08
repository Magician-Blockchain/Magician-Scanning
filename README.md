<h1> 
    <a href="https://magician-io.com">Magician-Scanning</a> ·
    <img src="https://img.shields.io/badge/licenes-MIT-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/jdk-8+-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/maven-3.5.4+-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/release-master-brightgreen.svg"/>
</h1>

Magician-Scanning is a toolkit for scanning blockchains developed in Java, which can come in handy when we need some functionality in our programs, for example.

- When an address receives ETH, a method in the program is automatically triggered and this transaction is passed into the method.

- When a function of a contract is called (like ERC20 transfer), it automatically triggers a method in the program and passes this transaction to this method. It can even be triggered only when tokens are transferred to a specified address.

- This toolkit can also be used when a program needs to keep a record of all transactions since the beginning of a block height.

It is planned to support three chains, ETH (BSC, POLYGON, etc.), SOL and TRON

## Running environment

JDK8+

## Documentation

[https://magician-io.com/chain](https://magician-io.com/chain)

## Example

### Importing dependencies
```xml
<dependency>
    <groupId>com.github.yuyenews</groupId>
    <artifactId>Magician-Scanning</artifactId>
    <version>1.0.11</version>
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
                        InputDataFilter.builder()
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
        .setRpcUrl(
                EthRpcInit.create()// Set multiple addresses, polling policy will be used automatically to do load balancing
                    .addRpcUrl("https://data-seed-prebsc-1-s1.binance.org:8545")
                    .addRpcUrl("https://data-seed-prebsc-2-s1.binance.org:8545")
                    .addRpcUrl("https://data-seed-prebsc-1-s2.binance.org:8545")
        )
        .setScanPeriod(5000)
        .setBeginBlockNumber(BigInteger.valueOf(24318610))
        .addEthMonitorEvent(new EventOne())
        .addEthMonitorEvent(new EventThree())
        .addEthMonitorEvent(new EventTwo())
        .start();
```

### ETH InputData Codec

```java
// Encode the function as inputData
String inputData = EthAbiCodec.getInputData("mint",
    new Address("0xqwasdasd"),
    new Utf8String("https://asdasdasdsadasd.json")
);

// Get the function's signature
String funcCode = EthAbiCodec.getFunAbiCode("mint",
    new Address("0xqwasdasd"),
    new Utf8String("https://asdasdasdsadasd.json")
);

// Decode inputData into raw data
List<Type> result = EthAbiCodec.decoderInputData("0xasdasdas00000000adasd",
    new TypeReference<Address>(){},
    new TypeReference<Utf8String>(){}
);
```