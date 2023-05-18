# Example ssl keystore reloading with spring-boot and tomcat

You can use either

`mvn clean spring-boot:run` 

or for example

```
mvn clean package && 
cp target/spring-boot-initial-0.0.1-SNAPSHOT.jar . && 
java -jar spring-boot-initial-0.0.1-SNAPSHOT.jar
```

for running.

## Check the certificate enddate

You can check the datetime of the current in use certificate with

`openssl s_client -servername localhost -connect localhost:8443 2>/dev/null | openssl x509 -noout -dates`

## Testing switching keystore

In application.properties, the keystore is set to be in `/tmp/cert/webservice_localhost-keystore.pkcs12`.
You can change the file on the fly and then test if the certificate was changed (see above).

```
mv /tmp/cert/webservice_localhost-keystore.pkcs12 /tmp/cert/webservice_localhost-keystore.pkcs12-1 && 
mv /tmp/cert/webservice_localhost-keystore-3.pkcs12 /tmp/cert/webservice_localhost-keystore.pkcs12
```

## See create-sertificate/README.md to create more sertificates/keystores