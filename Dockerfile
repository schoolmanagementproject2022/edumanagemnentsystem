FROM openjdk:11
EXPOSE 8082
WORKDIR /usr/edumanagementsystem
ADD target/edumanagement.jar edumanagement.jar
ENTRYPOINT ["java","-jar","edumanagement.jar"]
RUN mkdir "images"