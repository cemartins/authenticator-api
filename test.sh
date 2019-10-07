#/bin/bash

curl -v --cacert conf/ca.pem --cert conf/pkey.pem --key conf/pkey.key --pass changeit https://localhost:8444/accounts/swagger-ui.html
#curl -v --cacert conf/ca.pem --cert conf/pkey.pem --key conf/pkey.key --pass changeit https://localhost:8444/accounts/Stranger