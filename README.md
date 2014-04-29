spring-boot-glass
======

Google Glass Mirror API service that pushes text to subscriber timelines. Built on Spring Boot.

The following two VM arguments must be set for the application to boot:

-Doauth.google.clientid="id"
-Doauth.google.clientsecret="secret"

These OAuth 2.0 API access values can be obtained from Google's API console at: https://code.google.com/apis/console/ 
See this tutorial for additional details: https://developers.google.com/glass/develop/mirror/quickstart/java/
