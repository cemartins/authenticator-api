#!/usr/bin/env bash

WIREMOCK_DIR="./wiremock"
java -cp $WIREMOCK_DIR/wiremock-standalone-2.23.2.jar com.github.tomakehurst.wiremock.standalone.WireMockServerRunner \
--verbose \
--root-dir $WIREMOCK_DIR/stub \
--port 8081 \
--https-port 8444 \
--https-require-client-cert \
--https-keystore $WIREMOCK_DIR/jks/wiremock-keystore.jks \
--keystore-password changeit \
--https-truststore $WIREMOCK_DIR/jks/wiremock-truststore.jks \
--truststore-password changeit
