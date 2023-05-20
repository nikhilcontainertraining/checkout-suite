FROM openjdk:11-jdk-oracle
WORKDIR /payment-data-services
COPY /build/libs/payment-data-services-0.0.1-SNAPSHOT.jar /payment-data-services/lib/payment-data-services-0.0.1-SNAPSHOT.jar
VOLUME [ "/payment-data-services/lib" ]
CMD ["java","-jar","/payment-data-services/lib/payment-data-services-0.0.1-SNAPSHOT.jar"]