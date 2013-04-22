# es-dms #

Test web app using AngujarJS, Jersey and ElasticSearch

## How to compile and run ##

- Run: mvn clean install
- Run: es-dms-site\mvn clean tomcat7:run
- Open browser: http://localhost:8080/es-dms-site

## Project structure ##

This project contains 3 modules:

1. es-dms-core

  This module contains the object model and service interfaces

2. es-dms-service

  This module contains the services implemented for Elasticsearch

3. es-dmi-site

  This module contains the web application. It is using AngularJS as Javascript MVVM framework. REST services are provided using Jersey.

## Resources ##
* [AngularJS] (http://angularjs.org/)
* [Elasticsearch] (http://www.elasticsearch.org/)
* [Jersey] (https://jersey.java.net/)
