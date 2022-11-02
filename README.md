<h1> 
    <a href="https://magician-io.com">Magician-scanning</a> ·
    <img src="https://img.shields.io/badge/licenes-MIT-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/jdk-11+-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/maven-3.5.4+-brightgreen.svg"/>
    <img src="https://img.shields.io/badge/release-master-brightgreen.svg"/>
</h1>

Magician-scanning is a blockchain scanner that can easily scan transactions on the blockchain, 
and can monitor and process the balance of the specified address, 
account changes, and other events through simple configuration. It supports ETH, SOL, TRON three chains

## Running environment

JDK8+

## Documentation

[https://magician-io.com](https://magician-io.com)

## Example

### Importing dependencies
```xml
<dependency>
    <groupId>com.github.yuyenews</groupId>
    <artifactId>Magician</artifactId>
    <version>2.0.6</version>
</dependency>

<!-- This is the logging package, you must have it or the console will not see anything, any logging package that can bridge with slf4j is supported -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-jdk14</artifactId>
    <version>1.7.12</version>
</dependency>
```

### Creating an http service

Create a Handler

```java
@HttpHandler(path="/")
public class DemoHandler implements HttpBaseHandler {

    @Override
    public void request(MagicianRequest magicianRequest, MagicianResponse response) {
        // response data
        magicianRequest.getResponse()
                .sendJson(200, "{'status':'ok'}");
    }
}
```

Start the http service

```java
Magician.createHttp()
    .scan("handler所在的包名")
    .bind(8080);
```

### Creating WebSocket

```java
@WebSocketHandler(path = "/websocket")
public class DemoSocketHandler implements WebSocketBaseHandler {
   
    @Override
    public void onOpen(WebSocketSession webSocketSession) {
     
    }
   
    @Override
    public void onClose(WebSocketSession webSocketSession) {
        
    }

    @Override
    public void onMessage(WebSocketSession webSocketSession, byte[] message) {

    }
}
```