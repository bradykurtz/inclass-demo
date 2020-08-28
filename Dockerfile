FROM tomcat:9

COPY target/ROOT.war /usr/local/tomcat/webapps/ROOT.war

EXPOSE 8080

WORKDIR /usr/local/tomcat/bin

CMD ["catalina.sh", "run"]