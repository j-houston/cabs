**CABS**

9/18/2020

*SWAGGER*

Automatically generated from Springfox

`http://localhost/swagger-ui.html#/`


*OPTIONAL DB SETUP*
1. Using Postgres, but need to install geospatial libraries on the DB before use
1. I used https://postgresapp.com/ to create a local postgres server
1. I used https://www.pgadmin.org/ to create a DB 'guild' on that server
1. Once the server is created, I installed the postgis extensions:
    1. In pgadmin4 click "Query Tool"
    1. Enter "CREATE EXTENSION postgis;" and run (expect "Query returned successfully in 1 secs 389 msec.")
    1. Test with:
     
   ` -- Expect 3756.14 as a result
    select
    ST_DistanceSphere(ST_SetSRID(ST_MakePoint(-105.27061109698288, 40.015608833518925, 0), 4326), ST_SetSRID(ST_MakePoint(-105.27668403091766, 40.04906706154871, 0.0), 4326))`
    
    

