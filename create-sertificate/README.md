# Create certificate

`source env.sh`

## Create the CA key.

`openssl genrsa -des3 -passout pass:$CA_CERT_PASSWORD -out ca_key.pem 4096`

## Create the CA certificate.

`openssl req -x509 -new -nodes -key ca_key.pem -passin pass:$CA_CERT_PASSWORD -sha256 -days 1825 -out ca_cert.pem \
  -subj "/C=FI/ST=Helsinki/L=Helsinki/O=localhost AG/CN=ca"`

## Create the server key.

`openssl genrsa -des3 -passout pass:$SERVER_CERT_PASSWORD -out ${SERVER_CERT_CN}_key.pem 2048`

## Create the server certificate signing request.

`openssl req -new -key ${SERVER_CERT_CN}_key.pem -passin pass:$SERVER_CERT_PASSWORD -out ${SERVER_CERT_CN}.csr \
  -subj "/CN=$SERVER_CERT_CN"`

## Create server certificate extension configuration.

```
cat > ./${SERVER_CERT_CN}.cnf <<EOT
authorityKeyIdentifier=keyid,issuer
keyUsage=digitalSignature
extendedKeyUsage=serverAuth
subjectAltName = @alt_names

[alt_names]
DNS.1 = $SERVER_ALT_NAME
EOT
```

## Sign the server certificate signing request.

`openssl x509 -req -in ${SERVER_CERT_CN}.csr -CA ca_cert.pem -CAkey ca_key.pem -passin pass:$CA_CERT_PASSWORD -CAcreateserial \
  -out ${SERVER_CERT_CN}_cert.pem -days 1825 -sha256 -extfile ${SERVER_CERT_CN}.cnf`

[source](https://janikvonrotz.ch/2019/01/21/create-a-certificate-authority-ca-and-sign-server-certificates-without-prompting-using-openssl/)

# Create truststore

## Create the pkcs12 store containing the server cert and the ca trust.

`openssl pkcs12 -in ${SERVER_CERT_CN}_cert.pem -inkey ${SERVER_CERT_CN}_key.pem -passin pass:$SERVER_CERT_PASSWORD -certfile ca_cert.pem \
  -export -out ${APPLICATION_NAME}_${SERVER_CERT_CN}-keystore.pkcs12 -passout pass:$KEYSTORE_PASSWORD -name $SERVER_CERT_CN`

## Show the content of keystore.

`keytool -list -storetype PKCS12 -keystore $APPLICATION_NAME-keystore.pkcs12 \
  -storepass $KEYSTORE_PASSWORD`


## Create a pkcs12 truststore containing the ca cert.

`keytool -importcert -storetype PKCS12 -keystore $APPLICATION_NAME-truststore.pkcs12 \
  -storepass $TRUSTSTORE_PASSWORD -alias ca -file ca_cert.pem -noprompt`

## Show the content of the truststore.

`keytool -list -storetype PKCS12 -keystore $APPLICATION_NAME-truststore.pkcs12 \
  -storepass $TRUSTSTORE_PASSWORD`


[source](https://janikvonrotz.ch/2019/01/22/create-pkcs12-key-and-truststore-with-keytool-and-openssl/)