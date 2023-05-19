FROM gradle

WORKDIR /checkout-orchestrator

COPY /build/libs/checkout-orchestrator-0.0.1-SNAPSHOT.jar /checkout-orchestrator/lib/checkout-orchestrator-0.0.1-SNAPSHOT.jar

EXPOSE 8081

VOLUME [ "/checkout-orchestrator/lib" ]

CMD ["java", "-jar", "/checkout-orchestrator/lib/checkout-orchestrator-0.0.1-SNAPSHOT.jar"]

