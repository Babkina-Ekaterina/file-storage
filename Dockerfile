FROM openjdk:17
VOLUME /tmp
EXPOSE 8080
COPY target/file-storage-0.0.1-SNAPSHOT.jar file-storage.jar
ENTRYPOINT ["java","-jar","/file-storage.jar"]
