# Usa Java 21 oficial (Temurin)
FROM eclipse-temurin:21-jdk

# Crea un directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el JAR construido por Maven (asumiendo que haces 'mvn package' antes)
COPY target/*.jar app.jar

# Define el comando de arranque
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
