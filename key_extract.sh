#!/usr/bin/env bash

KEYSTORE_PATH=""
KEYSTORE_PASS=""
KEYSTORE_KEY_PASS=""
CERT_ALIAS=""
OUTPUT_NAME=""

function print_help {
    echo "USAGE: key_extract.sh --storepath PATH_TO_JKS --storepass JKS_PASSWORD --keypass PRIVATE_KEY_PASSWORD --certalias CERTIFICATE_ALIAS --outname OUTPUT_NAME"
}

function check_required_parameter() {
    if [ -z "$2" ]; then
        echo "The '$1' parameter must be provided!"
        print_help
        exit 1
    fi
}

if [ $# -eq 0 ]; then
    print_help
    exit 1
fi

POSITIONAL=()
while [[ $# -gt 0 ]]
do
key="$1"

case $key in
    --storepath)
    KEYSTORE_PATH="$2"
    shift # past argument
    shift # past value
    ;;
    --storepass)
    KEYSTORE_PASS="$2"
    shift # past argument
    shift # past value
    ;;
    --keypass)
    KEYSTORE_KEY_PASS="$2"
    shift # past argument
    shift # past value
    ;;
    --certalias)
    CERT_ALIAS="$2"
    shift # past argument
    shift # past value
    ;;
    --outname)
    OUTPUT_NAME="$2"
    shift # past argument
    shift # past value
    ;;
    *)    # unknown option
    POSITIONAL+=("$1") # save it in an array for later
    shift # past argument
    ;;
esac
done
set -- "${POSITIONAL[@]}" # restore positional parameters

check_required_parameter "--storepath" $KEYSTORE_PATH
check_required_parameter "--storepass" $KEYSTORE_PASS
check_required_parameter "--keypass" $KEYSTORE_KEY_PASS
check_required_parameter "--certalias" $CERT_ALIAS
check_required_parameter "--outname" $OUTPUT_NAME

keytool -export \
-storetype JCEKS \
-alias $CERT_ALIAS \
-file $OUTPUT_NAME.der \
-keystore $KEYSTORE_PATH \
-storepass $KEYSTORE_PASS

openssl x509 \
-inform der \
-in $OUTPUT_NAME.der \
-out $OUTPUT_NAME.pem

keytool -importkeystore \
-srcstoretype JCEKS \
-srckeystore $KEYSTORE_PATH \
-srcalias $CERT_ALIAS \
-srcstorepass $KEYSTORE_PASS \
-srckeypass $KEYSTORE_KEY_PASS \
-destkeystore $OUTPUT_NAME.p12 \
-deststoretype PKCS12 \
-deststorepass $KEYSTORE_KEY_PASS \
-destkeypass $KEYSTORE_KEY_PASS \
-noprompt

openssl pkcs12 \
-in $OUTPUT_NAME.p12  \
-nodes \
-nocerts \
-out $OUTPUT_NAME.key \
-password pass:$KEYSTORE_KEY_PASS

rm $OUTPUT_NAME.der
rm $OUTPUT_NAME.p12
