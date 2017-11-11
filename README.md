# Footballer
Footballer is an app which pulls data from the NFL website in order to create analytical season rankings.

## Screenshots
![Screenshot 1](https://i.imgur.com/DB219qw.png "Screenshot 1")
![Screenshot 2](https://i.imgur.com/pxfzf16.png "Screenshot 2")
![Screenshot 3](https://i.imgur.com/RMjC66M.png "Screenshot 3")

## Dependencies
### Required dependencies
Footballer requires the following dependencies to be already installed: 
- [Maven](https://maven.apache.org/): the Java build tool
- [Node.js](https://nodejs.org/): the runtime for React and other JavaScript libraries required by Footballer
- [NPM](https://www.npmjs.com/): the package manager for managing Footballer's frontend dependencies

### Maven dependencies
Footballer depends on certain packages from [Maven Central](https://search.maven.org/) which are automatically downloaded and installed by Maven. The dependencies are as follows:
- [Spark](http://sparkjava.com/): the web server
- [SLF4J](https://slf4j.org/): simple logging for Java
- [Gson](https://github.com/google/gson): JSON library for Java

## Running
To run the Footballer server, do the following:
1. Install the required dependencies
2. `mvn clean install` to create the runnable JAR
3. `java -jar target/footballer-1.0.jar` to run the JAR