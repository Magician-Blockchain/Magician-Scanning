<h1> 
    <a href="https://magician-io.com">Magician-web3</a> ·
    <img src="https://img.shields.io/badge/licenes-MIT-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/jdk-11+-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/maven-3.5.4+-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/release-master-brightgreen.svg"/>
</h1>

Magician-web3 is a blockchain development kit. 
It consists of two functions. It scans the blockchain to monitor transactions based on the needs of developers. 
It does some secondary encapsulation of web3j, which can reduce the workload of developers in some common scenarios. 
Plans to support ETH (BSC, XSC, POLYGAN, etc.), SOL, TRON three chains

## Running environment

JDK8+

## Documentation

[https://magician-io.com](https://magician-io.com)

## Example

### Importing dependencies
```xml
<dependency>
    <groupId>com.github.yuyenews</groupId>
    <artifactId>Magician-web3</artifactId>
    <version>1.0.0</version>
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
/**
 * Create an implementation class for EthMonitorEvent
 */
public class EthMonitorEventImpl implements EthMonitorEvent {

    /**
     * set filters
     *
     * When a qualified transaction is scanned, the call method will be triggered
     * @return
     */
    @Override
    public EthMonitorFilter ethMonitorFilter() {
        return EthMonitorFilter.builder()
                .setTokenType(TokenType.ERC20)
                .setContractAddress("0xasdasdasdasdasdasdasdasdas")
                .setFunctionCode("0x1s8d5j6j");
    }

    /**
     * Filter the transaction data according to the above conditions, and execute the monitoring event
     * @param transactionModel
     */
    @Override
    public void call(TransactionModel transactionModel) {

    }
}
```

Start a monitoring task

```java
// Set the number of threads to be consistent with the number of tasks
EventThreadPool.init(1);

// Start a scan task
// If you want to open multiple scanning tasks, 
// you can execute this code multiple times. 
// If you want to scan multiple chains at the same time, 
// you can do the same operation, 
// but you need to modify the ChainType to the name of the corresponding chain
MagicianBlockchainScan.create()
        .setRpcUrl("https://bsc-dataseed.binance.org/")
        .setChainType(ChainType.ETH)
        .setScanSize(1000)
        .setScanPeriod(5000)
        .setBeginBlockNumber(BigInteger.valueOf(1))
        .addEthMonitorEvent(new EthMonitorEventImpl())
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

// ------------------------ more ----------------------

Web3j web3j = Web3j.build(new HttpService(""));

// If you are interested, you can visit the official website
EthHelper ethHelper = MagicianWeb3.getEthBuilder().getEth(web3j, "private key");

// If you are interested, you can visit the official website
EthContract ethContract = MagicianWeb3.getEthBuilder().getEthContract(web3j, "private key");
```