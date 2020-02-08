FROM java:8
VOLUME /tmp
ADD springBootJavaKotlin.jar app.jar
RUN bash -c 'touch /app.jar'
ENTRYPOINT ["java","-jar","/app.jar"]