# Test stage
# For build and test, get an image that has gradle installed
FROM gradle:6.2.2-jdk8 AS TEMP_BUILD_IMAGE
LABEL application=cabs

# Copies the Java source code inside the container
COPY --chown=gradle:gradle . /home/gradle/src
WORKDIR /home/gradle/src

# Compiles the code and runs unit tests using the build task
RUN gradle build --no-daemon --info

# Release stage
# Discards the Gradle image with all the compiled classes/unit test results etc.
FROM openjdk:8-jdk-alpine
LABEL application=flexshare-api

# Opens the Tomcat port
EXPOSE 8080

# We added a VOLUME pointing to "/tmp" because that is where a Spring Boot application
# creates working directories for Tomcat by default. The effect is to create a
# temporary file on your host under "/var/lib/docker" and link it to the container
# under "/tmp". This step is optional for the simple app that we wrote here, but can
# be necessary for other Spring Boot applications if they need to actually write in
# the filesystem.
VOLUME /tmp

# Gradle will create the output API in the libs folder. Copy them to the image
COPY --from=TEMP_BUILD_IMAGE /home/gradle/src/build/libs/*.jar cabs.jar
COPY --from=TEMP_BUILD_IMAGE /home/gradle/src/build ./

# Add useful things for debugging
RUN apk update
RUN apk add curl

RUN env
ENTRYPOINT ["java","-Djava.awt.headless=true","-jar","/cabs.jar"]
