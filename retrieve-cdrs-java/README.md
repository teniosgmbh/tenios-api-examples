# Retrieve CDR example in Java

Requirements:
- Java 7(or higher version)
- Maven 3(https://maven.apache.org/install.html)

1. Go to `retrieve-cdrs-java/src/main/java/teniosgmbh/retrievecdrs` directory.
2. Edit `RetrieveCdrsTestClient.java` file and replace `ACCESS_KEY` key 
with real API key and your data. Save the file.
3. Go back to `retrieve-cdrs-java` directory and run `mvn clean package`;
4. Go to `retrieve-cdrs-java/target directory`
5. Run `java -jar retrieve-cdrs-java-v17-jar-with-dependencies.jar`