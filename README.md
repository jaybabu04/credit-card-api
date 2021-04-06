# credit-card-api

Simple Api to add and view credit-card information and used Luhn algorithm to validate the card number.

App URL
-------

http://localhost:8088/api/v1/credit-card/

Swagger URL
-----------

http://localhost:8088/swagger-ui/index.html

Authentication
---------------

Api requrired basic authentication.

Method:
--------

1.POST /api/v1/credit-card -save card informations
2.GET /api/v1/credit-card -get all card informations

Supported Content Type
----------------------

application/json

Schemas
---------

CardInfo{ name* string cardNumber string creditlimit number($double) balance number($double) createdDateTime string($date-time) updatedDateTime string($date-time) createdBy string updatedBy string }

To build Docker image

mvn spring-boot:build-image
