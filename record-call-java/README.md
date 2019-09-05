# Recording API client example in Java

Example demonstrate how to start/stop recording for specific call using TENIOS API

Requirements:
- Java 7 (or higher version)
- Maven 3 (https://maven.apache.org/install.html)

1. Go to `record-call-java/src/main/java/teniosgmbh/recording` directory.
2. Edit RecordingTestClient.java file and replace `ACCESS_KEY` key and `CALL_UUID`
with real API key and Call UUID. Save the file.
3. Go back to `record-call-java` directory and run `mvn clean package`;
4. Go to `record-call-java/target` directory
5. Run `java -jar record-call-java-v26-jar-with-dependencies.jar`


