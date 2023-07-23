FROM openjdk:11-jdk-oracle

WORKDIR /checkout-orchestrator

COPY /build/libs/checkout-orchestrator-0.0.2-SNAPSHOT.jar /checkout-orchestrator/lib/checkout-orchestrator-0.0.2-SNAPSHOT.jar

EXPOSE 8081

VOLUME [ "/checkout-orchestrator/lib" ]

CMD ["java", "-jar", "/checkout-orchestrator/lib/checkout-orchestrator-0.0.2-SNAPSHOT.jar"]

