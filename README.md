# A basic Alfresco serach webscript

## web scripts home

http://localhost:8080/alfresco/service/index
http://localhost:8080/alfresco/service/demo/simplesearch

## compile and deploy

```
hyland_javac -f java_webscripts02
```

or use the officially documented method.

## java webscripts docs

https://hub.alfresco.com/t5/alfresco-content-services-hub/java-backed-web-scripts-samples/ba-p/290666


web-scripts-application-context.xml - <Alfresco>/tomcat/webapps/alfresco/WEB-INF/classes/alfresco
simple.get.desc.xml  - <Alfresco>/tomcat/webapps/alfresco/WEB-INF/classes/alfresco/templates/webscripts/org/alfresco/demo


shared/classes/extension. 

Alfresco will pick up anything that ends in -context.xml.


Web Scripts are a way of implementing REST-based API. They could also be referred to as Web Services. They are stateless and scale extremely well. Repository Web Scripts are defined in XML, JavaScript, and FreeMarker files and stored under 

```
alfresco/extension/templates/webscripts 
```

The simplest Web Script you can write consists of a descriptor and a template.






# try it out

   http://localhost:8080/alfresco/service/demo/simplesearch

