FROM tomcat:10.0.5-jdk8-openjdk
MAINTAINER test

EXPOSE 8080

RUN rm -rf /usr/local/tomcat/webapps/*

COPY ./target/timetraveling-microservice2-1.0-SNAPSHOT.war /usr/local/tomcat/webapps/ROOT.war

CMD ["catalina.sh","run"]