#/bin/bash

#Get account details
curl -v --cacert conf/ca.pem --cert conf/pkey.pem --key conf/pkey.key --pass changeit https://localhost:8444/accounts/77853449

# Register new a  ccount
curl -v --cacert conf/ca.pem --cert conf/pkey.pem --key conf/pkey.key --pass changeit -d '{"username":"mememe", "password":"password", "account":"77853449"}' -H "Content-Type: application/json" -X POST https://localhost:8084/register

# Authenticate
curl -v --cacert conf/ca.pem --cert conf/pkey.pem --key conf/pkey.key --pass changeit -d '{"username":"mememe", "password":"password"}' -H "Content-Type: application/json" -X POST https://localhost:8084/authenticate
