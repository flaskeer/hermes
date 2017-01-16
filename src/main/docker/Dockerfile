FROM java:openjdk-8-jre-alpine
ENV APP_SLEEP 0
ADD *.war /app.war

RUN sh -c 'touch /app.war'
VOLUME /tmp
EXPOSE 8080
CMD echo "The application will start IN ${APP_SLEEP}s..." && \
     sleep ${APP_SLEEP} && \
     java -Djava.security.egd=file:/dev/./urandom -jar /app.war
