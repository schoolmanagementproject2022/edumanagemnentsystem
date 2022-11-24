FROM openjdk:11
EXPOSE 8082
ADD target/edumanagement.jar edumanagement.jar
ENTRYPOINT ["java","-jar","edumanagement.jar"]
WORKDIR /usr/edumanagemnentsystem
RUN mkdir images
COPY ./ ./