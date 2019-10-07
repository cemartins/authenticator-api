# authenticator-api

Swagger file
------------

https://localhost:8084/v2/api-docs

Swagger-UI
----------

https://localhost:8084/swagger-ui.html

Register
--------

curl -v --cacert conf/ca.pem --cert conf/pkey.pem --key conf/pkey.key --pass changeit -d '{"username":"mememe", "password":"password", "account":"77853449"}' -H "Content-Type: application/json" -X POST https://localhost:8084/register

Authenticate
------------

curl -v --cacert conf/ca.pem --cert conf/pkey.pem --key conf/pkey.key --pass changeit -d '{"username":"mememe", "password":"password"}' -H "Content-Type: application/json" -X POST https://localhost:8084/authenticate


References
----------

- JWT https://dzone.com/articles/spring-boot-security-json-web-tokenjwt-hello-world
- JWT https://dev.to/keysh/spring-security-with-jwt-3j76
- DUAL TLS https://medium.com/@niral22/2-way-ssl-with-spring-boot-microservices-2c97c974e83
- Swagger_2 https://springframework.guru/spring-boot-restful-api-documentation-with-swagger-2

