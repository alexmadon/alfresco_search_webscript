# web scripts home

http://localhost:8080/alfresco/service/index
http://localhost:8080/alfresco/service/demo/simplesearch

# compile

```
hyland_javac -f java_webscripts02
```


# java webscripts

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


# errors


Salut Nicolas, quand tu as une minute: j'essaie de créer un webscript minimal pour reproduire le pb observé par l'Etat de Vaud. Je t'attache mon code en TGZ: il compile mais je n'arrive pas à initialiser le search service, j'ai dans les logs:
025-01-16T11:43:09,625 [] ERROR [madon.tutwebscripts.SimpleSearchWebScript] [http-nio-8080-exec-9] Fail to find nodes 
java.lang.NullPointerException: Cannot invoke "org.alfresco.service.cmr.search.SearchService.query(org.alfresco.service.cmr.search.SearchParameters)" because "this.searchService" is null
(edited)
Binary
 

testws.tgz
Binary






NEW



Nicolas
Nicolas  12:51 PM
il faut que tu rajoutes la property searchService dans ton (edited) 
12:51
Nicolas
```
<bean id="webscript.alexsimplesearch.get" class="net.madon.tutwebscripts.SimpleSearchWebScript" parent="webscript"> </bean>
```


Alex Madon
Alex Madon  12:55 PM
juste comme ça:
12:55
Alex Madon
```
<property name="searchService" ref="SearchService" />
```
12:56
Alex Madon
?


Nicolas
Nicolas  12:56 PM
oui

1



Alex Madon
Alex Madon  1:05 PM

```
SEVERE: Exception sending context initialized event to listener instance of class [org.alfresco.web.app.servlet.CORSContextListener]
org.springframework.beans.factory.BeanCreationException: Error creating bean with name 'webscript.alexsimplesearch.get' defined in file [/home/madon/alfrescoenvs/acs2332pgslr/web-server/shared/classes/alfresco/extension/alexsearch-webscripts-context.xml]: Invalid property 'searchService' of bean class [net.madon.tutwebscripts.SimpleSearchWebScript]: Bean property 'searchService' is not writable or has an invalid setter method. Does the parameter type of the setter match the return type of the getter?
```

1:05
Alex Madon
il faut sans doute un setter dédié


Nicolas
Nicolas  1:05 PM
oui voilà, dans ta classe java


Alex Madon
Alex Madon  1:07 PM
si je mes tous mes services dans:

```
    public void setServiceRegistry(ServiceRegistry serviceRegistry) {
        this.serviceRegistry = serviceRegistry;
        this.nodeService = serviceRegistry.getNodeService();
        this.authorityService = serviceRegistry.getAuthorityService();
        this.searchService = serviceRegistry.getSearchService();
    }
```


Nicolas
Nicolas  1:07 PM
ça marcherait


Alex Madon
Alex Madon  1:08 PM
c'est pas bon si je ne rajoute que

```
<property name="serviceRegistry" ref="ServiceRegistry" /> (edited) 
```


Nicolas
Nicolas  1:08 PM
mais la logique de Spring c'est plutôt d'injecter (et de lister) toutes les dépendances dans le xml

1

1:08
Nicolas
si ça marcherait

1

1:09
Nicolas
serviceRegistry c'est plutôt déprécié ou un workaround
1:10
Nicolas
d'autant que ce n'est pas une bonne pratique de mettre de la logique dans les setteurs


Alex Madon
Alex Madon  1:15 PM
excellent! ça marche et effectivement c'est plus clair de les lister un par un

## error auth

```
2025-01-16T13:15:25,042 [] ERROR [madon.tutwebscripts.SimpleSearchWebScript] [http-nio-8080-exec-2] Fail to find nodes 
net.sf.acegisecurity.AuthenticationCredentialsNotFoundException: A valid SecureContext was not provided in the RequestContext
```




Looks like <authentication>user</authentication> tag was missing in the desc.xml. Now it's working. Thank you very much for the help! 
