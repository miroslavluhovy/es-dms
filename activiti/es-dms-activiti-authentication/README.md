How to install SSO between es-dms and Activiti
----------------------------------------------

Tested with Activiti 5.13

- Deploy activiti-explorer.war and activiti-rest.war in Tomcat 7
- Stop Tomcat
- Remove the war files but keep the folders activiti-explorer and activiti-rest

Copy ```target/es-dms-activiti-authentication-1.0-SNAPSHOT.jar``` in webapps/activiti-explorer/WEB-INF/lib and webapps/activiti-rest/WEB-INF/lib

Use ```mvn dependency:copy-dependencies``` to get the jar files from ```target/dependency``` folder

Dependencies to be copied in $TOMCAT_HOME\lib
```
guava-13.0.1.jar
```

Dependencies to be copied in webapps/activiti-explorer/WEB-INF/lib and webapps/activiti-rest/WEB-INF/lib
```
cglib-2.2.0-b14.jar
classmate-0.8.0.jar
es-dms-core-1.0-SNAPSHOT.jar
es-dms-rest-client-1.0-SNAPSHOT.jar
hibernate-validator-5.0.1.Final.jar
hk2-api-2.2.0-b14.jar
hk2-locator-2.2.0-b14.jar
hk2-utils-2.2.0-b14.jar
jackson-annotations-2.2.2.jar jars
jackson-core-2.2.2.jar
jackson-databind-2.2.2.jar jars
jackson-jaxrs-base-2.2.2.jar
jackson-jaxrs-json-provider-2.2.2.jar
jackson-module-jaxb-annotations-2.2.2.jar
javax.annotation-api-1.2.jar
javax.inject-2.2.0-b14.jar
javax.el-2.2.4.jar
javax.ws.rs-api-2.0.jar
jboss-logging-3.1.1.GA.jar
jersey-client-2.2.jar
jersey-common-2.2.jar
jersey-media-multipart-2.2.jar
jersey-server-2.2.jar
mimepull-1.9.3.jar
validation-api-1.1.0.Final.jar
```

If required changes settings in ```install/webapps/activiti-rest/WEB-INF/classes/es-dms.properties``` and in ```install/webapps/activiti-explorer/WEB-INF/classes/es-dms.properties```

- Copy and replace install/webapps in $TOMCAT_HOME/webapps

- Restart Tomcat

Validate the integration from cUrl:
----
```
curl -XGET --user admin:secret http://localhost:18080/activiti-rest/service/repository/process-definitions?category=es-dms
```
