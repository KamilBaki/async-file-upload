# async-file-upload
This is my inital attempt to create asynchronous file upload using Java 8 leveraging Spring. This is also my first project in GitHub.

This project is developed using Cloud IDE Codenvy with gradle. This is the first time I am using this technologies and tools.

Kudos goes to Spring because I am developing this project using their technologies, docs and guides, especially https://spring.io/guides/gs/uploading-files/

# Requirements, Building, and Running

Requires Java 8 and Gradle.

From inside the project directory (async-file-upload), execute the following command:
<pre>
    <b>gradle build && java -jar build/libs/async-file-upload-0.1.0.jar</b>
</pre>

Open your browser at http://hostname:8080 (usually localhost)

To use different port, use either the following methods:

1. Using property,
<pre>
    java -jar build/libs/async-file-upload-0.1.0.jar **--server.port=9090**
</pre>
2. Using system property,
<pre>    
    java **-Dserver.port=9090** -jar build/libs/async-file-upload-0.1.0.jar
</pre>
3. Using OS Environment Variable,
<pre>    
    Windows, 
            <b>SET SERVER_PORT=9090</b>
             java -jar build/libs/async-file-upload-0.1.0.jar

    UNIX,    
            <b>SERVER_PORT=9090</b> java -jar build/libs/async-file-upload-0.1.0.jar
</pre>
4. Using properties or yaml configuration file,
<pre>    
   ./application.properties
     <b>server.port=9090</b>
    
    ./application.yaml
      <b>server:
         port: 9090</b>
</pre>

<b>*</b>For full methods to configure the http port, please consult Spring Boot handbook.

The web interface contains 2 file upload forms, normal Upload (Spring MVC Controller - POST /) and Async Upload (Jersey JAX-RS - POST /rest/file/upload). 

Tags: Spring Boot, Jersey, JAX-RS

# Todos
1. Make the file upload asynchronous.                    - DONE
2. Create unit & integration tests.
3. Create a simple command line client to upload file.
  * Separate projects - server & client
