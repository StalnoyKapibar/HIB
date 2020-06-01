FROM openjdk:8-jdk-alpine
ARG DEPENDENCY=target/dependency
COPY ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY ${DEPENDENCY}/META-INF /app/META-INF
COPY ${DEPENDENCY}/BOOT-INF/classes /app
COPY img /img
COPY imgtestbook /imgtestbook
ENTRYPOINT ["java","-cp","app:app/lib/*","com.project.ProjectApplication"]