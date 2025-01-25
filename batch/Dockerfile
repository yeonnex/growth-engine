FROM openjdk:21

# 빌드된 JAR 파일만 복사
COPY build/libs/*.jar app.jar

# 애플리케이션 실행 명령어
ENTRYPOINT ["java", "-jar", "app.jar"]