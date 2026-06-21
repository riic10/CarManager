# ---- Stage 1: build the executable jar ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

# The pom's maven-toolchains-plugin requires a toolchains.xml declaring a JDK 17
# (temurin). This base image's JDK lives at /opt/java/openjdk.
RUN mkdir -p /root/.m2 && printf '%s\n' \
  '<?xml version="1.0" encoding="UTF-8"?>' \
  '<toolchains>' \
  '  <toolchain>' \
  '    <type>jdk</type>' \
  '    <provides><version>17</version><vendor>temurin</vendor></provides>' \
  '    <configuration><jdkHome>/opt/java/openjdk</jdkHome></configuration>' \
  '  </toolchain>' \
  '</toolchains>' > /root/.m2/toolchains.xml

# Resolve dependencies first so this layer is cached unless pom.xml changes.
COPY pom.xml .
RUN mvn -q dependency:go-offline

# Build the app. Tests are skipped here (they run in CI); the Testcontainers
# integration test needs a Docker daemon, which isn't available inside the build.
COPY src ./src
RUN mvn -q clean package -DskipTests

# ---- Stage 2: minimal runtime image ----
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=build /app/target/car-manager-*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
