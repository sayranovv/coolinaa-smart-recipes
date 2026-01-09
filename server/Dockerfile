FROM eclipse-temurin:21-jdk-alpine AS build
WORKDIR /workspace

COPY mvnw .
COPY .mvn/ .mvn/
COPY pom.xml .
RUN chmod +x mvnw \
 && ./mvnw -q -B -DskipTests dependency:go-offline

COPY src ./src
COPY HELP.md ./HELP.md
RUN ./mvnw -q -B -DskipTests package

FROM eclipse-temurin:21-jre-alpine AS runtime
ENV APP_HOME=/app
WORKDIR ${APP_HOME}

RUN addgroup -S spring && adduser -S spring -G spring

COPY --from=build /workspace/target/*.jar app.jar
RUN chown -R spring:spring ${APP_HOME}
USER spring

EXPOSE 8080
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75 -XX:+UseContainerSupport"
ENV SPRING_PROFILES_ACTIVE=prod

ENTRYPOINT ["sh","-c","java $JAVA_OPTS -Dserver.port=${PORT:-8080} -jar /app/app.jar"]
