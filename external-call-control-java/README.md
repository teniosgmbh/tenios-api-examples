# External Call Control in Java

Starts a server that accepts POSTs from TENIOS, which you can configure in your routing plans from within the TENIOS portal, using
"API Call Control" blocks.

Requirements:
- Java 7 (or higher version)
- Maven 3 (https://maven.apache.org/install.html)

## Test run:

1. Go to the `external-call-control-java/` directory and run `mvn clean package -DskipTests`;
2. Run `java -jar target/external-call-control-java-17-jar-with-dependencies.jar "https://localhost:8333/test"`
3. Keep the process running, and open another terminal
4. In that new terminal, go to the `external-call-control-java/` and run `mvn test`.
   This test should send requests to the java server we just started, and will hopefully result in BUILD SUCCESS.

## Adjusting for production use

### Approach 1: Using the Java server behind a web server

This is the easier approach of the two. Maybe your company has already set up an HTTPS capable web server (like e.g. Apache httpd or nginx)
which is already configured to handle HTTPS (SSL/TLS) traffic.

In this case, you can simply run the Java server with an `http:` url, e.g.

`java -jar target/external-call-control-java-17-jar-with-dependencies.jar "http://192.168.0.100/somepath"`

(Assuming, that 192.168.0.100 is the IP of our machine).

Then somebody in your company will have to set up the HTTP(s) server to forward e.g. `https://mycompany.com/somepath` to `http://192.168.0.100/somepath`.

Then, in the TENIOS customer portal, you would use `https://mycompany.com/somepath/externalpost` in your API Routing Control block.


## Approach 2: Setting up HTTPS without a web server

This approach is more difficult, but if you don't have an HTTPS cabable web server available, you will have to set up SSL/TLS handling directly in the Java server.

Our example already contains the necessary code, but you MUST replace the example keystore (`src/main/resources/keystore_server_example) with your own keystore.

Before you start, you will have to obtain a valid SSL/TLS certificate and key file, which can be used for your domain (e.g. mycompany.com).

There are several examples on the web which explain how to transform these files a keystore (how to do it exactly depends on which format your SSL/TLS certificate and key have).
You could try e.g. https://stackoverflow.com/a/17710626/291741

**IMPORTANT: Make sure to keep your keystore and server key safe at all times!**

After you're done with that, please adjust `teniosgmbh.externalcallcontrol.MyExternalCallControlServer` to use your keystore file, key store password and key password.

Now you can run the Java server with a `https:` URL like

`java -jar target/external-call-control-java-17-jar-with-dependencies.jar https://mycompany.com/somepath`.

Then, in the TENIOS customer portal, you would use `https://mycompany.com/somepath/externalpost` in your API Routing Control block.


### How the keystore_server_example and truststore_client_example were created

These stores are only for testing purposes. But if you want to create a test keystore with a self-signed certificate for yourself (not for production use with TENIOS), you can follow these steps:

````
keytool -genkeypair -keystore keystore_server_example -keyalg RSA -alias serverCert -dname "CN=localhost" -storepass 123456 -keypass changeme
keytool -exportcert -keystore keystore_server_example -alias serverCert -storepass 123456 -file server_example.cer
keytool -importcert -keystore truststore_client_example -alias serverCert -storepass 123456 -file server_example.cer -noprompt```