FROM openjdk:11
EXPOSE 8082
CMD ["edumanagement2022/edu-backend:v2.0"]
ADD target/edumanagement.jar edumanagement.jar
ENTRYPOINT ["java","-jar","edumanagement.jar"]

FROM edumanagement2022/edu-backend:v2.0
CMD mkdir /edu-application/images
COPY static/img /edu-application/images