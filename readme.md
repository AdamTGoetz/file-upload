file uploading web app
=
implement a file uploading web app using open source software & libraries: <br/>
**Build/Deploy** <br/>
Java 8 : <a href="http://openjdk.java.net/" target="_blank">http://openjdk.java.net/</a> | <a href="http://www.oracle.com/technetwork/java/index.html" target="_blank">http://www.oracle.com/technetwork/java/index.html</a> <br/>
Maven : <a href="https://maven.apache.org/" target="_blank">https://maven.apache.org/</a> <br/>
**Web app** <br/>
Spring Boot: <a href="http://projects.spring.io/spring-boot/" target="_blank">http://projects.spring.io/spring-boot/</a> <br/>
Twitter Bootstrap: <a href="http://v4-alpha.getbootstrap.com/" target="_blank">http://v4-alpha.getbootstrap.com/</a> <br/>
jQuery : <a href="http://jquery.com/" target="_blank">http://jquery.com/</a> <br/>
WebJars : <a href="http://www.webjars.org/" target="_blank">http://www.webjars.org/</a> <br/>
**Content-Type Detection** <br/>
Apache Tika : <a href="https://tika.apache.org/" target="_blank">https://tika.apache.org/</a> <br/>
**Helper libraries** <br/>
Google Guava : <a href="https://github.com/google/guava" target="_blank">https://github.com/google/guava</a> <br/>
Apache Commons-IO : <a href="https://commons.apache.org/proper/commons-io/" target="_blank">https://commons.apache.org/proper/commons-io/</a> <br/>
**Testing** <br/>
JUnit : <a href="http://junit.org/junit4/" target="_blank">http://junit.org/junit4/</a> <br/>
**Documentation/Guidelines** <br/>
OWASP - Unrestricted File Upload : <a href="https://www.owasp.org/index.php/Unrestricted_File_Upload" target="_blank">https://www.owasp.org/index.php/Unrestricted_File_Upload</a> <br/>

#### Settings
1. Clone this repository
```
git clone git remote add origin https://github.com/AdamTGoetz/file-upload.git
```

2. Review src/main/resources/application.properties file before building

> server.port=8090 <br/>
> spring.http.multipart.max-file-size=3MB <br/>
> spring.http.multipart.max-request-size=3MB <br/>
> temp.path=/tmp/unsafe <br/>
> file.path=/tmp/safe <br/>
> logging.level.org.springframework=INFO <br/>
> logging.level.com.goetz=DEBUG <br/>
where:
- *server.port* is the port number the embedded Tomcat server will be listening on.
- *spring.http.multipart.max-file-size* and *spring.http.multipart.max-request-size* set the maximum accepted file size.
- *temp.path* and *file.path* are the locations paths where uploaded files will be stored. **Important** If you are planning to run this web app in a Windows machine, the back-slash \ has to be escaped with double slashes //
- *logging.level.org.springframework* and *logging.level.com.goetz* are just logback levels.

3.- Run a mvn clean install to see if everything is OK including the tests.
```
mvn clean install
```

#### Run & Deploy
```
mvn spring-boot:run
```
#### Open the web app in your browser

<a href="http://localhost:8090/" target="_blank">http://localhost:8090/</a>

#### Additional Testing
Use the /src/test/resources files to perform some *curl* back-end testing
```
curl -i -X POST -F file=@fake-csv-it-is-a-png-file.csv http://localhost:8090/upload
```
produces a 400 Bad Request response because fake-csv-it-is-a-png-file.csv is a PNG image file with a fake extension:
```
HTTP/1.1 100

HTTP/1.1 400
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Thu, 27 Jul 2017 19:11:03 GMT
Connection: close

{"virusFree":true,"failed":true,"validFileExtension":true,"validContentType":false}
```
- - - -
```
curl -i -X POST http://localhost:8090/upload
```
produces a 415 Unsupported media type because there is no file:
```
HTTP/1.1 415
Accept: multipart/form-data
Content-Length: 0
Date: Thu, 27 Jul 2017 19:13:29 GMT
```
- - - -
```
curl -i -X POST -F file=@libre-office-csv-file.csv http://localhost:8090/upload
```
produces a 200 OK response code because libre-office-csv-file.csv is a valid virus-free file:
```
HTTP/1.1 100

HTTP/1.1 200
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Thu, 27 Jul 2017 19:17:36 GMT

{"virusFree":true,"failed":false,"validFileExtension":true,"validContentType":true}
```
- - - -
```
curl -i -X POST -F file=@FL_insurance_sample.csv http://localhost:8090/upload
```
produces a 413 Payload too large response code because FL_insurance_sample.csv size' is bigger than 3Mb:
```
HTTP/1.1 100

HTTP/1.1 413
Content-Type: application/json;charset=UTF-8
Transfer-Encoding: chunked
Date: Thu, 27 Jul 2017 19:18:52 GMT
Connection: close

{"validFileSize":false,"failed":true}
```

