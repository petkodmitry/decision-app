# Pull base image
FROM tomcat:jdk17

# Copy to images tomcat path
RUN rm -rf /usr/local/tomcat/webapps/ROOT
ADD target/petko.war /usr/local/tomcat/webapps/ROOT.war
